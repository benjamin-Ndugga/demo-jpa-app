package com.flyhub.demo.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flyhub.demo.entities.AppUser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Benjamin E Ndugga
 */
@Slf4j
@JsonIgnoreProperties(allowSetters = true, allowGetters = false, value = {"password"})
public class AppUserDetails implements UserDetails {

    private final AppUser appUser;

    public AppUserDetails(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        appUser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return appUser.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return appUser.isAccountNonExpired();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return appUser.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return appUser.isEnabled();
    }

    public Long getUserId() {
        return appUser.getUserId();
    }
}
