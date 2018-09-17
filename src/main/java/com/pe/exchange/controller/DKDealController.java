package com.pe.exchange.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;
import com.pe.exchange.entity.DKDealInfo;
import com.pe.exchange.service.DKDealService;
import com.pe.exchange.utils.OderQueueUtil;
import com.pe.exchange.utils.Pages;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	@ApiOperation("账户DK总资产")
	@PostMapping("findTotal")
	public Result findDKTotalNumber() {
		return Results.success(dkDealService.getUserDKNumber());
	}
	
	
	/***
	 * 发布DK订单
	 * @param dealInfo
	 * @return
	 */
	@ApiOperation("发布DK订单")
	@PostMapping("releaseDK")
	public Result releaseDk(@RequestBody DKDealInfo dealInfo) {
		dkDealService.saveDKDeal(dealInfo,true);
		return Results.success();
	}
	
	/***
	 * 查询订单 
	 * @param type 0全部 1买入 2卖入 
	 * @return
	 */
	@ApiOperation("查询订单 ")
	@PostMapping("dkByType")
	public Result findDKDeailByType(@RequestBody Map<String, String> param) {
		Pages pages = new Pages();
		pages.setPageSize(Integer.valueOf(param.get("pageSize")));
		pages.setCurrentPage(Integer.valueOf(param.get("pageNo")));
		return Results.success(dkDealService.findDKDeailList(pages,Integer.valueOf(param.get("type")),Integer.valueOf(param.get("status"))));
	}
	
	@PostMapping("findByCurrentUser")
	public Result findCurrentUserDkByType(@RequestBody Map<String, Integer> param) {
		Pages pages = new Pages();
		pages.setPageSize(Integer.valueOf(param.get("pageSize")));
		pages.setCurrentPage(Integer.valueOf(param.get("pageNo")));
		return Results.success(dkDealService.findByUser(pages,Integer.valueOf(param.get("type"))));
	}
	
	/***
	 * 查询订单详情
	 * @param id
	 * @return
	 */
	@ApiOperation("查询订单详情")
	@PostMapping("findDkById")
	public Result findDKById(@RequestBody Map<String, Integer> param) {
		return Results.success(dkDealService.findDkById(param.get("id")));
	}
	
	/***
	 * 购买订单
	 * @param id
	 * @return
	 */
	@ApiOperation("购买订单")
	@PostMapping("dkPurchase")
	public Result dkDeailPurchase(@RequestBody Map<String, Integer> param) {
		int id = dkDealService.dkDeailPurchase(param.get("id"),param.get("dealNumber"));
		return Results.success(id);
	}
	
	/**
	 * 取消订单
	 * @param param
	 * @return
	 */
	@ApiOperation("取消订单")
	@PostMapping("dkClean")
	public Result dkDeailClean(@RequestBody Map<String, Integer> param) {
	
		dkDealService.cleanDKDeal(param.get("id"));
		return Results.success();
	}
	
	/***
	 * 卖家确认收款
	 * @param id
	 * @return
	 */
	@ApiOperation("卖家(出售方)确认收款")
	@PostMapping("commit")
	public Result commitOder(@RequestBody Map<String, Integer> param) {
		dkDealService.commitDK(param.get("id"));
		return Results.success();
	}
	
	/***
	 * 卖家确认付款
	 * @param id
	 * @return
	 */
	@ApiOperation("买家确认付款(购买方)确认收款")
	@PostMapping("paymentCommitOder")
	public Result paymentCommitOder(@RequestBody Map<String, Integer> param) {
		
		dkDealService.paymentCommitOder(param.get("id"));
		return Results.success();
	}
	
	/***
	 * 订单过期时间
	 * @param id
	 * @return
	 */
	@ApiOperation("订单过期时间")
	@PostMapping("getExpiryTime")
	public Result getExpiryTime(@RequestBody Map<String, Integer> param) {
		Long times = -1L; 
		String rKey = dkDealService.getOderRedisKey(param.get("id"),"1")+"_4";
		if(OderQueueUtil.getQueues().containsKey(rKey)) {
			times = OderQueueUtil.get(rKey);
		}
		if(times < 0) {
			String rKey2 = dkDealService.getOderRedisKey(param.get("id"),dkDealService.findDkById(param.get("id")).getParentOrderNumber())+"_4";
			if(OderQueueUtil.getQueues().containsKey(rKey2)) {
				times = OderQueueUtil.get(rKey2);
			}
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
	@ApiOperation("申诉订单")
	@PostMapping("oderAppeal")
	public Result oderAppeal(@RequestBody Map<String, String> param) {
		dkDealService.oderAppeal(Integer.valueOf(param.get("id")), param.get("fileName"), param.get("desc"));
		return Results.success();
	}
}
