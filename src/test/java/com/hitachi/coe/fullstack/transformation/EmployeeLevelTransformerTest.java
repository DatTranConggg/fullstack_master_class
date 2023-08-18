package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.EmployeeLevel;
import com.hitachi.coe.fullstack.model.EmployeeLevelModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EmployeeLevelTransformerTest {
    @Autowired
    EmployeeLevelTransformer employeeLevelTransformer;

    @Test
    void testApply() {
        EmployeeLevel employeeLevel = new EmployeeLevel();
        employeeLevel.setId(1);

        EmployeeLevelModel model =  employeeLevelTransformer.apply(employeeLevel);
        assertEquals(1, model.getId());
    }

    @Test
    void testListApply() {

        EmployeeLevel entity1 = new EmployeeLevel();
        entity1.setId(1);
        EmployeeLevel entity2 = new EmployeeLevel();
        entity2.setId(2);
        List<EmployeeLevel> entities = new ArrayList<>(Arrays.asList(entity1, entity2));
        List<EmployeeLevelModel> models =  employeeLevelTransformer.applyList(entities);
        assertEquals(2, models.size());

    }

    @Test
    void testApplyList_whenEntityListEmpty_thenReturnEmptyList() {
        List<EmployeeLevel> emptyList = new ArrayList<>();
        List<EmployeeLevelModel> models = employeeLevelTransformer.applyList(emptyList);
        assertEquals(0, models.size());
    }
}
