package com.shengsu.any.message.mapper;

import com.shengsu.any.message.entity.Message;
import com.shengsu.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<Message,String> {
}
