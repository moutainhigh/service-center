package com.shengsu.website.market.util;

import com.shengsu.website.market.entity.LawDoc;
import com.shengsu.website.market.po.LawDocListPagePo;
import com.shengsu.website.market.po.LawDocPreviousPo;
import com.shengsu.website.market.po.LawDocQueryPo;
import com.shengsu.website.market.vo.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-13 11:03
 **/
public class LawDocUtils {
    public static LawDoc toLawDoc(LawDocCreateVo lawDocCreateVo) {
        if (lawDocCreateVo != null) {
            LawDoc lawDoc = new LawDoc();
            lawDoc.setDocType(lawDocCreateVo.getDocType());
            lawDoc.setDocName(lawDocCreateVo.getDocName());
            lawDoc.setFullName(lawDocCreateVo.getFullName());
            lawDoc.setOssResourceId(lawDocCreateVo.getOssResourceId());
            lawDoc.setDownloads(lawDocCreateVo.getDownloads());
            Date uploadTime = lawDocCreateVo.getUploadTime();
            lawDoc.setUploadTime(uploadTime==null?new Date():uploadTime);
            return lawDoc;
        }
        return null;
    }
    public static LawDoc toLawDoc(LawDocUpdateVo lawDocUpdateVo) {
        if (lawDocUpdateVo != null) {
            LawDoc lawDoc = new LawDoc();
            lawDoc.setDocId(lawDocUpdateVo.getDocId());
            lawDoc.setDocType(lawDocUpdateVo.getDocType());
            lawDoc.setDocName(lawDocUpdateVo.getDocName());
            lawDoc.setFullName(lawDocUpdateVo.getFullName());
            lawDoc.setOssResourceId(lawDocUpdateVo.getOssResourceId());
            lawDoc.setDownloads(lawDocUpdateVo.getDownloads());
            Date uploadTime = lawDocUpdateVo.getUploadTime();
            lawDoc.setUploadTime(uploadTime==null?new Date():uploadTime);
            return lawDoc;
        }
        return null;
    }

    public static LawDocQueryPo toLawDocQueryPo(LawDoc lawDoc) {
        if (lawDoc != null) {
            LawDocQueryPo lawDocQueryPo = new LawDocQueryPo();
            lawDocQueryPo.setDocType(lawDocQueryPo.getDocType());
            lawDocQueryPo.setDocName(lawDoc.getDocName());
            lawDocQueryPo.setFullName(lawDoc.getFullName());
            lawDocQueryPo.setOssResourceId(lawDoc.getOssResourceId());
            lawDocQueryPo.setDownloads(lawDoc.getDownloads());
            lawDocQueryPo.setUploadTime(lawDoc.getUploadTime());
            return lawDocQueryPo;
        }
        return null;
    }
    public static LawDoc toLawDoc(ListLawDocByPageVo lawDocByPageVo) {
        if (lawDocByPageVo != null) {
            LawDoc lawDoc = new LawDoc();
            lawDoc.setDocType(lawDocByPageVo.getDocType());
            lawDoc.setDocName(lawDocByPageVo.getDocName());
            lawDoc.setFullName(lawDocByPageVo.getFullName());
            lawDoc.setSearch(lawDocByPageVo.getSearch());
            lawDoc.setPage(lawDocByPageVo.getPage());
            lawDoc.setPageSize(lawDocByPageVo.getPageSize());
            return lawDoc;
        }
        return null;
    }

    public static List<LawDocListPagePo> toLawDocListPagePos(List<LawDoc> lawDocs) {
        if (!CollectionUtils.isEmpty(lawDocs)) {
            List<LawDocListPagePo> lawDocListPagePos = new ArrayList<>();
            LawDocListPagePo lawDocListPagePo = null;
            for (LawDoc lawDoc : lawDocs) {
                lawDocListPagePo = new LawDocListPagePo();
                lawDocListPagePo.setDocId(lawDoc.getDocId());
                lawDocListPagePo.setDocType(lawDoc.getDocType());
                lawDocListPagePo.setDocName(lawDoc.getDocName());
                lawDocListPagePo.setFullName(lawDoc.getFullName());
                lawDocListPagePo.setOssResourceId(lawDoc.getOssResourceId());
                lawDocListPagePo.setDownloads(lawDoc.getDownloads());
                lawDocListPagePo.setUploadTime(lawDoc.getUploadTime());
                lawDocListPagePos.add(lawDocListPagePo);
            }

            return lawDocListPagePos;
        }
        return null;
    }
    public static LawDoc toLawDoc( Date uploadTime, String docType) {
        if (uploadTime != null) {
            LawDoc lawDoc = new LawDoc();
            lawDoc.setUploadTime(uploadTime);
            lawDoc.setDocType(docType);
            return lawDoc;
        }
        return null;
    }

    public static LawDocPreviousPo toLawDocPreviousPo(LawDoc lawDoc) {
        if (lawDoc != null) {
            LawDocPreviousPo lawDocPreviousPo = new LawDocPreviousPo();
            lawDocPreviousPo.setDocId(lawDoc.getDocId());
            lawDocPreviousPo.setDocName(lawDoc.getDocName());
            return lawDocPreviousPo;
        }
        return null;
    }
}
