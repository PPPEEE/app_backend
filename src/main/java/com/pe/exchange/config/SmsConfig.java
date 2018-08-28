package com.pe.exchange.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsConfig {

    private final Region local=new Region();
    private final Region foreign=new Region();

    @Data
    public static class Region{
        private String url;
        private String username;
        private String password;
        private String sign;
    }
    @Data
    public static class Local{
        private String url;
        private String username;
        private String password;
        private String sign;
    }
    @Data
    public static class Foreign{
        private String url;
        private String username;
        private String password;
        private String sign;
    }
}

