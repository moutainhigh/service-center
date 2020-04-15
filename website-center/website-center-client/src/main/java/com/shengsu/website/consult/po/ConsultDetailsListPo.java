package com.shengsu.website.consult.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-03-24 19:00
 **/
@Data
public class ConsultDetailsListPo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String consultId;
    private String origin;
    private String consultContent;
    private String consultor;
    private String contact;
    private String target;
    private String reply;
    private String lawyer;
    private String source;
    private String redirectUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;//创建时间
    private List<ConsultAppendixDetailsPo> appendixList;
}
