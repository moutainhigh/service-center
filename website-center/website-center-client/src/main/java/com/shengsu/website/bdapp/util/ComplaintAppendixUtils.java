package com.shengsu.website.bdapp.util;

import com.shengsu.website.bdapp.entity.ComplaintAppendix;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-16 09:28
 **/
public class ComplaintAppendixUtils {
    public static List<ComplaintAppendix> toComplaintAppendix(String complaintId, List<ComplaintAppendix> appendixList) {
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
}
