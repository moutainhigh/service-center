package com.shengsu.website.market.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.market.entity.Lawyer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-04-10 13:22
 **/
@Mapper
public interface LawyerMapper extends BaseMapper<Lawyer,String> {
    List<Lawyer> randomSelect(String systemTag);
    int isLawyerExist(String lawyerName);
    int isLawyerExistOther(Lawyer lawyer);
    List<Lawyer> listLawyers(String systemTag);
}
