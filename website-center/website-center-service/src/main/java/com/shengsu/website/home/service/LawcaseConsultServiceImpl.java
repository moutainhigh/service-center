package com.shengsu.website.home.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.entity.SystemDict;
import com.shengsu.helper.service.RedisService;
import com.shengsu.helper.service.SystemDictService;
import com.shengsu.mq.MessageProcessor;
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

import javax.annotation.Resource;
import java.util.*;

import static com.shengsu.website.constant.ConsultConst.*;

@Slf4j
@Service(value = "lawcaseConsultService")
public class LawcaseConsultServiceImpl extends BaseServiceImpl implements LawcaseConsultService,MessageProcessor<JSONObject> {

    @Autowired
    private LawcaseConsultMapper lawcaseConsultMapper;
    @Autowired
    private LawcaseConsultAppendixService lawcaseConsultAppendixService;
    @Resource
    private RedisService redisService;
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
        List<LawcaseConsult> root = lawcaseConsultMapper.listByPage(lawcaseConsult);
        List<String> refIds = new ArrayList<>();
        for(LawcaseConsult consult : root){
            String refId = consult.getConsultId();
            refIds.add(refId);
        }
        Map<String, SystemDict> systemDictMap = systemDictService.mapByDictCode(DICT_CODE_LAW_FIELD);
        List<ConsultDetailsListPo> consultDetailsListPos = LawcaseConsultAppendixUtils.toConsultAppendixDetailsListPo(root,systemDictMap);
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
    public boolean handleMessage(JSONObject jsonObject) {
        log.info("处理消息："+ jsonObject);
        // 修改账户余额 并添加账户余额记录
        String outTradeNo = jsonObject.getString("outTradeNo");
        // 获取电话咨询信息
        String telConsultJson= (String)redisService.get(outTradeNo);
        JSONObject object = JSON.parseObject(telConsultJson);
        String tel =object.getString("tel");
        String lawField =object.getString("lawField");
        // 保存咨询消息
        LawcaseConsult lawcaseConsult = new LawcaseConsult();
        lawcaseConsult.setConsultId(UUID.randomUUID().toString());
        lawcaseConsult.setContact(tel);
        lawcaseConsult.setLawField(lawField);
        lawcaseConsult.setOrigin(ConsultConst.CONSULT_ORIGIN_PAYMENT_CALL_BACK);
        save(lawcaseConsult);
        // 缓存清除
        redisService.delete(outTradeNo);
        return true;
    }
    @Override
    public Class<JSONObject> getClazz() {
        return JSONObject.class;
    }
}
