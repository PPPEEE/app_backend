package com.pe.exchange.controller;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;
import com.pe.exchange.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "收支记录")
@RestController
@RequestMapping("balance")
public class BalanceController {

    @Autowired TransferService transferService;
    @Data
    public static class IncomeListBean {
        @ApiModelProperty(required = true,value = "币种,0DK,1DN")
        private int coinType;
        @ApiModelProperty(value = "收支类型,1为收入,2为支出,不填为全部")
        private int incomeType;
    }

    @Data
    public static class BalanceBean {
        @ApiModelProperty(required = true,value = "币种,0DK,1DN")
        private int coinType;

    }

    @ApiOperation("获取全部余额")
    @GetMapping("get")
    public Result getAllBalance(){
        return Results.success(transferService.getBalance());
    }

    @ApiOperation("获取余额")
    @GetMapping("getby")
    public Result getBalance(@RequestBody BalanceBean balanceBean){
        return Results.success(transferService.getBalance(balanceBean.getCoinType()));
    }

    @ApiOperation("收支记录")
    @GetMapping("incomeList")
    public Result incomeList(@RequestBody IncomeListBean incomeListBean){
        return Results.success(transferService.getIncomeList(incomeListBean.coinType,incomeListBean.incomeType));
    }
}