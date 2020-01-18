package com.shengsu.any.clue.service.impl;

import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.mapper.ClueMapper;
import com.shengsu.any.clue.service.ClueService;
import com.shengsu.any.clue.util.ClueUtils;
import com.shengsu.any.clue.vo.ClueShelfVo;
import com.shengsu.any.clue.vo.ClueVo;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:48
 **/
@Service("clueService")
public class ClueServiceImpl extends BaseServiceImpl<Clue, String> implements ClueService {
    @Autowired
    private ClueMapper clueMapper;
    @Override
    public BaseMapper<Clue, String> getBaseMapper() {
        return clueMapper;
    }
    @Override
    public ResultBean create(ClueVo clueVo){
        Clue clue = ClueUtils.toClue(clueVo);
        clueMapper.save(clue);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }
    @Override
    public ResultBean clueListByPage(Clue record){
        Map<String,Object> map = new HashMap();
        int totalCount = clueMapper.countAll(record);
        List<Clue> clues=clueMapper.listByPage(record);
        if(totalCount > 0){
        map.put("root",clues);
        map.put("totalCount",totalCount);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS,map);
    }
    @Override
    public ResultBean edit(Clue record){
        clueMapper.update(record);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }
    @Override
    public ResultBean onShelf(ClueShelfVo clueShelfVo){
        clueMapper.onShelf(clueShelfVo);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    @Override
    public ResultBean offShelf(ClueShelfVo clueShelfVo) {
        clueMapper.offShelf(clueShelfVo);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    @Override
    public ResultBean clueDelete(ClueShelfVo clueShelfVo) {
        clueMapper.softDelete(clueShelfVo);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

}
