package com.hitachi.coe.fullstack.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RightModelTest {

    @Test
    void testModelRight() {
        GroupRightModel groupRightModel = new GroupRightModel();
        RightModel rightModel = new RightModel();
        groupRightModel.setRightId(1);
        groupRightModel.setGroupId(1);

        rightModel.setCode("ADMIN");
        rightModel.setModule("Module1");
        rightModel.setGroupRights(List.of(groupRightModel));

        assertNotNull(rightModel);
        assertEquals("ADMIN", rightModel.getCode());
        assertEquals("Module1", rightModel.getModule());
        assertEquals(1, rightModel.getGroupRights().size());
    }
}
