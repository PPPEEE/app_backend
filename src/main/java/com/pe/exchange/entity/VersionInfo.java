package com.pe.exchange.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
public class VersionInfo {

	@Id
	@ApiModelProperty(hidden = true)
	private Integer id;
	
	@ApiModelProperty(value = "版本下载路径", position = 0)
	private String url;
	
	@ApiModelProperty(value = "版本号", position = 1)
	private String version;
}
