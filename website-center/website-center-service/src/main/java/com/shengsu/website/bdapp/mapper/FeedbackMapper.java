package com.shengsu.website.bdapp.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.bdapp.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback, String> {
}
