package com.shengsu.website.market.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.helper.constant.SmsSignEnum;
import com.shengsu.helper.constant.SmsTemplateEnum;
import com.shengsu.helper.service.RedisService;
import com.shengsu.helper.service.SmsService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.market.service.SmsSendService;
import com.shengsu.website.market.vo.SmsSendVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.shengsu.website.app.constant.BizConst.SYSTEM_TAG_YUANSHOU;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-07-23 14:30
 **/
@Service("smsSendService")
public class SmsSendServiceImpl implements SmsSendService {
    @Resource
    private RedisService redisService;
    @Resource
    private SmsService smsService;
    @Value("${sms.expireTimeSecond}")
    private long smsExpireTime;
    @Override
    public ResultBean sendSms(SmsSendVo smsSendVo) {
        String tel = smsSendVo.getTel();
        String smsCode = getSixRandomCode();
        redisService.set(tel, smsCode, smsExpireTime);
        // 发送手机验证码
        JSONObject smsParam180053728Json = new JSONObject();
        smsParam180053728Json.put("code", smsCode);
        if (SYSTEM_TAG_YUANSHOU.equals(smsSendVo.getSmsCode())) {
            return smsService.sendSms(tel, SmsTemplateEnum.SMS_180053728, JSON.toJSONString(smsParam180053728Json), SmsSignEnum.SMS_SIGN_CODE_YS);
        } else {
            return smsService.sendSms(tel, SmsTemplateEnum.SMS_180053728, JSON.toJSONString(smsParam180053728Json), SmsSignEnum.SMS_SIGN_CODE_SSKJ);
        }
    }

    private String getSixRandomCode() {
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }

    @Override
    public ResultBean checkPhoneValidationCode(SmsSendVo smsSendVo) {
        // 验证短信验证码
        // 根据手机号码取短信验证码
        String redisSmsCode = (String) redisService.get(smsSendVo.getTel());
        // 如果验证码为空就是超时
        if (StringUtils.isEmpty(redisSmsCode)) {
            return ResultUtil.formResult(false, ResultCode.SMS_AUTHENTICATION_CODE_OVERTIME);
        }
        // 校验短信验证码是否匹配
        if (!redisSmsCode.equals(smsSendVo.getSmsCode())) {
            return ResultUtil.formResult(false, ResultCode.SMS_AUTHENTICATION_CODE_ERROR);
        }
        // 缓存验证码清除
        redisService.delete(smsSendVo.getTel());
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }
}
