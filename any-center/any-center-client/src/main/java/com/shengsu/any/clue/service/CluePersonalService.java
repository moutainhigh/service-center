package com.shengsu.any.clue.service;


import com.shengsu.any.clue.entity.CluePersonal;
import com.shengsu.any.clue.vo.CluePersonVo;
import com.shengsu.base.service.BaseService;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 11:26
 **/
public interface CluePersonalService extends BaseService<CluePersonal,String> {
     void create(String clueId,String userId);
     CluePersonVo listByUserId(String userId);
}
