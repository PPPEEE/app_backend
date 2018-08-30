package com.pe.exchange.service;

import com.pe.exchange.dao.UserBalanceDao;
import com.pe.exchange.entity.UserBalance;
import com.pe.exchange.utils.UserUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    @Autowired UserBalanceDao userBalanceDao;

    public String getAddress(){
        Integer userId= UserUtil.get();
        String address=userBalanceDao.findAddressByUserIdAndCoinType(userId,0);
        return address;
    }
}
