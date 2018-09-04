/*package com.pe.exchange.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.exchange.dao.DKDealDao;
import com.pe.exchange.dao.DKOrderDao;
import com.pe.exchange.entity.DKDealInfo;
import com.pe.exchange.entity.DKOrder;
import com.pe.exchange.exception.BizException;
import com.pe.exchange.redis.RedisOps;
import com.pe.exchange.utils.OderQueueUtil;
import com.pe.exchange.utils.UserUtil;


@Service
public class DKOrderService {
	private Logger log = LoggerFactory.getLogger(DKOrderService.class);
	
	@Autowired
	private DKOrderDao dkOrderDao;
	
	@Autowired
	private RedisOps redisOps;
	
	@Autowired
	private DKDealDao dkDealDao;
	
	@Autowired
	private DKDealService dkDealService;
	
	private final String key = "_orderKey";
	
	public void dkDeailPurchase(DKOrder dkOrder) {
		//初始买入订单默认状态为等待付款
		dkOrder.setStatus(2);
		dkOrder.setUserId(UserUtil.get());
		
		dkOrderDao.save(dkOrder);
		try {
			DKDealInfo dkInfo = dkDealDao.findById(dkOrder.getOrderId()).get();
			String redisKey = dkOrder.getId()+"_"+dkOrder.getUserId() + dkInfo.getOrderNumber() + key;
			redisOps.setWithTimeout(redisKey, "", 1000 * 60 * dkInfo.getTimes());
			OderQueueUtil.setOderQueue(redisKey, 0l);
		} catch (Exception e) {
			//手动回滚
			dkOrderDao.deleteById(dkOrder.getId());
		}
	}
	
	public void commitOder(Integer id) {
		
		
		DKOrder dkO =dkOrderDao.findById(id).get();
		DKDealInfo dkE = dkDealDao.findById(dkO.getOrderId()).get();
		int dkNumber = dkDealService.getUserDKNumber();
		if(dkE.getMoney()>dkNumber) {
			throw new BizException(305, "账户DK余额不足");
		}
		
	}
	
	

}
*/