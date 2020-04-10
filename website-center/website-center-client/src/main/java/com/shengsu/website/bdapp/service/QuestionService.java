package com.shengsu.website.bdapp.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.bdapp.entity.Question;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-09 17:30
 **/
public interface QuestionService extends BaseService {
    ResultBean create(Question question);
}
