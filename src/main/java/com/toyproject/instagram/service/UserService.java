package com.toyproject.instagram.service;

import com.toyproject.instagram.dto.SignUpReqDto;
import com.toyproject.instagram.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// controller -> service -> Repository
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signUpUser(SignUpReqDto signUpReqDto) {
        Integer executeCount = userMapper.saveUser(signUpReqDto.toUserEntity(passwordEncoder));
        System.out.println(executeCount);
    }

}
