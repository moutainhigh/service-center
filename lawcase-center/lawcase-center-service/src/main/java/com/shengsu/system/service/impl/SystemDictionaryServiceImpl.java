package com.shengsu.system.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.system.entity.SystemDictionary;
import com.shengsu.system.mapper.SystemDictionaryMapper;
import com.shengsu.system.service.SystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("systemDictionaryService")
public class SystemDictionaryServiceImpl extends BaseServiceImpl<SystemDictionary,String> implements SystemDictionaryService {
    @Autowired
    SystemDictionaryMapper systemDictionaryMapper;
    @Override
    public BaseMapper<SystemDictionary, String> getBaseMapper() {
        return systemDictionaryMapper;
    }

    @Override
    public List<SystemDictionary> getManyByDictCodes(List<String> dictCodes) {
        return systemDictionaryMapper.getManyByDictCodes(dictCodes);
    }
}
