package com.shengsu.helper.service;


import java.util.List;
import java.util.Map;

public interface OssService{

    String getUrl(String filedir, String key);

    Map<String,String> getUrls(String filedir, List<String> keys);
}
