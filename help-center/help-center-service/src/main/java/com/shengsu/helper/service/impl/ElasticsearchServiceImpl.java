package com.shengsu.helper.service.impl;


import com.shengsu.helper.service.ElasticsearchService;
import com.shengsu.result.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

/**
 * Created by zyc on 2019/10/12.
 */
@Slf4j
@Service(value = "elasticsearchService")
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void index(Object indexObject) {
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(indexObject).build();
        elasticsearchTemplate.index(indexQuery);
    }

    @Override
    public ResultBean searchByPage(int page, int pageSize, String search,String sortField) {
        //TODO
        return null;
    }
}
