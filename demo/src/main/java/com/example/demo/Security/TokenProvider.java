package com.example.demo.Security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.example.demo.Entity.UserEntity;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenProvider {

    public static final String SECURITY_KEY = "aZdjk12bAQVxkjcakjb2kbakxc8123kbj12bkaxcaACZXCQWEBaxqwboxjo2t92995kskavnauwrqllsdasadasdkzx"; // DB
                                                                                                                                             // 에서
                                                                                                                                             // 뽑아와라

    public String create(UserEntity user) {
        Date expiryDate = Date.from(Instant.now().plus(7, ChronoUnit.DAYS));
        log.debug(expiryDate.toString());

        Key key = Keys.hmacShaKeyFor(SECURITY_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512) // 암호화 알고리즘 선택
                .setSubject(user.getNo().toString()) // 내용인데 보통 user no 넣음 (누구껀지 확인하기 위해서)
                .setIssuer("준호") // 발행자
                .setExpiration(expiryDate) // 만료기간 설정
                .compact() // 완성
        ;
    }

    public boolean validate(String token) {

        try {
            Key key = Keys.hmacShaKeyFor(SECURITY_KEY.getBytes(StandardCharsets.UTF_8));

            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            log.debug(e.toString());
            return false;
        }
    }

    public String getTokenBody(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(SECURITY_KEY.getBytes(StandardCharsets.UTF_8));
            String payload = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return payload;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
