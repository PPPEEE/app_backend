package com.pe.exchange.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pe.exchange.entity.User;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

 /*   User findByMobile(String mobile);*/
	
	@Query(value = "select * from user where user_name = :userName",nativeQuery = true)
    User findByUserName(@Param("userName")String userName);

    @Query(value = "select * from user where user_name=:param or telephone=:param or id=:param",nativeQuery = true)
    User findWithLogin(@Param("param")String param);

    User findByAddress(String address);

    @Modifying
    @Query(value = "update user set user_level=:userLevel where id=:userId",nativeQuery = true)
    int updateUserLevel(@Param("userId") Integer userId,@Param("userLevel") Integer userLevel);
    
    @Query(value = "select * from user where referee_id = :userId",nativeQuery = true)
    List<User> findMyTeam(@Param("userId") Integer userId);
   /* @Query(value = "select * from user where id = :id",nativeQuery = true)
    User findById(@Param("id")long id);*/
}
