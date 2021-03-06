package com.pe.exchange.service;

import com.pe.exchange.common.ResultEnum;
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
import com.pe.exchange.exception.BizException;
import com.pe.exchange.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TransferService {

    @Autowired UserService userService;
    @Autowired UserPayPwdService userPayPwdService;
    @Autowired UserDao userDao;
    @Autowired UserBalanceDao userBalanceDao;
    @Autowired TransferLogDao transferLogDao;
    @Autowired PlatformConfig platformConfig;
    @Autowired UserInvitDao userInvitDao;
    @Autowired UserBonusLogDao userBonusLogDao;

    public String getAddress(){
        Integer userId= UserUtil.get().getId();
        User user=userDao.findById(userId).get();
        return user.getAddress();
    }

    @Transactional(rollbackFor = Exception.class)
    public void transfer(String address,String amount,String payPwd,String code){

        User user=UserUtil.get();
        boolean isChecked = userService.checkVeriCode(user.getTelephone(), code);
        if(!isChecked){
            throw new BizException(ResultEnum.CODE_ERROR);
        }
        isChecked=userPayPwdService.isExits(payPwd);
        if(!isChecked){
            throw new BizException(ResultEnum.PAY_PWD_ERROR);
        }
        BigDecimal bigAmount=new BigDecimal(amount);

        User destUser=userDao.findByAddress(address);
        if(destUser==null){
            throw new BizException(ResultEnum.USER_NOT_EXISTS);
        }
        UserBalance balance=userBalanceDao.findByUserIdAndCoinType(user.getId(),0);
        if(balance.getBalance().compareTo(bigAmount)<0){
            throw new BizException(ResultEnum.INSUFFICIENT_BALANCE);
        }
        int updateCount = userBalanceDao.subDKBalance(user.getId(), bigAmount);
        if(updateCount==0){
            throw new BizException(ResultEnum.INSUFFICIENT_BALANCE);
        }
        BigDecimal dkAmount = bigAmount.multiply(new BigDecimal(80)).divide(new BigDecimal(100));
        BigDecimal dnAmount = bigAmount.subtract(dkAmount);
        userBalanceDao.addDKBalance(destUser.getId(),dkAmount);
        userBalanceDao.addDNBalance(destUser.getId(),dnAmount);

        List<UserBonusLog> bonusList=new ArrayList<>();
        //转账方dk减少记录
        UserBonusLog  userBonusLog=new UserBonusLog();
        userBonusLog.setAmount(bigAmount.multiply(new BigDecimal(-1)));
        userBonusLog.setUserId(user.getId());
        userBonusLog.setBonusCoinType(0);
        userBonusLog.setBonusType(11);
        bonusList.add(userBonusLog);

        //收款方DK增加记录
        userBonusLog=new UserBonusLog();
        userBonusLog.setAmount(dkAmount);
        userBonusLog.setUserId(destUser.getId());
        userBonusLog.setBonusCoinType(0);
        userBonusLog.setBonusType(11);
        bonusList.add(userBonusLog);

        //收款方DN增加记录
        userBonusLog=new UserBonusLog();
        userBonusLog.setAmount(dnAmount);
        userBonusLog.setUserId(destUser.getId());
        userBonusLog.setBonusCoinType(1);
        userBonusLog.setBonusType(11);
        bonusList.add(userBonusLog);

        userBonusLogDao.saveAll(bonusList);

        //保存一条转账记录
        TransferLog transferLog=new TransferLog();
        transferLog.setAmount(bigAmount);
        transferLog.setFromUserId(user.getId());
        transferLog.setToUserId(destUser.getId());
        transferLogDao.save(transferLog);
    }

    @Transactional(rollbackFor = Exception.class)
    public void exchange(String amount){
        Integer userId=UserUtil.get().getId();
        BigDecimal dkAmount = new BigDecimal(amount);
        UserBalance balance=userBalanceDao.findByUserIdAndCoinType(userId,0);
        if(balance.getBalance().compareTo(dkAmount)<0){
            throw new BizException(ResultEnum.INSUFFICIENT_BALANCE);
        }
        BigDecimal dnAmount = dkAmount.multiply(new BigDecimal(6));
        int updateCount = userBalanceDao.subDKBalance(userId, dkAmount);
        if(updateCount==0){
            throw new BizException(ResultEnum.INSUFFICIENT_BALANCE);
        }
        userBalanceDao.addDNBalance(userId, dnAmount);


        List<UserBonusLog> bonusList=new ArrayList<>();
        //dk减少记录
        UserBonusLog  userBonusLog=new UserBonusLog();
        userBonusLog.setAmount(dkAmount.multiply(new BigDecimal(-1)));
        userBonusLog.setUserId(userId);
        userBonusLog.setBonusCoinType(0);
        userBonusLog.setBonusType(10);
        bonusList.add(userBonusLog);

        //dN增加记录
        userBonusLog=new UserBonusLog();
        userBonusLog.setAmount(dnAmount);
        userBonusLog.setUserId(userId);
        userBonusLog.setBonusCoinType(1);
        userBonusLog.setBonusType(10);
        bonusList.add(userBonusLog);


        //转账人的(最近N代)邀请人拿兑换奖励,释放1%-3%

        //获取奖励代数
        int exchangeLevel = platformConfig.getUserBonusConfig().getTeam().getExchangeLevel();
        //取得最近circLevel代邀请人
        List<UserInvit> list =
            userInvitDao.findByInvitedUserIdAndInvitLevel(userId, exchangeLevel);

        for (UserInvit userInvit : list) {
            //根据邀请人的级别获得奖励比例
            Integer rate=platformConfig.getUserBonusConfig().getTeam().getExchange().get(userInvit.getUserLevel());
            BigDecimal bonusDK=dkAmount.multiply(new BigDecimal(rate)).divide(new BigDecimal(1000));
            //增加邀请人DK,减少DN
            updateCount = userBalanceDao.subDNBalance(userInvit.getUserId(), bonusDK);
            if(updateCount==0){
                log.warn("兑换奖励-用户DN不足,无法释放,用户id:"+userInvit.getId());
            }
            userBalanceDao.addDKBalance(userInvit.getUserId(), bonusDK);


            //dk增加记录
            userBonusLog=new UserBonusLog();
            userBonusLog.setAmount(bonusDK);
            userBonusLog.setUserId(userInvit.getUserId());
            userBonusLog.setBonusCoinType(0);
            userBonusLog.setBonusType(3);
            bonusList.add(userBonusLog);

            //dn减少记录
            userBonusLog=new UserBonusLog();
            userBonusLog.setAmount(bonusDK.multiply(new BigDecimal(-1)));
            userBonusLog.setUserId(userInvit.getUserId());
            userBonusLog.setBonusCoinType(1);
            userBonusLog.setBonusType(3);
            bonusList.add(userBonusLog);
        }

        userBonusLogDao.saveAll(bonusList);

    }

    public List<UserBonusLog> getIncomeList(int coinType,int incomeType,int pageNo,int pageSize){
        if(pageNo<=0){
            pageNo=1;
        }
        if(pageSize<=0){
            pageSize=20;
        }
        int offset=(pageNo-1)*pageSize;
        Integer userId=UserUtil.get().getId();
        if(incomeType==0){

                return userBonusLogDao.findBalanceList(coinType, userId, offset, pageSize);

        }
        if(incomeType==1){

                return userBonusLogDao.findBalanceIncomeList(coinType,userId,offset,pageSize);

        }
        if(incomeType==2) {
            if(coinType==0) {
                return userBonusLogDao.findBalanceOutlayList(coinType,userId,offset,pageSize);
            }
            if(coinType==1){
                return userBonusLogDao.findDNReleaseList( userId, offset, pageSize);
            }
        }
        if(incomeType==3){
            if(coinType==0){
                return userBonusLogDao.findDKForzenList(userId, offset, pageSize);
            }
            if(coinType==1){
                return  userBonusLogDao.findDNSpeedUpList(userId,offset,pageSize);
            }
        }
        return new ArrayList<UserBonusLog>();
    }

    public List<UserBalance> getBalance(){
        Integer userId=UserUtil.get().getId();
       return userBalanceDao.findByUserId(userId);
    }
    public UserBalance getBalance(int coinType){
        Integer userId=UserUtil.get().getId();
        return userBalanceDao.findByUserIdAndCoinType(userId,coinType);
    }
}
