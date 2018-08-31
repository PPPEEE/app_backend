package com.pe.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;
import com.pe.exchange.entity.DKDealInfo;
import com.pe.exchange.service.DKDealService;
import com.pe.exchange.utils.OderQueueUtil;

import io.swagger.annotations.Api;

/**
*
* @author jiangwei
* @version v1.0
* @email kuaidixiaogejiangmou@outlook.com
* @since 2018/3/30 23:05
*/
@Api(tags = "DK交易模块")
@RestController
@RequestMapping("dks")
public class DKDealController {

	@Autowired
	private DKDealService dkDealService;
	
	
	@PostMapping("findTotal")
	public Result findDKTotalNumber() {
		return Results.success(dkDealService.getUserDKNumber());
	}
	
	@PostMapping("releaseDK")
	public Result releaseDk(DKDealInfo dealInfo) {
		dkDealService.saveDKDeal(dealInfo);
		return Results.success();
	}
	
	@PostMapping("dkByType")
	public Result findDKDeailByType(@RequestParam("type") int type) {
		return Results.success(dkDealService.findDKDeailList(type));
	}
	
	@PostMapping("findDkById")
	public Result findDKById(@RequestParam("id") Integer id) {
		return Results.success(dkDealService.findDkById(id));
	}
	
	@PostMapping("dkPurchase")
	public Result dkDeailPurchase(Integer id) {
		dkDealService.dkDeailPurchase(id);
		return Results.success();
	}
	
	@PostMapping("commit")
	public Result commitOder(Integer id) {
		dkDealService.commitDK(id);
		return Results.success();
	}
	
	@PostMapping("getExpiryTime")
	public Result getExpiryTime(@RequestParam("oderId") Integer oderId) {
		Long times = 0L;
		String rKey = dkDealService.getOderRedisKey(oderId);
		if(OderQueueUtil.getQueues().containsKey(rKey)) {
			times = OderQueueUtil.get(rKey);
		}
		return Results.success(times);
	}
	
}
