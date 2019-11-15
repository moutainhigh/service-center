package com.shengsu.helper.service.impl;


import com.shengsu.helper.service.CodeGeneratorService;
import com.shengsu.mapper.CodeGeneratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 单据流水号产生服务,数据库存储过程方式
 */
@Service("codeGeneratorService")
public class CodeGeneratorServiceImpl implements CodeGeneratorService {


    @Autowired
    private CodeGeneratorMapper codeGeneratorMapper;

    public String generateCode(String prefixCode) {
        Map<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.put("prefixCode", prefixCode);
        parameterMap.put("newCode", "");
        codeGeneratorMapper.getCode(parameterMap);
        return parameterMap.get("newCode");
    }

    public String generateLawcaseCode(String prefixCode) {
        Map<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.put("prefixCode", prefixCode);
        parameterMap.put("newCode", "");
        codeGeneratorMapper.getLawcaseCode(parameterMap);
        return parameterMap.get("newCode");
    }
}
