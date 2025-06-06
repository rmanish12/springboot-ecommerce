package com.ecomm.ecommservice.security;

import com.ecomm.ecommservice.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserInfoSecurityDetails implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean isAccountNonLocked;

    public UserInfoSecurityDetails(User userInfo) {
        this.username = userInfo.getEmail();
        this.password = userInfo.getPassword();
        this.isAccountNonLocked = userInfo.isActive();

        Set<String> userPermissions = new HashSet<>();

        userInfo
                .getRoles()
                .forEach(role -> role.getPermissions()
                        .forEach(permission -> {
                            userPermissions.add(permission.getCode());
                        })
                );

        this.authorities = userPermissions
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
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
