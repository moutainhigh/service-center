package com.shengsu.service.Impl;


import com.shengsu.app.exception.BizException;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.constant.AntiDuplicateEnum;
import com.shengsu.helper.entity.AntiDuplicate;
import com.shengsu.helper.service.AntiDuplicateMapper;
import com.shengsu.service.AntiDuplicateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: AntiDuplicateServiceImpl
 * @Description: 防重复Service
 * @author zxh
 * @date 2018-10-30
 * 
 */
@Service("antiDuplicateService")
public class AntiDuplicateServiceImpl extends BaseServiceImpl<AntiDuplicate, String> implements AntiDuplicateService{
	@Autowired
	private AntiDuplicateMapper antiDuplicateMapper;


	@Override
	public BaseMapper<AntiDuplicate, String> getBaseMapper() {
		return antiDuplicateMapper;
	}

	/**
	 * 防重复-单个
	 * @param type 操作类型
	 * @param duplicateId 防重Id
	 */
	public void antiDuplicate(AntiDuplicateEnum type, String duplicateId) throws BizException {
        AntiDuplicate antiDuplicate = new AntiDuplicate();
        antiDuplicate.setDuplicateId(type + duplicateId);
        antiDuplicateMapper.save(antiDuplicate);
	}
	
	/**
	 * 防重复-批量
	 * @param type 操作类型
	 * @param duplicateIds 防重id列表
	 */
	public void antiDuplicates(AntiDuplicateEnum type, List<String> duplicateIds){
        List<AntiDuplicate>	antiDuplicates = new ArrayList<AntiDuplicate>();
        for(String duplicateId : duplicateIds){
            AntiDuplicate antiDuplicate = new AntiDuplicate();
            antiDuplicate.setDuplicateId(type+duplicateId);
            antiDuplicates.add(antiDuplicate);
        }
        antiDuplicateMapper.batchSave(antiDuplicates);
	}
	
	/**
	 * 防重复清理-单个
	 * @param type 操作类型
	 * @param duplicateId 防重Id
	 */
	public void antiDuplicateClear(AntiDuplicateEnum type, String duplicateId){
		antiDuplicateMapper.delete(type+duplicateId);
	}
	
	/**
	 * 防重复清理-批量
	 * @param type 操作类型
	 * @param duplicateIds 防重id列表
	 */
	public void antiDuplicatesClear(AntiDuplicateEnum type, List<String> duplicateIds){
		List<String> duplicateIdList = new ArrayList<>();
		for(String duplicateId : duplicateIds){
			duplicateIdList.add(type+duplicateId);
		}
		antiDuplicateMapper.batchDelete(duplicateIdList);	
	}
}
