package com.pe.exchange.dao;

import com.pe.exchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    User findByMobile(String mobile);
    User findByUserName(String userName);
}
