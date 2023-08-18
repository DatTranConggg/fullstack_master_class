package com.hitachi.coe.fullstack.service;

import java.util.List;

import com.hitachi.coe.fullstack.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.hitachi.coe.fullstack.entity.User;
import com.hitachi.coe.fullstack.model.UserModel;
import com.hitachi.coe.fullstack.repository.UserRepository;
import com.hitachi.coe.fullstack.transformation.UserTransformer;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
public class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserTransformer userTransformer;

    @Test
    public void testGetAllUsers_whenValidData_thenSuccess() {
        User user = new User();
        user.setId(1);
        UserModel userModel = new UserModel();
        userModel.setId(1);
        List<User> listUser = List.of(user);
        List<UserModel> listUserModel = List.of(userModel);
        Mockito.when(userRepository.getAllUsers()).thenReturn(listUser);
        Mockito.when(userTransformer.applyList(listUser)).thenReturn(listUserModel);

        List<UserModel> result = userService.getAllUsers();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testGetUserByGroupId_whenValidData_thenSuccess() {
        User user = new User();
        user.setId(1);
        UserModel userModel = new UserModel();
        userModel.setId(1);
        List<User> listUser = List.of(user);
        Mockito.when(userRepository.getUsersByGroupId(1)).thenReturn(listUser);
        Mockito.when(userTransformer.apply(user)).thenReturn(userModel);

        List<UserModel> result = userService.getUsersByGroupId(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testGetUserById_whenValidData_thenSuccess() {
        User user = new User();
        user.setId(1);
        UserModel userModel = new UserModel();
        userModel.setId(1);
        Mockito.when(userRepository.getUserById(1)).thenReturn(user);
        Mockito.when(userTransformer.apply(user)).thenReturn(userModel);

        UserModel result = userService.getUserById(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(userModel, result);
    }

}
