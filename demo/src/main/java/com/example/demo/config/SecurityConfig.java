package com.example.demo.config;

import com.example.demo.Security.JwtFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@SpringBootConfiguration // 설정파일 임을 명시
@EnableWebSecurity // 권한 인증 시 그 사용자가 누구인지 Controller에서 가져올 수 있음
@EnableGlobalMethodSecurity(securedEnabled = true) // custom role 적용 가능하게 (user/admin)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 설정 세팅을 하려면 http를 핸들링

        http.securityContext().and() // 보안 컨택스트 연계
                .exceptionHandling().and() // 에러핸들링
                .cors().and() // CrossOrigin resource sharing
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // 시큐리티에서 세션 관리 금지
                .httpBasic().disable()
                .formLogin().disable() // 로그인 화면 안나오게 (api만 할꺼니까)
                .authorizeRequests() // 다음에 올 리퀘스트에 대한 권한을 체크
                .antMatchers("/user/login").permitAll() // 로그인 화면에는 제한 x (주소는 우리 프로젝트 로그인 주소)
                .antMatchers("/user").hasAnyRole("ADMIN", "USER")
                .anyRequest().hasRole("USER").and()
                .addFilterBefore(jwtFilter, CorsFilter.class);

    }
}
