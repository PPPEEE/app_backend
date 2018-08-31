package com.pe.exchange.service;

import com.pe.exchange.dao.UserDao;
import com.pe.exchange.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    @Autowired UserDao userDao;

    public String getAddress(){
        Integer userId= UserUtil.get();
        String address=userDao.findAddressById(userId);
        return address;
    }
}
