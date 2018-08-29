package com.pe.exchange.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
     * 数量
     */
    private BigDecimal num;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 已成交数量
     */
    private BigDecimal dealNum;

    private LocalDateTime addTime;

    /**
     * 交易状态,0全部交易完成,1交易中,2取消
     */
    @ColumnDefault(value = "1")
    private Integer status;
}
