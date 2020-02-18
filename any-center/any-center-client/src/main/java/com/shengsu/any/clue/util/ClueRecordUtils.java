package com.shengsu.any.clue.util;

import com.shengsu.any.clue.Po.ClueRecordPo;
import com.shengsu.any.clue.entity.ClueRecord;
import com.shengsu.any.clue.vo.ClueRecordUpdateVo;
import com.shengsu.any.clue.vo.ClueRecordVo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-14 18:21
 **/
public class ClueRecordUtils {
    public static List<ClueRecordPo> toClueRecordPo(List<ClueRecord> clueRecords){
        List<ClueRecordPo> clueRecordPos = new ArrayList<>();
        for(ClueRecord clueRecord : clueRecords){
            ClueRecordPo clueRecordPo = new ClueRecordPo();
            clueRecordPo.setContent(clueRecord.getContent());
            clueRecordPo.setRecordId(clueRecord.getRecordId());
            clueRecordPo.setModifyTime(clueRecord.getModifyTime());
            clueRecordPos.add(clueRecordPo);
        }
        return clueRecordPos;
    }
    public static ClueRecord toClueRecord(ClueRecordVo clueRecordVo){
        ClueRecord clueRecord = new ClueRecord();
        clueRecord.setRecordId(UUID.randomUUID().toString());
        clueRecord.setContent(clueRecordVo.getContent());
        clueRecord.setClueId(clueRecordVo.getClueId());
        clueRecord.setUserId(clueRecordVo.getUserId());
        return clueRecord;
    }
    public static ClueRecord toClueRecord(ClueRecordUpdateVo clueRecordUpdateVo){
        ClueRecord clueRecord = new ClueRecord();
        clueRecord.setRecordId(clueRecordUpdateVo.getRecordId());
        clueRecord.setContent(clueRecordUpdateVo.getContent());
        clueRecord.setClueId(clueRecordUpdateVo.getClueId());
        clueRecord.setUserId(clueRecordUpdateVo.getUserId());
        return clueRecord;
    }
}
