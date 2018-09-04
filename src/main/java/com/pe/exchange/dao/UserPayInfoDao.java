package com.pe.exchange.dao;

<<<<<<< HEAD
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pe.exchange.entity.UserPayInfo;

public interface UserPayInfoDao extends JpaRepository<UserPayInfo, Integer>{
	
	@Query(value="select * from user_pay_info where user_Id = :userId",nativeQuery = true)
	List<UserPayInfo> queryUserPayInfoList(@Param("userId") String userId);
=======
import com.pe.exchange.entity.UserPayInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPayInfoDao extends JpaRepository<UserPayInfo, Integer>{
	
	@Query(value="select * from user_pay_info where user_Id = :userId",nativeQuery = true)
	List<UserPayInfo> queryUserPayInfoList(@Param("userId") Integer userId);
>>>>>>> c0172847aa2b773c7f165394c9a8e800930a7e84

}
