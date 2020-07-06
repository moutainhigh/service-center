package com.shengsu.website.market.util;

import com.shengsu.website.market.entity.KeyWord;
import com.shengsu.website.market.po.LawKnowledgeDetailsPo;
import com.shengsu.website.market.po.LawKnowledgeQueryPo;
import org.apache.commons.lang3.StringUtils;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-06-23 16:24
 **/
public class KeyWordUtils {
    static String head = "<span style=''color:dodgerblue;''>（{0}）</span> <br>";
    static String body = "<span style=''color:dodgerblue;''>（{0}）</span>";
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
            list=list.stream().distinct().collect(Collectors.toList());
        }
        if (content.length() <= 3000 && content.length() > 1500) {
            int finalPosition = getMinPosition(content, 0.3);
            list.add(finalPosition);
            finalPosition = getMinPosition(content, 0.6);
            list.add(finalPosition);
            finalPosition = getMinPosition(content, 0.9);
            list.add(finalPosition);
            list=list.stream().distinct().collect(Collectors.toList());
        }
        if (content.length() <= 6000 && content.length() > 3000) {
            int finalPosition = getMinPosition(content, 0.25);
            list.add(finalPosition);
            finalPosition = getMinPosition(content, 0.5);
            list.add(finalPosition);
            finalPosition = getMinPosition(content, 0.75);
            list.add(finalPosition);
            list.add(content.length());
            list=list.stream().distinct().collect(Collectors.toList());
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
            list=list.stream().distinct().collect(Collectors.toList());
        }

        //将String变成StringBuilder，字符串可编辑模式
        StringBuilder sb = new StringBuilder(content);
        Set set = new HashSet();
        Collections.sort(list);
        for (int i = list.size() - 1; i >= 0; i--) {
            sb.insert(list.get(i), MessageFormat.format(body, getRandomKeyword(set)));
        }

        //随机插入
        firstCategoryName = StringUtils.isBlank(firstCategoryName) ? "" : " " + firstCategoryName;
        sencondCategoryName = StringUtils.isBlank(sencondCategoryName) ? "" : " " + sencondCategoryName;
        thirdCatetoryName = StringUtils.isBlank(thirdCatetoryName) ? "" : " " + thirdCatetoryName;
        String headTail = city + firstCategoryName + sencondCategoryName + thirdCatetoryName;
        sb.insert(0, MessageFormat.format(head, headTail + " " + getRandomKeyword(set)));
        return sb.toString();
    }

    /**
     * @return int
     * @Author Bell
     * @Description 距最佳位置最近点
     * @Date 2020/6/28
     * @Param [content, proportion]
     **/
    public static int getMinPosition(String content, double proportion) {
        int total = content.length();
        int position = (int) Math.round(total * proportion);
        int backward = content.indexOf("。", position) + 1;
        String previou = content.substring(0, position + 1);
        previou = new StringBuffer(previou).reverse().toString();
        if(previou.indexOf("。")<0){
            if (previou.indexOf("；") > 0) {
                return previou.length() - previou.indexOf("；");
            }
            return backward;
        }
        int forward = previou.length() - previou.indexOf("。");
        int finalPosition = backward - forward < 0 ? backward : forward;
        return finalPosition;
    }

    private static String getRandomKeyword(Set<String> existKeywords) {
        String result = keywords.get((int) (Math.random() * keywords.size()));

        while (existKeywords.contains(result) && existKeywords.size() < keywords.size()) {
            result = keywords.get((int) (Math.random() * keywords.size()));
        }
        existKeywords.add(result);
        return result;
    }
}
