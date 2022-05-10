package com.example.demo.Entity;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {

    private Integer no;
    private String name;
    private String password;
    private String role;

    // private List<Auth> authList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // MyBatis DB 갔다와서 (권한 리스트 추출)
        ArrayList<GrantedAuthority> grantedAuth = new ArrayList<>(); // 1사람에 대한 권한 리스트
        // DB에서 가져오기 후 grantedAuth에 저장

        // for(Auth auth : this.authList) {
        grantedAuth.add(new SimpleGrantedAuthority("ROLE_" + this.role/* auth의 권한명 */));
        // }

        return grantedAuth;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isEnabled() {
        // 계정이 정지인지 아닌지
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
