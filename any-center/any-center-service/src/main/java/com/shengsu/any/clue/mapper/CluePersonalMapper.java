package com.shengsu.any.clue.mapper;

import com.shengsu.any.clue.entity.CluePersonal;
import com.shengsu.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 11:27
 **/
@Mapper
public interface CluePersonalMapper extends BaseMapper<CluePersonal,String> {
    List<CluePersonal> listByUserId(String userId);
}
