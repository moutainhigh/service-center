package com.shengsu.website.market.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.market.entity.SystemConfig;

public interface SystemConfigService extends BaseService<SystemConfig,String> {
    ResultBean selectConsultFeed();
}
