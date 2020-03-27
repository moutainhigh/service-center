package com.shengsu.any.clue.service;


import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.vo.*;
import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:45
 **/
public interface ClueService extends BaseService<Clue,String> {
     ResultBean create(ClueVo clueVo);
     ResultBean clueListByPage(ClueListByPageVo clueListByPageVo);
     ResultBean edit(ClueEditVo clueVo);
     ResultBean onShelf(ClueShelfVo clueShelfVo);
     ResultBean offShelf(ClueShelfVo clueShelfVo);
     ResultBean clueDelete(ClueShelfVo clueShelfVo);
     ResultBean buy(ClueBuyVo clueBuyVo);
     ResultBean listMyClue(String userId);
     ResultBean clueClientListByPage(ClueListByPageVo clueListByPageVo);
     ResultBean sendAxbUnBindRequest(AxbUnBindRequestVo axbUnBindRequestVo);
     ResultBean pushAllTemplateMsg();
}
