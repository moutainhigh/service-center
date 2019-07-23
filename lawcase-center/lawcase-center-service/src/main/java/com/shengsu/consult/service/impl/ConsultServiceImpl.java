package com.shengsu.consult.service.impl;


import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.consult.entity.Consult;
import com.shengsu.consult.mapper.ConsultMapper;
import com.shengsu.consult.service.ConsultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("consultService")
public class ConsultServiceImpl extends BaseServiceImpl<Consult, String> implements ConsultService {
	@Autowired
	ConsultMapper consultMapper;
	@Override
	public BaseMapper<Consult, String> getBaseMapper() {
		return consultMapper;
	}
}
