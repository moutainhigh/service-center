package com.shengsu.Entity;

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
    String extrasparam;
}