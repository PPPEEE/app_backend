package com.pe.exchange.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TransferServiceTest {
    @Autowired
    TransferService transferService;
    @Test
    public void transfer() {
       // UserUtil.set(111);
        transferService.transfer("111111","100");

    }

    @Test
    public void getAddress(){
       // UserUtil.set(1);
        String address = transferService.getAddress();
        System.out.println("=============");
        System.out.println(address);
        System.out.println("=============");
    }
}
