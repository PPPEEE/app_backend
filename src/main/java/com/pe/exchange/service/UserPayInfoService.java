package com.pe.exchange.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.exchange.dao.UserPayInfoDao;
import com.pe.exchange.entity.UserPayInfo;
import com.pe.exchange.redis.RedisOps;
import com.pe.exchange.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserPayInfoService {
	private Logger log = LoggerFactory.getLogger(UserPayInfoService.class);
	@Autowired
	private UserPayInfoDao userPayInfoDao;
	
	@Autowired
	RedisOps redisOps;
	 
	public List<UserPayInfo> findUserPayInfoList(){
		 Integer userId = UserUtil.get();
		 return userPayInfoDao.queryUserPayInfoList(userId);
	}
	
	public void saveUserPayInfo(UserPayInfo payInfo) {
		userPayInfoDao.save(payInfo);
	}
	
}
