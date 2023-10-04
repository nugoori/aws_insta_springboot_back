package com.toyproject.instagram.security;

import com.toyproject.instagram.entity.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PrincipalUser implements UserDetails {

    private String phoneOrEmailOrUsername;
    private String password;
    private List<Authority> authorities;

    // PrincipalDetailService에서 생성할 때 넘어온 user.get~ 메소드로 넘어온 데이터로 아래의 오버라이딩된 메소드를 실행
    // 데이터가 일치하는게 있으면 로그인 성공, 아니면 오류 메세지
    public PrincipalUser(String phoneOrEmailOrUsername, String password, List<Authority> authorities) {
        this.phoneOrEmailOrUsername = phoneOrEmailOrUsername;
        this.password = password;
        this.authorities = authorities;
    }

    // 로그인 할 사용자의 권한
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorities.forEach(authority ->
                authorityList.add(new SimpleGrantedAuthority(authority.getRole().getRoleName()))
        ); // SimpleGrantedAuthority을 생성할 때 권한의 이름을 넣어 줌
        return authorityList;
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
