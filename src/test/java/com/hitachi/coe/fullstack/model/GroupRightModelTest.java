package com.hitachi.coe.fullstack.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GroupRightModelTest {

    @Test
    void testModelGroupRight() {
        GroupRightModel groupRightModel = new GroupRightModel();
        groupRightModel.setRightId(1);
        groupRightModel.setGroupId(1);

        assertNotNull(groupRightModel);
        assertEquals(1, groupRightModel.getRightId());
        assertEquals(1, groupRightModel.getGroupId());
    }
}
