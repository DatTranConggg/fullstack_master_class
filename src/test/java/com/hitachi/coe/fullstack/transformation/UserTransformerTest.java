package com.hitachi.coe.fullstack.transformation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.hitachi.coe.fullstack.entity.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.hitachi.coe.fullstack.entity.User;
import com.hitachi.coe.fullstack.model.UserModel;

@SpringBootTest
@ActiveProfiles("test")
public class UserTransformerTest {
    @Autowired
    private UserTransformer userTransformer;

    @Test
    public void testApply_whenValidData_thenSuccess() {
        Group group = new Group();
        group.setId(1);
        User user = new User();
        user.setName("khoa");
        user.setEmail("khoa@gmail.com");
        user.setGroup(group);

        UserModel result = userTransformer.apply(user);
        Assertions.assertEquals("khoa", result.getName());
        Assertions.assertEquals("khoa@gmail.com", result.getEmail());
        Assertions.assertEquals(1, result.getGroupId());
    }

    @Test
    public void testApplyList_whenValidData_thenSuccess() {
        Group group = new Group();
        group.setId(1);
        User user1 = new User();
        user1.setName("khoa");
        user1.setEmail("khoa@gmail.com");
        user1.setGroup(group);

        User user2 = new User();
        user2.setName("hieu");
        user2.setEmail("hieu@gmail.com");
        user2.setGroup(group);

        List<User> listUsers = Arrays.asList(user1, user2);

        List<UserModel> result = userTransformer.applyList(listUsers);

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals("khoa", result.get(0).getName());
        Assertions.assertEquals("khoa@gmail.com", result.get(0).getEmail());
        Assertions.assertEquals(1, result.get(0).getGroupId());

        Assertions.assertEquals("hieu", result.get(1).getName());
        Assertions.assertEquals("hieu@gmail.com", result.get(1).getEmail());
        Assertions.assertEquals(1, result.get(1).getGroupId());
    }

    @Test
    public void testApplyList_whenEmptyListData_thenSuccess() {
        List<User> listUsers = Collections.emptyList();

        List<UserModel> result = userTransformer.applyList(listUsers);

        Assertions.assertTrue(result.isEmpty());
    }


}
