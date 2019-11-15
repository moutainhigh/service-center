package com.shengsu.helper.service;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.shengsu.helper.entity.SecurityToken;

public interface StsService {

    AssumeRoleResponse assumeRole(String policy,ProtocolType protocolType) throws ClientException;
    SecurityToken getToken();
    SecurityToken getToken(String fileDir);
}
