package com.pe.exchange.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
public class UserPayInfo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Integer id;
    @ApiModelProperty(hidden = true)
    private Integer userId;
    
    @ApiModelProperty(value = "支付方式 1微信 2 支付宝 3 银行卡", position = 0)
    private Integer payType;
    
    @ApiModelProperty(value = "支付用户名", position = 1)
    private String accountName;
    @ApiModelProperty(value = "支付码文件名", position = 2)
    private String accountId;
    @ApiModelProperty(value = "支付账号", position = 3)
    private String qrCode;
    @ApiModelProperty(value = "支付银行", position = 4)
    private String bank;
    @ApiModelProperty(value = "分行地址", position = 5)
    private String bankBranch;
}
