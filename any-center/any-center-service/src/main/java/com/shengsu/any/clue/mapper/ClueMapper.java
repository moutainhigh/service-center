package com.shengsu.any.clue.mapper;


import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.vo.ClueListByPageVo;
import com.shengsu.any.clue.vo.CluePersonalVo;
import com.shengsu.any.clue.vo.ClueShelfVo;
import com.shengsu.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:46
 **/
@Mapper
public interface ClueMapper extends BaseMapper<Clue,String> {
    String getClueState(String clueId);
    int onShelf(ClueShelfVo clueShelfVo);
    int offShelf(ClueShelfVo clueShelfVo);
    int softDelete(ClueShelfVo clueShelfVo);
    int countAll(ClueListByPageVo clueListByPageVo);
    List<Clue> listByPage(ClueListByPageVo clueListByPageVo);
    List<Clue> clueClientListByPage(ClueListByPageVo clueListByPageVo);
    void updateClueSold(String clueId);
    void updateClueTelX(Clue clue);
    List<Clue> getClues(CluePersonalVo cluePersonalVo);
}
