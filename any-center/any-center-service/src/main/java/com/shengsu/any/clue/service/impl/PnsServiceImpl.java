package com.shengsu.any.clue.service.impl;

import com.shengsu.any.clue.entity.Pns;
import com.shengsu.any.clue.mapper.PnsMapper;
import com.shengsu.any.clue.service.PnsService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-24 20:00
 **/
@Service("pnsService")
public class PnsServiceImpl extends BaseServiceImpl<Pns, String> implements PnsService {
    @Autowired
    private PnsMapper pnsMapper;
    @Override
    public BaseMapper<Pns, String> getBaseMapper() {
        return pnsMapper;
    }
}
