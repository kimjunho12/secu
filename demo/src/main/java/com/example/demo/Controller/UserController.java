package com.example.demo.Controller;

import com.example.demo.Entity.UserEntity;
import com.example.demo.Security.TokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UserController {

    @Autowired
    private TokenProvider tokenProvider;

    @RequestMapping("/user/login")
    public String getToken(/* ID / PW 받아서 DB에서 뽑아오기 */) {
        // 로그인 성공
        return tokenProvider.create(UserEntity.builder().no(1).build());
    }

    @PostMapping("/tokenlogin")
    public UserEntity UserTokenLogin(@AuthenticationPrincipal UserEntity user) { // Security Context에 있는 User 정보
        return user;
    }

    @Secured({ "ROLE_USER" })
    @GetMapping(value = "/user")
    public String TestUserLogin(@AuthenticationPrincipal UserEntity user) {
        return user.toString();
    }

}
