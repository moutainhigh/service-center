package com.shengsu.website.home.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.home.entity.SuccessCase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by zyc on 2019/9/17.
 */
@Mapper
public interface SuccessCaseMapper extends BaseMapper<SuccessCase, String> {

    SuccessCase selectById(Long id);

    SuccessCase selectByTitle(String title);

    int delete(Long id);

    List<SuccessCase> selectByPage(SuccessCase newsCenter);

    Integer countByPage(SuccessCase newsCenter);

    SuccessCase selectPreviousByIdToType(SuccessCase newsCenter);

    SuccessCase selectNextByIdToType(SuccessCase newsCenter);

    List<SuccessCase> selectRelevantByIds(List<Long> ids);

    List<SuccessCase> selectByIsHomeShow(Integer isHomeShow);
}
