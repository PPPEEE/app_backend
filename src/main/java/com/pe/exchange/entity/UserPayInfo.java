package com.pe.exchange.entity;

import lombok.Data;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserPayInfo {
    @Id
    private Integer userId;
    private Integer payType;
    private String accountName;
    private String accountId;
    private String qrCode;
    private String bank;
    private String bankBranch;
}
