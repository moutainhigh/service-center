package com.shengsu.user.service.impl;

import org.springframework.stereotype.Service;

/**
 * creator:zxh
 * 用户服务的别名服务、用于解决服务名称冲突问题
 */
@Service("aliasUserService")
public class AliasUserServiceImpl extends UserServiceImpl {
}
