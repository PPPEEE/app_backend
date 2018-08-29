package com.pe.exchange.entity;

import lombok.Data;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserPayInfo {
    @Id
    private Integer userId;
    /**
     * 支付类型, 1、微信  2、支付宝  3、银行卡
     */
    private Integer payType;
    /**
     * 微信(支付宝、银行)账户名称
     */
    private String accountName;
    /**
     * 微信(支付宝、银行)账户ID或卡号
     */
    private String accountId;
    /**
     * 微信、支付宝二维码,银行则不填
     */
    private String qrCode;
    /**
     * 银行名称
     */
    private String bank;
    /**
     * 支行
     */
    private String bankBranch;
}
