package com.shengsu.system.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.system.entity.SystemDictionary;

import java.util.List;


public interface SystemDictionaryService extends BaseService<SystemDictionary,String> {
    List<SystemDictionary> getManyByDictCodes(List<String> dictCodes);
}
