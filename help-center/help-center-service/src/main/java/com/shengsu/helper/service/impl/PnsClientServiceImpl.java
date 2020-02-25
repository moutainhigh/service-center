package com.shengsu.helper.service.impl;

import com.baidubce.BceClientConfiguration;
import com.baidubce.Protocol;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.client.PnsClient;
import com.shengsu.helper.entity.AxBindRequest;
import com.shengsu.helper.entity.AxbBindRequest;
import com.shengsu.helper.entity.BindResponse;
import com.shengsu.helper.service.PnsClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 使用DEMO，需要替换自己的AK以及SK
 *
 * BceClientConfiguration 相关配置
 * UserAgent用户代理，指HTTP的User-Agent头
 * Protocol	连接协议类型
 * ProxyDomain	访问NTLM验证的代理服务器的Windows域名
 * ProxyHost	代理服务器主机地址
 * ProxyPort	代理服务器端口
 * ProxyUsername	代理服务器验证的用户名
 * ProxyPassword	代理服务器验证的密码
 * ProxyPreemptiveAuthenticationEnabled	是否设置用户代理认证
 * ProxyWorkstation	NTLM代理服务器的Windows工作站名称
 * LocalAddress	本地地址
 * ConnectionTimeoutInMillis	建立连接的超时时间（单位：毫秒）
 * SocketTimeoutInMillis	通过打开的连接传输数据的超时时间（单位：毫秒）
 * MaxConnections	允许打开的最大HTTP连接数
 * RetryPolicy	连接重试策略
 * SocketBufferSizeInBytes	Socket缓冲区大小
 * StreamBufferSize	流文件缓冲区大小
 */
@Service("pnsClientService")
public class PnsClientServiceImpl implements PnsClientService {

    private static final String ENDPOINT_HOST = "pns.baidubce.com";

    @Value("${pns.accessKeyId}")
    String accessKeyId; // 用户的Access Key ID
    @Value("${pns.accessKeySecret}")
    String accessKeySecret; // 用户的Secret Access Key

    public BindResponse sendAxbBindRequest(AxbBindRequest axbBindRequest) {
        BceClientConfiguration bceClientConfiguration = new BceClientConfiguration();
        bceClientConfiguration.setProtocol(Protocol.HTTP); // 推荐使用HTTPS
        bceClientConfiguration.setEndpoint(ENDPOINT_HOST);
        bceClientConfiguration.setCredentials(new DefaultBceCredentials(accessKeyId, accessKeySecret));
        PnsClient pnsClient = new PnsClient(bceClientConfiguration);

        return pnsClient.createAxbBindRequest(axbBindRequest);
    }

    @Override
    public BindResponse sendAxBindRequest(AxBindRequest axBindRequest) {

        BceClientConfiguration bceClientConfiguration = new BceClientConfiguration();
        bceClientConfiguration.setProtocol(Protocol.HTTPS); // 推荐使用HTTPS
        bceClientConfiguration.setEndpoint(ENDPOINT_HOST);
        bceClientConfiguration.setCredentials(new DefaultBceCredentials(accessKeyId, accessKeySecret));
        PnsClient pnsClient = new PnsClient(bceClientConfiguration);
        return pnsClient.createAxBindRequest(axBindRequest);
    }

}
