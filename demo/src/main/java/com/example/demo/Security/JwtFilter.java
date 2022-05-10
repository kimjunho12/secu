package com.example.demo.Security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.Entity.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private FilterService filterService;

    public String replaceTokenBearer(String authorization) {
        if (authorization == null) {
            return null;
        }

        String access_token = authorization.replaceAll("Bearer ", "");
        return access_token;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String access_token = replaceTokenBearer(request.getHeader("Authorization"));

        if (access_token != null) {
            if (tokenProvider.validate(access_token)) {
                String no = tokenProvider.getTokenBody(access_token);

                // 토큰에서 가져온 유저를 가져오기
                UserEntity user = (UserEntity) filterService.loadUserByUsername(no);

                // 권한 인가
                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext(); // 빈 컨택스트 생성
                securityContext.setAuthentication(authenticationToken); // 컨택스트에 토큰 삽입
                SecurityContextHolder.setContext(securityContext); // securityContext에 방금 만든 context 삽입

            }
        }

        filterChain.doFilter(request, response);

    }

}
