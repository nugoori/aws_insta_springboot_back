package com.toyproject.instagram.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// CrosOrigin prodlem
@Configuration // component(IoC container에 spring 설정 관련 객체 생성)
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 요청 엔드포인트 
                .allowedMethods("*")    // 모든 요청 메소드
                .allowedOrigins("*");   // 모든 요청 서버 http://naver.com ~~
    }
}
