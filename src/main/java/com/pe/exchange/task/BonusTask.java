package com.pe.exchange.task;

import com.pe.exchange.config.PlatformConfig;
import com.pe.exchange.dao.TransferLogDao;
import com.pe.exchange.dao.UserBalanceDao;
import com.pe.exchange.dao.UserBonusLogDao;
import com.pe.exchange.dao.UserDao;
import com.pe.exchange.dao.UserInvitDao;
import com.pe.exchange.entity.TransferLog;
import com.pe.exchange.entity.UserBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
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
            try {
                bonusTaskHandle.caclBonus(transferLog);
            }catch (Exception e){
                log.error("转账奖励结算失败,转账ID:"+transferLog.getId()+",转账人: "+transferLog.getFromUserId()+",收款人: "+transferLog.getToUserId(),e);
            }
        }

    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void everydayRelease(){
        List<UserBalance> dnWithoutZero = userBalanceDao.findDNWithoutZero();
        for (UserBalance balance : dnWithoutZero) {

           try {
               bonusTaskHandle.release(balance);
           }catch (Exception e){
               log.error("用户每日释放失败,用户ID:"+balance.getUserId(),e);
           }
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
                bonusTaskHandle.updateUserLevel(userBalance.getUserId(),2);
            }

        }

        //普通会员
        //先查出分数达标的
        upToLevelUser = userBalanceDao.findUpToLevelUser(1, 1000);
        for (UserBalance userBalance : upToLevelUser) {

            //再查出推荐人数达标的
            List<Integer> userIds = userInvitDao.findInvitedUserIBydUserId(userBalance.getUserId(),1000,10);
            if(userIds.size()>=1){
                bonusTaskHandle.updateUserLevel(userBalance.getUserId(),1);
            }

        }
    }
}
