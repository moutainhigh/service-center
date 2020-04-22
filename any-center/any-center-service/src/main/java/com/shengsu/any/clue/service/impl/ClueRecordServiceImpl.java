package com.shengsu.any.clue.service.impl;

import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.clue.po.ClueRecordPo;
import com.shengsu.any.clue.entity.ClueRecord;
import com.shengsu.any.clue.mapper.ClueRecordMapper;
import com.shengsu.any.clue.service.ClueRecordService;
import com.shengsu.any.clue.util.ClueRecordUtils;
import com.shengsu.any.clue.vo.ClueRecordUpdateVo;
import com.shengsu.any.clue.vo.ClueRecordVo;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:48
 **/
@Service("clueRecordService")
public class ClueRecordServiceImpl extends BaseServiceImpl<ClueRecord, String> implements ClueRecordService {
    @Autowired
    private ClueRecordMapper clueRecordMapper;
    @Override
    public BaseMapper<ClueRecord, String> getBaseMapper() {
        return clueRecordMapper;
    }
    /**
     * @Author Bell
     * @Description 查询线索跟进记录
     * @Date  2020/2/15
     * @Param [clueRecord]
     * @return com.shengsu.result.ResultBean
     **/
    @Override
    public ResultBean listMyClueRecord(ClueRecord clueRecord){
        List<ClueRecord> clueRecords = clueRecordMapper.listMyClueRecord(clueRecord);
        List<ClueRecordPo> clueRecordPos = ClueRecordUtils.toClueRecordPo(clueRecords);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, clueRecordPos);
    }
    /**
     * @Author Bell
     * @Description 编辑线索跟进记录
     * @Date  2020/2/15
     * @Param [clueRecord]
     * @return com.shengsu.result.ResultBean
     **/
    @Override
    public ResultBean updateRecord(ClueRecordUpdateVo clueRecordUpdateVo){
        ClueRecord clueRecord = ClueRecordUtils.toClueRecord(clueRecordUpdateVo);
        clueRecordMapper.update(clueRecord);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }
    /**
     * @Author Bell
     * @Description 新建线索跟进记录
     * @Date  2020/2/15
     * @Param [clueRecordVo]
     * @return com.shengsu.result.ResultBean
     **/
    @Override
    public ResultBean saveRecord(ClueRecordVo clueRecordVo){
        ClueRecord clueRecord = ClueRecordUtils.toClueRecord(clueRecordVo);
        clueRecordMapper.save(clueRecord);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }
}
