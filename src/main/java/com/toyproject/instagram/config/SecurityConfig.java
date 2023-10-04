package com.toyproject.instagram.config;

import com.toyproject.instagram.exception.AuthenticateExceptionEntryPoint;
import com.toyproject.instagram.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity // 현재 우리가 만든 Security 설정 정책을 따르겠다
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticateExceptionEntryPoint authenticateExceptionEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    // 비밀번호 암호화
    @Bean // 이미 존재하는 class의 IoC 등록을 위해 사용
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(); // -> WebMvcConfig에서 설정한 cors정책을 따름
        http.csrf().disable(); // csrf 토큰 비활성 (SSR할 때는 중요 할 수 있음)

        http.authorizeHttpRequests()
                .antMatchers("/api/v1/auth/**") // /api/v1/auth로 시작하는 모든 요청
                .permitAll() // 인증 없이 요청을 허용
                .anyRequest() // 나머지 요청은
                .authenticated() // 인증을 필요로 함
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // spring security 구조에서 UPA filter전에 우리가 만든 jwtA~~Filter를 넣어줌
                .exceptionHandling()
                .authenticationEntryPoint(authenticateExceptionEntryPoint);

    }
}
