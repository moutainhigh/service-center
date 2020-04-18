package com.shengsu.website.home.util;


import com.shengsu.website.home.entity.LawcaseConsult;
import com.shengsu.website.home.entity.LawcaseConsultAppendix;
import com.shengsu.website.home.po.ConsultDetailsListPo;
import com.shengsu.website.home.po.ConsultAppendixDetailsPo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by zyc on 2019/12/5.
 */
public class LawcaseConsultAppendixUtils {
    public static List<ConsultDetailsListPo> toConsultAppendixDetailsListPo(List<LawcaseConsult> root){
        List<ConsultDetailsListPo> consultDetailsListPos = new ArrayList<>();
        for(LawcaseConsult lawcaseConsult : root){
            ConsultDetailsListPo consultDetailsListPo = new ConsultDetailsListPo();
            consultDetailsListPo.setOrigin(lawcaseConsult.getOrigin());
            consultDetailsListPo.setConsultId(lawcaseConsult.getConsultId());
            consultDetailsListPo.setConsultContent(lawcaseConsult.getConsultContent());
            consultDetailsListPo.setConsultor(lawcaseConsult.getConsultor());
            consultDetailsListPo.setContact(lawcaseConsult.getContact());
            consultDetailsListPo.setTarget(lawcaseConsult.getTarget());
            consultDetailsListPo.setReply(lawcaseConsult.getReply());
            consultDetailsListPo.setLawyer(lawcaseConsult.getLawyer());
            consultDetailsListPo.setSource(lawcaseConsult.getSource());
            consultDetailsListPo.setRedirectUrl(lawcaseConsult.getRedirectUrl());
            consultDetailsListPo.setCreateTime(lawcaseConsult.getCreateTime());
            consultDetailsListPos.add(consultDetailsListPo);
        }
        return consultDetailsListPos;
    }

    public static List<LawcaseConsultAppendix> toLawcaseConsultAppendixs(String uuid, String uploaderUserId, String appendixType, List<LawcaseConsultAppendix> appendixList) {
        if (StringUtils.isNotBlank(uuid) && appendixList != null) {
            for (LawcaseConsultAppendix consultAppendix :
                    appendixList) {
                consultAppendix.setAppendixId(UUID.randomUUID().toString());
                consultAppendix.setRefId(uuid);
                consultAppendix.setUploadTime(consultAppendix.getUploadTime() == null ? new Date() : consultAppendix.getUploadTime());
                consultAppendix.setAppendixType(appendixType);
            }
        }
        return null;
    }

    public static List<ConsultAppendixDetailsPo> toConsultAppendixDetailsPos(List<LawcaseConsultAppendix> appendixList) {
        if (appendixList != null) {
            List<ConsultAppendixDetailsPo> list = new ArrayList<>();
            ConsultAppendixDetailsPo appendixDetails = null;
            for (LawcaseConsultAppendix appendix :
                    appendixList) {
                appendixDetails = new ConsultAppendixDetailsPo();
                appendixDetails.setRefId(appendix.getRefId());
                appendixDetails.setAppendixId(appendix.getAppendixId());
                appendixDetails.setAppendixName(appendix.getAppendixName());
                appendixDetails.setFileSize(appendix.getFileSize());
                appendixDetails.setOssResourceId(appendix.getOssResourceId());
                appendixDetails.setFullName(appendix.getFullName());
                appendixDetails.setUploadTime(appendix.getUploadTime());
                list.add(appendixDetails);
            }
            return list;
        }
        return null;
    }
}
