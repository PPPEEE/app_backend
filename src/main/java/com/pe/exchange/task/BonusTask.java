package com.pe.exchange.task;

import com.pe.exchange.config.PlatformConfig;
import com.pe.exchange.dao.TransferLogDao;
import com.pe.exchange.dao.UserBalanceDao;
import com.pe.exchange.dao.UserBonusLogDao;
import com.pe.exchange.dao.UserDao;
import com.pe.exchange.dao.UserInvitDao;
import com.pe.exchange.entity.TransferLog;
import com.pe.exchange.entity.UserBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BonusTask {
    @Autowired TransferLogDao transferLogDao;
    @Autowired UserDao userDao;
    @Autowired UserBalanceDao userBalanceDao;
    @Autowired UserInvitDao userInvitDao;
    @Autowired UserBonusLogDao userBonusLogDao;
    @Autowired PlatformConfig platformConfig;

    @Autowired BonusTaskHandle bonusTaskHandle;

    @Scheduled(fixedDelay = 60*1000)
    public void transferBonus(){
        List<TransferLog> transferLogs = transferLogDao.findByBonusStatus(0);
        for (TransferLog transferLog : transferLogs) {

            bonusTaskHandle.caclBonus(transferLog);
        }

    }




    @Scheduled(fixedDelay = 60*1000)
    public void updateUserLevel(){

        //VIP
        //先查出分数达标的
        List<UserBalance> upToLevelUser = userBalanceDao.findUpToLevelUser(2, 1000000);
        for (UserBalance userBalance : upToLevelUser) {

            //再查出推荐人数达标的
            List<Integer> userIds = userInvitDao.findInvitedUserIBydUserId(userBalance.getUserId(),1000,1);
            if(userIds.size()>=5){
                userDao.updateUserLevel(userBalance.getUserId(),2);
            }

        }

        //普通会员
        //先查出分数达标的
        upToLevelUser = userBalanceDao.findUpToLevelUser(1, 1000);
        for (UserBalance userBalance : upToLevelUser) {

            //再查出推荐人数达标的
            List<Integer> userIds = userInvitDao.findInvitedUserIBydUserId(userBalance.getUserId(),1000,10);
            if(userIds.size()>=1){
                userDao.updateUserLevel(userBalance.getUserId(),1);
            }

        }
    }
}
