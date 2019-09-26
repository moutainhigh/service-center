package com.shengsu.bench.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.shengsu.bench.entity.BenchLawyer;
import com.shengsu.bench.po.BenchLawyerQueryPo;
import com.shengsu.bench.vo.BenchLawyerCreateVo;
import com.shengsu.bench.vo.BenchLawyerListPageVo;
import com.shengsu.bench.vo.BenchLawyerUpdateVo;
import com.shengsu.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyc on 2019/9/23.
 */
public class BenchLawyerUtils {

    public static BenchLawyer toBenchLawyer(BenchLawyerCreateVo lawyerCreateVo) {
        if (lawyerCreateVo != null) {
            BenchLawyer benchLawyer = new BenchLawyer();
            benchLawyer.setHeadOssId(lawyerCreateVo.getHeadOssId());
            benchLawyer.setPortraitOssId(lawyerCreateVo.getPortraitOssId());
            benchLawyer.setName(lawyerCreateVo.getName());
            benchLawyer.setField(lawyerCreateVo.getField());
            benchLawyer.setMajorExperience(lawyerCreateVo.getMajorExperience());
            if(lawyerCreateVo.getRepresentativeCaseList()!=null){
                benchLawyer.setRepresentativeCase(JSON.toJSONString(lawyerCreateVo.getRepresentativeCaseList()));
            }
            benchLawyer.setIsGoldMedal(lawyerCreateVo.getIsGoldMedal());
            benchLawyer.setIsTop(lawyerCreateVo.getIsTop());
            benchLawyer.setWeight(lawyerCreateVo.getWeight());
            return benchLawyer;
        }
        return null;
    }

    public static BenchLawyer toBenchLawyer(BenchLawyerUpdateVo lawyerUpdateVo) {
        if (lawyerUpdateVo != null) {
            BenchLawyer benchLawyer = new BenchLawyer();
            benchLawyer.setId(lawyerUpdateVo.getId());
            benchLawyer.setHeadOssId(lawyerUpdateVo.getHeadOssId());
            benchLawyer.setPortraitOssId(lawyerUpdateVo.getPortraitOssId());
            benchLawyer.setName(lawyerUpdateVo.getName());
            benchLawyer.setField(lawyerUpdateVo.getField());
            benchLawyer.setMajorExperience(lawyerUpdateVo.getMajorExperience());
            if(lawyerUpdateVo.getRepresentativeCaseList()!=null){
                benchLawyer.setRepresentativeCase(JSON.toJSONString(lawyerUpdateVo.getRepresentativeCaseList()));
            }
            benchLawyer.setIsGoldMedal(lawyerUpdateVo.getIsGoldMedal());
            benchLawyer.setIsTop(lawyerUpdateVo.getIsTop());
            benchLawyer.setWeight(lawyerUpdateVo.getWeight());
            return benchLawyer;
        }
        return null;
    }

    public static BenchLawyerQueryPo toBenchLawyerQueryPo(BenchLawyer lawyer) {
        if (lawyer != null) {
            BenchLawyerQueryPo lawyerQueryPo = new BenchLawyerQueryPo();
            lawyerQueryPo.setId(lawyer.getId());
            lawyerQueryPo.setHeadOssId(lawyer.getHeadOssId());
            lawyerQueryPo.setHeadOssUrl(lawyer.getHeadOssUrl());
            lawyerQueryPo.setPortraitOssId(lawyer.getPortraitOssId());
            lawyerQueryPo.setPortraitOssUrl(lawyer.getPortraitOssUrl());
            lawyerQueryPo.setName(lawyer.getName());
            lawyerQueryPo.setField(lawyer.getField());
            lawyerQueryPo.setMajorExperience(lawyer.getMajorExperience());
            if(lawyer.getRepresentativeCase()!=null){
                lawyerQueryPo.setRepresentativeCaseList(JSONArray.parseArray(lawyer.getRepresentativeCase(),String.class));
            }
            lawyerQueryPo.setIsGoldMedal(lawyer.getIsGoldMedal());
            lawyerQueryPo.setWeight(lawyer.getWeight());
            return lawyerQueryPo;
        }
        return null;
    }

    public static BenchLawyer toBenchLawyer(BenchLawyerListPageVo lawyerListPageVo) {
        if (lawyerListPageVo != null) {
            BenchLawyer benchLawyer = new BenchLawyer();
            benchLawyer.setName(StringUtil.ToLikeStr(lawyerListPageVo.getName()));
            benchLawyer.setIsGoldMedal(lawyerListPageVo.getIsGoldMedal());
            Integer page = lawyerListPageVo.getPage();
            benchLawyer.setPage(page==null?1:page);
            Integer pageSize = lawyerListPageVo.getPageSize();
            benchLawyer.setPageSize(pageSize==null?10:pageSize);
            return benchLawyer;
        }
        return null;
    }

    public static List<BenchLawyerQueryPo> toBenchLawyerQueryPo(List<BenchLawyer> lawyers) {
        if (lawyers != null) {
            List<BenchLawyerQueryPo> list = new ArrayList<>();
            BenchLawyerQueryPo lawyerQueryPo;
            for (BenchLawyer lawyer :
                    lawyers) {
                lawyerQueryPo = new BenchLawyerQueryPo();
                lawyerQueryPo.setId(lawyer.getId());
                lawyerQueryPo.setHeadOssId(lawyer.getHeadOssId());
                lawyerQueryPo.setHeadOssUrl(lawyer.getHeadOssUrl());
                lawyerQueryPo.setPortraitOssId(lawyer.getPortraitOssId());
                lawyerQueryPo.setPortraitOssUrl(lawyer.getPortraitOssUrl());
                lawyerQueryPo.setName(lawyer.getName());
                lawyerQueryPo.setField(lawyer.getField());
                lawyerQueryPo.setMajorExperience(lawyer.getMajorExperience());
                if(lawyer.getRepresentativeCase()!=null){
                    lawyerQueryPo.setRepresentativeCaseList(JSONArray.parseArray(lawyer.getRepresentativeCase(),String.class));
                }
                lawyerQueryPo.setIsGoldMedal(lawyer.getIsGoldMedal());
                lawyerQueryPo.setWeight(lawyer.getWeight());
                list.add(lawyerQueryPo);
            }

            return list;
        }
        return null;
    }
}
