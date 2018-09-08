package com.pe.exchange.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@ApiModel
public class UserInfo implements Serializable{
    @Id
    @ApiModelProperty(hidden = true)
    private Integer userId;
    @ApiModelProperty(value = "头像",position = 0)
    private String avatar;
    @ApiModelProperty(value = "昵称",position = 1)
    private String nickName;
    @ApiModelProperty(value = "真实姓名",position = 2)
    private String realName;
    @ApiModelProperty(value = "性别",position = 3)
    private String gender;
    @ApiModelProperty(value = "生日",position = 4)
    private String birthday;
    @ApiModelProperty(value = "地址",position = 5)
    private String addr;
    @ApiModelProperty(value = "电话",position = 6)
    private String mobile;
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = false,updatable = false,columnDefinition = "datetime not NULL DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdTime;
    
}
