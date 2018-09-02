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
public class TransferLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer fromUserId;
    private Integer toUserId;
    @Column(precision = 19,scale = 6)
    private BigDecimal amount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = false,columnDefinition = "datetime not NULL DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime addTime;
    /**
     * 奖励处理情况
     */
    @ColumnDefault("0")
    @Column(insertable = false)
    private Integer bonusStatus;
}
