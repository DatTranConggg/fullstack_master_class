package com.hitachi.coe.fullstack.security.services;

import com.hitachi.coe.fullstack.entity.GroupRight;
import com.hitachi.coe.fullstack.entity.Right;
import com.hitachi.coe.fullstack.model.UserModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Slf4j
public class UserDetailsImpl implements UserDetails {
    private UserModel userModel;
    private List<GroupRight> groupRights;

    public UserDetailsImpl(UserModel userModel, List<GroupRight> groupRights) {
        this.userModel = userModel;
        this.groupRights = groupRights;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (this.groupRights != null && !this.groupRights.isEmpty()) {
            this.groupRights.forEach(groupRightElement -> {
                Right right = groupRightElement.getRight();
                if (right != null) {
                    String rightString = right.getCode() + "_" + right.getModule();
                    authorities.add(new SimpleGrantedAuthority(rightString));
                }
            });
        }
        log.info("username = {}, authorities = {}", this.userModel.getName(), authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return userModel.getPassword();
    }

    @Override
    public String getUsername() {
        return userModel.getName();
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
