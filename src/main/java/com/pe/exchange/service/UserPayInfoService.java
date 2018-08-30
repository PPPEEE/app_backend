package com.pe.exchange.service;

import java.util.List;
import java.util.Optional;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.exchange.common.ResultEnum;
import com.pe.exchange.dao.UserPayInfoDao;
import com.pe.exchange.entity.UserPayInfo;
import com.pe.exchange.exception.BizException;
import com.pe.exchange.redis.RedisOps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserPayInfoService {
	private static final Logger log = LoggerFactory.getLogger(DKDealService.class);
	
	@Autowired
	private UserPayInfoDao userPayInfoDao;
	
	@Autowired
	RedisOps redisOps;
	 
	public List<UserPayInfo> findUserPayInfoList(String token){
		 String userId = redisOps.get(token);
		 if(!StringUtils.isNotBlank(userId)) {
			  log.error("用户失效！");
			  throw new BizException(ResultEnum.USER_FAIL);
		 }
		 return userPayInfoDao.queryUserPayInfoList(userId);
	}
	
	public void saveUserPayInfo(UserPayInfo payInfo) {
		userPayInfoDao.save(payInfo);
	}
	
}
