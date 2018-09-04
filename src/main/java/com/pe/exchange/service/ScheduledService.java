package com.pe.exchange.service;

import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pe.exchange.dao.DKDealDao;
import com.pe.exchange.entity.DKDealInfo;
import com.pe.exchange.redis.RedisOps;
import com.pe.exchange.utils.OderQueueUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduledService {
	
	@Autowired
	private RedisOps redisOps;
	
	@Autowired
	private DKDealDao dkDealDao;

	@Scheduled(fixedDelay = 5000)
	public void queueStart() {
		Map<String, Long> map = OderQueueUtil.getQueues();
		Iterator<String> it = map.keySet().iterator();
		String key = "";
		while(it.hasNext()) {
			key = it.next();
			Long times=redisOps.getOutTimes(key);
			System.out.println(key+"_"+times);
			if(times<1) {
				DKDealInfo dk = dkDealDao.findById(Integer.valueOf(key.split("_")[0])).get();
				if(3 == dk.getStatus() && "1".equals(key.split("_")[1])) {
					dk.setStatus(Integer.valueOf(key.split("_")[key.split("_").length-1]));
				}else if(6 == dk.getStatus()) {
					dk.setStatus(7);
				}
				dkDealDao.save(dk);
				OderQueueUtil.remove(key);
				redisOps.delete(key);
				it = map.keySet().iterator();
			}else {
				OderQueueUtil.setOderQueue(key, times);
			}
		}
	}
}
