package com.pe.exchange.service;

import com.pe.exchange.common.ResultEnum;
import com.pe.exchange.dao.TransferLogDao;
import com.pe.exchange.dao.UserBalanceDao;
import com.pe.exchange.dao.UserDao;
import com.pe.exchange.entity.TransferLog;
import com.pe.exchange.exception.BizException;
import com.pe.exchange.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
    public void transfer(String address,String amount){
        BigDecimal bigAmount=new BigDecimal(amount);
        Integer userId=UserUtil.get();
        Integer destUserId=userDao.findIdByAddress(address);
        if(destUserId==null){
            throw new BizException(ResultEnum.USER_NOT_EXISTS);
        }
        BigDecimal balance=userBalanceDao.findBanalceByUserIdAndCoinType(userId,0);
        if(balance.compareTo(bigAmount)<0){
            throw new BizException(ResultEnum.INSUFFICIENT_BALANCE);
        }
        userBalanceDao.subDKBalance(userId, bigAmount);
        BigDecimal dkAmount = bigAmount.multiply(new BigDecimal(80)).divide(new BigDecimal(100));
        BigDecimal dnAmount = bigAmount.subtract(dkAmount);
        userBalanceDao.addDKBalance(destUserId,dkAmount);
        userBalanceDao.addDNBalance(destUserId,dnAmount);

        //保存一条转账记录
        TransferLog transferLog=new TransferLog();
        transferLog.setAmount(bigAmount);
        transferLog.setFromUserId(userId);
        transferLog.setToUserId(destUserId);
        transferLogDao.save(transferLog);
    }

    @Transactional(rollbackFor = Exception.class)
    public void exchange(String amount){
        Integer userId=UserUtil.get();
        BigDecimal bigAmount = new BigDecimal(amount);
        BigDecimal balance=userBalanceDao.findBanalceByUserIdAndCoinType(userId,0);
        if(balance.compareTo(bigAmount)<0){
            throw new BizException(ResultEnum.INSUFFICIENT_BALANCE);
        }
        BigDecimal dnAmount = bigAmount.multiply(new BigDecimal(6));
        userBalanceDao.subDKBalance(userId, bigAmount);
        userBalanceDao.addDNBalance(userId, dnAmount);

    }
}
