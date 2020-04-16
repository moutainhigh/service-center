package com.shengsu.website.bdapp.util;

import com.shengsu.util.StringUtil;
import com.shengsu.website.bdapp.entity.Complaint;
import com.shengsu.website.bdapp.po.ComplaintListPagePo;
import com.shengsu.website.bdapp.vo.ComplaintCreateVo;
import com.shengsu.website.bdapp.vo.ComplaintListPageVo;

import java.util.ArrayList;
import java.util.List;

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
    public static Complaint toComplaint(ComplaintListPageVo complaintListPageVo) {
        if (complaintListPageVo != null) {
            Complaint complaint = new Complaint();
            complaint.setName(complaintListPageVo.getName());
            complaint.setTel(complaintListPageVo.getTel());
            complaint.setSearch(StringUtil.ToLikeStr(complaintListPageVo.getSearch()));
            complaint.setPage(complaintListPageVo.getPage());
            complaint.setPageSize(complaintListPageVo.getPageSize());
            return complaint;
        }
        return null;
    }

    public static List<ComplaintListPagePo> toComplaintListPagePos(List<Complaint> complaints) {
        if (complaints != null && !complaints.isEmpty()) {
            List<ComplaintListPagePo> complaintListPagePos = new ArrayList<>();
            for (Complaint complaint : complaints){
                complaintListPagePos.add(toComplaintListPagePo(complaint));
            }
            return complaintListPagePos;
        }
        return null;
    }

    private static ComplaintListPagePo toComplaintListPagePo(Complaint complaint) {
        if (complaint != null) {
            ComplaintListPagePo complaintListPagePo = new ComplaintListPagePo();
            complaintListPagePo.setComplaintId(complaint.getComplaintId());
            complaintListPagePo.setName(complaint.getName());
            complaintListPagePo.setTel(complaint.getTel());
            complaintListPagePo.setUrl(complaint.getUrl());
            complaintListPagePo.setAppeal(complaint.getAppeal());
            return complaintListPagePo;
        }
        return null;
    }
}
