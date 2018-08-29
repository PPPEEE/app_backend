package com.pe.exchange.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserBalance {
    @Id
    private Integer userId;
    /**
     * 币种类型,0、平台DK 1、平台DN  2、比特币  2、以太币
     */
    private Integer coinType;
    /**
     * 各币地址,平台币不需要
     */
    private String address;
    /**
     * 各币余额
     */
    private String balance;



}
