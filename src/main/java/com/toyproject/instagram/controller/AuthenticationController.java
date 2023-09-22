package com.toyproject.instagram.controller;

import com.toyproject.instagram.dto.SignUpReqDto;
import com.toyproject.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth") // 쓸수도 있고 안 쓸수도
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<?> signUp(@RequestBody SignUpReqDto signUpReqDto) {
        userService.signUpUser(signUpReqDto); //
        return ResponseEntity.ok(null);
    }
}
