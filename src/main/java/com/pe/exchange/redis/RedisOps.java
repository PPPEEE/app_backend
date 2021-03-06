package com.pe.exchange.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisOps {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

    /**
     *
     * @param key
     * @param value
     * @param time 单位默认为秒
     */
    public void setWithTimeout(String key ,String value,long time){

        stringRedisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
    }
    public String get(String key){

      return  stringRedisTemplate.opsForValue().get(key);
    }

    public boolean hasKey(String key){
        return stringRedisTemplate.hasKey(key);
    }

    public boolean delete(String key){
        return stringRedisTemplate.delete(key);
    }
    
    
    public Long getOutTimes(String key) {
    	return stringRedisTemplate.getExpire(key);
    }
}
