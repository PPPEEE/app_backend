package com.pe.exchange.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pe.exchange.entity.UserPayInfo;
import com.pe.exchange.entity.UserPayPwdInfo;

@Repository
public interface UserPayPwdDao extends JpaRepository<UserPayPwdInfo, Integer>{

	@Query(value = "select * from user_pay_pwd_info where user_id = :userId",nativeQuery=true)
	UserPayInfo queryPayPwdExists(@Param("userId")Integer userId);
	
	@Query(value = "select * from user_pay_pwd_info where user_id = :userId and pwd = :pwd",nativeQuery=true)
	UserPayInfo queryPayPwdExists(@Param("userId")Integer userId,@Param("pwd") String pwd);
	
}
