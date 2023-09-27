package com.pig4cloud.pig.auth.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisUtils {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;


	public boolean deleteKey(String key) {
		return redisTemplate.delete(key);
	}

	public void setValue(String key, Object object, Long expire) {
		redisTemplate.opsForValue().set(key, object, expire);
	}

}
