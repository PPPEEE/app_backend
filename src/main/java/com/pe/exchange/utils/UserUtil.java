package com.pe.exchange.utils;

import com.pe.exchange.entity.User;

public class UserUtil {
    private static ThreadLocal<User> CURRENT_USER=new ThreadLocal<>();
    public static User get(){
       return CURRENT_USER.get();
    }
    public static void set(User user){
        CURRENT_USER.set(user);
    }
}
