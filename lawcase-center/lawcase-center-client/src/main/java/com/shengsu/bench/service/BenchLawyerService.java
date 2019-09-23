package com.shengsu.bench.service;


import com.shengsu.base.service.BaseService;
import com.shengsu.bench.entity.BenchJournalism;
import com.shengsu.bench.entity.BenchLawyer;
import com.shengsu.bench.po.BenchLawyerQueryPo;
import com.shengsu.bench.vo.BenchLawyerCreateVo;
import com.shengsu.bench.vo.BenchLawyerDeleteVo;
import com.shengsu.bench.vo.BenchLawyerQueryVo;
import com.shengsu.bench.vo.BenchLawyerUpdateVo;

import java.util.List;

/**
 * Created by zyc on 2019/8/7.
 */
public interface BenchLawyerService extends BaseService<BenchLawyer, String> {


    BenchLawyer selectByName(String name);

    BenchLawyer selectById(Long id);

    int softDelete(Long id);
}
