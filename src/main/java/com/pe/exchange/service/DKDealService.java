package com.pe.exchange.service;

<<<<<<< HEAD
import java.util.List;
import java.util.Optional;

import org.codehaus.plexus.util.StringUtils;
=======
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import com.pe.exchange.common.ResultEnum;
import com.pe.exchange.dao.DKDealDao;
import com.pe.exchange.dao.UserDao;
=======
import com.pe.exchange.dao.DKDealAppealDao;
import com.pe.exchange.dao.DKDealDao;
import com.pe.exchange.dao.UserDao;
import com.pe.exchange.entity.Appeal;
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
import com.pe.exchange.entity.DKDealInfo;
import com.pe.exchange.entity.User;
import com.pe.exchange.exception.BizException;
import com.pe.exchange.exception.SysException;
import com.pe.exchange.redis.RedisOps;
<<<<<<< HEAD
=======
import com.pe.exchange.utils.OderQueueUtil;
import com.pe.exchange.utils.UserUtil;
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
import com.pe.exchange.utils.VeriCodeUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DKDealService {
	
<<<<<<< HEAD
	private static final Logger log = LoggerFactory.getLogger(DKDealService.class);
	
=======
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
	@Autowired
	private DKDealDao dkDealDao;
	
	 @Autowired
	 RedisOps redisOps;
	 
	 @Autowired
	 UserDao userDao;
<<<<<<< HEAD
=======
	 
	 @Autowired
	 UserPayInfoService userPayInfoService;
	 
	 @Autowired
	 DKDealAppealDao dkDealAppealDao; 
	 
	 private final String key = "_orderKey";
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
	
	/***
	 * 查询用户DK总资产
	 * @return
	 */
<<<<<<< HEAD
	public Integer getUserDKNumber(String token) {
	  String userId = redisOps.get(token);
	  if(!StringUtils.isNotBlank(userId)) {
		  log.error("用户失效！");
		  throw new BizException(ResultEnum.USER_FAIL);
	  }
	  List<DKDealInfo> dkList = dkDealDao.getDKTotalNumber(userId);
=======
	public Integer getUserDKNumber() {
	  User user = UserUtil.get();
	 
	  List<DKDealInfo> dkList = dkDealDao.getDKTotalNumber(user.getId());
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
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
	
<<<<<<< HEAD
	public void saveDKDeal(DKDealInfo dealInfo,String token) {
		String userId = redisOps.get(token);
		if(!StringUtils.isNotBlank(userId)) {
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
=======
	public void saveDKDeal(DKDealInfo dealInfo) {
		User user = UserUtil.get();
		//绑定订单号
		dealInfo.setOrderNumber(VeriCodeUtils.getOrderIdByUUId());
		dealInfo.setMoney(dealInfo.getDealNumber() * 0.8);
		dealInfo.setUser_id(user.getId());
		int total = 0;
		if(2 == dealInfo.getType()) {
			total = getUserDKNumber();
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
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
<<<<<<< HEAD
		return dkDealDao.findTypeDKList(type);
	}
=======
		List<DKDealInfo> list =  null;
		if(type == 0) {
			list = dkDealDao.findUserDKList(UserUtil.get().getId());
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
		dkDealInfo.setUser_id(UserUtil.get().getId());
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
	
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84
}
