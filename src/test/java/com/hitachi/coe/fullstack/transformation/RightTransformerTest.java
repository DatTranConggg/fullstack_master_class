package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.GroupRight;
import com.hitachi.coe.fullstack.entity.Right;
import com.hitachi.coe.fullstack.model.RightModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class RightTransformerTest {

    @Autowired
    private RightTransformer rightTransformer;

    @Test
    public void testApply_whenValidData_thenSuccess() {
        GroupRight groupRight = new GroupRight();
        Right right = new Right();
        groupRight.setId(1);
        groupRight.setGroupId(1);

        right.setId(1);
        right.setCode("ADMIN");
        right.setModule("Module1");
        right.setGroupRights(List.of(groupRight));
        groupRight.setRight(right);

        RightModel result = rightTransformer.apply(right);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("ADMIN", result.getCode());
        Assertions.assertEquals("Module1", result.getModule());
        Assertions.assertEquals(1, result.getGroupRights().size());

    }

    @Test
    public void testApplyList_whenValidData_thenSuccess() {
        Date date = new Date();
        GroupRight groupRight = new GroupRight();
        groupRight.setId(1);
        groupRight.setGroupId(1);

        Right right = new Right();
        right.setId(1);
        right.setCode("ADMIN");
        right.setModule("Module1");
        right.setGroupRights(List.of(groupRight));
        right.setCreatedBy("Admin");
        right.setCreated(date);
        groupRight.setRight(right);

        List<RightModel> result = rightTransformer.applyList(List.of(right));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("ADMIN", result.get(0).getCode());
        Assertions.assertEquals("Module1", result.get(0).getModule());
        Assertions.assertEquals(1, result.get(0).getGroupRights().size());
    }

    @Test
    public void testApplyList_whenEmptyListData_thenSuccess() {

        List<RightModel> result = rightTransformer.applyList(Collections.emptyList());

        Assertions.assertTrue(result.isEmpty());
    }
}
