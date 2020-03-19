package com.shengsu.website.consult.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import com.shengsu.util.StringUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.app.util.ResultUtil;
import com.shengsu.website.consult.entity.LawcaseConsult;
import com.shengsu.website.consult.mapper.LawcaseConsultMapper;
import com.shengsu.website.consult.service.LawcaseConsultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service(value = "lawcaseConsultService")
public class LawcaseConsultServiceImpl extends BaseServiceImpl implements LawcaseConsultService {

    @Autowired
    private LawcaseConsultMapper lawcaseConsultMapper;

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
    public void create(LawcaseConsult lawcaseConsult) {
        lawcaseConsult.setConsultId(UUID.randomUUID().toString());
        save(lawcaseConsult);
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
        int totalCount = lawcaseConsultMapper.countAll(lawcaseConsult);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("root", root);
        map.put("totalCount", totalCount);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
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
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("root", root);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }
}
