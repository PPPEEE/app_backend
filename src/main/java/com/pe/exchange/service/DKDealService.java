package com.pe.exchange.service;


import com.pe.exchange.common.ResultEnum;
import com.pe.exchange.dao.DKDealAppealDao;
import com.pe.exchange.dao.DKDealDao;
import com.pe.exchange.dao.UserBalanceDao;
import com.pe.exchange.dao.UserBonusLogDao;
import com.pe.exchange.dao.UserDao;
import com.pe.exchange.entity.Appeal;
import com.pe.exchange.entity.DKDealInfo;
import com.pe.exchange.entity.User;
import com.pe.exchange.entity.UserBonusLog;
import com.pe.exchange.entity.UserPayInfo;
import com.pe.exchange.exception.BizException;
import com.pe.exchange.exception.SysException;
import com.pe.exchange.redis.RedisOps;
import com.pe.exchange.utils.OderQueueUtil;
import com.pe.exchange.utils.Pages;
import com.pe.exchange.utils.UserUtil;
import com.pe.exchange.utils.VeriCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class DKDealService {
	
	@Autowired
	private DKDealDao dkDealDao;
	
	 @Autowired
	 RedisOps redisOps;
	 
	 @Autowired
	 UserDao userDao;
	 
	 @Autowired
	 UserPayInfoService userPayInfoService;
	 
	 @Autowired
	 DKDealAppealDao dkDealAppealDao; 
	 
	 @Autowired
	 UserBalanceDao userBalanceDao;
	 @Autowired UserBonusLogDao userBonusLogDao;
	 
	 private final String key = "_orderKey";
	 
	 
	
	/***
	 * 查询用户DK总资产
	 * @return
	 */
	public Integer getUserDKNumber() {
	  User user = UserUtil.get();
	 
	  List<DKDealInfo> dkList = dkDealDao.getDKTotalNumber(user.getId());
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
	
	/**
	 * 发布订单
	 * @param dealInfo
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveDKDeal(DKDealInfo dealInfo,boolean flag) {
		User user = UserUtil.get();
		//绑定订单号
		dealInfo.setOrderNumber(VeriCodeUtils.getOrderIdByUUId());
		dealInfo.setMoney(dealInfo.getDealNumber() * 0.8);
		dealInfo.setUser_id(user.getId());
		dealInfo.setStatus(2);
		dealInfo.setTotalMoney(dealInfo.getDealNumber());
		int total = 0;
		if(2 == dealInfo.getType()) {
			total = getUserDKNumber();
			if(total<1) {
				throw new SysException();
			}
		}
		dkDealDao.save(dealInfo);
		if(flag) {
			userBalanceDao.subDKBalance(user.getId(), new BigDecimal(dealInfo.getDealNumber()));
		}
	}
	
	/**
	 * 取消订单
	 */
	@Transactional(rollbackFor = Exception.class)
	public void cleanDKDeal(Integer id) {
		Integer userId = UserUtil.get().getId();
		DKDealInfo dk = dkDealDao.findById(id).get();
		if(dk.getStatus() == 2 && dk.getUser_id() == userId) {
			dk.setStatus(0);
		}else {
			throw new BizException(ResultEnum.INTERNAL_SERVER_ERROR);
		}
		dkDealDao.save(dk);
		userBalanceDao.addDKBalance(dk.getUser_id(), new BigDecimal(dk.getDealNumber()));
	}
	
	/***
	 * 查询买或麦的全部订单
	 * @return
	 */
	public Pages findDKDeailList(Pages pages,int type,int status){
		Integer userId = UserUtil.get().getId();
		List<DKDealInfo> list =  null;
		int count = pages.getPageSize();
		if(type == 0) {
			list = dkDealDao.findUserDKList(userId,status,pages.getCurrentPage()*pages.getPageSize()-pages.getPageSize(),pages.getPageSize());
			count = dkDealDao.findUserDKList(userId,status);
		}else {
			list = dkDealDao.findTypeDKList(type,userId,status,pages.getCurrentPage()*pages.getPageSize()-pages.getPageSize(),pages.getPageSize());
			count = dkDealDao.findTypeDKList(type,userId,status);
		}
		setUserInfo(list);
		pages.setRecordTotal(count);
		pages.setResult(list);
		return pages;
	}
	
	private void setUserInfo(List<DKDealInfo> list) {
		User u = null;
		for (DKDealInfo dk : list) {
			u = userDao.findById(dk.getUser_id()).get();
			List<UserPayInfo> uPList =userPayInfoService.findUserPayInfoList(dk.getUser_id());
			u.setUserPayInfo(getPayListByType(uPList,dk.getPayInfo()));
			u.setPwd("");
			dk.setUser(u);
		}
	}
	
	private List<UserPayInfo> getPayListByType(List<UserPayInfo> list ,String type){
		List<UserPayInfo> _list = new ArrayList<UserPayInfo>();
		if(type != null && !"".equals(type)) {
			String[]  ts = type.split(",");
			for (int i = 0; i < ts.length; i++) {
				for (UserPayInfo userPayInfo : list) {
					if((userPayInfo.getPayType()+"").equals(ts[i].trim().toString())) {
						_list.add(userPayInfo);
					}
				}
			}
			return _list;
		}
		return list;
		
	}
	
	public DKDealInfo findDkById(Integer id) {
		List<DKDealInfo> dkInfo = new ArrayList<DKDealInfo>();
		dkInfo.add(dkDealDao.findById(id).get());
		setUserInfo(dkInfo);
		return dkInfo.size()>0?dkInfo.get(0):null;
		
		
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer dkDeailPurchase(Integer id,Integer dealNumber) {
		int userId = -1;
		//Integer status = 0;
		DKDealInfo dkInfo = dkDealDao.findById(id).get();
		//int number = 0;
		//status = dkInfo.getStatus();
		try {
			if(dealNumber > dkInfo.getDealNumber()) {
				throw new BizException(306,"购买金额大于订单金额");
			}
			
		//	if(dealNumber < dkInfo.getDealNumber()) {
				
				String newOrderNumber = VeriCodeUtils.getOrderIdByUUId();
				
				DKDealInfo dkDealInfo = new DKDealInfo();
				dkDealInfo.setDealNumber(dealNumber);
				dkDealInfo.setOrderNumber(newOrderNumber);
				dkDealInfo.setMinNumber(dkInfo.getMinNumber());
				dkDealInfo.setMoney(dealNumber * 0.8);
				dkDealInfo.setParentOrderNumber(dkInfo.getOrderNumber());
				dkDealInfo.setTimes(dkInfo.getTimes());
				//初始买入订单默认状态为等待付款
				dkDealInfo.setStatus(3);
				dkDealInfo.setUser_id(dkInfo.getUser_id());
				dkDealInfo.setType(dkInfo.getType());
				
				
				DKDealInfo dkDealInfo2 = new DKDealInfo();
				dkDealInfo2.setDealNumber(dealNumber);
				dkDealInfo2.setOrderNumber(newOrderNumber);
				dkDealInfo2.setMinNumber(dkInfo.getMinNumber());
				dkDealInfo2.setMoney(dealNumber * 0.8);
				dkDealInfo2.setTimes(dkInfo.getTimes());
				//初始买入订单默认状态为等待付款
				dkDealInfo2.setStatus(3);
				dkDealInfo2.setUser_id(UserUtil.get().getId());
				dkDealInfo2.setType(dkInfo.getType()==1?2:1);
				
				dkDealDao.save(dkDealInfo);
				dkDealDao.save(dkDealInfo2);
				
				String _redisKey = getOderRedisKey(dkDealInfo.getId(),dkInfo.getOrderNumber())+"_4";
				redisOps.setWithTimeout(_redisKey, "", 60 * dkInfo.getTimes());
				OderQueueUtil.setOderQueue(_redisKey, 0L);
				String redisKey = getOderRedisKey(dkDealInfo2.getId(),"1")+"_4";
				redisOps.setWithTimeout(redisKey, "",  60 * dkInfo.getTimes());
				OderQueueUtil.setOderQueue(redisKey, 0L);
				
				userId = dkDealInfo2.getId();
			/*}else {
				DKDealInfo dkDealInfo = new DKDealInfo();
				dkDealInfo.setDealNumber(dealNumber);
				dkDealInfo.setMinNumber(dkInfo.getMinNumber());
				dkDealInfo.setMoney(dealNumber * 0.8);
				dkDealInfo.setOrderNumber(dkInfo.getOrderNumber());
				dkDealInfo.setTimes(dkInfo.getTimes());
				//初始买入订单默认状态为等待付款
				dkDealInfo.setStatus(3);
				dkDealInfo.setUser_id(UserUtil.get().getId());
				dkDealInfo.setType(dkInfo.getType()==1?2:1);
				dkInfo.setStatus(3);
				dkDealDao.save(dkDealInfo);
				userId = dkDealInfo.getId();
				String _redisKey = getOderRedisKey(dkDealInfo.getId(),"1")+"_4";
				redisOps.setWithTimeout(_redisKey, "", 60 * dkInfo.getTimes());
				OderQueueUtil.setOderQueue(_redisKey, 0L);
				String redisKey = getOderRedisKey(id,"1")+"_"+status;
				redisOps.setWithTimeout(redisKey, "",  60 * dkInfo.getTimes());
				OderQueueUtil.setOderQueue(redisKey, 0L);
			}*/
				
				if(dkInfo.getDealNumber() - dealNumber == 0) {
					dkInfo.setDealNumber(0);
					dkInfo.setStatus(9);
				/*	if(!(Integer.valueOf(dkInfo.getTotalMoney()).intValue() == Integer.valueOf(dealNumber).intValue())) {
						
					}else {
						dkInfo.setStatus(3);
					}*/
				}else {
					dkInfo.setStatus(2);//一次购买未完成则首次发布订单状态不变 仍可继续交易
					dkInfo.setDealNumber(dkInfo.getDealNumber() - dealNumber);
				}
				dkDealDao.save(dkInfo);
		
			
			//userBalanceDao.subDKBalance(UserUtil.get().getId(), new BigDecimal(dkInfo.getDealNumber()));
		} catch (Exception e) {
		}
		return  userId;
	}
	
	
	

	@Transactional(rollbackFor = Exception.class)
	public void commitDK(Integer id) {
		//卖家
		DKDealInfo dkInfo = dkDealDao.findById(id).get();
		dkInfo.setStatus(1);
		String number = dkInfo.getOrderNumber();
		//买家
		DKDealInfo dealInfo = dkDealDao.findUserDKByNumber(number, dkInfo.getUser_id());
		dealInfo.setStatus(1);
		
		if(dealInfo.getDealNumber() < dkInfo.getDealNumber()){
			dkInfo.setDealNumber(dkInfo.getDealNumber()-dealInfo.getDealNumber());
		}
		Set<DKDealInfo> entitys = new HashSet<DKDealInfo>();
		entitys.add(dkInfo);
		entitys.add(dealInfo);
		dkDealDao.saveAll(entitys);
		userBalanceDao.addDKBalance(dealInfo.getUser_id(), new BigDecimal(dealInfo.getDealNumber()));

		List<UserBonusLog> bonusList=new ArrayList<>();
		//卖家增加DK记录
		UserBonusLog  userBonusLog=new UserBonusLog();
		userBonusLog.setAmount(new BigDecimal(dealInfo.getDealNumber()));
		userBonusLog.setUserId(dealInfo.getUser_id());
		userBonusLog.setBonusCoinType(0);
		userBonusLog.setBonusType(12);
		bonusList.add(userBonusLog);

		//买家减少DK记录
		userBonusLog=new UserBonusLog();
		userBonusLog.setAmount(new BigDecimal(dkInfo.getDealNumber()).multiply(new BigDecimal(-1)));
		userBonusLog.setUserId(dkInfo.getUser_id());
		userBonusLog.setBonusCoinType(1);
		userBonusLog.setBonusType(12);
		bonusList.add(userBonusLog);

	/*	userBonusLogDao.saveAll(bonusList);
		if(dealInfo.getDealNumber() < dkInfo.getDealNumber()) {
			dkInfo.setId(null);
			saveDKDeal(dkInfo,false);
		}*/
		
		
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
		
		String redisKey = getOderRedisKey(id,"2");
		String _redisKey = getOderRedisKey(dealInfo.getId(),"2");
		redisOps.setWithTimeout(redisKey, "",  60 * 60 * 30L);
		redisOps.setWithTimeout(_redisKey, "",  60 * 60 * 30L);
		OderQueueUtil.setOderQueue(redisKey, 0L);
		OderQueueUtil.setOderQueue(_redisKey, 0L);
	}
	
	public String getOderRedisKey(Integer id,String type) {
		DKDealInfo dkInfo = dkDealDao.findById(id).get();
		return dkInfo.getId() + "_" + type + key;
	}
	
	
	public void oderAppeal(Integer id,String fileName,String desc) {
		DKDealInfo dk = dkDealDao.findById(id).get();
		if(dk.getType() == 7) {
			Appeal appeal = new Appeal();
			appeal.setDkId(id);
			appeal.setFilePngName(fileName);
			appeal.setDescText(desc);
			dkDealAppealDao.save(appeal);
		}else {
			throw new BizException(305, "该订单状态不可申诉！");
		}
	}
	
}
