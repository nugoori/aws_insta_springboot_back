package com.toyproject.instagram.dto;

import lombok.Data;

@Data
public class SignInReqDto {
    private String phoneOrEmailOrUsername;
    private String loginPassword;
}
