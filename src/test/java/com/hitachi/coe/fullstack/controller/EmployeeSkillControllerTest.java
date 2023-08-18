package com.hitachi.coe.fullstack.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import com.hitachi.coe.fullstack.model.SkillSetModel;
import com.hitachi.coe.fullstack.service.EmployeeSkillService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
class EmployeeSkillControllerTest {
	
	@Autowired
	EmployeeSkillController employeeSkillController;
	
	@MockBean
	EmployeeSkillService employeeSkillService;
	
	@Test
	void testGetTopSkillSet_whenSuccess_thenReturnList() {
		List<SkillSetModel> skillSets = new ArrayList<>();
		SkillSetModel test1 = new SkillSetModel();
		SkillSetModel test2 = new SkillSetModel();
		SkillSetModel test3 = new SkillSetModel();
		skillSets.add(test1);
		skillSets.add(test2);
		skillSets.add(test3);
		Mockito.when(employeeSkillService.getTopSkillSet(3)).thenReturn(skillSets);
		ResponseEntity<List<SkillSetModel>> status = employeeSkillController.getTopSkillSet(3);
		assertEquals(200, status.getStatusCodeValue());
		assertEquals(3, status.getBody().size());
	}
		@Test
	void testGetEmployeeSkillPieChart_WhenFullParams_ThenSucces() {
		int branchId = 1;
		int coeCoreTeamId = 1;
		int coeId = 1;
		List<Integer> arrTopSkillIds = Arrays.asList(1, 2, 3, 4, 5, 6);
		List<IPieChartModel> expected = new ArrayList<IPieChartModel>();
		Mockito.when(employeeSkillService.getEmployeeSkillPieChart(branchId, coeCoreTeamId, coeId, arrTopSkillIds))
				.thenReturn(expected);

		ResponseEntity<List<IPieChartModel>> response = employeeSkillController.getEmployeeSkillPieChart(branchId,
				coeCoreTeamId, coeId, arrTopSkillIds);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(expected, response.getBody());
	}

	@Test
	void testGetEmployeeSkillPieChart_WhenNullBranchIdAndCoeCoreTeamIdAndCoeId_ThenSuccess() {
		Integer branchId = null;
		Integer coeCoreTeamId = null;
		Integer coeId = null;
		List<Integer> arrTopSkillIds = Arrays.asList(1, 2, 3, 4, 5, 6);
		List<IPieChartModel> expected = new ArrayList<IPieChartModel>();
		Mockito.when(employeeSkillService.getEmployeeSkillPieChart(branchId, coeCoreTeamId, coeId, arrTopSkillIds))
				.thenReturn(expected);

		ResponseEntity<List<IPieChartModel>> response = employeeSkillController.getEmployeeSkillPieChart(branchId,
				coeCoreTeamId, coeId, arrTopSkillIds);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(expected, response.getBody());

	}

	@Test
	void testGetEmployeeSkillPieChart_WhenNullArrTopSkillIds_ThenThrowCoEException(){

		Integer branchId = 1;
		Integer coeCoreTeamId = 2;
		Integer coeId = 3;
		List<Integer> arrTopSkillIds = null;
		Mockito.when(employeeSkillService.getEmployeeSkillPieChart(branchId, coeCoreTeamId, coeId, arrTopSkillIds))
				.thenThrow(new CoEException(ErrorConstant.CODE_LIST_ID_OF_SKILL_NULL,
						ErrorConstant.MESSAGE_LIST_ID_OF_SKILL_NULL));

		Throwable throwable = Assertions.assertThrows(CoEException.class, () -> {
			employeeSkillService.getEmployeeSkillPieChart(branchId, coeCoreTeamId, coeId, arrTopSkillIds);
		});

		Assertions.assertEquals(CoEException.class, throwable.getClass());
	}

	@Test
	void testGetEmployeeSkillPieChart_WhenNullCoeId_ThenThrowCoEException(){

		Integer branchId = 1;
		Integer coeCoreTeamId = 2;
		Integer coeId = null;
		List<Integer> arrTopSkillIds = Arrays.asList(1, 2, 3, 4, 5, 6);
		Mockito.when(employeeSkillService.getEmployeeSkillPieChart(branchId, coeCoreTeamId, coeId, arrTopSkillIds))
				.thenThrow(new CoEException(ErrorConstant.CODE_CENTER_OF_EXCELLENCE_NULL,
						ErrorConstant.MESSAGE_CENTER_OF_EXCELLENCE_NULL)); 

		Throwable throwable = Assertions.assertThrows(CoEException.class, () -> {
			employeeSkillService.getEmployeeSkillPieChart(branchId, coeCoreTeamId, coeId, arrTopSkillIds);
		});

		Assertions.assertEquals(CoEException.class, throwable.getClass());
	}

	@Test
	void testGetEmployeeSkillPieChart_WhenNullBranchAndCoeCoreTeamIdAndNotNullCoeId_ThenSuccess() {
		Integer branchId = null;
		Integer coeCoreTeamId = null;
		Integer coeId = 3;
		List<Integer> arrTopSkillIds = Arrays.asList(1, 2, 3, 4, 5, 6);
		List<IPieChartModel> expected = new ArrayList<IPieChartModel>();
		Mockito.when(employeeSkillService.getEmployeeSkillPieChart(branchId, coeCoreTeamId, coeId, arrTopSkillIds))
				.thenReturn(expected);

		ResponseEntity<List<IPieChartModel>> response = employeeSkillController.getEmployeeSkillPieChart(branchId,
				coeCoreTeamId, coeId, arrTopSkillIds);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(expected, response.getBody());
	}

	@Test
	void testGetEmployeeSkillPieChart_WhenNullCoeCoreTeamIdAndcoeId_ThenSuccess() {
		Integer branchId = 1;
		Integer coeCoreTeamId = null;
		Integer coeId = null;
		List<Integer> arrTopSkillIds = Arrays.asList(1, 2, 3, 4, 5, 6);
		List<IPieChartModel> expected = new ArrayList<IPieChartModel>();
		Mockito.when(employeeSkillService.getEmployeeSkillPieChart(branchId, coeCoreTeamId, coeId, arrTopSkillIds))
				.thenReturn(expected);

		ResponseEntity<List<IPieChartModel>> response = employeeSkillController.getEmployeeSkillPieChart(branchId,
				coeCoreTeamId, coeId, arrTopSkillIds);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(expected, response.getBody());
	}

	@Test
	void testGetEmployeeSkillPieChart_WhenNullBranchId_ThenSuccess() {
		Integer branchId = null;
		Integer coeCoreTeamId = 2;
		Integer coeId = 3;
		List<Integer> arrTopSkillIds = Arrays.asList(1, 2, 3, 4, 5, 6);
		List<IPieChartModel> expected = new ArrayList<IPieChartModel>();
		Mockito.when(employeeSkillService.getEmployeeSkillPieChart(branchId, coeCoreTeamId, coeId, arrTopSkillIds))
				.thenReturn(expected);

		ResponseEntity<List<IPieChartModel>> response = employeeSkillController.getEmployeeSkillPieChart(branchId,
				coeCoreTeamId, coeId, arrTopSkillIds);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(expected, response.getBody());
	}
}
