package com.pe.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pe.exchange.common.ResultEnum;
import com.pe.exchange.dao.TransferLogDao;
import com.pe.exchange.dao.UserBalanceDao;
import com.pe.exchange.dao.UserDao;
import com.pe.exchange.entity.TransferLog;
import com.pe.exchange.exception.BizException;
import com.pe.exchange.utils.UserUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransferService {
    @Autowired UserDao userDao;
    @Autowired UserBalanceDao userBalanceDao;
    @Autowired TransferLogDao transferLogDao;

    public String getAddress(){
        Integer userId= UserUtil.get();
        String address=userDao.findAddressById(userId);
        return address;
    }

    @Transactional(rollbackFor = Exception.class)
    public void transfer(String address,Integer amount){
        Integer userId=UserUtil.get();
        Integer destUserId=userDao.findIdByAddress(address);
        if(destUserId==null){
            throw new BizException(ResultEnum.USER_NOT_EXISTS);
        }
        Integer balance=userBalanceDao.findBanalceByUserId(userId);
        if(balance.compareTo(amount)<0){
            throw new BizException(ResultEnum.INSUFFICIENT_BALANCE);
        }
        userBalanceDao.subBalance(userId, amount);
        userBalanceDao.addBalance(destUserId,amount);

        //保存一条转账记录
        TransferLog transferLog=new TransferLog();
        transferLog.setAmount(amount);
        transferLog.setFromUserId(userId);
        transferLog.setToUserId(destUserId);
        transferLogDao.save(transferLog);
    }
}
