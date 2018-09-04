package com.pe.exchange.config;

import com.pe.exchange.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration

public class WebConfig implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor LoginInterceptor;

    List<String> exclude;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> exclude=new ArrayList<>();
        exclude.add("/user/login");
        exclude.add("/user/register");
        exclude.add("/user/sendCode");
        exclude.add("/user/userNameExists");


        //swagger相关
        exclude.add("/swagger-ui.html/**");
        exclude.add("/webjars/**");
        exclude.add("/error");
        exclude.add("/v2/**");
        exclude.add("/swagger-resources/**");
        registry.addInterceptor(LoginInterceptor).addPathPatterns("/**").excludePathPatterns(exclude);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
            // 设置允许跨域请求的域名
            .allowedOrigins("*")
            // 是否允许证书 不再默认开启
            .allowCredentials(true)
            // 设置允许的方法
            .allowedMethods("*")
            // 跨域允许时间
            .maxAge(3600);
    }
}
