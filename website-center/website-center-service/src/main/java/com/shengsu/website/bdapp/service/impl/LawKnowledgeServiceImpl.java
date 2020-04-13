package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.constant.CommonConst;
import com.shengsu.result.ResultBean;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.app.util.ResultUtil;
import com.shengsu.website.bdapp.entity.LawKnowledge;
import com.shengsu.website.bdapp.mapper.LawKnowledgeMapper;
import com.shengsu.website.bdapp.po.LawKnowledgeListPagePo;
import com.shengsu.website.bdapp.service.LawKnowledgeService;
import com.shengsu.website.bdapp.util.LawKnowledgeUtils;
import com.shengsu.website.bdapp.vo.LawKnowledgeListPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-10 18:03
 **/
@Service("lawKnowledgeService")
public class LawKnowledgeServiceImpl extends BaseServiceImpl<LawKnowledge, String> implements LawKnowledgeService {
    @Autowired
    private LawKnowledgeMapper lawKnowledgeMapper;
    @Override
    public BaseMapper<LawKnowledge, String> getBaseMapper() {
        return lawKnowledgeMapper;
    }

    @Override
    public ResultBean listPage(LawKnowledgeListPageVo lawKnowledgeListPageVo) {
        LawKnowledge lawKnowledge = LawKnowledgeUtils.toLawKnowledge(lawKnowledgeListPageVo);
        Integer count = lawKnowledgeMapper.countAll(lawKnowledge);
        Map<String, Object> resultMap = new HashMap<>();
        if (count > 0) {
            List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.listByPage(lawKnowledge);
            if(null == lawKnowledges || lawKnowledges.size() == 0){
                return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
            }
            List<LawKnowledgeListPagePo> lawKnowledgeListPagePos = LawKnowledgeUtils.toLawKnowledgeListPagePos(lawKnowledges);
            resultMap.put(CommonConst.ROOT, lawKnowledgeListPagePos);
            resultMap.put(CommonConst.TOTAL_COUNT, count);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }
}
