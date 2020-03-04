package com.shengsu.helper.service.impl;

import com.shengsu.app.constant.ResultCode;
import com.shengsu.app.util.ResultUtil;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.entity.SystemDict;
import com.shengsu.helper.mapper.SystemDictMapper;
import com.shengsu.helper.service.SystemDictService;
import com.shengsu.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("systemDictService")
public class SystemDictServiceImpl extends BaseServiceImpl<SystemDict, String> implements SystemDictService {
    @Autowired
    SystemDictMapper systemDictMapper;

    @Override
    public BaseMapper<SystemDict, String> getBaseMapper() {
        return systemDictMapper;
    }

    @Override
    public List<SystemDict> getManyByDictCodes(List<String> dictCodes) {
        return systemDictMapper.getManyByDictCodes(dictCodes);
    }

    @Override
    public int dictExistCheck(SystemDict systemDict) {
        return systemDictMapper.dictExistCheck(systemDict);
    }

    @Override
    public void create(SystemDict systemDict) {
        systemDict.setDictId(UUID.randomUUID().toString());
        save(systemDict);
    }

    @Override
    public int dictExistCheckExceptSelf(SystemDict systemDict) {
        return systemDictMapper.dictExistCheckExceptSelf(systemDict);
    }

    /**
     * @return java.util.List<com.shengsu.helper.entity.SystemDict>
     * @Author Bell
     * @Description 获取字典列表
     * @Date 2020/3/4
     * @Param [Dictionaries]
     **/
    @Override
    public ResultBean listDicts(List<SystemDict> Dictionaries) {
        if (Dictionaries.isEmpty())
            return null;
        List<String> dictCodes = new ArrayList<>();
        for (SystemDict systemDict : Dictionaries) {
            dictCodes.add(systemDict.getDictCode());
        }
        List<SystemDict> systemDictList = systemDictMapper.getMany(dictCodes);
        Map<String, Object> map = new HashMap<String, Object>();
        //遍历包装
        Map<String, List<SystemDict>> systemDictMap = makeSystemDict(systemDictList, systemDictList);
        map.put("root", systemDictMap);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }

    /**
     * 包装字典
     *
     * @param Dicts
     * @param DictList
     * @return
     */
    @Override
    public Map<String, List<SystemDict>> makeSystemDict(List<SystemDict> dicts, List<SystemDict> dictList) {
        Map<String, List<SystemDict>> listMap = new HashMap<String, List<SystemDict>>();
        for (SystemDict systemDict : dicts) {
            List<SystemDict> systemDictsList = new ArrayList<>();
            for (SystemDict dict : dictList) {
                if (systemDict.getDictCode().equals(dict.getDictCode())) {
                    systemDictsList.add(dict);
                }
            }
            listMap.put(systemDict.getDictCode(), systemDictsList);
        }
        return listMap;
    }

    /**
     * @return java.util.List<com.shengsu.helper.entity.SystemDict>
     * @Author Bell
     * @Description 根据案件类型值获取字典表中的名称 返回多个
     * @Date 2020/3/4
     * @Param [map]
     **/
    @Override
    public List<SystemDict> getManyByDisplayValue(Map<String, Object> map) {
        return systemDictMapper.getManyByDisplayValue(map);
    }

    /**
     * @return com.shengsu.helper.entity.SystemDict
     * @Author Bell
     * @Description 根据案件类型值获取字典表中的名称 返回单个
     * @Date 2020/3/4
     * @Param [dictCode, displayValue]
     **/
    @Override
    public SystemDict getOneByDisplayValue(String dictCode, String displayValue) {
        Map<String, Object> systeMap = new HashMap<>();
        systeMap.put("dictCode", dictCode);
        systeMap.put("displayValue", displayValue);
        return systemDictMapper.getOneByDisplayValue(systeMap);
    }

    @Override
    public List<SystemDict> listByDictCode(String dictCode) {
        return systemDictMapper.listByDictCode(dictCode);
    }
}
