package com.hitachi.coe.fullstack.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.SkillSet;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.EmployeeSkillModel;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import com.hitachi.coe.fullstack.model.SkillSetModel;
import com.hitachi.coe.fullstack.repository.EmployeeSkillRepository;
import com.hitachi.coe.fullstack.repository.SkillSetRepository;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
public class EmployeeSkillServiceTest {

	@MockBean
	private EmployeeSkillRepository skillSetRepository;

	@Autowired
	private EmployeeSkillService employeeSkillService;
	
	@MockBean
	private SkillSetRepository skillRepository;

	@Test
	void testGetEmployeeSkillPieChart_WhenFullParams_ThenSuccess() {

		Integer branchId = 1;
		Integer coeId = 2;
		Integer coeCoreTeamId = 3;
		String strTopSkillIds = "1,2,3,4,5,6";
		List<Integer> arrTopSkillIds = Arrays.asList(1, 2, 3, 4, 5, 6);

		List<IPieChartModel> mockData = new ArrayList<IPieChartModel>();

		when(skillSetRepository.getEmployeeSkillPieChart(branchId, coeId, coeCoreTeamId, strTopSkillIds))
				.thenReturn(mockData);

		List<IPieChartModel> responseEntity = employeeSkillService.getEmployeeSkillPieChart(branchId, coeId,
				coeCoreTeamId, arrTopSkillIds);

		assertNotNull(responseEntity);

	}

	@Test
	void testGetEmployeeSkillPieChartt_WhenNullBranchIdAndCoeCoreTeamIAndCoeId_ThenSuccess() {

		Integer branchId = null;
		Integer coeCoreTeamId = null;
		Integer coeId = null;
		String strTopSkillIds = "1,2,3,4,5,6";
		List<Integer> arrTopSkillIds = Arrays.asList(1, 2, 3, 4, 5, 6);
		List<IPieChartModel> mockData = new ArrayList<IPieChartModel>();

		when(skillSetRepository.getEmployeeSkillPieChart(branchId, coeId, coeCoreTeamId, strTopSkillIds))
				.thenReturn(mockData);

		List<IPieChartModel> responseEntity = employeeSkillService.getEmployeeSkillPieChart(branchId, coeId,
				coeCoreTeamId, arrTopSkillIds);

		assertNotNull(responseEntity);

	}

	@Test
	void testGetEmployeeSkillPieChart_WhenNullBranchId_ThenSuccess() {
		Integer branchId = null;
		Integer coeCoreTeamId = 2;
		Integer coeId = 3;
		String strTopSkillIds = "1,2,3,4,5,6";
		List<Integer> arrTopSkillIds = Arrays.asList(1, 2, 3, 4, 5, 6);
		List<IPieChartModel> mockData = new ArrayList<IPieChartModel>();

		when(skillSetRepository.getEmployeeSkillPieChart(branchId, coeId, coeCoreTeamId, strTopSkillIds))
				.thenReturn(mockData);

		List<IPieChartModel> responseEntity = employeeSkillService.getEmployeeSkillPieChart(branchId, coeId,
				coeCoreTeamId, arrTopSkillIds);

		assertNotNull(responseEntity);

	}

	@Test
	void testGetEmployeeSkillPieChart_WhenNullCoeCoreTeamIdAndcoeId_ThenSuccess() {
		Integer branchId = 1;
		Integer coeCoreTeamId = null;
		Integer coeId = null;
		String strTopSkillIds = "1,2,3,4,5,6";
		List<Integer> arrTopSkillIds = Arrays.asList(1, 2, 3, 4, 5, 6);
		List<IPieChartModel> mockData = new ArrayList<IPieChartModel>();

		when(skillSetRepository.getEmployeeSkillPieChart(branchId, coeId, coeCoreTeamId, strTopSkillIds))
				.thenReturn(mockData);

		List<IPieChartModel> responseEntity = employeeSkillService.getEmployeeSkillPieChart(branchId, coeId,
				coeCoreTeamId, arrTopSkillIds);

		assertNotNull(responseEntity);

	}

	@Test
	void testGetEmployeeSkillPieChart_WhenNullCoeId_ThenThrowCoEException() {
		Integer branchId = 1;
		Integer coeCoreTeamId = 2;
		Integer coeId = null;
		List<Integer> arrTopSkillIds = Arrays.asList(1, 2, 3, 4, 5, 6);

		Throwable throwable = Assertions.assertThrows(CoEException.class, () -> {
			employeeSkillService.getEmployeeSkillPieChart(branchId, coeCoreTeamId, coeId, arrTopSkillIds);
		});

		Assertions.assertEquals(CoEException.class, throwable.getClass());

	}

	@Test
	void testGetEmployeeSkillPieChart_WhenNullArrTopSkillIds_ThenThrowCoEException() {
		Integer branchId = 1;
		Integer coeCoreTeamId = 2;
		Integer coeId = 3;
		List<Integer> arrTopSkillIds = null;
		Throwable throwable = Assertions.assertThrows(CoEException.class, () -> {
			employeeSkillService.getEmployeeSkillPieChart(branchId, coeCoreTeamId, coeId, arrTopSkillIds);
		});

		Assertions.assertEquals(CoEException.class, throwable.getClass());

	}
	
	@Test
	void testGetTopSkillSet_whenSuccess_thenReturnListTopSkill() {
		int limit = 6;
		Pageable pageable = PageRequest.of(0, limit);

		List<SkillSet> topSkills = new ArrayList<>();
		SkillSet skill1 = new SkillSet();
		SkillSet skill2 = new SkillSet();
		skill1.setName("Java");
		skill2.setName("React");;
		topSkills.add(skill1);
		topSkills.add(skill2);
		Mockito.when(skillSetRepository.getTopSkillSet(pageable)).thenReturn(topSkills);

		List<SkillSet> expectedResult = new ArrayList<>();
		SkillSet skill1Map = new SkillSet();
		SkillSet skill2Map = new SkillSet();
		skill1Map.setName("Java");;
		skill2Map.setName("React");;
		expectedResult.add(skill1Map);
		expectedResult.add(skill2Map);

		List<SkillSetModel> result = employeeSkillService.getTopSkillSet(6);
		Assertions.assertEquals(expectedResult.size(), result.size());
		Assertions.assertEquals(expectedResult.get(0).getName(), result.get(0).getName());
	}
	
	@Test
	void testAddEmployeeSkill_whenSuccess() {
		Employee employee = new Employee();
		EmployeeSkillModel employeeSkillModel = new EmployeeSkillModel();
		employeeSkillModel.setId(1);
		SkillSet skill = new SkillSet();
		skill.setName("Java");
		skill.setId(1);
		Mockito.when(skillRepository.findById(skill.getId())).thenReturn(Optional.of(skill));
		employeeSkillService.addEmployeeSkill(employee, employeeSkillModel);
		verify(skillSetRepository).save(argThat(emSkill ->
			emSkill.getSkillLevel() == 1 &&
			emSkill.getSkillSet().getId() == 1
				));
	}
	
	@Test
	void testAddEmployeeSkill_whenSkillSetEmpty_thenThrowException() {
		Employee employee = new Employee();
		EmployeeSkillModel employeeSkillModel = new EmployeeSkillModel();
		Mockito.when(skillRepository.findById(1)).thenReturn(null);		
		assertThatThrownBy(() -> employeeSkillService.addEmployeeSkill(employee, employeeSkillModel)).isInstanceOf(InvalidDataException.class);
	}
}
