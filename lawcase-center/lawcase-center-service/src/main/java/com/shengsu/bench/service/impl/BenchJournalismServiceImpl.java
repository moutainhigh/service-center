package com.shengsu.bench.service.impl;


import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.bench.constant.BenchConstant;
import com.shengsu.bench.entity.BenchJournalism;
import com.shengsu.bench.mapper.BenchJournalismMapper;
import com.shengsu.bench.service.BenchJournalismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * Created by zyc on 2019/8/9.
 */
@Service(value = "benchJournalismService")
public class BenchJournalismServiceImpl  extends BaseServiceImpl<BenchJournalism,String> implements BenchJournalismService {

    @Autowired
    private BenchJournalismMapper benchJournalismMapper;


    @Override
    public BaseMapper<BenchJournalism, String> getBaseMapper() {
        return benchJournalismMapper;
    }


    /**
     * 功能描述: <br>
     * 〈〉新闻资讯分页查询
     * @Param: [journalism]
     * @Return: com.shengsu.base.domain.ResultBean
     * @Author: zyc
     * @Date: 2019/8/8 15:00
     */
    @Override
    public List<BenchJournalism> listByPage(BenchJournalism journalism){

        //分页获取
        List<BenchJournalism> benchJournalisms = benchJournalismMapper.selectJournalismByPage(journalism);
        if(benchJournalisms==null||benchJournalisms.isEmpty()){
            return null;
        }
        return benchJournalisms;
    }
    /**
     * 功能描述: <br>
     * 〈获取总条数〉
     * @Param: [benchJournalism]
     * @Return: int
     * @Author: zyc
     * @Date: 2019/8/9 16:43
     */
    public int countAll(BenchJournalism benchJournalism){
        return benchJournalismMapper.countJournalism(benchJournalism);
    }

    @Override
    public int create(BenchJournalism journalism) {
        return benchJournalismMapper.save(journalism);
    }
    @Override
    public int batchDelete(List<String> ids) {
        return benchJournalismMapper.batchDelete(ids);
    }
    @Override
    public int update(BenchJournalism journalism) {
        return benchJournalismMapper.update(journalism);
    }

    @Override
    public BenchJournalism selectById(Long id) {
        return benchJournalismMapper.selectById(id);
    }
}
