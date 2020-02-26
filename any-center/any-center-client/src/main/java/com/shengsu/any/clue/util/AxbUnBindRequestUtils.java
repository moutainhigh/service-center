package com.shengsu.any.clue.util;

import com.shengsu.any.clue.vo.AxbUnBindRequestVo;
import com.shengsu.helper.entity.AxbUnBindRequest;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-26 18:13
 **/
public class AxbUnBindRequestUtils {
    public static AxbUnBindRequest toAxbUnBindRequest(AxbUnBindRequestVo axbUnBindRequestVo) {
        AxbUnBindRequest axbUnBindRequest = new AxbUnBindRequest();
        axbUnBindRequest.setBindId(axbUnBindRequestVo.getBindId());
        return axbUnBindRequest;
    }
}
