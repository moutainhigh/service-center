package com.shengsu.website.consult.po;

import lombok.Data;

import java.io.Serializable;
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
    private List<ConsultAppendixDetailsPo> appendixList;
}
