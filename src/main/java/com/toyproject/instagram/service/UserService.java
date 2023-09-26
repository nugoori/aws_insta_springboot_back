package com.toyproject.instagram.service;

import com.toyproject.instagram.dto.SignInReqDto;
import com.toyproject.instagram.dto.SignUpReqDto;
import com.toyproject.instagram.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// controller -> service -> Repository
@Service
@RequiredArgsConstructor
public class UserService {

    // IoC에 등록된 클래스 -> Spring에서 필요한 곳에 가져다 사용이 가능
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public void signUpUser(SignUpReqDto signUpReqDto) {
        Integer executeCount = userMapper.saveUser(signUpReqDto.toUserEntity(passwordEncoder));
        System.out.println(executeCount);
    }

    public void signInUser(SignInReqDto signInReqDto) {
        //아이디,비밀 번호를 가지고 토큰을 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signInReqDto.getPhoneOrEmailOrUsername(), signInReqDto.getLoginPassword());

        // ManagerBuilder가 UserDetailService라는 인터페이스로 구현된 클래스가 있는지 IoC Container에서 찾음 -> 오버라이드된 loadUserByUsername() 메소드를 찾고 username을 넘겨줌
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }

}
