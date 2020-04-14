package com.shengsu.bench.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.bench.entity.BenchLawyer;
import com.shengsu.bench.mapper.BenchLawyerMapper;
import com.shengsu.bench.service.BenchLawyerService;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.service.OssService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by zyc on 2019/8/9.
 */
@Service(value = "benchLawyerService")
public class BenchLawyerServiceImpl extends BaseServiceImpl<BenchLawyer, String> implements BenchLawyerService {

    @Autowired
    private BenchLawyerMapper lawyerMapper;

    @Autowired
    OssService ossService;


    @Override
    public BaseMapper<BenchLawyer, String> getBaseMapper() {
        return lawyerMapper;
    }

    @Override
    public BenchLawyer selectByName(String name) {
        return lawyerMapper.selectByName(name);
    }

    @Override
    public BenchLawyer selectById(Long id) {
        BenchLawyer benchLawyer = lawyerMapper.selectById(id);
        if (benchLawyer != null && StringUtils.isNoneBlank(benchLawyer.getPortraitOssId())) {
            benchLawyer.setPortraitOssUrl(ossService.getUrl(OssConstant.OSS_LAWYER_FILEDIR, benchLawyer.getPortraitOssId()));
            benchLawyer.setHeadOssUrl(ossService.getUrl(OssConstant.OSS_LAWYER_FILEDIR, benchLawyer.getHeadOssId()));
        }
        return benchLawyer;
    }

    @Override
    public List<BenchLawyer> listByPage(BenchLawyer benchLawyer) {
        List<BenchLawyer> lawyers = lawyerMapper.listByPage(benchLawyer);
        //使用ossid获取ossurl
        List<String> portraitOssIds = new ArrayList<>();
        List<String> headOssIds = new ArrayList<> ();
        for (BenchLawyer lawyer :
                lawyers) {
            String portraitOssId = lawyer.getPortraitOssId();
            portraitOssIds.add(portraitOssId);

            String headOssId = lawyer.getHeadOssId();
            headOssIds.add(headOssId);
        }
        Map<String, String> mapPortrait = ossService.getUrls(OssConstant.OSS_LAWYER_FILEDIR,portraitOssIds);
        Map<String, String> mapHead = ossService.getUrls(OssConstant.OSS_LAWYER_FILEDIR,headOssIds);
        for (BenchLawyer lawyer :
                lawyers) {
            lawyer.setPortraitOssUrl(mapPortrait.get(lawyer.getPortraitOssId()));
            lawyer.setHeadOssUrl(mapHead.get(lawyer.getHeadOssId()));
        }

        return lawyers;
    }

    @Override
    public int softDelete(Long id) {
        return lawyerMapper.softDelete(id);
    }


}
