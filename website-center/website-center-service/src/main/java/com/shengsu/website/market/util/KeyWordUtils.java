package com.shengsu.website.market.util;

import com.shengsu.website.market.entity.KeyWord;
import com.shengsu.website.market.po.LawKnowledgeDetailsPo;
import com.shengsu.website.market.po.LawKnowledgeQueryPo;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-06-23 16:24
 **/
public class KeyWordUtils {
    static String head = "<span style=\'color:dodgerblue;\'>（{0}）</span> <br>";
    static String tail = "<br><span style=\'color:dodgerblue;\'>（{0}）</span>";
    static String body = "<span style=\'color:dodgerblue;\'>（{0}）</span>";
    static List<String> keywords = Arrays.asList(KeyWord.listKeyWord.split("，"));

    public static void addKeyWord(LawKnowledgeQueryPo lawKnowledgeQueryPo ,String city){
        String content = lawKnowledgeQueryPo.getContent();
        String firstCategoryName = lawKnowledgeQueryPo.getFirstCategoryName();
        String secondCategoryName = lawKnowledgeQueryPo.getSecondCategoryName();
        String thirdCategoryName = lawKnowledgeQueryPo.getThirdCategoryName();
        content = formatContent(content,city,firstCategoryName,secondCategoryName,thirdCategoryName);
        lawKnowledgeQueryPo.setContent(content);
    }
    public static void addKeyWord(LawKnowledgeDetailsPo lawKnowledgeDetailsPo,String city){
        String content = lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getContent();
        String firstCategoryName = lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getFirstCategoryName();
        String secondCategoryName = lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getSecondCategoryName();
        String thirdCategoryName = lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getThirdCategoryName();
        content = formatContent(content,city,firstCategoryName,secondCategoryName,thirdCategoryName);
        lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().setContent(content);
    }


    /**
     * 格式化内容
     *
     * @param content
     * @param city
     * @param firstCategoryName
     * @param sencondCategoryName
     * @param thirdCatetoryName
     * @return
     */
    private static String formatContent(String content, String city, String firstCategoryName, String sencondCategoryName, String thirdCatetoryName) {
        content = content.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
        List<Integer> list = new ArrayList<>();
        if (content.length() <= 500) {
            int finalPosition = getMinPosition(content, 0.5);
            list.add(finalPosition);
        }
        if (content.length() <= 1500 && content.length() > 500) {
            int finalPosition = getMinPosition(content, 0.4);
            list.add(finalPosition);
            finalPosition = getMinPosition(content, 0.8);
            list.add(finalPosition);
        }
        if (content.length() <= 3000 && content.length() > 1500) {
            int finalPosition = getMinPosition(content, 0.3);
            list.add(finalPosition);
            finalPosition = getMinPosition(content, 0.6);
            list.add(finalPosition);
            finalPosition = getMinPosition(content, 0.9);
            list.add(finalPosition);
        }
        if (content.length() <= 6000 && content.length() > 3000) {
            int finalPosition = getMinPosition(content, 0.25);
            list.add(finalPosition);
            finalPosition = getMinPosition(content, 0.5);
            list.add(finalPosition);
            finalPosition = getMinPosition(content, 0.75);
            list.add(finalPosition);
            list.add(content.length());
        }
        if (content.length() > 6000) {
            int finalPosition = getMinPosition(content, 0.2);
            list.add(finalPosition);
            finalPosition = getMinPosition(content, 0.4);
            list.add(finalPosition);
            finalPosition = getMinPosition(content, 0.6);
            list.add(finalPosition);
            finalPosition = getMinPosition(content, 0.8);
            list.add(finalPosition);
            list.add(content.length());
        }

        //将String变成StringBuilder，字符串可编辑模式
        StringBuilder sb = new StringBuilder(content);
        String randomWord;
        for (int i = list.size() - 1; i >= 0; i--) {
            randomWord = keywords.get((int) (Math.random() * keywords.size()));
            sb.insert(list.get(i), MessageFormat.format(body, randomWord));
        }


        //随机插入
        firstCategoryName = StringUtils.isBlank(firstCategoryName) ? "" : " " + firstCategoryName;
        sencondCategoryName = StringUtils.isBlank(sencondCategoryName) ? "" : " " + sencondCategoryName;
        thirdCatetoryName = StringUtils.isBlank(thirdCatetoryName) ? "" : " " + thirdCatetoryName;
        String headTail = city + firstCategoryName + sencondCategoryName + thirdCatetoryName;
        randomWord = " " + keywords.get((int) (Math.random() * keywords.size()));
        sb.insert(0, MessageFormat.format(head, headTail + randomWord));
        randomWord = " " + keywords.get((int) (Math.random() * keywords.size()));
        sb.append(MessageFormat.format(tail, headTail + randomWord));
        return sb.toString();
    }

    /**
     * @return int
     * @Author Bell
     * @Description 据最佳位置最近点
     * @Date 2020/6/28
     * @Param [content, proportion]
     **/
    public static int getMinPosition(String content, double proportion) {
        int total = content.length();
        int position = (int) Math.round(total * proportion);
        int backward = content.indexOf("。", position);
        String previou = content.substring(0, position + 1);
        previou = reverseByRecursion(previou);
        int forward = previou.length() - previou.indexOf("。");
        int finalPosition = backward - forward < 0 ? backward : forward;
        return finalPosition;
    }

    /**
     * @return java.lang.String
     * @Author Bell
     * @Description 反转字符串
     * @Date 2020/6/28
     * @Param [str]
     **/
    public static String reverseByRecursion(String str) {
        int length = str.length();
        if (length <= 1) {
            return str;
        }

        return reverseByRecursion(str.substring(1)) + str.charAt(0);
    }
}
