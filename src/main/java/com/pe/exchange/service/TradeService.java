package com.pe.exchange.service;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;
import com.pe.exchange.dao.TradeDao;
import com.pe.exchange.entity.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeService {
    @Autowired TradeDao tradeDao;
    public Result publish(Trade trade){

        Trade t = tradeDao.save(trade);
        return Results.success(t.getId());
    }
}
