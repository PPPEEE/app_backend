package com.pe.exchange.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsConfig {

    private final Local local=new Local();
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
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
        
        
    }
    @Data
    public static class Foreign{
        private String url;
        private String username;
        private String password;
        private String sign;
    }
	public Local getLocal() {
		return local;
	}
    
    
    
    
    
}

