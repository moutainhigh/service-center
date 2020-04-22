package com.shengsu.helper.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.exception.BizException;
import com.shengsu.helper.constant.AntiDuplicateEnum;
import com.shengsu.helper.entity.AntiDuplicate;
import com.shengsu.helper.mapper.AntiDuplicateMapper;
import com.shengsu.helper.service.AntiDuplicateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zxh
 * @ClassName: AntiDuplicateService
 * @Description: 防重复Service
 * @date 2018-10-30
 */
@Service("antiDuplicateService")
public class AntiDuplicateServiceImpl extends BaseServiceImpl<AntiDuplicate, String> implements AntiDuplicateService {
    @Autowired
    private AntiDuplicateMapper antiDuplicateMapper;


    @Override
    public BaseMapper<AntiDuplicate, String> getBaseMapper() {
        return antiDuplicateMapper;
    }

    /**
     * 防重复-单个
     *
     * @param type        操作类型
     * @param duplicateId 防重Id
     */
    @Override
    public void antiDuplicate(AntiDuplicateEnum type, String duplicateId) throws BizException {
        AntiDuplicate antiDuplicate = new AntiDuplicate();
        antiDuplicate.setDuplicateId(duplicateId);
        antiDuplicate.setDuplicateType(type);
        antiDuplicateMapper.save(antiDuplicate);
    }

    /**
     * 防重复-批量
     *
     * @param type         操作类型
     * @param duplicateIds 防重id列表
     */
    @Override
    public void antiDuplicates(AntiDuplicateEnum type, List<String> duplicateIds) {
        List<AntiDuplicate> antiDuplicates = new ArrayList<AntiDuplicate>();
        for (String duplicateId : duplicateIds) {
            AntiDuplicate antiDuplicate = new AntiDuplicate();
            antiDuplicate.setDuplicateId(duplicateId);
            antiDuplicate.setDuplicateType(type);
            antiDuplicates.add(antiDuplicate);
        }
        antiDuplicateMapper.batchSave(antiDuplicates);
    }

    /**
     * 防重复清理-单个
     *
     * @param type        操作类型
     * @param duplicateId 防重Id
     */
    @Override
    public void antiDuplicateClear(AntiDuplicateEnum type, String duplicateId) {
        AntiDuplicate antiDuplicate = new AntiDuplicate();
        antiDuplicate.setDuplicateId(duplicateId);
        antiDuplicate.setDuplicateType(type);
        antiDuplicateMapper.delete(antiDuplicate);
    }

    /**
     * 防重复清理-批量
     *
     * @param type         操作类型
     * @param duplicateIds 防重id列表
     */
    @Override
    public void antiDuplicatesClear(AntiDuplicateEnum type, List<String> duplicateIds) {
        List<AntiDuplicate> antiDuplicates = new ArrayList<AntiDuplicate>();
        for (String duplicateId : duplicateIds) {
            AntiDuplicate antiDuplicate = new AntiDuplicate();
            antiDuplicate.setDuplicateId(duplicateId);
            antiDuplicate.setDuplicateType(type);
            antiDuplicates.add(antiDuplicate);
        }
        antiDuplicateMapper.batchDeleteAntiDuplicate(antiDuplicates);
    }
}
