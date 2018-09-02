package com.pe.exchange.config;

import com.alibaba.fastjson.JSON;
import com.pe.exchange.dao.UserBonusLogDao;
import com.pe.exchange.entity.UserBonusConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PlatformConfig {
    @Autowired 
    UserBonusLogDao userBonusLogDao;

    private volatile UserBonusConfig userBonusConfig;

    @PostConstruct
    public void init(){
        refreshConfig();
    }

    @Scheduled(cron="0 0 0 * * ?")
    public void refreshConfig(){
        String config = userBonusLogDao.findConfig();
        UserBonusConfig c= JSON.parseObject(config,UserBonusConfig.class);
        userBonusConfig=c;
    }

    public UserBonusConfig getUserBonusConfig(){
        return userBonusConfig;
    }

}
