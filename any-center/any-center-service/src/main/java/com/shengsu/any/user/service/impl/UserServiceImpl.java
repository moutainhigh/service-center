package com.shengsu.any.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.any.account.entity.Account;
import com.shengsu.any.account.service.AccountServcie;
import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.RedisUtil;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.constant.TemplateMessageEnum;
import com.shengsu.any.message.entity.Message;
import com.shengsu.any.message.service.MessageService;
import com.shengsu.any.message.util.MessageUtils;
import com.shengsu.any.system.util.SystemDictUtils;
import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.mapper.UserMapper;
import com.shengsu.any.user.po.UserDetailsPo;
import com.shengsu.any.user.service.AuthorizedService;
import com.shengsu.any.user.service.UserService;
import com.shengsu.any.user.util.UserUtils;
import com.shengsu.any.user.vo.*;
import com.shengsu.any.wechat.entity.TempMessageContent;
import com.shengsu.any.wechat.entity.TempMessageData410928703;
import com.shengsu.any.wechat.service.TemplateMessageService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.constant.OssConstant;
import com.shengsu.helper.constant.SmsSignEnum;
import com.shengsu.helper.constant.SmsTemplateEnum;
import com.shengsu.helper.entity.SystemDict;
import com.shengsu.helper.service.OssService;
import com.shengsu.helper.service.SmsService;
import com.shengsu.helper.service.SystemDictService;
import com.shengsu.result.ResultBean;
import com.shengsu.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.shengsu.any.app.constant.BizConst.*;
import static com.shengsu.any.message.constant.MessageConst.MESSAGE_CONTENT_PASS;
import static com.shengsu.any.message.constant.MessageConst.MESSAGE_CONTENT_REJECT;
import static com.shengsu.any.wechat.constant.TemplateMessageConst.*;
import static com.shengsu.any.user.util.UserUtils.toUserDetailsPo;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-07 17:20
 **/
