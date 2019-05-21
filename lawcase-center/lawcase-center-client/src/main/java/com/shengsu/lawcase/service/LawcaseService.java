package com.shengsu.lawcase.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.lawcase.entity.Lawcase;

import java.util.List;

/**
 * Created by zxh on 2019/5/8.
 */
public interface LawcaseService extends BaseService<Lawcase,String>{
    List<Lawcase> getInvestLawcaseList();
}
