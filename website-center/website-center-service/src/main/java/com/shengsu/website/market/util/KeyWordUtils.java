package com.shengsu.website.market.util;

import com.shengsu.website.market.entity.KeyWord;
import com.shengsu.website.market.po.LawKnowledgeDetailsPo;
import com.shengsu.website.market.po.LawKnowledgeQueryPo;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

        List randomWords  = getRandomList(keywords,list.size() +2);
        for (int i = list.size() - 1; i >= 0; i--) {
            sb.insert(list.get(i), MessageFormat.format(body, randomWords.get(i)));
        }


        //随机插入
        firstCategoryName = StringUtils.isBlank(firstCategoryName) ? "" : " " + firstCategoryName;
        sencondCategoryName = StringUtils.isBlank(sencondCategoryName) ? "" : " " + sencondCategoryName;
        thirdCatetoryName = StringUtils.isBlank(thirdCatetoryName) ? "" : " " + thirdCatetoryName;
        String headTail = city + firstCategoryName + sencondCategoryName + thirdCatetoryName;
        sb.insert(0, MessageFormat.format(head, headTail + randomWords.get(list.size())));
        sb.append(MessageFormat.format(tail, headTail + randomWords.get(list.size()+1)));
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
        int backward = content.indexOf("。", position);
        String previou = content.substring(0, position + 1);
        previou = new StringBuffer(previou).reverse().toString();
        int forward = previou.length() - previou.indexOf("。");
        int finalPosition = backward - forward < 0 ? backward : forward;
        return finalPosition;
    }

    /**
     * @return java.util.List
     * @Author Bell
     * @Description 从list中随机抽取若干不重复元素
     * @Date 2020/6/29
     * @Param [paramList, count]
     **/
    public static List getRandomList(List paramList, int count) {
        if (paramList.size() < count) {
            return paramList;
        }
        Random random = new Random();
        List<Integer> tempList = new ArrayList<Integer>();
        List<Object> newList = new ArrayList<Object>();
        int temp = 0;
        for (int i = 0; i < count; i++) {
            temp = random.nextInt(paramList.size());//将产生的随机数作为被抽list的索引
            if(!tempList.contains(temp)){
                tempList.add(temp);
                newList.add(paramList.get(temp));
            }
            else{
                i--;
            }
        }
        return newList;
    }
}
