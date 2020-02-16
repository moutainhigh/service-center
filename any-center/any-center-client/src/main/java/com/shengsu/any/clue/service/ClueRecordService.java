package com.shengsu.any.clue.service;

import com.shengsu.any.clue.entity.ClueRecord;
import com.shengsu.any.clue.vo.ClueRecordUpdateVo;
import com.shengsu.any.clue.vo.ClueRecordVo;
import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 11:52
 **/
public interface ClueRecordService extends BaseService<ClueRecord,String> {
    ResultBean listMyClueRecord(ClueRecord clueRecord);
    ResultBean updateRecord(ClueRecordUpdateVo clueRecordUpdateVo);
    ResultBean saveRecord(ClueRecordVo clueRecordVo);
}
