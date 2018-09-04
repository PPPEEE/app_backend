package com.pe.exchange.service;

import java.util.List;
<<<<<<< HEAD
import java.util.Optional;

import org.codehaus.plexus.util.StringUtils;
=======

>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import com.pe.exchange.common.ResultEnum;
import com.pe.exchange.dao.UserPayInfoDao;
import com.pe.exchange.entity.UserPayInfo;
import com.pe.exchange.exception.BizException;
import com.pe.exchange.redis.RedisOps;
=======
import com.pe.exchange.dao.UserPayInfoDao;
import com.pe.exchange.entity.UserPayInfo;
import com.pe.exchange.redis.RedisOps;
import com.pe.exchange.utils.UserUtil;
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserPayInfoService {
<<<<<<< HEAD
	private static final Logger log = LoggerFactory.getLogger(DKDealService.class);
	
=======
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
	@Autowired
	private UserPayInfoDao userPayInfoDao;
	
	@Autowired
	RedisOps redisOps;
	 
<<<<<<< HEAD
	public List<UserPayInfo> findUserPayInfoList(String token){
		 String userId = redisOps.get(token);
		 if(!StringUtils.isNotBlank(userId)) {
			  log.error("用户失效！");
			  throw new BizException(ResultEnum.USER_FAIL);
		 }
		 return userPayInfoDao.queryUserPayInfoList(userId);
=======
	public List<UserPayInfo> findUserPayInfoList(Integer...ids){
		Integer userId = UserUtil.get().getId();
		if(ids.length > 0) {
			userId = ids[0];
		}
		return userPayInfoDao.queryUserPayInfoList(userId);
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
	}
	
	public void saveUserPayInfo(UserPayInfo payInfo) {
		userPayInfoDao.save(payInfo);
	}
	
}
