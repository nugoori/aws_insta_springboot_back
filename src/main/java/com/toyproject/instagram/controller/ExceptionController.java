package com.toyproject.instagram.controller;

import com.toyproject.instagram.exception.SignupException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// advice가 예외를 기다리다가 예외가 발생하면 이곳으로 예외가 던져(Throw)하면 처리 되도록
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(SignupException.class)
    public ResponseEntity<?> signupExceptionHandle(SignupException signupException) {
        return ResponseEntity.badRequest().body(signupException.getErrorMap());
    }
}
