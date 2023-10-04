package com.toyproject.instagram.service;

import com.toyproject.instagram.dto.SignInReqDto;
import com.toyproject.instagram.dto.SignUpReqDto;
import com.toyproject.instagram.entity.User;
import com.toyproject.instagram.exception.JwtException;
import com.toyproject.instagram.exception.SignupException;
import com.toyproject.instagram.repository.UserMapper;
import com.toyproject.instagram.security.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// controller -> service -> Repository
@Service
@RequiredArgsConstructor
public class UserService {

    // IoC에 등록된 클래스 -> Spring에서 필요한 곳에 가져다 사용이 가능
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JWTTokenProvider jwtTokenProvider;

    public void signUpUser(SignUpReqDto signUpReqDto) {
        User user = signUpReqDto.toUserEntity(passwordEncoder);

        Matcher emailMatcher = Pattern.compile("^[a-zA-Z0-9]+@[\\da-zA-Z]+\\.[a-z]").matcher(signUpReqDto.getPhoneOrEmail());
        Matcher phoneMatcher = Pattern.compile("^[0-9]{11}+$").matcher(signUpReqDto.getPhoneOrEmail());

        if(emailMatcher.matches()) {
            user.setEmail(signUpReqDto.getPhoneOrEmail());
        }
        if(phoneMatcher.matches()) {
            user.setPhone(signUpReqDto.getPhoneOrEmail());
        }

        checkDuplicated(user);
        userMapper.saveUser(user);
    }

    private void checkDuplicated(User user) {
        if(StringUtils.hasText(user.getPhone())) {
            if(userMapper.findUserByPhone(user.getPhone()) != null) {
                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("phone", "이미 사용중인 휴대폰 번호입니다.");
                throw new SignupException(errorMap);
            }
        }
        if(StringUtils.hasText(user.getEmail())) {
            if(userMapper.findUserByEmail(user.getEmail()) != null) {
                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("email", "이미 사용중인 이메일입니다.");
                throw new SignupException(errorMap);
            }
        }
        if(StringUtils.hasText(user.getUsername())) {
            if(userMapper.findUserByUsername(user.getUsername()) != null) {
                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("username", "이미 사용중인 사용자 이름입니다.");
                throw new SignupException(errorMap);
            }
        }
    }

    public String signInUser(SignInReqDto signInReqDto) {
        //아이디,비밀 번호를 가지고 토큰을 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signInReqDto.getPhoneOrEmailOrUsername(), signInReqDto.getLoginPassword());

        // Authentication을 상속받은 AbstractAuthenticationToken를 상속 받은 UsernamePasswordAuthenticationToken 의 객체 authenticationToken을 넘김
        // ManagerBuilder가 UserDetailService라는 인터페이스로 구현된 클래스가 있는지 IoC Container에서 찾음 -> 오버라이드된 loadUserByUsername() 메소드를 찾고 authenticationToken에서 username을 찾아 넘겨줌
        // 예외가 발생하면 securityConfig의 예외처리 handling 부분으로 -> 거기서 필요한 AuthenticationEntryPoint을 implements한 calss에서 로그인에 관한 에러에 넘겨줄 메세지를 담은 메소드를 작성
        // 인증이 되면 Authentication 객체에 담음 ***

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // jwt 부분 추가
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        return accessToken; 
    }

    public Boolean authenticate(String token) {
        String accessToken = jwtTokenProvider.convertToken(token);
        if(!jwtTokenProvider.validateToken(accessToken)) {
            // 11:50
            throw new JwtException("사용자 정보가 만료되었습니다. 다시 로그인하세요");
        }
        return true;
    }

}
