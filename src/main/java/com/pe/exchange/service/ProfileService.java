package com.pe.exchange.service;

import com.pe.exchange.config.SwaggerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ProfileService {

    private static final String PROFILE_PROD="prod";

    @Value("${spring.profiles.active}")
   private String env;

   public static boolean isTest=false;
   public static boolean isProd=true;

   @PostConstruct
    public  void init(){
       isTest=!env.equals(PROFILE_PROD);
       isProd=!isTest;
   }
}
