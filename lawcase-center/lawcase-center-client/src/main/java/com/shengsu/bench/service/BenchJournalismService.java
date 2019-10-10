package com.shengsu.bench.service;


import com.shengsu.base.service.BaseService;
import com.shengsu.bench.entity.BenchJournalism;

import java.util.List;

/**
 * Created by zyc on 2019/8/7.
 */
public interface BenchJournalismService extends BaseService<BenchJournalism, String> {


    int countAll(BenchJournalism benchJournalism);

    List<BenchJournalism> listByPage(BenchJournalism journalism);

    int create(BenchJournalism journalism);

    int batchDelete(List<String> ids);

    int update(BenchJournalism benchJournalism);

    BenchJournalism selectById(Long Id);

    BenchJournalism selectByTitle(String title);
}
