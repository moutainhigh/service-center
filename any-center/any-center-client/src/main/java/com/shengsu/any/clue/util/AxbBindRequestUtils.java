package com.shengsu.any.clue.util;

import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.entity.Pns;
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
    public static AxbBindRequest toAxbBindRequest(Pns pns) {
        AxbBindRequest axbBindRequest = new AxbBindRequest();
        axbBindRequest.setTelA(pns.getTela());
        axbBindRequest.setTelB(pns.getTelb());
        axbBindRequest.setTelX(pns.getTelx());
        axbBindRequest.setAreaCode(pns.getAreaCode());
        axbBindRequest.setExpiration(pns.getExpiration());
        axbBindRequest.setRecord(1);
        return axbBindRequest;
    }
}
