package com.chx.chat.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@Configuration
public class CorsConfig implements WebMvcConfigurer  {

    // 当前跨域请求最大有效时长。这里默认1天
    private static final long MAX_AGE = 24 * 60 * 60;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //指定允许跨域请求的路径模式为"/**"，表示所有的路径都将允许跨域访问。
                .allowedOrigins("*") // 允许访问资源的域名
                .allowedMethods("*") // 允许的HTTP方法
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(false) // 是否允许发送凭证信息
                .maxAge(3600); // 预检请求的有效期
    }


}
