package com.shengsu.website.home.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.home.entity.WheelCenter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by zyc on 2019/9/17.
 */
@Mapper
public interface WheelCenterMapper  extends BaseMapper{
    WheelCenter selectById(Long id);

    List<WheelCenter> selectByType(Integer type);
}
