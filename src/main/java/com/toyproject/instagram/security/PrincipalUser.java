package com.toyproject.instagram.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class PrincipalUser implements UserDetails {

    private String phoneOrEmailOrUsername;
    private String password;

    // PrincipalDetailService에서 생성할 때 넘어온 user.get~ 메소드로 넘어온 데이터로 아래의 오버라이딩된 메소드를 실행
    // 데이터가 일치하는게 있으면 로그인 성공, 아니면 오류 메세지
    public PrincipalUser(String phoneOrEmailOrUsername, String password) {
        this.phoneOrEmailOrUsername = phoneOrEmailOrUsername;
        this.password = password;
    }

    // 로그인 할 사용자의 권한
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phoneOrEmailOrUsername;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 비밀번호를 5번 틀리는 경우
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 자격 증명 만료(공공 인증서나 공동 인증서, 휴대폰 인증)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 활성화(회원 가입을 한 이후 본인 인증이 아직 되지 않은 경우)
    @Override
    public boolean isEnabled() {
        return true;
    }

    // return에 false가 하나라도 있으면 작동X
}
