package com.shengsu.any.user.mapper;

import com.shengsu.any.user.entity.User;
import com.shengsu.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-07 17:23
 **/
@Mapper
public interface UserMapper extends BaseMapper<User,String> {
    User getUserByTel(String tel);

    User selectByWeChatUnionId(String weChatUnionId);
}
