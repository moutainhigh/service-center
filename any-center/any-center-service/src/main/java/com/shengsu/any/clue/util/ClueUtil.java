package com.shengsu.any.clue.util;

import java.util.Random;

public class ClueUtil {
    /**
     * 生产随机字符串（英文字符+数字）
     * @param length
     * @return
     */
    public static String getStringRandom(int length) {
        StringBuffer val=null;
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if("char".equalsIgnoreCase(charOrNum)){
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char)(random.nextInt(26) + temp));
            }else if("num".equalsIgnoreCase(charOrNum)) {
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }
}
