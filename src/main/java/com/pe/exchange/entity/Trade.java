package com.pe.exchange.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    /**
     * 支付类型,0卖1买
     */
    private Integer tradeType;

    /**
     * 暂时填dk_cny，以后可以支持btc_dk, dk_usd等
     */
    private String marketType;

    /**
     * 价格类型,0限价 1市价,暂时固定为0
     */
    private Integer priceType;
    /**
     * 交易数量
     */
    private BigDecimal num;
    /**
     * 交易单价
     */
    private BigDecimal price;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    private BigDecimal dealed;
}
