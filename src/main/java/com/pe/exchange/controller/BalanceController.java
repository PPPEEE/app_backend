package com.pe.exchange.controller;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;
import com.pe.exchange.entity.UserBalance;
import com.pe.exchange.entity.UserBonusLog;
import com.pe.exchange.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = "收支记录")
@RestController
@RequestMapping("balance")
public class BalanceController {

    @Autowired TransferService transferService;

    @Data
    @ApiModel
    public static class IncomeListBean {
        @ApiModelProperty(required = true,value = "币种,0DK,1DN")
        private int coinType;
        @ApiModelProperty(value = "收支类型,1:DK、DN均为收入,2: DK为支出 DN为释放,3: DK为冻结 DN为加速,不填为全部")
        private int incomeType;

        private int pageNo;

        private int pageSize;


    }

    @Data
    @ApiModel
    public static class BalanceBean {
        @ApiModelProperty(required = true,value = "币种,0DK,1DN")
        private int coinType;

    }

    @ApiOperation("获取全部余额")
    @GetMapping("get")
    public Result<List<UserBalance>> getAllBalance(){
        return Results.success(transferService.getBalance());
    }

    @ApiOperation("获取余额")
    @PostMapping("getby")
    public Result<UserBalance> getBalance(@RequestBody BalanceBean balanceBean){
        return Results.success(transferService.getBalance(balanceBean.getCoinType()));
    }

    @ApiOperation("收支记录")
    @PostMapping("incomeList")
    public Result<List<UserBonusLog>> incomeList(@RequestBody IncomeListBean incomeListBean){
        return Results.success(transferService.getIncomeList(incomeListBean.coinType,incomeListBean.incomeType,incomeListBean.getPageNo(),incomeListBean.getPageSize()));
    }
}
