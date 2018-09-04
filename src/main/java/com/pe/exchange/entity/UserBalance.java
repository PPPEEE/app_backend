package com.pe.exchange.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(precision = 19,scale = 6)
    private BigDecimal balance;

    /**
     * 锁定余额
     */
    @Column( precision = 19,scale = 6)
    private BigDecimal lockBalance;


}
