package com.shengsu.helper.service.impl;


import com.aliyun.oss.OSSClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.shengsu.helper.service.OssService;

import java.net.URL;
import java.util.Date;

/**
 * Created by zyc on 2019/10/12.
 */
@Service(value = "ossService")
public class OssServiceImpl implements OssService{

    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucketName}")
    private String bucketName;

    private OSSClient ossClient;

    /**
     * 初始化
     */
    public void init() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 获得url链接(指定路径）
     *
     * @param key
     * @return
     */
    @Override
    public String getUrl(String filedir, String key) {
        if(StringUtils.isBlank(key)){
            return null;
        }

        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        init();
        URL url = ossClient.generatePresignedUrl(bucketName, filedir + key, expiration);
        destory();
        if (url == null) {
            return null;
        }

        return url.toString();

    }
    /**
     * 销毁
     */
    public void destory() {
        ossClient.shutdown();
    }
}
