package com.pe.exchange.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pe.exchange.entity.UserInfo;

@Repository
public interface UserInfoDao extends JpaRepository<UserInfo, Integer>{


	
}
