package com.shengsu.system.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.lawcase.entity.LawcaseGroupPermanentMember;
import com.shengsu.result.ResultBean;
import com.shengsu.system.vo.SystemConfigUpdateDelVo;

/**
 * Created by zyc on 2019/11/15.
 */
public interface SystemConfigService  {

    ResultBean getLawcaseUser();

    ResultBean updateDel(SystemConfigUpdateDelVo systemConfigUpdateDelVo);
}
