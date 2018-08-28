package com.pe.exchange.dao;

import com.pe.exchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    User findByMobile(String mobile);
    User findByUserName(String userName);

    @Query(value = "select * from user where user_name=:param or mobile=:param or user_id=:param",nativeQuery = true)
    User findWithLogin(@Param("param")String param);
}
