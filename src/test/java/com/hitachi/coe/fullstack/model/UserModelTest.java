package com.hitachi.coe.fullstack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class UserModelTest {

	@Test
	void testModelUser() {

		UserModel userModel = new UserModel();
		userModel.setName("thien");
		userModel.setEmail("thien@gmail.com");
		userModel.setGroupId(1);

		assertNotNull(userModel);
		assertEquals("thien", userModel.getName());
		assertEquals("thien@gmail.com", userModel.getEmail());
		assertEquals(1, userModel.getGroupId());
	}

}
