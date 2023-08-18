package com.hitachi.coe.fullstack.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeStatus;
import com.hitachi.coe.fullstack.model.EmployeeStatusModel;
import com.hitachi.coe.fullstack.repository.EmployeeStatusRepository;
import com.hitachi.coe.fullstack.transformation.EmployeeStatusTransformer;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
public class EmployeeStatusServiceTests {

	@Autowired
	private EmployeeStatusService employeeStatusService;
	
	@MockBean
	private EmployeeStatusTransformer employeeStatusTransformer;
	@MockBean
	private EmployeeStatusRepository employeeStatusRepository;
	
	@Test
	void testDeleteEmployeeById_OnCommonSuccess() {
			Employee employee = new Employee();
			employee.setId(15);

			EmployeeStatus employeeStatus = new EmployeeStatus();
			employeeStatus.setStatus(0);
			employeeStatus.setEmployee(employee);

			EmployeeStatusModel employeeStatusModel = new EmployeeStatusModel();
			employeeStatusModel.setStatus(0);
			employeeStatus.setEmployee(employee);


			when(employeeStatusRepository.createDeleteStatusEmployee(anyInt())).thenReturn(employeeStatus);
			when(employeeStatusRepository.findById(anyInt())).thenReturn(Optional.of(employeeStatus));
			when(employeeStatusTransformer.apply(employeeStatus)).thenReturn(employeeStatusModel);

			EmployeeStatusModel employeeStatusCreated =  employeeStatusService.deleteEmployeeById(15);

			assertEquals(0,employeeStatusCreated.getStatus());
	}

	
//	TODO
//	mvn test have Failures: Expected com.hitachi.coe.fullstack.exceptions.CoEException to be thrown, but nothing was thrown.
//	@Test
//	void testDeleteEmployeeById_OnCommonNotFound() {
//			Employee employee = new Employee();
//			employee.setId(15);
//
//			EmployeeStatus employeeStatus = new EmployeeStatus();
//			employeeStatus.setStatus(0);
//			employeeStatus.setEmployee(employee);
//
//			EmployeeStatusModel employeeStatusModel = new EmployeeStatusModel();
//			employeeStatusModel.setStatus(0);
//			employeeStatus.setEmployee(employee);
//
//			when(employeeStatusRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
//
//			Throwable throwable = assertThrows(CoEException.class, 
//					() -> employeeStatusService.deleteEmployeeById(anyInt()));
//		
//	        assertEquals(CoEException.class, throwable.getClass());
//	        assertEquals(ErrorConstant.MESSAGE_EMPLOYEE_NULL, throwable.getMessage());
//	}
}
