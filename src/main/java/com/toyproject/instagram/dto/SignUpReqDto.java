package com.toyproject.instagram.dto;

import com.toyproject.instagram.entity.User;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class SignUpReqDto {
    private String phoneAndEmail;
    private String name;
    private String username;
    private String password;

    // dto를 entity로 변환
    public User toUserEntity(          ) {
        return User.builder()
                .email(phoneAndEmail)
                .name(name)
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
