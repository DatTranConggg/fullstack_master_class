package com.hitachi.coe.fullstack.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmployeeUTModelTest {

    @Test
    public void testModelEmployeeUtilization() {
        Date date = new Date();
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
        employeeUtilizationModel.setStartDate(date);

        assertNotNull(employeeUtilizationModel);
        assertEquals(200, employeeUtilizationModel.getBillableHours());
        assertEquals(150, employeeUtilizationModel.getLoggedHours());
        assertEquals(124.2, employeeUtilizationModel.getBillable());
        assertEquals(15, employeeUtilizationModel.getPtoOracle());
        assertEquals(161, employeeUtilizationModel.getAvailableHours());
        assertEquals(123, employeeUtilizationModel.getHccId());
        assertEquals("Nguyen A", employeeUtilizationModel.getName());
        assertEquals("abc@hitachivantara.com", employeeUtilizationModel.getEmail());
        assertEquals("Missing Timesheet", employeeUtilizationModel.getTimeSheet());
        assertEquals("FSCMS", employeeUtilizationModel.getOracleStaffedProject());
        assertEquals(1, employeeUtilizationModel.getNo());
        assertEquals("CoE1", employeeUtilizationModel.getCoe());
        assertEquals("Loc1", employeeUtilizationModel.getLocation());
        assertEquals("lv1", employeeUtilizationModel.getLevel());
        assertEquals("Bu1", employeeUtilizationModel.getBu());
        assertEquals(date, employeeUtilizationModel.getStartDate());
    }
}
