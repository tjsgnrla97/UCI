package com.cos.jwt.config.Auth;

import com.cos.jwt.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//시큐리티가 로그인 주소 요청이 오면 낚아채서 로그인을 진행
//로그인을 진행이 완료되면 시큐리티가 세션을 만들어준다(Security ContextHolder)
//세션에 들어갈수 있는 오브젝트 정해져있음 Authentication 타입 객체
//Authentication 안에 User정보가 있어야됨
//User 오브젝트타입은 UserDetails 타입 객체여야함 (정해진것)

//Security Session에 들어갈 수 있는 객체 => Authentication , Authentication에 들어갈 수 있는 UserObject 타입 => UserDetails

@Data
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoleList().forEach(r->{
            authorities.add(()-> r);
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {

        return user.getEmail();
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
