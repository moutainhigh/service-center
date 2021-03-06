package com.shengsu.helper.service.impl;

import com.shengsu.app.constant.ResultCode;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.entity.SystemDict;
import com.shengsu.helper.mapper.SystemDictMapper;
import com.shengsu.helper.service.SystemDictService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.util.SystemDictUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public ResultBean listDicts(List<SystemDict> dictionaries) {
        List<String> dictCodes = SystemDictUtil.toDictCodes(dictionaries);
        List<SystemDict> systemDictList = systemDictMapper.getMany(dictCodes);
        Map<String, Object> map = SystemDictUtil.toSystemDictListMap(systemDictList);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
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
        Map<String, Object> systeMap = SystemDictUtil.toParamMap(dictCode, displayValue);
        return systemDictMapper.getOneByDisplayValue(systeMap);
    }

    @Override
    public List<SystemDict> listByDictCode(String dictCode) {
        return systemDictMapper.listByDictCode(dictCode);
    }
    @Override
    public Map<String,SystemDict> mapByDictCode(String dictCode) {
        List<SystemDict> systemDicts = systemDictMapper.listByDictCode(dictCode);
        return SystemDictUtil.toSystemDictMap(systemDicts);
    }
}
