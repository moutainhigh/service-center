package com.shengsu.bench.util;

import com.shengsu.bench.entity.BenchLawyer;
import com.shengsu.bench.po.BenchLawyerQueryPo;
import com.shengsu.bench.vo.BenchLawyerCreateVo;
import com.shengsu.bench.vo.BenchLawyerListPageVo;
import com.shengsu.bench.vo.BenchLawyerUpdateVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyc on 2019/9/23.
 */
public class BenchLawyerUtils {

    public static BenchLawyer toBenchLawyer(BenchLawyerCreateVo lawyerCreateVo) {
        if (lawyerCreateVo != null) {
            BenchLawyer benchLawyer = new BenchLawyer();
            benchLawyer.setPortraitOssId(lawyerCreateVo.getPortraitOssId());
            benchLawyer.setName(lawyerCreateVo.getName());
            benchLawyer.setField(lawyerCreateVo.getField());
            benchLawyer.setMajorExperience(lawyerCreateVo.getMajorExperience());
            benchLawyer.setRepresentativeCase(lawyerCreateVo.getRepresentativeCase());
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
            benchLawyer.setPortraitOssId(lawyerUpdateVo.getPortraitOssId());
            benchLawyer.setName(lawyerUpdateVo.getName());
            benchLawyer.setField(lawyerUpdateVo.getField());
            benchLawyer.setMajorExperience(lawyerUpdateVo.getMajorExperience());
            benchLawyer.setRepresentativeCase(lawyerUpdateVo.getRepresentativeCase());
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
            lawyerQueryPo.setPortraitOssId(lawyer.getPortraitOssId());
            lawyerQueryPo.setPortraitOssUrl(lawyer.getPortraitOssUrl());
            lawyerQueryPo.setName(lawyer.getName());
            lawyerQueryPo.setField(lawyer.getField());
            lawyerQueryPo.setMajorExperience(lawyer.getMajorExperience());
            lawyerQueryPo.setRepresentativeCase(lawyer.getRepresentativeCase());
            lawyerQueryPo.setIsGoldMedal(lawyer.getIsGoldMedal());
            lawyerQueryPo.setIsTop(lawyer.getIsTop());
            return lawyerQueryPo;
        }
        return null;
    }

    public static BenchLawyer toBenchLawyer(BenchLawyerListPageVo lawyerListPageVo) {
        if (lawyerListPageVo != null) {
            BenchLawyer benchLawyer = new BenchLawyer();
            benchLawyer.setName(lawyerListPageVo.getName());
            benchLawyer.setIsGoldMedal(lawyerListPageVo.getIsGoldMedal());
            benchLawyer.setPage(lawyerListPageVo.getPage());
            benchLawyer.setPageSize(lawyerListPageVo.getPageSize());
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
                lawyerQueryPo.setPortraitOssUrl(lawyer.getPortraitOssUrl());
                lawyerQueryPo.setPortraitOssId(lawyer.getPortraitOssId());
                lawyerQueryPo.setName(lawyer.getName());
                lawyerQueryPo.setField(lawyer.getField());
                lawyerQueryPo.setMajorExperience(lawyer.getMajorExperience());
                lawyerQueryPo.setRepresentativeCase(lawyer.getRepresentativeCase());
                lawyerQueryPo.setIsGoldMedal(lawyer.getIsGoldMedal());
                lawyerQueryPo.setIsTop(lawyer.getIsTop());
                list.add(lawyerQueryPo);
            }

            return list;
        }
        return null;
    }
}
