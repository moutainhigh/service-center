package com.shengsu.website.market.util;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-07-06 15:52
 **/
public class KnowledgeContentUtils {
    public static String replace(String content){
        content = content.replaceAll( "(?i)(\\<img)([^\\>]+\\>)","$1 style=\"max-width:100%;height:auto\"$2")
                .replace("<section","<div")
                .replace("/section>","/div>")
                .replace("<fieldset","<div")
                .replace("/fieldset>","/div>")
                .replace("&amp;","&")
                .replace("<br>", "<br></br>")
                .replace("\">", "\"/>")
                .replace("<o:p></o:p>", "")
                .replace("&#x27;","\"")
                .replaceAll("/<(?!\\w)(?![/\\/])/g","&lt;");
        return content;
    }
}
