package com.shengsu.website.consult.util;


import com.shengsu.website.consult.entity.LawcaseConsult;
import com.shengsu.website.consult.entity.LawcaseConsultAppendix;
import com.shengsu.website.consult.po.ConsultAppendixDetailsListPo;
import com.shengsu.website.consult.po.ConsultAppendixDetailsPo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by zyc on 2019/12/5.
 */
public class LawcaseConsultAppendixUtils {
    public static List<ConsultAppendixDetailsListPo> toConsultAppendixDetailsListPo(List<LawcaseConsult> root){
        List<ConsultAppendixDetailsListPo> consultAppendixDetailsListPos = new ArrayList<>();
        for(LawcaseConsult lawcaseConsult : root){
            ConsultAppendixDetailsListPo consultAppendixDetailsListPo = new ConsultAppendixDetailsListPo();
            consultAppendixDetailsListPo.setConsultId(lawcaseConsult.getConsultId());
            consultAppendixDetailsListPo.setConsultContent(lawcaseConsult.getConsultContent());
            consultAppendixDetailsListPo.setConsultor(lawcaseConsult.getConsultor());
            consultAppendixDetailsListPo.setContact(lawcaseConsult.getContact());
            consultAppendixDetailsListPo.setTarget(lawcaseConsult.getTarget());
            consultAppendixDetailsListPo.setReply(lawcaseConsult.getReply());
            consultAppendixDetailsListPo.setLawyer(lawcaseConsult.getLawyer());
            consultAppendixDetailsListPo.setSource(lawcaseConsult.getSource());
            consultAppendixDetailsListPos.add(consultAppendixDetailsListPo);
        }
        return consultAppendixDetailsListPos;
    }

    public static List<LawcaseConsultAppendix> toLawcaseFinanceAppendixs(String uuid, String uploaderUserId, String appendixType, List<LawcaseConsultAppendix> appendixList) {
        if (StringUtils.isNotBlank(uuid) && appendixList != null) {
            for (LawcaseConsultAppendix financeAppendix :
                    appendixList) {
                financeAppendix.setAppendixId(UUID.randomUUID().toString());
                financeAppendix.setRefId(uuid);
                financeAppendix.setUploaderUserId(StringUtils.isBlank(financeAppendix.getUploaderUserId())?uploaderUserId:financeAppendix.getUploaderUserId());
                financeAppendix.setUploadTime(financeAppendix.getUploadTime() == null ? new Date() : financeAppendix.getUploadTime());
                financeAppendix.setAppendixType(appendixType);
            }
        }
        return null;
    }

    public static List<ConsultAppendixDetailsPo> toFinanceAppendixDetailsPos(List<LawcaseConsultAppendix> appendixList) {
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
