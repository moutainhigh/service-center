package com.shengsu.website.consult.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zyc on 2019/12/5.
 */
@Data
public class ConsultAppendixDetailsPo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String appendixId;
    private String refId;
    private String appendixName;
    private String ossResourceId;
    private String ossResourceUrl;
    private double fileSize;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date uploadTime;
    private String fullName;
}
