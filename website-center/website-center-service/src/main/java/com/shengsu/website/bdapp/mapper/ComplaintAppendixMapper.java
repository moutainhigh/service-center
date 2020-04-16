package com.shengsu.website.bdapp.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.bdapp.entity.ComplaintAppendix;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ComplaintAppendixMapper extends BaseMapper<ComplaintAppendix, String> {
    List<ComplaintAppendix> listComplaintAppendix(ComplaintAppendix complaintAppendix);

    int deleteByComplaintId(String complaintId);
}
