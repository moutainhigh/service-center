package com.shengsu.helper.service;


import com.shengsu.helper.entity.*;

/**
 * Created by zhanghao on 2019/12/3
 */
public interface PnsClientService {
    BindResponse sendAxbBindRequest(AxbBindRequest axbBindRequest);

    BindResponse sendAxBindRequest(AxBindRequest axbBindRequest);

    BindResponse sendAxbUnBindRequest(AxbUnBindRequest axbUnBindRequest);
}
