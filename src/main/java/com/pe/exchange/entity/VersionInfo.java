package com.pe.exchange.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class VersionInfo {

	@Id
	private Integer id;
	private String url;
	private String version;
}
