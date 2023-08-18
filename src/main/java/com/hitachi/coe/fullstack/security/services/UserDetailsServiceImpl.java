package com.hitachi.coe.fullstack.security.services;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.GroupRight;
import com.hitachi.coe.fullstack.entity.User;
import com.hitachi.coe.fullstack.model.UserModel;
import com.hitachi.coe.fullstack.repository.GroupRightRepository;
import com.hitachi.coe.fullstack.repository.UserRepository;
import com.hitachi.coe.fullstack.transformation.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTransformer userTransformer;

    @Autowired
    GroupRightRepository groupRightRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByName(username))
                .orElseThrow(() -> new UsernameNotFoundException(ErrorConstant.MESSAGE_USER_NOT_FOUND));
        List<GroupRight> groupRights = Optional.ofNullable(groupRightRepository.findByGroupId(user.getGroup().getId()))
                .orElseThrow(() -> new RuntimeException("Group right not found"));
        UserModel userModel = userTransformer.apply(user);
        return new UserDetailsImpl(userModel, groupRights);
    }
}

