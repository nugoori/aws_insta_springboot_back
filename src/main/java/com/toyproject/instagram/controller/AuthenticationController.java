package com.toyproject.instagram.controller;

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
}





