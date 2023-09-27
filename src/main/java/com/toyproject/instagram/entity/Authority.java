package com.toyproject.instagram.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Authority {
    private int authorityId;
    private int userId;
    private int roleId;
    private Role role; // authority와 role의 관계는 1:1 관계
}
