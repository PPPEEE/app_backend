package com.pe.exchange.utils;

public class UserUtil {
    private static ThreadLocal<Integer> USER_ID=new ThreadLocal<>();
    public static Integer get(){
       return USER_ID.get();
    }
    public static void set(Integer userId){
        USER_ID.set(userId);
    }
}
