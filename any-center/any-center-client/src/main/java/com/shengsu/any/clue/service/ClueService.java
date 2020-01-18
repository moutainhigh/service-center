package com.shengsu.any.clue.service;


import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.vo.ClueShelfVo;
import com.shengsu.any.clue.vo.ClueVo;
import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:45
 **/
public interface ClueService extends BaseService<Clue,String> {
     ResultBean create(ClueVo clueVo);
     ResultBean clueListByPage(Clue record);
     ResultBean edit(Clue record);
     ResultBean onShelf(ClueShelfVo clueShelfVo);
     ResultBean offShelf(ClueShelfVo clueShelfVo);
     ResultBean clueDelete(ClueShelfVo clueShelfVo);
}
