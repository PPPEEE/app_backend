package com.pe.exchange.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class UserBalance {
    @Id
    private Integer userId;
    /**
     * 币种类型,0、平台DK 1、平台DN
     */
    private Integer coinType;

    /**
     * 收款地址
     */
    private String address;
    /**
     *  余额
     */
    private Integer balance;

    /**
     * 锁定余额
     */
    private Integer lockBalance;


}
