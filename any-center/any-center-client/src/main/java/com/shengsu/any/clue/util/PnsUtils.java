package com.shengsu.any.clue.util;

import com.shengsu.any.clue.entity.Pns;
import com.shengsu.helper.entity.AxbBindRequest;
import com.shengsu.helper.entity.BindResponse;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-25 09:19
 **/
public class PnsUtils {
    public static Pns toPns(BindResponse bindResponse, AxbBindRequest axbBindRequest,String clueId){
        Pns pns = new Pns();
        pns.setClueId(clueId);
        pns.setBindId(bindResponse.getData().getBindId());
        pns.setTela(axbBindRequest.getTelA());
        pns.setTelb(axbBindRequest.getTelB());
        pns.setTelx(bindResponse.getData().getTelX());
        pns.setExpiration(axbBindRequest.getExpiration());
        pns.setAreaCode(axbBindRequest.getAreaCode());
        return pns;
    }
}
