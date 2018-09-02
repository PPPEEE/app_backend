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
	
	
	/***
	 * 账户DK总资产
	 * @return
	 */
	@PostMapping("findTotal")
	public Result findDKTotalNumber() {
		return Results.success(dkDealService.getUserDKNumber());
	}
	
	
	/***
	 * 发布DK订单
	 * @param dealInfo
	 * @return
	 */
	@PostMapping("releaseDK")
	public Result releaseDk(DKDealInfo dealInfo) {
		dkDealService.saveDKDeal(dealInfo);
		return Results.success();
	}
	
	/***
	 * 查询订单 
	 * @param type 0全部 1买入 2卖入 
	 * @return
	 */
	@PostMapping("dkByType")
	public Result findDKDeailByType(@RequestParam("type") int type) {
		return Results.success(dkDealService.findDKDeailList(type));
	}
	
	/***
	 * 查询订单详情
	 * @param id
	 * @return
	 */
	@PostMapping("findDkById")
	public Result findDKById(@RequestParam("id") Integer id) {
		return Results.success(dkDealService.findDkById(id));
	}
	
	/***
	 * 购买订单
	 * @param id
	 * @return
	 */
	@PostMapping("dkPurchase")
	public Result dkDeailPurchase(Integer id) {
		dkDealService.dkDeailPurchase(id);
		return Results.success();
	}
	
	/***
	 * 卖家确认收款
	 * @param id
	 * @return
	 */
	@PostMapping("commit")
	public Result commitOder(Integer id) {
		dkDealService.commitDK(id);
		return Results.success();
	}
	
	/***
	 * 卖家确认付款
	 * @param id
	 * @return
	 */
	@PostMapping("paymentCommitOder")
	public Result paymentCommitOder(Integer id) {
		
		dkDealService.paymentCommitOder(id);
		return Results.success();
	}
	
	/***
	 * 订单过期时间
	 * @param oderId
	 * @return
	 */
	@PostMapping("getExpiryTime")
	public Result getExpiryTime(@RequestParam("oderId") Integer oderId) {
		Long times = 0L;
		String rKey = dkDealService.getOderRedisKey(oderId,1);
		if(OderQueueUtil.getQueues().containsKey(rKey)) {
			times = OderQueueUtil.get(rKey);
		}
		return Results.success(times);
	}
	
	/***
	 * 申诉订单
	 * @param id
	 * @param fileName
	 * @param desc
	 * @return
	 */
	@PostMapping("oderAppeal")
	public Result oderAppeal(Integer id,String fileName,String desc) {
		
		return Results.success();
	}
}
