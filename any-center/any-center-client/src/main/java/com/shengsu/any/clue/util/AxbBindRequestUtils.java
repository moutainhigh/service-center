package com.shengsu.any.clue.util;

import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.user.entity.User;
import com.shengsu.helper.entity.AxbBindRequest;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-27 19:06
 **/
public class AxbBindRequestUtils {
    public static AxbBindRequest toAxbBindRequest(Clue clue, User lawyer, String areaCode, Integer expireTimeSecond) {
        AxbBindRequest axbBindRequest = new AxbBindRequest();
        axbBindRequest.setTelA(clue.getTel());
        axbBindRequest.setTelB(lawyer.getTel());
        axbBindRequest.setAreaCode(areaCode);
        axbBindRequest.setExpiration(expireTimeSecond);
        axbBindRequest.setRecord(1);
        return axbBindRequest;
    }
}
