package com.shengsu.website.market.util;

import com.shengsu.util.StringUtil;
import com.shengsu.website.market.entity.Complaint;
import com.shengsu.website.market.po.ComplaintListPagePo;
import com.shengsu.website.market.vo.ComplaintCreateVo;
import com.shengsu.website.market.vo.ComplaintListPageVo;

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
            complaint.setComplaintType(complaintCreateVo.getComplaintType());
            return complaint;
        }
        return null;
    }
    public static Complaint toComplaint(ComplaintListPageVo complaintListPageVo) {
        if (complaintListPageVo != null) {
            Complaint complaint = new Complaint();
            complaint.setName(complaintListPageVo.getName());
            complaint.setTel(complaintListPageVo.getTel());
            complaint.setComplaintType(complaintListPageVo.getComplaintType());
            complaint.setSearch(StringUtil.ToLikeStr(complaintListPageVo.getSearch()));
            complaint.setStartTime(complaintListPageVo.getStartTime());
            complaint.setEndTime(complaintListPageVo.getEndTime());
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
            complaintListPagePo.setComplaintType(complaint.getComplaintType());
            complaintListPagePo.setCreateTime(complaint.getCreateTime());
            return complaintListPagePo;
        }
        return null;
    }
}
