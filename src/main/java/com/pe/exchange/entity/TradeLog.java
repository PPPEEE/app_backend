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
public class TradeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 买方ID
     */
    private Integer buyerId;
    /**
     * 卖方ID
     */
    private Integer sellerId;
    /**
     * 市场类型
     */
    private String marketType;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 买入数量
     */
    private BigDecimal num;
    /**
     * 买入金额
     */
    private BigDecimal amount;
    /**
     * 消费手续费
     */
    private BigDecimal fee;
    /**
     * 状态,1正常,0删除
     */
    @ColumnDefault("1")
    private Integer status;
    /**
     * 源买(卖)单ID
     */
    private Integer sourceTradeId;
    private LocalDateTime addTime;


    
}
