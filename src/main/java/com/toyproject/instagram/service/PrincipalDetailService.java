package com.toyproject.instagram.service;

import com.toyproject.instagram.entity.User;
import com.toyproject.instagram.repository.UserMapper;
import com.toyproject.instagram.security.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserMapper userMapper;

    // loadUserByUsername가 안쓰이는데 어디서 매개변수를 받아오지?
    @Override
    public UserDetails loadUserByUsername(String phoneOrEmailOrUsername) throws UsernameNotFoundException {
//        System.out.println("아이디 : " + phoneOrEmailOrUsername);
        // UserDetails를 리턴받는 PrincipalUser에 loadUserByUsername의 결과가 리턴

        User user = userMapper.findUserByPhone(phoneOrEmailOrUsername);
        if(user != null) {
            return new PrincipalUser(user.getPhone(), user.getPassword());
        }

        user = userMapper.findUserByEmail(phoneOrEmailOrUsername);
        if(user != null) {
            return new PrincipalUser(user.getEmail(), user.getPassword());
        }

        user = userMapper.findUserByUsername(phoneOrEmailOrUsername);
        if(user != null) {
            return new PrincipalUser(user.getUsername(), user.getPassword());
        }

        // UserDetails가 아닌 null로 반환하면 자동으로 예외처리를 해줌
        // return null;
        throw new UsernameNotFoundException("로그인 정보가 잘못 되었습니다.");
    }
}
