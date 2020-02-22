package com.shengsu.helper.service;


import com.shengsu.helper.entity.AxBindRequest;
import com.shengsu.helper.entity.AxbBindRequest;
import com.shengsu.helper.entity.BindResponse;

/**
 * Created by zhanghao on 2019/12/3
 */
public interface PnsClientService {
    BindResponse sendAxbBindRequest(AxbBindRequest axbBindRequest);

    BindResponse sendAxBindRequest(AxBindRequest axbBindRequest);
}
