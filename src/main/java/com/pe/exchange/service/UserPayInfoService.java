package com.pe.exchange.service;

import com.pe.exchange.common.ResultEnum;
import com.pe.exchange.dao.UserPayInfoDao;
import com.pe.exchange.entity.UserPayInfo;
import com.pe.exchange.exception.BizException;
import com.pe.exchange.redis.RedisOps;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class UserPayInfoService {

	@Autowired
	private UserPayInfoDao userPayInfoDao;
	
	@Autowired
	RedisOps redisOps;
	 
	public List<UserPayInfo> findUserPayInfoList(String token){
		 String userId = redisOps.get(token);
		 if(StringUtils.isEmpty(userId)) {
			  log.error("用户失效！");
			  throw new BizException(ResultEnum.USER_FAIL);
		 }
		 return userPayInfoDao.queryUserPayInfoList(userId);
	}
	
	public void saveUserPayInfo(UserPayInfo payInfo) {
		userPayInfoDao.save(payInfo);
	}
	
}
