package com.shengsu.helper.service.impl;

import com.shengsu.helper.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Service("redisService")
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate<Serializable,Serializable> redisTemplate;
    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间 单位秒
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        Boolean ret = redisTemplate.expire(key, timeout, unit);
        return ret != null && ret;
    }

    /**
     * 删除单个key
     *
     * @param key 键
     */
    public void delete(final String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除多个key
     *
     * @param keys 键集合
     */
    public void delete(final Collection<Serializable> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 存入普通对象
     *
     * @param key Redis键
     * @param value 值 单位分
     */
    public void set(final String key, final Serializable value) {
        redisTemplate.opsForValue().set(key, value, 1, TimeUnit.MINUTES);
    }

    /**
     * 存入普通对象
     *
     * @param key 键
     * @param value 值
     * @param timeout 有效期，单位秒
     */
    public void set(final String key, final Serializable value, final long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取普通对象
     *
     * @param key 键
     * @return 对象
     */
    public Serializable get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
