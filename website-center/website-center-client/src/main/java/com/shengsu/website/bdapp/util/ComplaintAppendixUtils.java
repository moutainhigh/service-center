package com.shengsu.website.bdapp.util;

import com.shengsu.website.bdapp.entity.ComplaintAppendix;
import com.shengsu.website.bdapp.po.ComplaintAppendixSimplePo;
import com.shengsu.website.bdapp.vo.ComplaintAppendixDetailsVo;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-16 09:28
 **/
public class ComplaintAppendixUtils {
    public static List<ComplaintAppendix> toComplaintAppendices(String complaintId, List<ComplaintAppendix> appendixList) {
        if (StringUtils.isNotBlank(complaintId) && appendixList != null) {
            for (ComplaintAppendix complaintAppendix : appendixList) {
                complaintAppendix.setAppendixId(UUID.randomUUID().toString());
                complaintAppendix.setComplaintId(complaintId);
                Date uploadTime = complaintAppendix.getUploadTime();
                complaintAppendix.setUploadTime(uploadTime==null?new Date():uploadTime);
                complaintAppendix.setIdCardFlag(complaintAppendix.getIdCardFlag()==null?"0":complaintAppendix.getIdCardFlag());
                complaintAppendix.setOrtFlag(complaintAppendix.getOrtFlag()==null?"0":complaintAppendix.getOrtFlag());
                complaintAppendix.setEnterpriseFlag(complaintAppendix.getEnterpriseFlag()==null?"0":complaintAppendix.getEnterpriseFlag());
                complaintAppendix.setTrademarkFlag(complaintAppendix.getTrademarkFlag()==null?"0":complaintAppendix.getTrademarkFlag());
            }
        }
        return null;
    }
    public static ComplaintAppendix toComplaintAppendix(ComplaintAppendixDetailsVo detailsVo) {
        if (detailsVo != null) {
            ComplaintAppendix complaintAppendix = new ComplaintAppendix();
            complaintAppendix.setComplaintId(detailsVo.getComplaintId());
            complaintAppendix.setIdCardFlag(detailsVo.getIdCardFlag()==null?"0":detailsVo.getIdCardFlag());
            complaintAppendix.setOrtFlag(detailsVo.getOrtFlag()==null?"0":detailsVo.getOrtFlag());
            complaintAppendix.setEnterpriseFlag(detailsVo.getEnterpriseFlag()==null?"0":detailsVo.getEnterpriseFlag());
            complaintAppendix.setTrademarkFlag(detailsVo.getTrademarkFlag()==null?"0":detailsVo.getTrademarkFlag());
            return complaintAppendix;
        }
        return null;
    }

    public static List<ComplaintAppendixSimplePo> toComplaintAppendixSimplePos(List<ComplaintAppendix> complaintAppendices,Map<String, String> ossIdMap) {
        if (complaintAppendices != null && !complaintAppendices.isEmpty()) {
            List<ComplaintAppendixSimplePo> complaintAppendixSimplePos = new ArrayList<>();
            for (ComplaintAppendix complaintAppendix : complaintAppendices){
                complaintAppendixSimplePos.add(toComplaintAppendixSimplePo(complaintAppendix,ossIdMap));
            }
            return complaintAppendixSimplePos;
        }
        return null;
    }

    private static ComplaintAppendixSimplePo toComplaintAppendixSimplePo(ComplaintAppendix complaintAppendix,Map<String, String> ossIdMap) {
        if (complaintAppendix != null) {
            ComplaintAppendixSimplePo complaintAppendixSimplePo = new ComplaintAppendixSimplePo();
            complaintAppendixSimplePo.setAppendixName(complaintAppendix.getAppendixName());
            complaintAppendixSimplePo.setFullName(complaintAppendix.getFullName());
            complaintAppendixSimplePo.setOssResourceId(complaintAppendix.getOssResourceId());
            complaintAppendixSimplePo.setOssResourceUrl(ossIdMap.get(complaintAppendix.getOssResourceId()));
            complaintAppendixSimplePo.setUploadTime(complaintAppendix.getUploadTime());
            return complaintAppendixSimplePo;
        }
        return null;
    }
}
