package com.shengsu.helper.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * Created by Bell on 2019/10/21.
 */
@Data
public class JpushNormal {
    List<String> aliasList;
    String notificationTitle;
    String msgTitle;
    String msgContent;
    JSONObject extrasparam;
}
