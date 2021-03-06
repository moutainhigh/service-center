package com.shengsu.any.user.mapper;

import com.shengsu.any.user.entity.User;
import com.shengsu.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-07 17:23
 **/
@Mapper
public interface UserMapper extends BaseMapper<User,String> {
    User selectByTel(String tel);

    User selectByWeChatOpenid(String wechatOpenid);

    void bandWechat(User user);

    void pass(String userId);

    void reject(String userId);

    void uploadHeadImage(User user);

    String getUserIdByTel(String tel);

    List<User> getAllPushUsers();
}
