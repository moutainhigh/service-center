package com.shengsu.website.bdapp.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.constant.CommonConst;
import com.shengsu.result.ResultBean;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.app.util.ResultUtil;
import com.shengsu.website.bdapp.entity.Complaint;
import com.shengsu.website.bdapp.entity.ComplaintAppendix;
import com.shengsu.website.bdapp.entity.Feedback;
import com.shengsu.website.bdapp.mapper.ComplaintMapper;
import com.shengsu.website.bdapp.po.ComplaintListPagePo;
import com.shengsu.website.bdapp.po.FeedbackListPagePo;
import com.shengsu.website.bdapp.service.ComplaintAppendixService;
import com.shengsu.website.bdapp.service.ComplaintService;
import com.shengsu.website.bdapp.util.ComplaintAppendixUtils;
import com.shengsu.website.bdapp.util.ComplaintUtils;
import com.shengsu.website.bdapp.util.FeedbackUtils;
import com.shengsu.website.bdapp.vo.ComplaintCreateVo;
import com.shengsu.website.bdapp.vo.ComplaintListPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-15 17:04
 **/
@Service("complaintService")
public class ComplaintServiceImpl extends BaseServiceImpl<Complaint, String> implements ComplaintService {
    @Autowired
    private ComplaintAppendixService complaintAppendixService;
    @Autowired
    private ComplaintMapper complaintMapper;
    @Override
    public BaseMapper<Complaint, String> getBaseMapper() {
        return complaintMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean create(ComplaintCreateVo complaintCreteVo) {
        // 保存投诉信息
        String complaintId = UUID.randomUUID().toString();
        Complaint complaint = ComplaintUtils.toComplaint(complaintCreteVo);
        complaint.setComplaintId(complaintId);
        save(complaint);

        // 保存投诉附件信息
        List<ComplaintAppendix> complaintAppendices = complaintCreteVo.getComplaintAppendices();
        ComplaintAppendixUtils.toComplaintAppendices(complaintId,complaintAppendices);
        complaintAppendixService.batchSave(complaintAppendices);

        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean listPage(ComplaintListPageVo complaintListPageVo) {
        Complaint complaint = ComplaintUtils.toComplaint(complaintListPageVo);
        Integer count = complaintMapper.countAll(complaint);
        Map<String, Object> resultMap = new HashMap<>();
        if (count > 0) {
            List<Complaint> complaints = complaintMapper.listByPage(complaint);
            if(null == complaints || complaints.size() == 0){
                return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
            }
            List<ComplaintListPagePo> complaintListPagePos = ComplaintUtils.toComplaintListPagePos(complaints);
            resultMap.put(CommonConst.ROOT, complaintListPagePos);
            resultMap.put(CommonConst.TOTAL_COUNT, count);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }

    @Override
    public ResultBean deleteComplaint(String complaintId) {
        // 删除投诉
        softDelete(complaintId);
        // 删除投诉附件
        complaintAppendixService.deleteByComplaintId(complaintId);
        return null;
    }
}
