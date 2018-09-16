package com.pe.exchange.task;

import com.pe.exchange.config.PlatformConfig;
import com.pe.exchange.dao.TransferLogDao;
import com.pe.exchange.dao.UserBalanceDao;
import com.pe.exchange.dao.UserBonusLogDao;
import com.pe.exchange.dao.UserDao;
import com.pe.exchange.dao.UserInvitDao;
import com.pe.exchange.entity.TransferLog;
import com.pe.exchange.entity.User;
import com.pe.exchange.entity.UserBalance;
import com.pe.exchange.entity.UserBonusLog;
import com.pe.exchange.entity.UserInvit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class BonusTaskHandle {
    @Autowired TransferLogDao transferLogDao;
    @Autowired UserDao userDao;
    @Autowired UserBalanceDao userBalanceDao;
    @Autowired UserInvitDao userInvitDao;
    @Autowired UserBonusLogDao userBonusLogDao;
    @Autowired PlatformConfig platformConfig;

    @Transactional(rollbackFor = Exception.class)
    public void caclBonus(TransferLog transferLog ){

        List<UserBonusLog> bonusList=new ArrayList<>();

        Integer toUserId = transferLog.getToUserId();
        User one = userDao.findById(toUserId).get();

        //转账人获得对冲DN奖励,金额的80%
        BigDecimal bonusDN = transferLog.getAmount().multiply(new BigDecimal(platformConfig.getUserBonusConfig().getInvit().getDn())).divide(new BigDecimal(1000));
        userBalanceDao.addDNBalance(transferLog.getFromUserId(),bonusDN);
        UserBonusLog userBonusLog=new UserBonusLog();
        userBonusLog.setUserId(transferLog.getFromUserId());
        userBonusLog.setAmount(bonusDN);
        userBonusLog.setBonusCoinType(1);
        userBonusLog.setBonusType(1);
        bonusList.add(userBonusLog);

        //如果对方是转账人直接推荐的人,则转账人拿直推DK奖励,金额的5%-10%
        if(transferLog.getFromUserId().toString().equals(one.getRefereeId())){

            //直推DK奖励,根据邀请顺序分不同的档
            Integer order =
                userInvitDao.findInvitOrderByUserIdAndInvitedUserId(transferLog.getFromUserId(), toUserId);
            Integer rate=platformConfig.getUserBonusConfig().getInvit().getDk().get(order);
            if(rate==null){
                //如果未命中,那肯定是最大一档
                int size = platformConfig.getUserBonusConfig().getInvit().getDk().size();
                rate=platformConfig.getUserBonusConfig().getInvit().getDk().get(size-1);
                BigDecimal bonusDK = transferLog.getAmount().multiply(new BigDecimal(rate)).divide(new BigDecimal(1000));
                userBalanceDao.addDKBalance(transferLog.getFromUserId(),bonusDK);

                userBonusLog=new UserBonusLog();
                userBonusLog.setAmount(bonusDK);
                userBonusLog.setUserId(transferLog.getFromUserId());
                userBonusLog.setBonusCoinType(1);
                userBonusLog.setBonusType(2);
                bonusList.add(userBonusLog);
            }

        }
        //转账人的(最近N代)邀请人拿流通奖励,释放0.4%-0.7%

        //获取奖励代数
        int circLevel = platformConfig.getUserBonusConfig().getTeam().getCircLevel();
        //取得最近circLevel代邀请人
        List<UserInvit> list =
            userInvitDao.findByInvitedUserIdAndInvitLevel(transferLog.getFromUserId(), circLevel);

        for (UserInvit userInvit : list) {
            //根据邀请人的级别获得奖励比例
            Integer rate=platformConfig.getUserBonusConfig().getTeam().getCirc().get(userInvit.getUserLevel());
            BigDecimal bonusDK=transferLog.getAmount().multiply(new BigDecimal(rate)).divide(new BigDecimal(1000));
            //增加邀请人DK,减少DN

            int count = userBalanceDao.subDNBalance(userInvit.getUserId(), bonusDK);
            if(count==0){
                log.warn("流通奖励-用户DN不足,无法释放,用户id:"+userInvit.getId());
            }
            userBalanceDao.addDKBalance(userInvit.getUserId(), bonusDK);

            //dk增加记录
            userBonusLog=new UserBonusLog();
            userBonusLog.setAmount(bonusDK);
            userBonusLog.setUserId(userInvit.getUserId());
            userBonusLog.setBonusCoinType(0);
            userBonusLog.setBonusType(4);
            bonusList.add(userBonusLog);

            //dn减少记录
            userBonusLog=new UserBonusLog();
            userBonusLog.setAmount(bonusDK.multiply(new BigDecimal(-1)));
            userBonusLog.setUserId(userInvit.getUserId());
            userBonusLog.setBonusCoinType(1);
            userBonusLog.setBonusType(4);
            bonusList.add(userBonusLog);
        }
        //保存奖励记录,供追溯
        userBonusLogDao.saveAll(bonusList);

        //修改转账记录的奖励结算状态.
        transferLog.setBonusStatus(1);
        transferLogDao.save(transferLog);
    }

    @Transactional(rollbackFor = Exception.class)
    public void release(UserBalance balance){
        int rate=platformConfig.getUserBonusConfig().getDn2dk();
        BigDecimal amount = balance.getBalance().multiply(new BigDecimal(rate)).divide(new BigDecimal(1000));
        userBalanceDao.subDNBalance(balance.getUserId(),amount);
        userBalanceDao.addDKBalance(balance.getUserId(),amount);
        List<UserBonusLog> list=new ArrayList<>();

        //DK增加记录
        UserBonusLog userBonusLog=new UserBonusLog();
        userBonusLog.setAmount(amount);
        userBonusLog.setUserId(balance.getUserId());
        userBonusLog.setBonusCoinType(0);
        userBonusLog.setBonusType(5);
        list.add(userBonusLog);
        //DN减少记录
        userBonusLog=new UserBonusLog();
        userBonusLog.setAmount(amount.multiply(new BigDecimal(-1)));
        userBonusLog.setUserId(balance.getUserId());
        userBonusLog.setBonusCoinType(1);
        userBonusLog.setBonusType(5);
        list.add(userBonusLog);

        userBonusLogDao.saveAll(list);

    }
}
