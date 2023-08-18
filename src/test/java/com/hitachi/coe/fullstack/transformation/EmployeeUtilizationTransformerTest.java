package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.CoeUtilization;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeUtilization;
import com.hitachi.coe.fullstack.model.EmployeeUtilizationModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class EmployeeUtilizationTransformerTest {
    @Autowired
    private EmployeeUtilizationTransformer employeeUtilizationTransformer;
    @Test
    public void testToEntityEmployeeUtilization_whenValidData_thenSuccess() {
        EmployeeUtilizationModel employeeUtilizationModel = new EmployeeUtilizationModel();
        employeeUtilizationModel.setEmail("abc@hitachivantara.com");
        employeeUtilizationModel.setName("Nguyen A");
        employeeUtilizationModel.setHccId(123);
        employeeUtilizationModel.setTimeSheet("Missing Timesheet");
        employeeUtilizationModel.setOracleStaffedProject("FSCMS");
        employeeUtilizationModel.setNo(1);
        employeeUtilizationModel.setCoe("CoE1");
        employeeUtilizationModel.setLocation("Loc1");
        employeeUtilizationModel.setLevel("lv1");
        employeeUtilizationModel.setBu("Bu1");
        employeeUtilizationModel.setBillableHours(200);
        employeeUtilizationModel.setLoggedHours(150);
        employeeUtilizationModel.setBillable(124.2);
        employeeUtilizationModel.setPtoOracle(15);
        employeeUtilizationModel.setAvailableHours(161);

        EmployeeUtilization employeeUTExpected = employeeUtilizationTransformer.toEntity(employeeUtilizationModel,new Employee(),new CoeUtilization());

        assertEquals(200, employeeUTExpected.getBillableHours());
        assertEquals(150, employeeUTExpected.getLoggedHours());
        assertEquals(124.2, employeeUTExpected.getBillable());
        assertEquals(15, employeeUTExpected.getPtoOracle());
        assertEquals(161, employeeUTExpected.getAvailableHours());
        assertEquals("FSCMS", employeeUTExpected.getProjectCode());
    }
}
