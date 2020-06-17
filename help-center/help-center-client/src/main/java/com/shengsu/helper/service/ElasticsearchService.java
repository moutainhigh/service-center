package com.shengsu.helper.service;

import com.shengsu.result.ResultBean;

public interface ElasticsearchService {
    void index(Object indexObject);
    ResultBean searchByPage(int page,int pageSize,String search,String sortField);
}
