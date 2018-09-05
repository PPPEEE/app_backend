package com.pe.exchange.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
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

    /**1、dk_cny
     * 2、btc_dk
     * 3、dk_usd
     * ...
     * 暂时填1
     */
    private Integer marketType;

    /**
     * 价格类型,0限价 1市价,暂时固定为0
     */
    private Integer priceType;
    /**
     * 数量
     */
    @Column(precision = 19,scale = 6)
    private BigDecimal num;
    /**
     * 单价
     */
    @Column(precision = 19,scale = 2)
    private BigDecimal price;
    /**
     * 金额
     */
    @Column(precision = 19,scale = 2)
    private BigDecimal amount;

    /**
     * 手续费
     */
    @Column(precision = 19,scale = 2)
    private BigDecimal fee;

    /**
     * 已成交数量
     */
    @ColumnDefault("0")
    @Column(precision = 19,scale = 6)
    private BigDecimal dealNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = false,updatable = false,columnDefinition = "datetime not NULL DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime addTime;

    /**
     * 交易状态,0全部交易完成,1交易中,2取消
     */
    @ColumnDefault(value = "1")
    @Column(insertable = false)
    private Integer status;
}
