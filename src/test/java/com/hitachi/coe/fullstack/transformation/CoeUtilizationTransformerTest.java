package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.CoeUtilization;
import com.hitachi.coe.fullstack.entity.EmployeeUtilization;
import com.hitachi.coe.fullstack.model.CoeUtilizationModel;
import com.hitachi.coe.fullstack.util.DateFormatUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class CoeUtilizationTransformerTest {

	@Autowired
	CoeUtilizationTransformer transformer;

	@Test
	void testApply() {
		List<EmployeeUtilization> employeeUtilizations = new ArrayList<>();
		CoeUtilization coeUtilization = new CoeUtilization();
		coeUtilization.setId(1);
		coeUtilization.setDuration("15 Aug 2023 - 30 Aug 2023");
		coeUtilization.setStartDate(DateFormatUtils.convertDateFromString("15 Aug 2023", "dd MMM uuuu"));
		coeUtilization.setEndDate(DateFormatUtils.convertDateFromString("30 Aug 2023", "dd MMM uuuu"));
		coeUtilization.setTotalUtilization(60.8);
		coeUtilization.setEmployeeUtilizations(employeeUtilizations);
		CoeUtilizationModel model = transformer.apply(coeUtilization);
		assertEquals(coeUtilization.getId(), model.getId());
		assertEquals(coeUtilization.getDuration(), model.getDuration());
		assertEquals(coeUtilization.getTotalUtilization(), model.getTotalUtilization());
	}

	@Test
	void testListApply() {
		List<EmployeeUtilization> employeeUtilizations = new ArrayList<>();
		CoeUtilization coeUtilization = new CoeUtilization();
		coeUtilization.setId(1);
		coeUtilization.setEmployeeUtilizations(employeeUtilizations);
		List<CoeUtilization> coeUtilizations = List.of(coeUtilization);
		List<CoeUtilizationModel> models = transformer.applyList(coeUtilizations);
		assertEquals(1, models.size());
	}

	@Test
	void testApplyList_whenEntityListEmpty_thenReturnEmptyList() {
		List<CoeUtilization> coeUtilizations = new ArrayList<>();
		List<CoeUtilizationModel> models = transformer.applyList(coeUtilizations);
		assertEquals(0, models.size());
	}

}
