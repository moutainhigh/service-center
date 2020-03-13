package com.shengsu.helper.service;

import com.shengsu.helper.constant.AntiDuplicateEnum;

import java.util.List;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-03-13 16:22
 **/
public interface AntiDuplicateService {
    void antiDuplicate(AntiDuplicateEnum type, String duplicateId) throws Exception;

    void antiDuplicates(AntiDuplicateEnum type, List<String> duplicateIds);

    void antiDuplicateClear(AntiDuplicateEnum type, String duplicateId);

    void antiDuplicatesClear(AntiDuplicateEnum type, List<String> duplicateIds);
}
