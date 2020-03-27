package com.shengsu.any.clue.service;

import com.shengsu.any.clue.entity.Pns;
import com.shengsu.any.clue.vo.ClueBuyVo;
import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-24 20:01
 **/
public interface PnsService extends BaseService<Pns,String> {
    ResultBean sendAxbReBindRequest(ClueBuyVo clueBuyVo);
}
