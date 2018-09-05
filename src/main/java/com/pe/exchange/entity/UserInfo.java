package com.pe.exchange.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class UserInfo implements Serializable{
    @Id
    @ApiModelProperty(hidden = true)
    private Integer userId;
    private String avatar;
    private String nickName;
    private String realName;
    private String gender;
    private String birthday;
    private String addr;
    private String mobile;
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = false,updatable = false,columnDefinition = "datetime not NULL DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdTime;
    
}
