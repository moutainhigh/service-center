package com.shengsu.any.user.service.impl;

import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.mapper.UserMapper;
import com.shengsu.any.user.po.UserDetailsPo;
import com.shengsu.any.user.service.AuthorizedService;
import com.shengsu.any.user.service.UserService;
import com.shengsu.any.user.util.UserUtils;
import com.shengsu.any.user.vo.SmsSendVo;
import com.shengsu.any.user.vo.UserLoginVo;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.service.OssService;
import com.shengsu.helper.service.SmsService;
import com.shengsu.result.ResultBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
    @Autowired
    private OssService ossService;
    @Autowired
    private AuthorizedService authorizedService;
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
        smsSendVo.setSmsCode(getSixRandomCode());
        // 将短信验证码存储到redis,时效是1分钟
        redisTemplate.opsForValue().set(tel, smsSendVo.getSmsCode(), SMS_INVALID_TIME, TimeUnit.SECONDS);
        // 发送手机验证码
        return smsService.sendSms(tel, smsSendVo.getSmsCode());
    }
    /**
    * @Description: 获取6位手机验证码
    * @Param: 
    * @Return: * @return: java.lang.String
    * @date: 
    */
    private String getSixRandomCode() {
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }
    @Override
    public ResultBean login(UserLoginVo loginUserVo) throws IOException{
        String tel = loginUserVo.getTel();
        // 校验验证码
        ResultBean resultBean = checkPhoneValidationCode(tel,loginUserVo.getSmsCode());
        if (!resultBean.isSuccess()){
            return resultBean;
        }
        Map<String, Object> resultMap = new HashMap<>();
        User user = userMapper.getUserByTel(tel);
        if (user == null) {
            // 保存用户
            user = UserUtils.toUser(tel);
            save(user);
        }
        UserDetailsPo userDetailsPo = UserUtils.toUserDetailsPo(user);
        userDetailsPo.setIconUrl(ossService.getUrl(OssConstant.OSS_USER_CENTERR_FFILEDIR, user.getIconOssResourceId()));
        resultMap.put("user", userDetailsPo);
        resultMap.put("token", authorizedService.generateToken(userDetailsPo));
        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }
    /**
     * @Description: 校验手机验证码
     * @Param: * @Param userLoginVo:
     * @Return: * @return: void
     * @date:
     */
    public ResultBean checkPhoneValidationCode(String tel, String smsCode) {
        // 验证短信验证码
        // 根据手机号码取短信验证码
        String redisSmsCode = (String) redisTemplate.opsForValue().get(tel);
        // 如果验证码为空就是超时
        if (StringUtils.isEmpty(redisSmsCode)) {
            return ResultUtil.formResult(false, ResultCode.SMS_AUTHENTICATION_CODE_OVERTIME);
        }
        // 校验短信验证码是否匹配
        if (!redisSmsCode.equals(smsCode)) {
            return ResultUtil.formResult(false, ResultCode.SMS_AUTHENTICATION_CODE_ERROR);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }
    @Override
    public ResultBean getUserBytoken(String token) {
        if (token == null) {
            ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        UserDetailsPo user = authorizedService.getUserByToken(token);
        if (user == null) {
            return ResultUtil.formResult(false, ResultCode.FAIL, null);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("token", token);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, result);
    }
}
