package com.shengsu.helper.service;

import com.shengsu.app.constant.ResultBean;
import com.shengsu.helper.entity.DingTalkLink;
import com.taobao.api.ApiException;

/**
 * Created by zyc on 2019/11/5.
 */
public interface DingTalkService {

    ResultBean sendLink(DingTalkLink dingTalkLink) ;
}
