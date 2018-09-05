package com.pe.exchange.service;

import com.alibaba.fastjson.JSON;
import com.pe.exchange.dao.UserBalanceDao;
import com.pe.exchange.entity.User;
import com.pe.exchange.entity.UserBalance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserBalanceDao userBalanceDao;

    @Test
    @Transactional
    public void register() {
        User user = new User();
        user.setTelephone("18576739167");
        user.setPwd("12sdf13");
        user.setUserName("谢灿");
        int aa = userBalanceDao.subDKBalance(111, new BigDecimal(10000));
        System.out.println(aa);
        // userService.register(user,"000000");
    }
    @Test
    public void test(){
        User user=new User();
        user.setId(121212);
        //userService.initUserBalance(user);
    }
    @Test
    public void balance(){
        UserBalance byUserIdAndCoinType = userBalanceDao.findByUserIdAndCoinType(121212, 0);
        System.out.println(JSON.toJSONString(byUserIdAndCoinType));
        List<UserBalance> byUserId = userBalanceDao.findByUserId(121212);
        System.out.println(JSON.toJSONString(byUserId));
    }
}
