package com.shengsu.service;

import com.shengsu.app.exception.BizException;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.helper.constant.AntiDuplicateEnum;
import com.shengsu.helper.entity.AntiDuplicate;

import java.util.List;

/**
 * Created by Bell on 2019/11/14.
 */
public interface AntiDuplicateService {
    BaseMapper<AntiDuplicate, String> getBaseMapper();

    void antiDuplicate(AntiDuplicateEnum type, String duplicateId)throws BizException;

    void antiDuplicates(AntiDuplicateEnum type, List<String> duplicateIds);

    void antiDuplicateClear(AntiDuplicateEnum type, String duplicateId);

    void antiDuplicatesClear(AntiDuplicateEnum type, List<String> duplicateIds);
}
