package com.shengsu.helper.util;

public class SmsUtil {
    public static String getSixRandomCode() {
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }
}