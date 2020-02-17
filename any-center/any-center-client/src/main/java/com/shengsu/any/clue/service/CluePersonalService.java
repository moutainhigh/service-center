package com.shengsu.any.clue.service;


import com.shengsu.any.clue.entity.CluePersonal;
import com.shengsu.base.service.BaseService;

import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 11:26
 **/
public interface CluePersonalService extends BaseService<CluePersonal,String> {
     void create(String clueId,String userId);
    List<CluePersonal> listByUserId(String userId);
}
