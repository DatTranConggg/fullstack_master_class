package com.hitachi.coe.fullstack.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmployeeImportModelTest {

    @Test
    public void testModelEmployeeImport(){
        Date date = new Date();
        EmployeeImportModel employee = new EmployeeImportModel();
        employee.setHccId("123456");
        employee.setLdap("1234");
        employee.setEmployeeName("John Doe");
        employee.setEmail("johndoe@example.com");
        employee.setBusinessUnit("Software Engineering");
        employee.setBranch("San Francisco");
        employee.setLevel("Senior");
        employee.setLegalEntityHireDate(date);

        assertNotNull(employee);
        assertEquals("123456", employee.getHccId());
        assertEquals("1234", employee.getLdap());
        assertEquals("John Doe", employee.getEmployeeName());
        assertEquals("johndoe@example.com", employee.getEmail());
        assertEquals("Software Engineering", employee.getBusinessUnit());
        assertEquals("San Francisco", employee.getBranch());
        assertEquals("Senior", employee.getLevel());
        assertEquals(date, employee.getLegalEntityHireDate());
    }
}
