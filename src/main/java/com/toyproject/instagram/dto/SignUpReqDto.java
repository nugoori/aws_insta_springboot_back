package com.toyproject.instagram.dto;

import com.toyproject.instagram.entity.User;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignUpReqDto {
//    @NotBlank(message = "전화번호 또는 이메일은 공백일 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+@[\\da-zA-Z]+\\.[a-z]+|[0-9]{11}+$", message = "이메일 또는 전화번호를 입력하세요.") // \\ 문자열 안에서 사용해야 하므로 두개를 사용
    private String phoneOrEmail;

//    @NotBlank(message = "이름은 공백일 수 없습니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ|가-힣]*$", message = "한글 이름을 입력하세요.")
    private String name;

    @Pattern(regexp = "^[a-z0-9_.]*$", message = "사용할 수 없는 사용자 이름입니다. 다른 이름을 사용하세요")
    private String username;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$", message = "비밀번호는 영문, 숫자 조합으로 8자 이상 입력하세요.")
    private String password;

    // dto를 entity로 변환 // BCryptPasswordEncoder가 DTO가 생성 될 때 마다 생성하지 않도록 service에서 IoC에 등록하고 매개변수로 받음
    public User toUserEntity(BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .name(name)
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
