package com.shengsu.website.home.util;


import com.shengsu.website.home.entity.WheelCenter;
import com.shengsu.website.home.po.WheelCenterListPo;
import com.shengsu.website.home.po.WheelCenterQueryPo;
import com.shengsu.website.home.vo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bell on 2019/9/17.
 */
public class WheelCenterUtils {

   public static WheelCenter toWheelCenter(WheelCenterCreateVo wheelCenterCreateVo){
       if(wheelCenterCreateVo !=null){
           WheelCenter wheelCenter=new WheelCenter();
           wheelCenter.setType(wheelCenterCreateVo.getType());
           wheelCenter.setPictureOssId(wheelCenterCreateVo.getPictureOssId());
           wheelCenter.setUrl(wheelCenterCreateVo.getUrl());
           wheelCenter.setWeight(wheelCenterCreateVo.getWeight());
           return wheelCenter;
       }
       return null;
   }
    public static WheelCenter toWheelCenter(WheelCenterQueryVo wheelCenterQueryVo){
        if(wheelCenterQueryVo !=null){
            WheelCenter wheelCenter=new WheelCenter();
            wheelCenter.setId(wheelCenterQueryVo.getId());
            return wheelCenter;
        }
        return null;
    }
    public static WheelCenter toWheelCenter(WheelCenterUpdateVo wheelCenterUpdateVo){
        if(wheelCenterUpdateVo !=null){
            WheelCenter wheelCenter=new WheelCenter();
            wheelCenter.setId(wheelCenterUpdateVo.getId());
            wheelCenter.setType(wheelCenterUpdateVo.getType());
            wheelCenter.setPictureOssId(wheelCenterUpdateVo.getPictureOssId());
            wheelCenter.setUrl(wheelCenterUpdateVo.getUrl());
            wheelCenter.setWeight(wheelCenterUpdateVo.getWeight());
            return wheelCenter;
        }
        return null;
    }
    public static WheelCenter toWheelCenter(WheelCenterDeleteVo wheelCenterDeleteVo){
        if(wheelCenterDeleteVo !=null){
            WheelCenter wheelCenter=new WheelCenter();
            wheelCenter.setId(wheelCenterDeleteVo.getId());
            return wheelCenter;
        }
        return null;
    }

    public static WheelCenterQueryPo toWheelCenterQueryPo(WheelCenter wheelCenter){
        if(wheelCenter !=null){
            WheelCenterQueryPo wheelCenterQueryPo=new WheelCenterQueryPo();
            wheelCenterQueryPo.setId(wheelCenter.getId());
            wheelCenterQueryPo.setType(wheelCenter.getType());
            wheelCenterQueryPo.setPictureOssId(wheelCenter.getPictureOssId());
            wheelCenterQueryPo.setUrl(wheelCenter.getUrl());
            wheelCenterQueryPo.setWeight(wheelCenter.getWeight());
            return wheelCenterQueryPo;
        }
        return null;
    }
    public static WheelCenter toWheelCenter(WheelCenterListVo wheelCenterListVo){
        if(wheelCenterListVo !=null){
            WheelCenter wheelCenter=new WheelCenter();
            wheelCenter.setType(wheelCenterListVo.getType());
            return wheelCenter;
        }
        return null;
    }

    public static List<WheelCenterListPo> toWheelCenterListPo(List<WheelCenter> wheelCenters){
        if(wheelCenters !=null && !wheelCenters.isEmpty()){
            List<WheelCenterListPo> wheelCenterListPos=new ArrayList<>();
            WheelCenterListPo wheelCenterListPo=null;
            for(WheelCenter wheelCenter:wheelCenters){
                wheelCenterListPo=new WheelCenterListPo();
                wheelCenterListPo.setId(wheelCenter.getId());
                wheelCenterListPo.setType(wheelCenter.getType());
                wheelCenterListPo.setPictureOssId(wheelCenter.getPictureOssId());
                wheelCenterListPo.setWeight(wheelCenter.getWeight());
                wheelCenterListPo.setUrl(wheelCenter.getUrl());
                wheelCenterListPos.add(wheelCenterListPo);
            }
            return wheelCenterListPos;
        }
            return null;
    }
}
