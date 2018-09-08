package com.pe.exchange.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class UserBonusLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Integer id;
    @ApiModelProperty(hidden = true)
    private Integer userId;
    /**
     * 0、冻结
     * 1、对冲奖励  DN+
     * 2、直推奖励  DK+
     * 3、团队兑换奖励 DN- DK+
     * 4、团队流通奖励 DN- DK+
     * 5、每日释放 DN- DK+
     * 10、正常兑换
     * 11、正常转账
     * 12、正常买卖
     *
     */
    @ApiModelProperty(value ="类型\n0、冻结\n" + "1、对冲奖励  DN+\n" + "2、直推奖励  DK+\n" + "3、团队兑换奖励 DN- DK+\n" + "4、团队流通奖励 DN- DK+\n"
        + "5、每日释放 DN- DK+\n" + "10、正常兑换\n" + "11、正常转账\n" + "12、正常买卖",position = 0)
    private Integer bonusType;

    /**
     * 奖励币的类型
     *0 DN ,1DK
     */
    @ApiModelProperty(value = "奖励币的类型,0 DN ,1DK",position = 1)
    private Integer bonusCoinType;
    /**
     * 奖励金额,主要是DN
     */
    @Column(precision = 19,scale = 6)
    @ApiModelProperty(value = "奖励金额",position = 2)
    private BigDecimal amount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = false,updatable = false,columnDefinition = "datetime not NULL DEFAULT CURRENT_TIMESTAMP")
    @ApiModelProperty(value = "时间",position = 3)
    private LocalDateTime addTime;
    /**
     * 状态,1正常  2取消
     */
    @ColumnDefault("1")
    @Column(insertable = false)
    @ApiModelProperty(hidden = true)
    private Integer status;



}
