package com.shengsu.bench.mapper;


import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.bench.entity.BenchJournalism;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by zyc on 2019/8/9.
 */
@Mapper
public interface BenchJournalismMapper extends BaseMapper<BenchJournalism,String>{

     Integer countJournalism(BenchJournalism benchournalism);

     List<BenchJournalism> selectJournalismByPage(BenchJournalism benchournalism);

     int  save(BenchJournalism benchournalism);

     int  batchDelete(List<String> ids);

     int  update(BenchJournalism benchournalism);

     BenchJournalism selectById(Long id);
}
