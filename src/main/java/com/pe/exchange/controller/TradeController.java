package com.pe.exchange.controller;

import com.pe.exchange.common.Result;
import com.pe.exchange.entity.Trade;
import com.pe.exchange.service.TradeService;
import com.pe.exchange.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Api(tags = "交易")
@RestController
@RequestMapping("trade")
public class TradeController {

    @Autowired TradeService tradeService;
    @ApiOperation("发布交易")
    @PostMapping("publish")
    public Result publish(@RequestParam("tradeType") int tradeType,@RequestParam("num") String num,@RequestParam("price") String price){
        Trade trade=new Trade();
        trade.setTradeType(tradeType);
        trade.setMarketType(1);
        trade.setPriceType(0);
        trade.setPrice(new BigDecimal(price));
        trade.setNum(new BigDecimal(num));
        trade.setAmount(trade.getPrice().multiply(trade.getNum()));
        trade.setUserId(UserUtil.get());
       return tradeService.publish(trade);

    }


}
