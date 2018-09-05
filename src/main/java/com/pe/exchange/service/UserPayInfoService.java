package com.pe.exchange.service;

import java.util.List;
import java.util.Optional;

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
	@Autowired
	private UserPayInfoDao userPayInfoDao;
	
	@Autowired
	RedisOps redisOps;
	 
	public List<UserPayInfo> findUserPayInfoList(Integer...ids){
		Integer userId = UserUtil.get().getId();
		if(ids.length > 0) {
			userId = ids[0];
		}
		return userPayInfoDao.queryUserPayInfoList(userId);
	}
	
	public void saveUserPayInfo(UserPayInfo payInfo) {
		/*Optional<UserPayInfo> u = userPayInfoDao.findById(payInfo.getId());
		if(u.isPresent()) {
			userPayInfoDao.updatePayInfo(payInfo.getAccountId(), payInfo.getAccountName(), payInfo.getBank(), payInfo.getBankBranch(), payInfo.getPayType(), payInfo.getQrCode(), payInfo.getId(), payInfo.getUserId());
		}else {
			userPayInfoDao.save(payInfo);
		}*/
		payInfo.setUserId(UserUtil.get().getId());
		userPayInfoDao.save(payInfo);
	}
	
}
