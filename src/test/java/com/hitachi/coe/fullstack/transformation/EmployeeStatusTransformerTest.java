package com.hitachi.coe.fullstack.transformation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeStatus;
import com.hitachi.coe.fullstack.model.EmployeeStatusModel;

@SpringBootTest
@ActiveProfiles("test")
class EmployeeStatusTransformerTest {

	@Autowired
	private EmployeeStatusTransformer employeeStatusTransformer;

	@Test
	public void testApply_ConvertsEmployeeStatus_To_EmployeeStatusModel() {
		// Create an EmployeeStatus object and set its properties using the setter
		// methods
		EmployeeStatus employeeStatus = new EmployeeStatus();
		employeeStatus.setId(1);
		employeeStatus.setStatus(1);
		employeeStatus.setStatusDate(new Timestamp(System.currentTimeMillis()));

		Employee employee = new Employee();
		employee.setId(1);
		employee.setName("John Doe");

		employeeStatus.setEmployee(employee);

		// Call the apply method of the EmployeeStatusTransformer class to convert the
		// EmployeeStatus object to an EmployeeStatusModel object
		EmployeeStatusModel employeeStatusModel = employeeStatusTransformer.apply(employeeStatus);

		// Check whether the properties of the EmployeeStatusModel object returned by
		// the apply method match the expected values
		assertEquals(1, employeeStatusModel.getId());
		assertEquals(1, employeeStatusModel.getStatus());
		assertEquals(employeeStatus.getStatusDate(), employeeStatusModel.getStatusDate());
		assertEquals(1, employeeStatusModel.getEmployeeId());
		assertEquals("John Doe", employeeStatusModel.getEmployeeName());
	}

	@Test
	public void testApplyList_ConvertsEmployeeStatusList_To_EmployeeStatusModelList() {
		// Create a list of EmployeeStatus objects and set their properties using the
		// setter methods
		List<EmployeeStatus> employeeStatusList = new ArrayList<>();

		EmployeeStatus employeeStatus1 = new EmployeeStatus();
		employeeStatus1.setId(1);
		employeeStatus1.setStatus(1);
		employeeStatus1.setStatusDate(new Timestamp(System.currentTimeMillis()));

		Employee employee1 = new Employee();
		employee1.setId(1);
		employee1.setName("John Doe");

		employeeStatus1.setEmployee(employee1);

		employeeStatusList.add(employeeStatus1);

		EmployeeStatus employeeStatus2 = new EmployeeStatus();
		employeeStatus2.setId(2);
		employeeStatus2.setStatus(2);
		employeeStatus2.setStatusDate(new Timestamp(System.currentTimeMillis()));

		Employee employee2 = new Employee();
		employee2.setId(2);
		employee2.setName("Jane Doe");

		employeeStatus2.setEmployee(employee2);

		employeeStatusList.add(employeeStatus2);

		// Call the applyList method of the EmployeeStatusTransformer class to convert
		// the list of EmployeeStatus objects to a list of EmployeeStatusModel objects
		List<EmployeeStatusModel> employeeStatusModelList = employeeStatusTransformer.applyList(employeeStatusList);

		// Check whether the properties of the EmployeeStatusModel objects returned by
		// the applyList method match the expected values
		assertEquals(2, employeeStatusModelList.size());

		EmployeeStatusModel employeeStatusModel1 = employeeStatusModelList.get(0);
		assertEquals(1, employeeStatusModel1.getId());
		assertEquals(1, employeeStatusModel1.getStatus());
		assertEquals(employeeStatus1.getStatusDate(), employeeStatusModel1.getStatusDate());
		assertEquals(1, employeeStatusModel1.getEmployeeId());
		assertEquals("John Doe", employeeStatusModel1.getEmployeeName());

		EmployeeStatusModel employeeStatusModel2 = employeeStatusModelList.get(1);
		assertEquals(2, employeeStatusModel2.getId());
		assertEquals(2, employeeStatusModel2.getStatus());
		assertEquals(employeeStatus2.getStatusDate(), employeeStatusModel2.getStatusDate());
		assertEquals(2, employeeStatusModel2.getEmployeeId());
		assertEquals("Jane Doe", employeeStatusModel2.getEmployeeName());
	}

	@Test
	public void testApplyList_WhenEmptyInput_ThenReturnsEmptyList() {
		List<EmployeeStatus> models = Collections.emptyList();

		List<EmployeeStatusModel> result = employeeStatusTransformer.applyList(models);
		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	public void testApplyList_WhenNullInput_ThenReturnsEmptyList() {
		// Call the applyList method of the EmployeeStatusTransformer with a null list
		// of entities
		List<EmployeeStatusModel> result = employeeStatusTransformer.applyList(null);

		// Assert that the result is an empty list
		assertEquals(Collections.emptyList(), result);
	}
}
