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

        // Authentication을 상속받은 AbstractAuthenticationToken를 상속 받은 UsernamePasswordAuthenticationToken 의 객체 authenticationToken을 넘김
        // ManagerBuilder가 UserDetailService라는 인터페이스로 구현된 클래스가 있는지 IoC Container에서 찾음 -> 오버라이드된 loadUserByUsername() 메소드를 찾고 authenticationToken에서 username을 찾아 넘겨줌
        // 예외가 발생하면 securityConfig의 예외처리 handling 부분으로 -> 거기서 필요한 AuthenticationEntryPoint을 implements한 calss에서 로그인에 관한 에러에 넘겨줄 메세지를 담은 메소드를 작성
        // 인증이 되면 Authentication 객체에 담음
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }

}
