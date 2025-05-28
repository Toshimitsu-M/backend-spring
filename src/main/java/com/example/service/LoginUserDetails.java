package com.example.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.model.LoginUser;

public class LoginUserDetails implements UserDetails {

    private final LoginUser loginUser;
//    private final Collection<? extends GrantedAuthority> authorities;

    public LoginUserDetails(LoginUser loginUser) {
        this.loginUser = loginUser;
//        this.authorities = loginUser.getRoleList()
//                .stream()
//                .map(role -> new SimpleGrantedAuthority(role))
//                .toList();
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
//        return authorities;
    }

    @Override
    public String getPassword() {
        return loginUser.getPassword();
    }

    @Override
    public String getUsername() {
        return loginUser.getEmail();
    }

    //ユーザが期限切れでなければtrueを返す
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //ユーザがロックされていなければtrueを返す
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //ユーザのパスワードが期限切れでなければtrueを返す
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //ユーザが有効であればtrueを返す
    @Override
    public boolean isEnabled() {
        return true;
    }
}