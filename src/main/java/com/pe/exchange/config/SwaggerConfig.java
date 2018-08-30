package com.pe.exchange.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Value("${spring.profiles.active}")
    String env;

    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        ticketPar.name("token").description("TOKEN")
            .modelRef(new ModelRef("string")).parameterType("header")
            .build();
        List<Parameter> parameterBuilders =new ArrayList<>();
        parameterBuilders.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2).enable(!env.equals("prod"))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pe.exchange.controller"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameterBuilders);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("APP后端")
                //.contact(contact)
                .version("1.0")
                .build();
    }

    @Override public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
            //设置允许跨域请求的域名
            .allowedOrigins("*")
            //是否允许证书 不再默认开启
            .allowCredentials(true)
            //设置允许的方法
            .allowedMethods("*")
            //跨域允许时间
            .maxAge(3600);
    }

}
