package com.pe.exchange.task;

import com.pe.exchange.dao.TransferLogDao;
import com.pe.exchange.dao.UserDao;
import com.pe.exchange.entity.TransferLog;
import com.pe.exchange.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BonusTask {
    @Autowired TransferLogDao transferLogDao;
    @Autowired UserDao userDao;

    @Scheduled(fixedDelay = 2*60*1000)
    public void transferBonus(){
        List<TransferLog> transferLogs = transferLogDao.findByBonusStatus(0);
        transferLogs.forEach(transferLog -> {
            Integer toUserId = transferLog.getToUserId();
            User one = userDao.getOne(toUserId);
            //如果是直推人,则拿直推奖励
            if(transferLog.getFromUserId().toString().equals(one.getRefereeId())){

            }
        });

    }
}
