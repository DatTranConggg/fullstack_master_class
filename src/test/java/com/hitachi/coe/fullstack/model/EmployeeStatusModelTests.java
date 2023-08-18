package com.hitachi.coe.fullstack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
public class EmployeeStatusModelTests {
	 @Test
	    public void testEmployeeStatusModel() {
	        EmployeeStatusModel employeeStatus = new EmployeeStatusModel();

	        Integer id = 1;
	        Date statusDate = new Date();
	        Integer status = 2;
	        Integer employeeId = 3;
	        String employeeName = "John Doe";
	        
	        employeeStatus.setId(id);
	        employeeStatus.setStatusDate(statusDate);
	        employeeStatus.setStatus(status);
	        employeeStatus.setEmployeeId(employeeId);
	        employeeStatus.setEmployeeName(employeeName);
	        
	        assertEquals(id, employeeStatus.getId());
	        assertEquals(statusDate, employeeStatus.getStatusDate());
	        assertEquals(status, employeeStatus.getStatus());
	        assertEquals(employeeId, employeeStatus.getEmployeeId());
	        assertEquals(employeeName, employeeStatus.getEmployeeName());
	    }
}
