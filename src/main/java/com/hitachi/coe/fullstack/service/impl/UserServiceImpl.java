package com.hitachi.coe.fullstack.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitachi.coe.fullstack.model.UserModel;
import com.hitachi.coe.fullstack.repository.UserRepository;
import com.hitachi.coe.fullstack.service.UserService;
import com.hitachi.coe.fullstack.transformation.UserTransformer;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransformer userTransformer;

    @Override
    public List<UserModel> getAllUsers() {
        return userTransformer.applyList(userRepository.getAllUsers());
    }

    @Override
    public List<UserModel> getUsersByGroupId(Integer groupId) {
        return userRepository.getUsersByGroupId(groupId).stream().map(userTransformer).collect(Collectors.toList());
    }

    @Override
    public UserModel getUserById(Integer id) {
        return userTransformer.apply(userRepository.getUserById(id));
    }
}
