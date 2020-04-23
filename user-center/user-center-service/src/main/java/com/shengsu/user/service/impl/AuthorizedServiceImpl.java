package com.shengsu.user.service.impl;


import com.alibaba.fastjson.JSON;
import com.shengsu.app.constant.ResultCode;
import com.shengsu.helper.service.RedisService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.user.entity.Auth;
import com.shengsu.user.po.UserDetailsPo;
import com.shengsu.user.service.AuthorizedService;
import com.shengsu.util.MD5Util;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zyc on 2019/10/15.
 */
@Slf4j
@Service(value = "authorizedService")
public class AuthorizedServiceImpl implements AuthorizedService {

    /**
     * token的签名密钥
     */
    @Value("${token.secretKey}")
    private String SECRET_KEY;
    /**
     * token存活时间(秒）
     */
    @Value("${token.expireTimeSecond}")
    private long INVALID_TIME;


    @Resource
    private RedisService redisService;


    /**
     * 生成Token
     *
     * @return
     * @date 2018年1月17日 下午2:49:03
     */
    public String generateToken(UserDetailsPo user) {
        // 加密生成token
        Date iat = new Date();
        Date exp = new Date(System.currentTimeMillis() + INVALID_TIME);
        String token = Jwts.builder().setSubject(user.getUserName())
                .setIssuedAt(iat)// 生成时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)// 设置编码
                .compact();
        // 将TOKEN放入缓存中，方便验证
        Auth auth = new Auth(iat.getTime(), exp.getTime(), token, user);
        String cacheKey = getCacheKey(token);
        redisService.set(cacheKey, JSON.toJSONString(auth), INVALID_TIME);
        return token;
    }

    /**
     * md5加密
     *
     * @param token
     * @return
     */
    private String getCacheKey(String token) {
        if (StringUtils.isBlank(token)) {
            token = "";
        }
        return MD5Util.md5(token);
    }

    /**
     * 使Token失效
     *
     * @param token
     * @date 2018年1月18日 上午10:03:17
     */
    public void destoryToken(String token) {
        redisService.delete(getCacheKey(token));
    }

    /**
     * 刷新token中的用户信息
     *
     * @param user
     */
    public void flushUserToken(UserDetailsPo user, String token) {
        if (user != null && StringUtils.isNoneBlank(token)) {
            Date iat = new Date();
            Date exp = new Date(System.currentTimeMillis() + INVALID_TIME);
            Auth auth = new Auth(iat.getTime(), exp.getTime(), token, user);
            String cacheKey = getCacheKey(token);
            redisService.set(cacheKey, JSON.toJSONString(auth), INVALID_TIME);
        }
    }

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return 其它认证信息异常（签名不正确，token格式不正确等token错误）
     * @date 2018年1月17日 下午3:55:58
     */
    public UserDetailsPo getUserByToken(String token) {
        if (StringUtils.isNoneBlank(token)) {
            parseToken(token);
            //解析token,这里会校验token是否正确
            String cacheKey = getCacheKey(token);
            Serializable authJsonStr =  redisService.get(cacheKey);
            Auth auth = JSON.parseObject(authJsonStr.toString(), Auth.class);
            if (null == authJsonStr)
                return null;
            return auth.getUser();
        }

        return null;
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     * @throws ExpiredJwtException      Token已过期
     * @throws MalformedJwtException    {@code token}不是有效的token字符串（格式不正确）
     * @throws SignatureException       {@code token}签名验证失败
     * @throws IllegalArgumentException 如果{@code token}参数为null或空字符串或只是空格
     * @throws ExpiredJwtException
     * @throws SignatureException
     * @date 2018年1月17日 下午2:33:57
     */
    private void parseToken(String token) throws ExpiredJwtException,MalformedJwtException, SignatureException, IllegalArgumentException {
        log.info(token);
        final Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * 验证token是否有效,token无效时会抛出对应的异常
     *
     * @param token 这里指代客户机的唯一标识，目前未启用
     * @date 2018年1月17日 下午6:13:14
     */
    public ResultBean checkToken(String token) {
        //解析token,这里会校验token是否正确
        if (StringUtils.isBlank(token)) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_LOGIN_PARAM_ERROR);
        }
        try {
            parseToken(token);
            String cacheKey = getCacheKey(token);
            Serializable authJsonStr =  redisService.get(cacheKey);
            Auth auth = JSON.parseObject(authJsonStr.toString(), Auth.class);
            if(auth==null){
                return ResultUtil.formResult(true, ResultCode.EXCEPTION_LOGIN_TOKEN_EXPIRED);
            }
            redisService.set(cacheKey, JSON.toJSONString(auth), INVALID_TIME);
            return ResultUtil.formResult(true, ResultCode.SUCCESS);
        } catch (ExpiredJwtException e) {
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_LOGIN_TOKEN_EXPIRED);
        } catch (RuntimeException e){
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_LOGIN_TOKEN_INVALID);
        }
    }
}
