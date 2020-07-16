package com.shengsu.website.mq.service;

import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-07-16 11:49
 **/
public abstract class BaseElasticsearchService {

    protected abstract void index(Object indexObject);

    protected abstract void update(Map<String,Object> source, String id);

    protected abstract void delete(String id);

    protected abstract void deleteIndex();
}
