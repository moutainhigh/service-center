package com.shengsu.website.market.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.website.market.entity.LawDoc;
import com.shengsu.website.market.mapper.LawDocMapper;
import com.shengsu.website.market.service.LawDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-07-13 17:03
 **/
@Service("lawDocService")
public class LawDocServiceImpl extends BaseServiceImpl<LawDoc, String> implements LawDocService {
    @Autowired
    private LawDocMapper lawDocMapper;
    @Override
    public BaseMapper<LawDoc, String> getBaseMapper() {
        return lawDocMapper;
    }
}
