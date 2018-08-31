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
	
}
