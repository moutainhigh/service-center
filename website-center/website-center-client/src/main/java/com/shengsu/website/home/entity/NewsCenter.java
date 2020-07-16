package com.shengsu.website.home.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;
import com.shengsu.util.DateUtil;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;

/**
 * Created by zyc on 2019/9/17.
 */
@Data
@Document(indexName = "news", type = "news_center")
public class NewsCenter extends BaseEntity{
    @Id
    private Long id;
    private Integer type;
    @Field
    private String title;
    @JsonFormat(pattern = DateUtil.DEFAULT_DATE_FORMAT,timezone = "GMT+8")
    private Date newsDate;
    private String summary;
    @Field
    private String source;
    private String author;
    @Field
    private String content;
    private Integer isHomeShow;
    private String relevant;
    private String pictureOssId;// 新闻图片资源id
    private String ascription;// 新闻归属
}
