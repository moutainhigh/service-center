package com.shengsu.log.util;

import com.shengsu.helper.entity.DingTalkLink;
import com.shengsu.log.entity.LogError;

/**
 * Created by zyc on 2019/11/5.
 */
public class LogErrorUtils {

    public static DingTalkLink toDingTalkLink(LogError logError, String groupUrl, String msgUrl, String picUrl, String environment) {
        if (logError != null) {
            DingTalkLink dingTalkLink = new DingTalkLink();
            dingTalkLink.setDingGroupUrl(groupUrl);
            dingTalkLink.setMsgUrl(msgUrl);
            dingTalkLink.setPicUrl(picUrl);
            String title = logError.getSystemTag() + "-" + environment + "\n" + logError.getCreator()+"\n" + logError.getErrName();
            dingTalkLink.setTitle(title);
            dingTalkLink.setText(logError.getErrRemark());
            return dingTalkLink;
        }
        return null;
    }
}
