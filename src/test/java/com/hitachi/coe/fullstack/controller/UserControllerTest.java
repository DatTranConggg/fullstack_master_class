package com.hitachi.coe.fullstack.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.hitachi.coe.fullstack.model.common.BaseResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import com.hitachi.coe.fullstack.model.UserModel;
import com.hitachi.coe.fullstack.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
public class UserControllerTest {

	@Autowired
	private UserController userController;

	@MockBean
	private UserService userService;

	@Test
	public void testGetAllUsers_OnCommonSuccess() {
		UserModel userModel = new UserModel();
		userModel.setId(1);
		userModel.setName("khoa");
		userModel.setEmail("khoa@gmail.com");
		List<UserModel> listUser = new ArrayList<>();
		listUser.add(userModel);
		when(userService.getAllUsers()).thenReturn(listUser);

		BaseResponse<List<UserModel>> allUserSearched = userController.getAllUsers();
		assertEquals(200, allUserSearched.getStatus());
		assertEquals(listUser, allUserSearched.getData());
	}

	@Test
	public void testGetUsersByGroupId_OnCommonSuccess() {
		UserModel userModel = new UserModel();
		userModel.setId(1);
		userModel.setName("khoa");
		userModel.setEmail("khoa@gmail.com");
		List<UserModel> listUser = new ArrayList<>();
		listUser.add(userModel);
		when(userService.getUsersByGroupId(anyInt())).thenReturn(listUser);

		BaseResponse<List<UserModel>> listUserSearchedFromGroupId = userController.getUsersByGroupId(anyInt());
		assertEquals(200, listUserSearchedFromGroupId.getStatus());
		assertEquals(listUser, listUserSearchedFromGroupId.getData());
	}

	@Test
	public void testGetUserById_OnCommonSuccess() {
		UserModel userModel = new UserModel();
		userModel.setId(1);
		userModel.setName("khoa");
		userModel.setEmail("khoa@gmail.com");
		when(userService.getUserById(anyInt())).thenReturn(userModel);
		BaseResponse<UserModel> userSearched = userController.getUserById(anyInt());
		assertEquals(200, userSearched.getStatus());
		assertEquals(userModel, userSearched.getData());
	}
}
