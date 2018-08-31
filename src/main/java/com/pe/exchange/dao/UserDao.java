package com.pe.exchange.dao;

import com.pe.exchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

 /*   User findByMobile(String mobile);*/
	
	@Query(value = "select * from user where user_name = :userName",nativeQuery = true)
    User findByUserName(@Param("userName")String userName);

    @Query(value = "select * from user where user_name=:param or telephone=:param or id=:param",nativeQuery = true)
    User findWithLogin(@Param("param")String param);

    String findAddressById(Integer id);
    
    
   /* @Query(value = "select * from user where id = :id",nativeQuery = true)
    User findById(@Param("id")long id);*/
}
