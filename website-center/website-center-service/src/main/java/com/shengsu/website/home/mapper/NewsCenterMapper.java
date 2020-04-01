package com.shengsu.website.home.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.home.entity.NewsCenter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by zyc on 2019/9/17.
 */
@Mapper
public interface NewsCenterMapper extends BaseMapper<NewsCenter, String> {

    NewsCenter selectById(Long id);

    NewsCenter selectByTitle(String title);

    int delete(Long id);

    List<NewsCenter> selectByPage(NewsCenter newsCenter);

    Integer countByPage(NewsCenter newsCenter);

    NewsCenter selectPreviousByIdToType(NewsCenter newsCenter);

    NewsCenter selectNextByIdToType(NewsCenter newsCenter);

    List<NewsCenter> selectRelevantByIds(List<Long> ids);

    List<NewsCenter> getNewsCenter(NewsCenter newsCenter);
}
