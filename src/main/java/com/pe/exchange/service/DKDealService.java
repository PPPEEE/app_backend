package com.pe.exchange.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.exchange.dao.DKDealDao;
import com.pe.exchange.dao.UserDao;
import com.pe.exchange.entity.DKDealInfo;
import com.pe.exchange.entity.User;
import com.pe.exchange.exception.SysException;
import com.pe.exchange.redis.RedisOps;
import com.pe.exchange.utils.OderQueueUtil;
import com.pe.exchange.utils.UserUtil;
import com.pe.exchange.utils.VeriCodeUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DKDealService {
	
	private Logger log = LoggerFactory.getLogger(DKDealInfo.class);

	@Autowired
	private DKDealDao dkDealDao;
	
	 @Autowired
	 RedisOps redisOps;
	 
	 @Autowired
	 UserDao userDao;
	 
	 @Autowired
	 UserPayInfoService userPayInfoService;
	 
	 private final String key = "_orderKey";
	
	/***
	 * 查询用户DK总资产
	 * @return
	 */
	public Integer getUserDKNumber() {
	  Integer userId = UserUtil.get();
	 
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
	
	public void saveDKDeal(DKDealInfo dealInfo) {
		Integer userId = UserUtil.get();
		//绑定订单号
		dealInfo.setOrderNumber(VeriCodeUtils.getOrderIdByUUId());
		dealInfo.setMoney(dealInfo.getDealNumber() * 0.8);
		dealInfo.setUser_id(userId);
		int total = 0;
		if(2 == dealInfo.getType()) {
			total = getUserDKNumber();
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
		List<DKDealInfo> list =  null;
		if(type == 0) {
			list = dkDealDao.findUserDKList(UserUtil.get());
		}else {
			list = dkDealDao.findTypeDKList(type);
		}
		setUserInfo(list);
		return list;
	}
	
	private void setUserInfo(List<DKDealInfo> list) {
		User u = null;
		for (DKDealInfo dk : list) {
			u = userDao.findById(dk.getUser_id()).get();
			u.setUserPayInfo(userPayInfoService.findUserPayInfoList(dk.getUser_id()));
			u.setPwd("");
			dk.setUser(u);
		}
	}
	
	public DKDealInfo findDkById(Integer id) {
		List<DKDealInfo> dkInfo = new ArrayList<DKDealInfo>();
		dkInfo.add(dkDealDao.findById(id).get());
		setUserInfo(dkInfo);
		return dkInfo.size()>0?dkInfo.get(0):null;
		
		
	}
	
	public void dkDeailPurchase(Integer id) {
		Integer status = 0;
		DKDealInfo dkInfo = dkDealDao.findById(id).get();
		status = dkInfo.getStatus();
		DKDealInfo dkDealInfo = new DKDealInfo();
		dkDealInfo.setDealNumber(dkInfo.getDealNumber());
		dkDealInfo.setMinNumber(dkInfo.getMinNumber());
		dkDealInfo.setMoney(dkInfo.getMoney());
		dkDealInfo.setOrderNumber(dkInfo.getOrderNumber());
		dkDealInfo.setTimes(dkInfo.getTimes());
		//初始买入订单默认状态为等待付款
		dkDealInfo.setStatus(3);
		dkDealInfo.setUser_id(UserUtil.get());
		dkDealInfo.setType(dkInfo.getType()==1?2:1);
		dkInfo.setStatus(3);
		try {
			dkDealDao.save(dkInfo);
			dkDealDao.save(dkDealInfo);
			String redisKey = getOderRedisKey(id,1)+"_"+status;
			String _redisKey = getOderRedisKey(dkDealInfo.getId(),1)+"_4";
			redisOps.setWithTimeout(redisKey, "", 1000 * 60 * dkInfo.getTimes());
			redisOps.setWithTimeout(_redisKey, "", 1000 * 60 * dkInfo.getTimes());
			OderQueueUtil.setOderQueue(redisKey, 0l);
			OderQueueUtil.setOderQueue(_redisKey, 0l);
		} catch (Exception e) {
		}
	}
	
	
	public void commitDK(Integer id) {
		//卖家
		DKDealInfo dkInfo = dkDealDao.findById(id).get();
		dkInfo.setStatus(1);
		String number = dkInfo.getOrderNumber();
		//买家
		DKDealInfo dealInfo = dkDealDao.findUserDKByNumber(number, dkInfo.getUser_id());
		dealInfo.setStatus(1);
		
		Set<DKDealInfo> entitys = new HashSet<DKDealInfo>();
		entitys.add(dkInfo);
		entitys.add(dealInfo);
		dkDealDao.saveAll(entitys);
		
	/*	String redisKey = getOderRedisKey(id)+"_7";
		String _redisKey = getOderRedisKey(dealInfo.getId())+"_7";
		if(OderQueueUtil.getQueues().containsKey(arg0)) {
			
		}*/
	}
	
	
	public void paymentCommitOder(Integer id) {
		
		DKDealInfo dk = dkDealDao.findById(id).get();
		dk.setStatus(6);
		DKDealInfo dealInfo = dkDealDao.findUserDKByNumber(dk.getOrderNumber(), dk.getUser_id());
		dealInfo.setStatus(6);
		
		Set<DKDealInfo> entitys = new HashSet<DKDealInfo>();
		entitys.add(dk);
		entitys.add(dealInfo);
		dkDealDao.saveAll(entitys);
		
		String redisKey = getOderRedisKey(id,2);
		String _redisKey = getOderRedisKey(dealInfo.getId(),2);
		redisOps.setWithTimeout(redisKey, "", 1000 * 60 );
		redisOps.setWithTimeout(_redisKey, "", 1000 * 60);
		OderQueueUtil.setOderQueue(redisKey, 0l);
		OderQueueUtil.setOderQueue(_redisKey, 0l);
	}
	
	public String getOderRedisKey(Integer id,Integer type) {
		DKDealInfo dkInfo = dkDealDao.findById(id).get();
		return dkInfo.getId() + "_" + type + key;
	}
	
}
