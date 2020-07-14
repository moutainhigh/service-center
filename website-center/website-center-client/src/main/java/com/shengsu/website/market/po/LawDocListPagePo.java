package com.shengsu.website.market.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.util.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zyc on 2019/9/17.
 */
@Data
public class LawDocListPagePo implements Serializable {
    private String docId;
    private String docType;
    private String docTypeStr;
    private String docName;
    private String fullName;
    private String ossResourceId;
    private String ossResourceUrl;
    private String downloads;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uploadTime;
}
