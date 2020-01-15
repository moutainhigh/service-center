package com.shengsu.any.user.service.impl;

import com.shengsu.any.app.constant.BizConst;
import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.system.entity.SystemDict;
import com.shengsu.any.system.service.SystemDictService;
import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.mapper.UserMapper;
import com.shengsu.any.user.po.UserDetailsPo;
import com.shengsu.any.user.service.AuthorizedService;
import com.shengsu.any.user.service.UserService;
import com.shengsu.any.user.util.UserUtils;
import com.shengsu.any.user.vo.SmsSendVo;
import com.shengsu.any.user.vo.UserBandVo;
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
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService,BizConst {
    @Autowired
    private SmsService smsService;
    @Autowired
    private OssService ossService;
    @Autowired
    private AuthorizedService authorizedService;
    @Autowired
    private SystemDictService systemDictService;
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
        User user = userMapper.selectByTel(tel);
        if (user == null) {
            // 保存用户
            user = UserUtils.toUser(tel);
            save(user);
        }
        UserDetailsPo userDetailsPo = UserUtils.toUserDetailsPo(user);
        supplyUserDetailsPo(userDetailsPo,user);
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
        // 缓存验证码清除
        redisTemplate.delete(tel);
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

    @Override
    public User selectByWeChatOpenid(String openid) {
        if (StringUtils.isBlank(openid)) {
            return null;
        }

        User user = userMapper.selectByWeChatOpenid(openid);
        if (user == null) {
            return null;
        }
        return user;
    }
    public void supplyUserDetailsPo(UserDetailsPo userDetailsPo,User user){
        SystemDict systemDict = systemDictService.getOneByDisplayVlue(DICT_CODE_AUTH_STATE,user.getAuthState());
        if (systemDict != null) {
            userDetailsPo.setAuthStateStr(systemDict.getDisplayName());
        }
        userDetailsPo.setIconUrl(ossService.getUrl(OssConstant.OSS_ANY_PLATFORM_FFILEDIR, user.getIconOssResourceId()));
        userDetailsPo.setIdCardFrontUrl(ossService.getUrl(OssConstant.OSS_ANY_PLATFORM_FFILEDIR, user.getIdCardFrontOssResourceId()));
        userDetailsPo.setIdCardBackUrl(ossService.getUrl(OssConstant.OSS_ANY_PLATFORM_FFILEDIR, user.getIdCardBackOssResourceId()));
        userDetailsPo.setLicenseUrl(ossService.getUrl(OssConstant.OSS_ANY_PLATFORM_FFILEDIR, user.getLicenseOssResourceId()));
    }

    @Override
    public ResultBean band(UserBandVo userBandVo) {
        // 手机验证码校验
        ResultBean phoneCodeChekResult = checkPhoneValidationCode(userBandVo.getTel(),userBandVo.getSmsCode());
        if (!phoneCodeChekResult.isSuccess()){
            return phoneCodeChekResult;
        }
        // 一个微信只能绑定一个手机号
        User userResult = userMapper.selectByWeChatOpenid(userBandVo.getOpenid());
        if (null != userResult){
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_REGISTER_TEL_BANDED);
        }

        User oldUser = userMapper.selectByTel(userBandVo.getTel());
        User user = UserUtils.toUser(userBandVo);
        // 库中无此用户 保存
        if (oldUser == null){
            save(user);
            // 构造返回值
            Map<String, Object> result = userTokenResult(user);
            return ResultUtil.formResult(true, ResultCode.SUCCESS, result);
        }

        // 库中已经有在网页中注册过的手机号但未绑定过微信
        if (StringUtils.isNotBlank(oldUser.getTel())&&StringUtils.isBlank(oldUser.getWechatOpenid())){
            userMapper.bandWechat(user);
            // 构造返回值
            Map<String, Object> result = userTokenResult(oldUser);
            return ResultUtil.formResult(true, ResultCode.SUCCESS, result);
        }
        // 一个手机号只能和一个微信号绑定
        if (StringUtils.isNotBlank(oldUser.getTel())&&StringUtils.isNotBlank(oldUser.getWechatOpenid())){
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_REGISTER_TEL_BANDED);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }
    private  Map<String, Object> userTokenResult(User user){
        UserDetailsPo userDetailsPo = UserUtils.toUserDetailsPo(user);
        supplyUserDetailsPo(userDetailsPo,user);
        Map<String, Object> result = new HashMap<>();
        result.put("user", userDetailsPo);
        result.put("token", authorizedService.generateToken(userDetailsPo));
        return result;
    }

}
