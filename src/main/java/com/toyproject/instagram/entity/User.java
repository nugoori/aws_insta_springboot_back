package com.toyproject.instagram.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    private Integer userId;
    private String email;
    private String phone;
    private String name;
    private String username;
    private String password;
    private String provider;
    // 0927 0421
    private List<Authority> authorities; // 유저1 과 role이 일대 다의 관계
}
