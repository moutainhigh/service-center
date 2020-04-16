package com.shengsu.website.bdapp.util;

import com.shengsu.website.bdapp.entity.Complaint;
import com.shengsu.website.bdapp.vo.ComplaintCreateVo;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-16 09:21
 **/
public class ComplaintUtils {
    public static Complaint toComplaint(ComplaintCreateVo complaintCreateVo) {
        if (complaintCreateVo != null) {
            Complaint complaint = new Complaint();
            complaint.setUrl(complaintCreateVo.getUrl());
            complaint.setName(complaintCreateVo.getName());
            complaint.setTel(complaintCreateVo.getTel());
            complaint.setAppeal(complaintCreateVo.getAppeal());
            return complaint;
        }
        return null;
    }
}