@Service("anyUserService")
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {
    @Autowired
    private SmsService smsService;
    @Autowired
    private OssService ossService;
    @Autowired
    private AuthorizedService authorizedService;
    @Autowired
    private SystemDictService systemDictService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountServcie accountServcie;
    @Autowired
    private TemplateMessageService templateMessageService;
    @Resource
    private RedisUtil redisUtil;
    @Value("${sms.expireTimeSecond}")
    private long smsExpireTime;
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
        redisUtil.set(tel, smsSendVo.getSmsCode(), smsExpireTime);
        // 发送手机验证码
        JSONObject smsParam180053728Json = new JSONObject();
        smsParam180053728Json.put("code",smsSendVo.getSmsCode());
        return smsService.sendSms(tel, SmsTemplateEnum.SMS_180053728, JSON.toJSONString(smsParam180053728Json),SmsSignEnum.SMS_SIGN_CODE_SSKJ);
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
        String redisSmsCode = (String) redisUtil.get(tel);
        // 如果验证码为空就是超时
        if (StringUtils.isEmpty(redisSmsCode)) {
            return ResultUtil.formResult(false, ResultCode.SMS_AUTHENTICATION_CODE_OVERTIME);
        }
        // 校验短信验证码是否匹配
        if (!redisSmsCode.equals(smsCode)) {
            return ResultUtil.formResult(false, ResultCode.SMS_AUTHENTICATION_CODE_ERROR);
        }
        // 缓存验证码清除
        redisUtil.delete(tel);
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
    /**
    * @Description: 微信openid获取用户
    * @Param: * @Param openid: 
    * @Return: * @return: com.shengsu.any.user.entity.User
    * @date: 
    */
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
        SystemDict systemDict = systemDictService.getOneByDisplayValue(DICT_CODE_AUTH_STATE,user.getAuthState());
        if (systemDict != null) {
            userDetailsPo.setAuthStateStr(systemDict.getDisplayName());
        }

        List<SystemDict> systemDicts = systemDictService.listByDictCode(DICT_CODE_FIELD);
        Map<String, SystemDict> systemDictMap = SystemDictUtils.toSystemDictMap(systemDicts);
        String field = "";
        if (StringUtils.isNotBlank(user.getField())){
            List<String> fieldItems =Arrays.asList(StringUtils.split(user.getField(), ","));
            for (String item : fieldItems){
                SystemDict dict = systemDictMap.get(item);
                if (null!=dict) field = field + " "+ dict.getDisplayName();
            }
        }
        userDetailsPo.setFieldStr(field.trim());

        userDetailsPo.setIconUrl(ossService.getUrl(OssConstant.OSS_ANY_PLATFORM_FFILEDIR, user.getIconOssResourceId()));
        userDetailsPo.setIdCardFrontUrl(ossService.getUrl(OssConstant.OSS_ANY_PLATFORM_FFILEDIR, user.getIdCardFrontOssResourceId()));
        userDetailsPo.setIdCardBackUrl(ossService.getUrl(OssConstant.OSS_ANY_PLATFORM_FFILEDIR, user.getIdCardBackOssResourceId()));
        userDetailsPo.setLicenseUrl(ossService.getUrl(OssConstant.OSS_ANY_PLATFORM_FFILEDIR, user.getLicenseOssResourceId()));
    }
    /**
    * @Description: 用户绑定(手机号)
    * @Param: * @Param userBandVo: 
    * @Return: * @return: com.shengsu.result.ResultBean
    * @date: 
    */
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
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_WECHAT_USER_TEL_BANDED);
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
    /**
    * @Description: token结果处理
    * @Param: * @Param user: 
    * @Return: * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @date: 
    */
    private  Map<String, Object> userTokenResult(User user){
        UserDetailsPo userDetailsPo = toUserDetailsPo(user);
        supplyUserDetailsPo(userDetailsPo,user);
        Map<String, Object> result = new HashMap<>();
        result.put("user", userDetailsPo);
        result.put("token", authorizedService.generateToken(userDetailsPo));
        return result;
    }

    /**
    * @Description: 登出
    * @Param: 
    * @Return: * @return: com.shengsu.result.ResultBean
    * @date: 
    */
    @Override
    public ResultBean logout(String token) {
        authorizedService.logout(token);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }
    /**
    * @Description: 编辑
    * @Param: * @Param userEditVo: 
    * @Return: * @return: com.shengsu.result.ResultBean
    * @date: 
    */
    @Override
    public ResultBean edit(UserEditVo userEditVo) {
        String userId = userEditVo.getUserId();
        User oldUser = userMapper.get(userId);
        if (oldUser == null) {
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_REGISTER_USER_NOT_EXISTED);
        }
        if (USER_AUTH_STATE_AUTHENTICATION.equals(oldUser.getAuthState())){
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_USER_AUTH_STATE_IN_REVIEW);
        }
        if (USER_AUTH_STATE_AUTHENTICATED.equals(oldUser.getAuthState())){
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_USER_AUTH_STATE_REVIEW_PASS);
        }
        User user = UserUtils.toUser(userEditVo);
        user.setUserName(oldUser.getUserName());
        user.setTel(oldUser.getTel());
        user.setCreateTime(oldUser.getCreateTime());
        user.setAuthState(USER_AUTH_STATE_AUTHENTICATION);
        userMapper.update(user);
        String token = userEditVo.getToken();
        if (StringUtils.isNoneBlank(token)) {
            UserDetailsPo userDetailsPo = UserUtils.toUserDetailsPo(user);
            supplyUserDetailsPo(userDetailsPo,user);
            authorizedService.flushUserToken(userDetailsPo, token);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }
    /**
    * @Description: 返回用户详细信息
    * @Param: * @Param users: 
    * @Return: * @return: com.shengsu.result.ResultBean<java.util.List<com.shengsu.any.user.po.UserDetailsPo>>
    * @date: 
    */
    @Override
    public ResultBean<List<UserDetailsPo>> toUserDetailsPos(List<User> users) {
        if (users != null) {
            List<UserDetailsPo> userDetailsPos = new ArrayList<>();
            for (User user :users) {
                UserDetailsPo userDetailsPo =toUserDetailsPo(user);
                supplyUserDetailsPo(userDetailsPo,user);
                userDetailsPos.add(userDetailsPo);
            }
            return ResultUtil.formResult(true, ResultCode.SUCCESS,userDetailsPos);
        }
        return ResultUtil.formResult(false, ResultCode.FAIL);
    }
    /**
    * @Description: 分页查询用户
    * @Param: * @Param user: 
    * @Return: * @return: com.shengsu.result.ResultBean
    * @date: 
    */
    @Override
    public ResultBean listPage(User user) {
        Map<String, Object> map = new HashMap<>();
        user.setSearch(StringUtil.ToLikeStr(user.getSearch()));
        int totalCount = userMapper.countAll(user);
        if (totalCount > 0) {
            List<User> users = userMapper.listByPage(user);
            ResultBean<List<UserDetailsPo>> userDetailsPosResult = toUserDetailsPos(users);
            List<UserDetailsPo> detailDetailsPos= null;
            if (userDetailsPosResult.isSuccess()) {
                 detailDetailsPos= userDetailsPosResult.getBody();
            }
            map.put("root", detailDetailsPos);
            map.put("totalCount", totalCount);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }
    /**
    * @Description: 认证通过
    * @Param: * @Param userAuthStateVo:
    * @Return: * @return: com.shengsu.result.ResultBean
    * @date:
    */
    @Override
    public ResultBean pass(UserAuthStateVo userAuthStateVo) {
        String userId = userAuthStateVo.getUserId();
        User user = userMapper.get(userId);
        if (!USER_AUTH_STATE_AUTHENTICATION.equals(user.getAuthState())){
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_USER_AUTH_STATE_UNREVIEW);
        }
        userMapper.pass(userId);
        Message message = MessageUtils.toMessage(userId);
        message.setMessageContent(MESSAGE_CONTENT_PASS);
        messageService.save(message);
        // 保存账户默认账户余额为0
        Account account = accountServcie.getByUserId(userId);
        if (null == account){
            accountServcie.create(userId,new BigDecimal(0));
        }
        // 认证通过给公众号推送模板消息
        // 设置模板
        TempMessageData410928703 data = assembleTemplateDate(MessageFormat.format(TEMPLATE_MESSAGE_AUTH_PASS_FIRST_VALUE, user.getRealName()),TEMPLATE_MESSAGE_AUTH_PASS_KEYWORD1_VALUE,TEMPLATE_MESSAGE_AUTH_PASS_REMARK_VALUE);
        // 推送消息
        templateMessageService.pushTemplateMessage(user.getWechatOpenid(),TemplateMessageEnum.MESSAGE_TEMPLATE_USER_AUTHROTION,data);

        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }
    /**
    * @Description: 组装要发送的模板数据
    * @Param: 
    * @date:
    */
    @Override
    public TempMessageData410928703 assembleTemplateDate(String firstValue,String keyWord1Value,String remarkValue) {
        //设置模板标题
        TempMessageContent first=new TempMessageContent(firstValue,TEMPLATE_MESSAGE_COLOR_D5D5D5);
        //设置模板内容
        TempMessageContent keyword1=new TempMessageContent(keyWord1Value,TEMPLATE_MESSAGE_COLOR_0000FF);
        //设置时间
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        TempMessageContent keyword2=new TempMessageContent(format,TEMPLATE_MESSAGE_COLOR_0000FF);
        //设置备注
        TempMessageContent remark=new TempMessageContent(remarkValue,TEMPLATE_MESSAGE_COLOR_0000FF);
        //创建模板信息数据对象
        TempMessageData410928703 data=new TempMessageData410928703();
        data.setFirst(first);
        data.setKeyword1(keyword1);
        data.setKeyword2(keyword2);
        data.setRemark(remark);
        return data;
    }

    /**
    * @Description: 认证拒绝
    * @Param: * @Param userAuthStateVo:
    * @Return: * @return: com.shengsu.result.ResultBean
    * @date:
    */
    @Override
    public ResultBean reject(UserAuthStateVo userAuthStateVo) {
        String userId = userAuthStateVo.getUserId();
        User user = userMapper.get(userId);
        if (!USER_AUTH_STATE_AUTHENTICATION.equals(user.getAuthState())){
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_USER_AUTH_STATE_UNREVIEW);
        }
        userMapper.reject(userId);
        Message message = MessageUtils.toMessage(userId);
        message.setMessageContent(MESSAGE_CONTENT_REJECT);
        messageService.save(message);
        // 认证拒绝给公众号推送模板消息
        // 设置模板
        TempMessageData410928703 data = assembleTemplateDate(MessageFormat.format(TEMPLATE_MESSAGE_AUTH_REJECT_FIRST_VALUE, user.getRealName()),TEMPLATE_MESSAGE_AUTH_REJECT_KEYWORD1_VALUE,TEMPLATE_MESSAGE_AUTH_REJECT_REMARK_VALUE);
        // 推送消息
        templateMessageService.pushTemplateMessage(user.getWechatOpenid(),TemplateMessageEnum.MESSAGE_TEMPLATE_USER_AUTHROTION,data);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    @Override
    public ResultBean uploadHeadImage(UploadHeadImageVo uploadHeadImageVo) {
        String userId = uploadHeadImageVo.getUserId();
        User user = new User();
        user.setUserId(userId);
        user.setIconOssResourceId(uploadHeadImageVo.getIconOssResourceId());
        userMapper.uploadHeadImage(user);

        User oldUser = userMapper.get(userId);
        String token = uploadHeadImageVo.getToken();
        if (StringUtils.isNoneBlank(token)) {
            UserDetailsPo userDetailsPo = UserUtils.toUserDetailsPo(oldUser);
            userDetailsPo.setIconUrl(ossService.getUrl(OssConstant.OSS_ANY_PLATFORM_FFILEDIR, uploadHeadImageVo.getIconOssResourceId()));
            authorizedService.flushUserToken(userDetailsPo, token);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    @Override
    public String getUserIdByTel(String tel) {
        return userMapper.getUserIdByTel(tel);
    }

    @Override
    public List<String> getAllOpenId() {
        return userMapper.getAllOpenId();
    }

}
