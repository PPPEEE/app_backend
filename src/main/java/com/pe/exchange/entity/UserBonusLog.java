package com.pe.exchange.entity;

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
public class UserBonusLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    /**
     * 1、对冲奖励  DN
     * 2、直推奖励  DK
     * 3、团队兑换奖励  DK
     * 4、团队流通奖励 DK
     */
    private Integer bonusType;

    /**
     * 奖励币的类型
     *0 DN ,1DK
     */
    private Integer bonusCoinType;
    /**
     * 奖励金额,主要是DN
     */
    @Column(precision = 6)
    private BigDecimal amount;
    private LocalDateTime addTime;
    /**
     * 状态,1正常  2取消
     */
    @ColumnDefault("1")
    private Integer status;



}
