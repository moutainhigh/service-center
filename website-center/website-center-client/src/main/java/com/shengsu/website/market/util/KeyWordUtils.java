package com.shengsu.website.market.util;

import com.shengsu.website.market.entity.KeyWord;
import com.shengsu.website.market.po.LawKnowledgeDetailsPo;
import com.shengsu.website.market.po.LawKnowledgeQueryPo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-06-23 16:24
 **/
public class KeyWordUtils {
    public static void addKeyWord(LawKnowledgeQueryPo lawKnowledgeQueryPo){
        List<String> words = Arrays.asList(KeyWord.listKeyWord.split("，"));
        String result = lawKnowledgeQueryPo.getContent();
        String reg = "<p>[\\s]*</p>";
        String content = result.replaceAll(reg,"");
        List<Integer> list = new ArrayList<>();
        //定位段落
        for (int index = 0; index < content.length(); index++) {
            index = content.indexOf("</p>", index);//获取src下标，从而找到插入位置
            if (index < 0) {
                break;
            }
            list.add(index);
        }

        StringBuilder sb = new StringBuilder(content);//将String变成StringBuilder，字符串可编辑模式
        for (int i = list.size() - 1; i >= 0; i--) {
            sb.insert(list.get(i), "<span style=\"color: rgb(249, 150, 59);\">"+"（" + words.get((int) (Math.random() * words.size())) + "）</span>");
        }//随机插入
        if (StringUtils.isNotBlank(lawKnowledgeQueryPo.getThirdCategoryName())) {
            sb.insert(0, "<span style=\"color: rgb(249, 150, 59);\">"+"（杭州 " + lawKnowledgeQueryPo.getFirstCategoryName() + "  " + lawKnowledgeQueryPo.getSecondCategoryName() + "  " + lawKnowledgeQueryPo.getThirdCategoryName() + words.get((int) (Math.random() * words.size()))+") </span> <p>");
            sb.append("<span style=\"color: rgb(249, 150, 59);\">"+"（杭州 " + lawKnowledgeQueryPo.getFirstCategoryName() + "  " + lawKnowledgeQueryPo.getSecondCategoryName() + "  " + lawKnowledgeQueryPo.getThirdCategoryName() + words.get((int) (Math.random() * words.size()))+ ") </span> <p>");
        }
        sb.insert(0, "<span style=\"color: rgb(249, 150, 59);\">"+"（杭州 " + lawKnowledgeQueryPo.getFirstCategoryName() + "  " + lawKnowledgeQueryPo.getSecondCategoryName() + "  " + words.get((int) (Math.random() * words.size()))  +") </span> <p>");
        sb.append("<span style=\"color: rgb(249, 150, 59);\">"+"（杭州 " + lawKnowledgeQueryPo.getFirstCategoryName() + "  " + lawKnowledgeQueryPo.getSecondCategoryName() + "  " + words.get((int) (Math.random() * words.size())) + ") </span> <p>");
        lawKnowledgeQueryPo.setContent(sb.toString());
    }
    public static void addKeyWord(LawKnowledgeDetailsPo lawKnowledgeDetailsPo){
        List<String> words = Arrays.asList(KeyWord.listKeyWord.split("，"));
        String result = lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getContent();
        String reg = "<p>[\\s]*</p>";
        String content = result.replaceAll(reg,"");
        List<Integer> list = new ArrayList<>();
        //定位段落
        for (int index = 0; index < content.length(); index++) {
            index = content.indexOf("</p>", index);//获取src下标，从而找到插入位置
            if (index < 0) {
                break;
            }
            list.add(index);
        }

        StringBuilder sb = new StringBuilder(content);//将String变成StringBuilder，字符串可编辑模式
        for (int i = list.size() - 1; i >= 0; i--) {
            sb.insert(list.get(i), "<span style=\"color: rgb(249, 150, 59);\">"+"（" + words.get((int) (Math.random() * words.size())) + "）</span>");
        }//随机插入
        if (StringUtils.isNotBlank(lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getThirdCategoryName())) {
            sb.insert(0, "<span style=\"color: rgb(249, 150, 59);\">"+"（杭州 " + lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getFirstCategoryName() + "  " + lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getSecondCategoryName() + "  " + lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getThirdCategoryName() + words.get((int) (Math.random() * words.size()))+") </font> <p>");
            sb.append("<span style=\"color: rgb(249, 150, 59);\">"+"（杭州 " + lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getFirstCategoryName() + "  " + lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getSecondCategoryName() + "  " + lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getThirdCategoryName() + words.get((int) (Math.random() * words.size()))+ ") </span> <p>");
        }
        sb.insert(0, "<span style=\"color: rgb(249, 150, 59);\">"+"（杭州 " + lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getFirstCategoryName() + "  " + lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getSecondCategoryName() + "  " + words.get((int) (Math.random() * words.size()))  +") </span> <p>");
        sb.append("<span style=\"color: rgb(249, 150, 59);\">"+"（杭州 " + lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getFirstCategoryName() + "  " + lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().getSecondCategoryName() + "  " + words.get((int) (Math.random() * words.size())) + ") </span> <p>");
        lawKnowledgeDetailsPo.getLawKnowledgeCurrentPo().setContent(sb.toString());
    }

}
