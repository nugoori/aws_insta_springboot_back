package com.toyproject.instagram.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

// JWT 토큰을 관리해주는 로직
@Component
public class JWTTokenProvider {

    private final Key key;

    // Autowired는 IoC 컨테이너에서 객체를 자동 주입
    // Value는 application.yml에서 변수 데이터를 자동 주입

    public JWTTokenProvider(@Value("${jwt.secret}") String secret) {
        // jjwt -> keys
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    // accessToken  // refreshToken

    // JWT 토큰을 생성하는 로직
    // 로그인을 성공했을 때 만들었던 Authentication 객체를 사용
    public String generateAccessToken(Authentication authentication) {
        String accessToken = null;

        // PrincipalUser(imple UserDetails) <- getPrincipal로 가져올 수 있는 것
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();

        // new Date() : 현재 시간 // (new Date().getTime() 현 날짜의 현 시간을 객체로 지정)
        Date tokenExpireDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));

        accessToken = Jwts.builder()
                .setSubject("AccessToken") // 토큰 이름
                .claim("username", principalUser.getUsername())
                .setExpiration(tokenExpireDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return accessToken;
    }

    // 20230927 11:34
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder() // 알고리즘으로 변형된 토큰을 복호화?
                    .setSigningKey(key) // 알고리즘에 사용된 키??
                    .build() //
                    .parseClaimsJws(token); //
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // 순수한 토큰 부분만 뽑아내는 메소드
    public String convertToken(String bearerToken) {
        String type = "Bearer ";
        // null 인지 , 공백인지 확인 : hasText(): springboot에만 있음
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(type)) {
            return bearerToken.substring(type.length());
        }
        return "";
    }
}
