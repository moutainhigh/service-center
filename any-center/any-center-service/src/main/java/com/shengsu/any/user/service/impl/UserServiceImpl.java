package com.shengsu.any.user.service.impl;

import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.mapper.UserMapper;
import com.shengsu.any.user.service.UserService;
import com.shengsu.any.user.vo.SmsSendVo;
import com.shengsu.any.user.vo.UserLoginVo;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.service.SmsService;
import com.shengsu.helper.util.SmsUtil;
import com.shengsu.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-07 17:20
 **/
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {
    @Autowired
    private SmsService smsService;
    @Resource
    private RedisTemplate<Serializable, Serializable> redisTemplate;
    @Value("${sms.expireTimeSecond}")
    private long SMS_INVALID_TIME;
    @Autowired
    private UserMapper userMapper;
    @Override
    public BaseMapper<User, String> getBaseMapper() {
        return userMapper;
    }

    @Override
    public ResultBean sendSms(SmsSendVo smsSendVo) {
        String tel = smsSendVo.getTel();
        smsSendVo.setSmsCode(SmsUtil.getSixRandomCode());
        // 将短信验证码存储到redis,时效是1分钟
        redisTemplate.opsForValue().set(tel, smsSendVo.getSmsCode(), SMS_INVALID_TIME, TimeUnit.SECONDS);
        // 发送手机验证码
        return smsService.sendSms(tel, smsSendVo.getSmsCode());
    }

    @Override
    public ResultBean login(UserLoginVo loginUserVo) throws IOException {
        return null;
    }
}
