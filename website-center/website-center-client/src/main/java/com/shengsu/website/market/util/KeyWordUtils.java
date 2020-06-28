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
    static String head = "<span style=\"color: rgb(249, 150, 59);\">（{0}）</span> <br>";
    static String tail = "<br><span style=\"color: rgb(249, 150, 59);\">（{0}）</span>";
    static String body = "<span style=\"color: rgb(249, 150, 59);\">（{0}）</span>";
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
     * @param content
     * @param city
     * @param firstCategoryName
     * @param sencondCategoryName
     * @param thirdCatetoryName
     * @return
     */
    private static String formatContent(String content, String city, String firstCategoryName, String sencondCategoryName, String thirdCatetoryName){
        String reg = "<p>[\\s]*</p>";
        content = content.replaceAll(reg,"");
        String regex = "<p><br></p>";
        content = content.replaceAll(regex,"");
        List<Integer> list = new ArrayList<>();
        //定位段落
        for (int index = 0; index < content.length(); index++) {
            //获取</p>下标，从而找到插入位置
            index = content.indexOf("</p>", index);
            if (index < 0) {
                break;
            }
            list.add(index);
        }
        //将String变成StringBuilder，字符串可编辑模式
        StringBuilder sb = new StringBuilder(content);
        String randomWord;
        for (int i = list.size() - 1; i >= 0; i--) {
            randomWord = keywords.get((int) (Math.random() * keywords.size()));
            if (Math.random() > 0.3) {
                sb.insert(list.get(i), MessageFormat.format(body,randomWord));
            }
        }
        //随机插入
        firstCategoryName = StringUtils.isBlank(firstCategoryName)?"":" "+firstCategoryName;
        sencondCategoryName = StringUtils.isBlank(sencondCategoryName)?"":" "+sencondCategoryName;
        thirdCatetoryName = StringUtils.isBlank(thirdCatetoryName)?"":" "+thirdCatetoryName;
        String headTail = city+firstCategoryName+ sencondCategoryName+thirdCatetoryName;
        randomWord =" "+ keywords.get((int) (Math.random() * keywords.size()));
        sb.insert(0, MessageFormat.format(head,headTail+randomWord));
        randomWord =" "+ keywords.get((int) (Math.random() * keywords.size()));
        sb.append(MessageFormat.format(tail,headTail+randomWord));
        return sb.toString();
    }
}
