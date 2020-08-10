package com.shengsu.website.home.service;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.entity.SystemDict;
import com.shengsu.helper.service.SystemDictService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.util.StringUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.constant.ConsultConst;
import com.shengsu.website.home.entity.LawcaseConsult;
import com.shengsu.website.home.entity.LawcaseConsultAppendix;
import com.shengsu.website.home.mapper.LawcaseConsultMapper;
import com.shengsu.website.home.po.ConsultAppendixDetailsPo;
import com.shengsu.website.home.po.ConsultDetailsListPo;
import com.shengsu.website.home.util.LawcaseConsultAppendixUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.shengsu.website.constant.ConsultConst.DICT_CODE_LAW_FIELD;
import static com.shengsu.website.constant.ConsultConst.LAWCASE_CONSULT_CLUE;

@Slf4j
@Service(value = "lawcaseConsultService")
public class LawcaseConsultServiceImpl extends BaseServiceImpl implements LawcaseConsultService{

    @Autowired
    private LawcaseConsultMapper lawcaseConsultMapper;
    @Autowired
    private LawcaseConsultAppendixService lawcaseConsultAppendixService;
    @Autowired
    private SystemDictService systemDictService;

    @Override
    public BaseMapper<LawcaseConsult, String> getBaseMapper() {
        return lawcaseConsultMapper;
    }

    /**
     * 构造咨询内容
     *
     * @param
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(LawcaseConsult lawcaseConsult) {
        lawcaseConsult.setConsultId(UUID.randomUUID().toString());
        save(lawcaseConsult);

        //存储上传附件
        List<LawcaseConsultAppendix> appendixList = lawcaseConsult.getAppendixList();
        LawcaseConsultAppendixUtils.toLawcaseConsultAppendixs(lawcaseConsult.getConsultId(), lawcaseConsult.getConsultor(), LAWCASE_CONSULT_CLUE, appendixList);
        lawcaseConsultAppendixService.batchSave(appendixList);
    }

    /**
     * @return com.shengsu.result.ResultBean
     * @Author Bell
     * @Description 分页查询咨询列表
     * @Date 2020/3/19
     * @Param [lawcaseConsult]
     **/
    @Override
    public ResultBean listByPage(LawcaseConsult lawcaseConsult) {
        String search = lawcaseConsult.getSearch();
        search = StringUtil.ToLikeStr(search);
        lawcaseConsult.setSearch(search);
        List<LawcaseConsult> lawcaseConsults = lawcaseConsultMapper.listByPage(lawcaseConsult);
        List<String> refIds = lawcaseConsults.stream().map(LawcaseConsult::getConsultId).collect(Collectors.toList());
        Map<String, SystemDict> systemDictMap = systemDictService.mapByDictCode(DICT_CODE_LAW_FIELD);
        List<ConsultDetailsListPo> consultDetailsListPos = LawcaseConsultAppendixUtils.toConsultAppendixDetailsListPo(lawcaseConsults,systemDictMap);
        int totalCount = lawcaseConsultMapper.countAll(lawcaseConsult);
        if(!refIds.isEmpty()) {
            ResultBean<List<ConsultAppendixDetailsPo>> appendixResult = lawcaseConsultAppendixService.getDetailsListByPage(refIds);
            if (appendixResult.isSuccess()) {
                List<ConsultAppendixDetailsPo> appendixList = appendixResult.getBody();
                Map<String,List<ConsultAppendixDetailsPo>> appendixGroupList = appendixList.stream().collect(Collectors.groupingBy(ConsultAppendixDetailsPo::getRefId));
                for (ConsultDetailsListPo consultDetailsListPo : consultDetailsListPos) {
                    consultDetailsListPo.setAppendixList(appendixGroupList.get(consultDetailsListPo.getConsultId()));
                }
            }
        }
        return ResultUtil.formPageResult(true, ResultCode.SUCCESS, consultDetailsListPos,totalCount);
    }

    /**
     * @return com.shengsu.result.ResultBean
     * @Author Bell
     * @Description 查询咨询列表
     * @Date 2020/3/19
     * @Param [lawcaseConsult]
     **/
    @Override
    public ResultBean listByModel(LawcaseConsult lawcaseConsult) {
        List<LawcaseConsult> root = lawcaseConsultMapper.listByModel(lawcaseConsult);
        List<String> refIds = new ArrayList<>();
        for(LawcaseConsult consult : root){
            String refId = consult.getConsultId();
            refIds.add(refId);
        }
        Map<String, SystemDict> systemDictMap = systemDictService.mapByDictCode(DICT_CODE_LAW_FIELD);
        List<ConsultDetailsListPo> consultDetailsListPos = LawcaseConsultAppendixUtils.toConsultAppendixDetailsListPo(root,systemDictMap);
        if(!refIds.isEmpty()) {
            ResultBean<List<ConsultAppendixDetailsPo>> appendixResult = lawcaseConsultAppendixService.getDetailsListByPage(refIds);
            if (appendixResult.isSuccess()) {
                List<ConsultAppendixDetailsPo> appendixList = appendixResult.getBody();
                for (ConsultDetailsListPo consultDetailsListPo : consultDetailsListPos) {
                    List<ConsultAppendixDetailsPo> appendixDetailsList = new ArrayList<>();
                    for (ConsultAppendixDetailsPo consultAppendixDetailsPo : appendixList) {
                        if (consultDetailsListPo.getConsultId().equals(consultAppendixDetailsPo.getRefId())) {
                            appendixDetailsList.add(consultAppendixDetailsPo);
                            consultDetailsListPo.setAppendixList(appendixDetailsList);
                        }
                    }
                    consultDetailsListPo.setAppendixList(appendixDetailsList);
                }
            }
        }
        return ResultUtil.formRootResult(true, ResultCode.SUCCESS, consultDetailsListPos);
    }

    @Override
    public void saveTelConsultData(String tel, String lawField,String source) {
        // 保存咨询消息
        LawcaseConsult lawcaseConsult = new LawcaseConsult();
        lawcaseConsult.setConsultId(UUID.randomUUID().toString());
        lawcaseConsult.setContact(tel);
        lawcaseConsult.setLawField(lawField);
        lawcaseConsult.setSource(source);
        lawcaseConsult.setOrigin(ConsultConst.CONSULT_ORIGIN_PAYMENT_CALL_BACK);
        save(lawcaseConsult);
    }

    public static void main(String[] args) {
        List<LawcaseConsult> list = new ArrayList();
        LawcaseConsult lawcaseConsult = new LawcaseConsult();
        lawcaseConsult.setConsultId("1");
        list.add(lawcaseConsult);
        List<String> resutl = list.stream().map(LawcaseConsult::getConsultId).collect(Collectors.toList());
        for(String s:resutl){
            System.out.println(s);
        }
    }
}
