package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.model.EmployeeModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class EmployeeModelTransformerTest {
    @InjectMocks
    private EmployeeModelTransformer employeeModelTransformer;

    @Test
    public void testApply() {
        EmployeeModel model = new EmployeeModel();
        model.setName(ArgumentMatchers.anyString());

        Employee result = employeeModelTransformer.apply(model);

        Assertions.assertEquals(ArgumentMatchers.anyString(), result.getName());

    }

    @Test
    public void testApplyList() {
        EmployeeModel model1 = new EmployeeModel();
        model1.setName(ArgumentMatchers.anyString());

        EmployeeModel model2 = new EmployeeModel();
        model2.setName(ArgumentMatchers.anyString());

        List<EmployeeModel> models = Arrays.asList(model1, model2);

        List<Employee> result = employeeModelTransformer.applyList(models);

        Assertions.assertEquals(2, result.size());

        result.stream().map(Employee::getName)
                .forEach(name ->Assertions.assertEquals(ArgumentMatchers.anyString(), name));
    }

    @Test
    public void testApplyList_EmptyList() {
        List<EmployeeModel> models = Collections.emptyList();

        List<Employee> result = employeeModelTransformer.applyList(models);

        Assertions.assertTrue(result.isEmpty());
    }

}