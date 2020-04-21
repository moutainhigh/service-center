package com.shengsu.website.home.service;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import com.shengsu.util.StringUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.app.util.ResultUtil;
import com.shengsu.website.home.entity.LawcaseConsult;
import com.shengsu.website.home.entity.LawcaseConsultAppendix;
import com.shengsu.website.home.mapper.LawcaseConsultMapper;
import com.shengsu.website.home.po.ConsultDetailsListPo;
import com.shengsu.website.home.po.ConsultAppendixDetailsPo;
import com.shengsu.website.home.util.LawcaseConsultAppendixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.shengsu.website.constant.ConsultConst.LAWCASE_CONSULT_CLUE;


@Service(value = "lawcaseConsultService")
public class LawcaseConsultServiceImpl extends BaseServiceImpl implements LawcaseConsultService {

    @Autowired
    private LawcaseConsultMapper lawcaseConsultMapper;
    @Autowired
    private LawcaseConsultAppendixService lawcaseConsultAppendixService;

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
        List<LawcaseConsult> root = lawcaseConsultMapper.listByPage(lawcaseConsult);
        List<String> refIds = new ArrayList<>();
        for(LawcaseConsult consult : root){
            String refId = consult.getConsultId();
            refIds.add(refId);
        }
        List<ConsultDetailsListPo> consultDetailsListPos = LawcaseConsultAppendixUtils.toConsultAppendixDetailsListPo(root);
        int totalCount = lawcaseConsultMapper.countAll(lawcaseConsult);
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
        List<ConsultDetailsListPo> consultDetailsListPos = LawcaseConsultAppendixUtils.toConsultAppendixDetailsListPo(root);
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
}