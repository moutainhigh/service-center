package com.shengsu.website.consult.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import com.shengsu.util.StringUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.app.util.ResultUtil;
import com.shengsu.website.consult.entity.LawcaseConsult;
import com.shengsu.website.consult.entity.LawcaseConsultAppendix;
import com.shengsu.website.consult.mapper.LawcaseConsultMapper;
import com.shengsu.website.consult.po.ConsultAppendixDetailsListPo;
import com.shengsu.website.consult.po.ConsultAppendixDetailsPo;
import com.shengsu.website.consult.service.LawcaseConsultAppendixService;
import com.shengsu.website.consult.service.LawcaseConsultService;
import com.shengsu.website.consult.util.LawcaseConsultAppendixUtils;
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
        LawcaseConsultAppendixUtils.toLawcaseFinanceAppendixs(lawcaseConsult.getConsultId(), lawcaseConsult.getConsultor(), LAWCASE_CONSULT_CLUE, appendixList);
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
        List<ConsultAppendixDetailsListPo> consultAppendixDetailsListPos = LawcaseConsultAppendixUtils.toConsultAppendixDetailsListPo(root);
        int totalCount = lawcaseConsultMapper.countAll(lawcaseConsult);
        ResultBean<List<ConsultAppendixDetailsPo>> appendixResult = lawcaseConsultAppendixService.getDetailsListByPage(refIds);
        if (appendixResult.isSuccess()) {
            List<ConsultAppendixDetailsPo> appendixList = appendixResult.getBody();
            for(ConsultAppendixDetailsListPo consultAppendixDetailsListPo : consultAppendixDetailsListPos){
                List<ConsultAppendixDetailsPo> appendixDetailsList = new ArrayList<>();
                for(ConsultAppendixDetailsPo consultAppendixDetailsPo : appendixList){
                    if(consultAppendixDetailsListPo.getConsultId().equals(consultAppendixDetailsPo.getRefId())){
                        appendixDetailsList.add(consultAppendixDetailsPo);
                        consultAppendixDetailsListPo.setAppendixList(appendixDetailsList);
                    }
                }
                consultAppendixDetailsListPo.setAppendixList(appendixDetailsList);
            }
        }
        return ResultUtil.formPageResult(true, ResultCode.SUCCESS,consultAppendixDetailsListPos,totalCount);
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
        List<ConsultAppendixDetailsListPo> consultAppendixDetailsListPos = LawcaseConsultAppendixUtils.toConsultAppendixDetailsListPo(root);
        ResultBean<List<ConsultAppendixDetailsPo>> appendixResult = lawcaseConsultAppendixService.getDetailsListByPage(refIds);
        if (appendixResult.isSuccess()) {
            List<ConsultAppendixDetailsPo> appendixList = appendixResult.getBody();
            for(ConsultAppendixDetailsListPo consultAppendixDetailsListPo : consultAppendixDetailsListPos){
                List<ConsultAppendixDetailsPo> appendixDetailsList = new ArrayList<>();
                for(ConsultAppendixDetailsPo consultAppendixDetailsPo : appendixList){
                    if(consultAppendixDetailsListPo.getConsultId().equals(consultAppendixDetailsPo.getRefId())){
                        appendixDetailsList.add(consultAppendixDetailsPo);
                        consultAppendixDetailsListPo.setAppendixList(appendixDetailsList);
                    }
                }
                consultAppendixDetailsListPo.setAppendixList(appendixDetailsList);
            }
        }
        return ResultUtil.formRootResult(true, ResultCode.SUCCESS, consultAppendixDetailsListPos);
    }
}
