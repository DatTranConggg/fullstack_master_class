package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.GroupRight;
import com.hitachi.coe.fullstack.entity.Right;
import com.hitachi.coe.fullstack.model.GroupRightModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class GroupRightTransformerTest {

    @Autowired
    GroupRightTransformer groupRightTransformer;
    @Test
    public void testApply_whenValidData_thenSuccess() {
        GroupRight groupRight = new GroupRight();
        Right right = new Right();
        right.setId(1);
        groupRight.setId(1);
        groupRight.setGroupId(1);
        groupRight.setRight(right);

        GroupRightModel result = groupRightTransformer.apply(groupRight);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals(1, result.getGroupId());
        Assertions.assertEquals(1, result.getRightId());

    }

    @Test
    public void testApplyList_whenValidData_thenSuccess() {
        GroupRight groupRight = new GroupRight();
        Right right = new Right();
        right.setId(1);
        groupRight.setId(1);
        groupRight.setGroupId(1);
        groupRight.setRight(right);

        List<GroupRightModel> result = groupRightTransformer.applyList(List.of(groupRight));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1, result.get(0).getId());
        Assertions.assertEquals(1, result.get(0).getGroupId());
        Assertions.assertEquals(1, result.get(0).getRightId());
    }

    @Test
    public void testApplyList_whenEmptyListData_thenSuccess() {

        List<GroupRightModel> result = groupRightTransformer.applyList(Collections.emptyList());

        Assertions.assertTrue(result.isEmpty());
    }
}
