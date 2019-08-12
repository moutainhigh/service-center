package com.shengsu.bench.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shengsu.base.entity.BaseEntity;

import java.util.Date;

/**
 * Created by zyc on 2019/8/7.
 */
public class BenchJournalism extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date journalismDate;
    private String url;
    private String pictureOssId;
    private Integer isTop;
    private Integer delFlag;
    private String features;


    private String pictureOssUrl;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getJournalismDate() {
        return journalismDate;
    }

    public void setJournalismDate(Date journalismDate) {
        this.journalismDate = journalismDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPictureOssId() {
        return pictureOssId;
    }

    public void setPictureOssId(String pictureOssId) {
        this.pictureOssId = pictureOssId;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getPictureOssUrl() {
        return pictureOssUrl;
    }

    public void setPictureOssUrl(String pictureOssUrl) {
        this.pictureOssUrl = pictureOssUrl;
    }


    public BenchJournalism() {
    }
}
