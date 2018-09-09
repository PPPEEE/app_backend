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
public class Appeal implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 @ApiModelProperty(hidden = true)
	private Integer id;
	
	//申诉订单ID
	@ApiModelProperty(value = "订单ID",position = 0)
	private Integer dkId;
	
	//申诉图片名称
	@ApiModelProperty(value = "申诉上传文件名称",position = 0)
	private String filePngName;
	
	//申诉内容
	@ApiModelProperty(value = "申诉描述",position = 0)
	private String descText;
}
