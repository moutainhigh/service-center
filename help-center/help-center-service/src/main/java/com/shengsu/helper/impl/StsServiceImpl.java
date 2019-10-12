package com.shengsu.helper.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.shengsu.helper.entity.SecurityToken;
import com.shengsu.helper.service.StsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by zyc on 2019/10/12.
 */
@Service(value = "stsService")
public class StsServiceImpl implements StsService {
    // 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
    public static final String REGION_CN_HANGZHOU = "cn-hangzhou";
    // 当前 STS API 版本
    public static final String STS_API_VERSION = "2015-04-01";
    // token过期时间(s)
    public static final long EXPIRE_TIME = 1*60*60;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucketName}")
    private String bucketName;
    @Value("${sts.roleArn}")
    private String roleArn;
    @Value("${sts.roleSessionName}")
    private String roleSessionName;

    /**
     * 请求阿里云STS服务
     * @param policy
     * @param protocolType
     * @return
     * @throws ClientException
     */
    @Override
    public AssumeRoleResponse assumeRole(String policy, ProtocolType protocolType) throws ClientException {
        try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(STS_API_VERSION);
            request.setMethod(MethodType.POST);
            request.setProtocol(protocolType);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setDurationSeconds(EXPIRE_TIME);
            request.setPolicy(policy);
            // 发起请求，并得到response
            final AssumeRoleResponse response = client.getAcsResponse(request);
            return response;
        } catch (ClientException e) {
            throw e;
        }
    }


    /**
     * 获取阿里云资源访问临时token
     * @return
     */
    @Override
    public SecurityToken getToken() {
        //需要在RAM控制台获取，此时要给子账号权限，并建立一个角色，把这个角色赋给子账户，这个角色会有一串值，就是rolearn要填的　　　　　　　　　　
        //记得角色的权限，子账户的权限要分配好，不然会报错
        //临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        ProtocolType protocolType = ProtocolType.HTTPS;
        SecurityToken securityToken = null;
        try {
            AssumeRoleResponse response = assumeRole( null, protocolType);
            securityToken = new SecurityToken();
            securityToken.setAccessKeyId(response.getCredentials().getAccessKeyId());
            securityToken.setAccessKeySecret(response.getCredentials().getAccessKeySecret());
            securityToken.setStsToken(response.getCredentials().getSecurityToken());
            securityToken.setBucket(bucketName);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return securityToken;
    }
}
