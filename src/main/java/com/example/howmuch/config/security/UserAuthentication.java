package com.example.howmuch.config.security;

import com.example.howmuch.domain.entity.User;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserAuthentication extends AbstractAuthenticationToken {


    private static final String USER = "USER";
    private final Long userId;


    public UserAuthentication(User user) {
        super(authorities(user));
        this.userId = user.getId();
    }

    private static List<GrantedAuthority> authorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRoleType().name()));
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}
