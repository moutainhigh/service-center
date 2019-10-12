package com.shengsu.bench.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.bench.constant.BenchConstant;
import com.shengsu.bench.entity.BenchJournalism;
import com.shengsu.bench.entity.BenchLawyer;
import com.shengsu.bench.mapper.BenchJournalismMapper;
import com.shengsu.bench.mapper.BenchLawyerMapper;
import com.shengsu.bench.po.BenchLawyerQueryPo;
import com.shengsu.bench.service.BenchJournalismService;
import com.shengsu.bench.service.BenchLawyerService;
import com.shengsu.bench.vo.BenchLawyerCreateVo;
import com.shengsu.bench.vo.BenchLawyerDeleteVo;
import com.shengsu.bench.vo.BenchLawyerQueryVo;
import com.shengsu.bench.vo.BenchLawyerUpdateVo;
import com.shengsu.util.OssClientUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by zyc on 2019/8/9.
 */
@Service(value = "benchLawyerService")
public class BenchLawyerServiceImpl extends BaseServiceImpl<BenchLawyer, String> implements BenchLawyerService {

    @Autowired
    private BenchLawyerMapper lawyerMapper;

    @Autowired
    OssClientUtil ossClientUtil;


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
            benchLawyer.setPortraitOssUrl(ossClientUtil.getUrl(BenchConstant.OSS_LAWYER_FILEDIR, benchLawyer.getPortraitOssId()));
            benchLawyer.setHeadOssUrl(ossClientUtil.getUrl(BenchConstant.OSS_LAWYER_FILEDIR, benchLawyer.getHeadOssId()));
        }
        return benchLawyer;
    }

    @Override
    public List<BenchLawyer> listByPage(BenchLawyer benchLawyer) {
        List<BenchLawyer> lawyers = lawyerMapper.listByPage(benchLawyer);
        //使用ossid获取ossurl
        for (BenchLawyer lawyer :
                lawyers) {
            String portraitOssId = lawyer.getPortraitOssId();
            lawyer.setPortraitOssUrl(ossClientUtil.getUrl(BenchConstant.OSS_LAWYER_FILEDIR, portraitOssId));

            String headOssId = lawyer.getHeadOssId();
            lawyer.setHeadOssUrl(ossClientUtil.getUrl(BenchConstant.OSS_LAWYER_FILEDIR, headOssId));
        }
        return lawyers;
    }

    @Override
    public int softDelete(Long id) {
        return lawyerMapper.softDelete(id);
    }


}
