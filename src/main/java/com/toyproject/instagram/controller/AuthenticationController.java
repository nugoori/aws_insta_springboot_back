package com.toyproject.instagram.controller;

import com.toyproject.instagram.dto.SignInReqDto;
import com.toyproject.instagram.dto.SignUpReqDto;
import com.toyproject.instagram.exception.SignupException;
import com.toyproject.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth") // 쓸수도 있고 안 쓸수도
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService; // IoC Container에 AuthenticationController가 등록되어있음 -> AuthenticationController를 생성할 때 상수인 userService가 반드시 초기화 되어야 함 -> IoC container에서 자동으로 userService를 찾아서 연결

    @PostMapping("/user")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpReqDto signUpReqDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errorMap.put(error.getField(), error.getDefaultMessage());
            });
//            return ResponseEntity.badRequest().body(errorMap);
            throw new SignupException(errorMap); //
        }
        //  공통적으로 사용되는 부분들을 AOP로 구현

        userService.signUpUser(signUpReqDto); //
        return ResponseEntity.ok(null);
    }

    // Validation은 DTO형태로 데이터가 들어왔을 때 검사 하는것 , 
    // 사용자 관점에서 로그인이 안되는 경우 입력 값이 사용자의 잘못 되었는지 DB에서 비교를 해야하기 때문에 로그인때는 Valid검사를 하지 않는 것이 좋음
    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody SignInReqDto signInReqDto) {
        userService.signInUser(signInReqDto);
        return ResponseEntity.ok(null);
    }
}





