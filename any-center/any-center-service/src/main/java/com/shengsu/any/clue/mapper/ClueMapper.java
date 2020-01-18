package com.shengsu.any.clue.mapper;


import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.vo.ClueShelfVo;
import com.shengsu.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:46
 **/
@Mapper
public interface ClueMapper extends BaseMapper<Clue,String> {
    int onShelf(ClueShelfVo clueShelfVo);
    int offShelf(ClueShelfVo clueShelfVo);
    int softDelete(ClueShelfVo clueShelfVo);
}
