package com.pe.exchange.service;

import com.pe.exchange.common.ResultEnum;
import com.pe.exchange.dao.DKDealDao;
import com.pe.exchange.dao.UserDao;
import com.pe.exchange.entity.DKDealInfo;
import com.pe.exchange.entity.User;
import com.pe.exchange.exception.BizException;
import com.pe.exchange.exception.SysException;
import com.pe.exchange.redis.RedisOps;
import com.pe.exchange.utils.VeriCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class DKDealService {
	

	@Autowired
	private DKDealDao dkDealDao;
	
	 @Autowired
	 RedisOps redisOps;
	 
	 @Autowired
	 UserDao userDao;
	
	/***
	 * 查询用户DK总资产
	 * @return
	 */
	public Integer getUserDKNumber(String token) {
	  String userId = redisOps.get(token);
	  if(StringUtils.isEmpty(userId)) {
		  log.error("用户失效！");
		  throw new BizException(ResultEnum.USER_FAIL);
	  }
	  List<DKDealInfo> dkList = dkDealDao.getDKTotalNumber(userId);
	  return computeDKTotal(dkList);
	}
	
	/***
	 * 每次重新计算用户DK资产额度
	 * @param list
	 * @return
	 */
	private Integer computeDKTotal(List<DKDealInfo> list) {
		Integer total = 0;
		for (DKDealInfo dk : list) {
			if(1 == dk.getType()) {
				total += dk.getDealNumber();
			}else if(2 == dk.getType()) {
				total -= dk.getDealNumber();
			}
		}
		return total;
	}
	
	public void saveDKDeal(DKDealInfo dealInfo,String token) {
		String userId = redisOps.get(token);
		if(StringUtils.isEmpty(userId)) {
			  log.error("用户失效！");
			  throw new BizException(ResultEnum.USER_FAIL);
		  }
		//绑定订单号
		dealInfo.setOrderNumber(VeriCodeUtils.getOrderIdByUUId());
		dealInfo.setMoney(dealInfo.getDealNumber() * 0.8);
		User u = new User();
		u.setId(Integer.valueOf(userId));
		dealInfo.setUser(u);
		int total = 0;
		if(2 == dealInfo.getType()) {
			total = getUserDKNumber(token);
			if(total<1) {
				throw new SysException();
			}
		}
		dkDealDao.save(dealInfo);
	}
	
	/***
	 * 查询买或麦的全部订单
	 * @return
	 */
	public List<DKDealInfo> findDKDeailList(int type){
		return dkDealDao.findTypeDKList(type);
	}
}
