package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.util.DateFormatUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CoeUtilizationModelTest {

	@Test
	void testCoeUtilizationModel() {
		CoeUtilizationModel model = new CoeUtilizationModel();
		List<EmployeeUtilizationModel> employeeModels = new ArrayList<>();
		model.setId(1);
		model.setDuration("15 Aug 2023 - 30 Aug 2023");
		model.setStartDate(DateFormatUtils.convertDateFromString("15 Aug 2023", "dd MMM uuuu"));
		model.setEndDate(DateFormatUtils.convertDateFromString("30 Aug 2023", "dd MMM uuuu"));
		model.setTotalUtilization(60.8);
		model.setEmployeeUtilizations(employeeModels);
		assertEquals(1, model.getId());
		assertEquals(0, model.getEmployeeUtilizations().size());
		assertEquals("15 Aug 2023 - 30 Aug 2023", model.getDuration());
		assertEquals(60.8, model.getTotalUtilization());
	}
}
