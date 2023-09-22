package com.toyproject.instagram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // 현재 우리가 만든 Security 설정 정책을 따르겠다
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 비밀번호 암호화
    @Bean // 이미 존재하는 class의 IoC 등록을 위해
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(); // -> WebMvcConfig에서 설정한 cors정책을 따름
        http.csrf().disable(); // csrf 토큰 비활성

        http.authorizeHttpRequests()
                .antMatchers("/api/v1/auth/**") // /api/v1/auth로 시작하는 모든 요청
                .permitAll(); // 인증 없이 요청을 허용
    }
}