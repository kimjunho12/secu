package com.example.demo.Security;

import com.example.demo.Entity.UserEntity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FilterService implements UserDetailsService {

    // MyBatis 가져오기 구현 -> Repository AutoWired
    @Override
    public UserDetails loadUserByUsername(String no) throws UsernameNotFoundException {
        if (no != null) {
            UserEntity user = UserEntity.builder().no(1).name("춘식이").password("1234").role("ADMIN").build();
            return user;
        } else {
            return null;
        }
    }
}
