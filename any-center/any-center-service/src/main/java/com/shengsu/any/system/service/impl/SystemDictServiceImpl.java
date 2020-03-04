package com.shengsu.any.system.service.impl;

import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.system.entity.SystemDict;
import com.shengsu.any.system.mapper.SystemDictMapper;
import com.shengsu.any.system.service.SystemDictService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-13 09:59
 **/
public class SystemDictServiceImpl extends BaseServiceImpl<SystemDict, String> implements SystemDictService {
    @Autowired
    private SystemDictMapper systemDictMapper;
    @Override
    public BaseMapper<SystemDict, String> getBaseMapper() {
        return systemDictMapper;
    }

    @Override
    public ResultBean listDicts(List<SystemDict> systemDicts) {
        if (systemDicts.isEmpty())
            return null;
        List<String> dictCodes = new ArrayList<>();
        for (SystemDict systemDict : systemDicts) {
            dictCodes.add(systemDict.getDictCode());
        }
        List<SystemDict> systemDictList = systemDictMapper.getMany(dictCodes);
        Map<String, Object> map = new HashMap<String, Object>();
        //遍历包装
        Map<String, List<SystemDict>> systemDictMap = makeSystemDict(systemDicts, systemDictList);
        map.put("root", systemDictMap);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }
    /**
    * @Description: 组装字典
    * @Param: * @Param systemDicts: 
 * @Param systemDictList: 
    * @Return: * @return: java.util.Map<java.lang.String,java.util.List<com.shengsu.any.system.entity.SystemDict>>
    * @date: 
    */
    public Map<String, List<SystemDict>> makeSystemDict(List<SystemDict> systemDicts, List<SystemDict> systemDictList) {
        Map<String, List<SystemDict>> listMap = new HashMap<String, List<SystemDict>>();
        for (SystemDict systemDict : systemDicts) {
            List<SystemDict> systemDictsList = new ArrayList<>();
            for (SystemDict dict : systemDictList) {
                if (systemDict.getDictCode().equals(dict.getDictCode())) {
                    systemDictsList.add(dict);
                }
            }
            listMap.put(systemDict.getDictCode(), systemDictsList);
        }
        return listMap;
    }
    public List<SystemDict> getManyByDisplayValue(Map<String, Object> map) {
        return systemDictMapper.getManyByDisplayValue(map);
    }
    public SystemDict getOneByDisplayValue(String dictCode,String displayValue){
        Map<String, Object> systeMap = new HashMap<>();
        systeMap.put("dictCode", dictCode);
        systeMap.put("displayValue", displayValue);
        return systemDictMapper.getOneByDisplayValue(systeMap);
    }
    public List<SystemDict> listByDictCode(String dictCode) {
        return systemDictMapper.listByDictCode(dictCode);
    }

}
