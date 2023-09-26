package com.toyproject.instagram.security;

import com.toyproject.instagram.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class PrincipalUser implements UserDetails {

    private String phoneOrEmailOrUsername;
    private String password;

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
}
