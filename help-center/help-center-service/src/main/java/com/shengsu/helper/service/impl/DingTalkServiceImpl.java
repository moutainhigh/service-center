package com.shengsu.helper.service.impl;


import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.shengsu.result.ResultBean;
import com.shengsu.app.constant.ResultCode;
import com.shengsu.helper.entity.DingTalkLink;
import com.shengsu.helper.service.DingTalkService;
import com.shengsu.result.ResultUtil;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by zyc on 2019/10/12.
 */
@Slf4j
@Service(value = "dingTalkService")
public class DingTalkServiceImpl implements DingTalkService {

    @Override
    public ResultBean sendLink(DingTalkLink dingTalkLink)  {
        DingTalkClient client = new DefaultDingTalkClient(dingTalkLink.getDingGroupUrl());
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("link");
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setMessageUrl(dingTalkLink.getMsgUrl());
        link.setPicUrl(dingTalkLink.getPicUrl());
        link.setTitle(dingTalkLink.getTitle());
        link.setText(dingTalkLink.getText());
        request.setLink(link);
        OapiRobotSendResponse response = null;
        try {
            response = client.execute(request);
        } catch (ApiException e) {
            log.error("调用钉钉api异常",e);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, response);
    }

}
