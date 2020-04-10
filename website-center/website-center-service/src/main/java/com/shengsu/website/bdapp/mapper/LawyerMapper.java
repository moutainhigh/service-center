package com.shengsu.website.bdapp.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.bdapp.entity.Lawyer;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-10 13:22
 **/
@Mapper
public interface LawyerMapper extends BaseMapper<Lawyer,String> {
}
