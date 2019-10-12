package com.shengsu.util;

import com.aliyun.oss.OSSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;


/**
 * 阿里云 OSS工具类
 *
 * @author Monkey
 * @version 1.0
 * @date 2017年9月30日下午3:38:09
 */
@Service
public class OssClientUtil {

    public static final Logger logger = LoggerFactory.getLogger(OssClientUtil.class);
    @Value("${oss.endpoint:}")
    private String endpoint;
    @Value("${oss.accessKeyId:}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret:}")
    private String accessKeySecret;
    @Value("${oss.bucketName:}")
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
    public String getUrl(String filedir, String key) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        init();
        URL url = ossClient.generatePresignedUrl(bucketName, filedir + key, expiration);
        destory();
        if (url != null) {
            return url.toString();
        }
        return null;
    }
    /**
     * 销毁
     */
    public void destory() {
        ossClient.shutdown();
    }


}
