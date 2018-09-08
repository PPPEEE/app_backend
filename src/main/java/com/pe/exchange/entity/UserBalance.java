package com.pe.exchange.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UserBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Integer id;

    @ApiModelProperty(hidden = true)
    private Integer userId;
    /**
     * 币种类型,0、平台DK 1、平台DN
     */

    @ApiModelProperty(value = "币种类型,0、平台DK 1、平台DN",position = 0)
    private Integer coinType;

    /**
     * 收款地址
     */
    @ApiModelProperty(value = "在址,暂时为空",position = 1)
    private String address;
    /**
     *  余额
     */
    @Column(precision = 19,scale = 6)
    @ApiModelProperty(value = "余额",position = 2)
    private BigDecimal balance;

    /**
     * 锁定余额
     */
    @Column( precision = 19,scale = 6)
    @ApiModelProperty(hidden = true)
    private BigDecimal lockBalance;


}
