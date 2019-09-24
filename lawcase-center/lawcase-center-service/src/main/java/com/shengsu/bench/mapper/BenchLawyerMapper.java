package com.shengsu.bench.mapper;


import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.bench.entity.BenchJournalism;
import com.shengsu.bench.entity.BenchLawyer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by zyc on 2019/8/9.
 */
@Mapper
public interface BenchLawyerMapper extends BaseMapper<BenchLawyer, String> {

    BenchLawyer selectByName(String name);

    BenchLawyer selectById(Long id);

    int softDelete(Long id);
}
