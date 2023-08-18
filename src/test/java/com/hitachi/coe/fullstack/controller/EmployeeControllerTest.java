package com.hitachi.coe.fullstack.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.model.IEmployeeDetails;
import com.hitachi.coe.fullstack.model.BarChartModel;
import com.hitachi.coe.fullstack.model.EmployeeModel;
import com.hitachi.coe.fullstack.model.EmployeeStatusModel;
import com.hitachi.coe.fullstack.model.IBarChartDepartmentModel;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import com.hitachi.coe.fullstack.service.EmployeeService;
import com.hitachi.coe.fullstack.service.EmployeeStatusService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-data-test.properties")
class EmployeeControllerTest {
	@Autowired
	private MockMvc mvc;

	@Mock
	private EmployeeService employeeService;
	@MockBean
	private EmployeeStatusService employeeStatusService;

	@InjectMocks
	private EmployeeController employeeController;

//	private static String asJsonString(final Object obj) {
//		try {
//			return new ObjectMapper().writeValueAsString(obj);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	@Test
	public void testSearch() {
		// Mock input data
		String keyword = "nien";
		String practiceName = "";
		String coeCoreTeamName = "";
		String branchName = "";
		String fromDateStr = "2023-05-13 00:00:00";
		String toDateStr = "2023-05-14 00:00:00";
		Integer no = 0;
		Integer limit = 10;
		String sortBy = "name";
		Boolean desc = false;
		// Mock the expected result from employeeService
		List<EmployeeModel> employees = new ArrayList<>();
		employees.add(new EmployeeModel());
		Page<EmployeeModel> expectedResult = new PageImpl<>(employees);

		// Mock the behavior of employeeService.search()
		when(employeeService.searchEmployees(keyword, practiceName, coeCoreTeamName, branchName,1, fromDateStr, toDateStr,
				no, limit, sortBy, desc)).thenReturn(expectedResult);

		// Call the controller method
		employeeController.searchEmployees(keyword, practiceName, coeCoreTeamName, branchName, 1,fromDateStr, toDateStr,
				no, limit, sortBy, desc);

		// Verify that employeeService.search() is called with the correct parameters
		verify(employeeService).searchEmployees(keyword, practiceName, coeCoreTeamName, branchName,1, fromDateStr,
				toDateStr, no, limit, sortBy, desc);

	}


	@Test
    void testDeleteEmployeeById_WhenEmployeeDeleted_ThenSuccess() throws Exception {
        Integer id = 1;
        EmployeeStatusModel employeeStatus = new EmployeeStatusModel();
        when(employeeService.deleteEmployeeById(id)).thenReturn(new EmployeeModel());
        when(employeeStatusService.deleteEmployeeById(id)).thenReturn(employeeStatus);
        employeeController.deleteEmployeeById(id);
        verify(employeeService).deleteEmployeeById(id);
        verify(employeeStatusService).deleteEmployeeById(id);
    }
	
	@Test
	@SneakyThrows
	void updateEmployee() {
		EmployeeModel employeeModel = new EmployeeModel();
		when(employeeService.updateEmployee(any(EmployeeModel.class))).thenReturn(1234);

		ResponseEntity<Object> response = employeeController.updateEmployee(employeeModel);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetEmployeeById() {
		Integer employeeId = 1;
		EmployeeModel employeeModel = new EmployeeModel();
		when(employeeService.getEmployeeById(employeeId)).thenReturn(employeeModel);
		ResponseEntity<EmployeeModel> response = employeeController.getEmployeeById(employeeId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(employeeModel, response.getBody());
	}

//	TODO
//	mvn test have Error 
//    @Test
//    @SneakyThrows
//    void testAddEmployeeController() {
//        String url = "/api/v1/member/create";
//        EmployeeModel employeeModel = new EmployeeModel();
//        employeeModel.setEmail("pikachu@gmail.com");
//        employeeModel.setName("Tien");
//        employeeModel.setHccId("tien");
//        employeeModel.setLdap("tien");
//        employeeModel.setBranch(new Branch());
//        employeeModel.setCoeCoreTeam(new CoeCoreTeam());
//        employeeModel.setPractice(new Practice());
//        when(employeeService.add(any(EmployeeModel.class))).thenReturn(anyInt());
//        mvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(employeeModel)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isNotEmpty())
//                .andExpect(jsonPath("$.id", is("0")))
//                .andReturn();
//    }

	@Test
	void testGetQuantityDepartmentOfLevel_WithSpecificBranch(){
		Integer branchId = 1;
		Integer groupId = null;
		Integer teamId = null;
		ResponseEntity<List<IBarChartDepartmentModel>> response = employeeController.getQuantityOfLevel(branchId,
				groupId, teamId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testGetQuantityDepartmentOfLevel_WithSpecificBranchAndGroup(){
		Integer branchId = 1;
		Integer groupId = 1;
		Integer teamId = null;
		ResponseEntity<List<IBarChartDepartmentModel>> response = employeeController.getQuantityOfLevel(branchId,
				groupId, teamId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testGetQuantityDepartmentOfLevel_WithSpecificBranchGroupAndTeam(){
		Integer branchId = 1;
		Integer groupId = 1;
		Integer teamId = 1;
		ResponseEntity<List<IBarChartDepartmentModel>> response = employeeController.getQuantityOfLevel(branchId,
				groupId, teamId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testGetQuantityDepartmentOfLevel_WithSpecificGroupAndTeam(){
		Integer branchId = null;
		Integer groupId = 1;
		Integer teamId = 1;
		ResponseEntity<List<IBarChartDepartmentModel>> response = employeeController.getQuantityOfLevel(branchId,
				groupId, teamId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testGetQuantityDepartmentOfLevel() {
		Integer branchId = null;
		Integer groupId = null;
		Integer teamId = null;
		ResponseEntity<List<IBarChartDepartmentModel>> response = employeeController.getQuantityOfLevel(branchId,
				groupId, teamId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testGetQuantityOfLevel_whenAllNull() {
		Integer branchId = null;
		Integer groupId = null;
		Integer teamId = null;
		ResponseEntity<BarChartModel> response = employeeController.getQuantityOfLevelForBarChart(branchId, groupId,
				teamId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testGetQuantityOfLevel_whenBranchNotNull() {
		Integer branchId = 1;
		Integer groupId = null;
		Integer teamId = null;
		ResponseEntity<BarChartModel> response = employeeController.getQuantityOfLevelForBarChart(branchId, groupId,
				teamId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testGetQuantityOfLevel_whenBranchAndGroupNotNull() {
		Integer branchId = 1;
		Integer groupId = 1;
		Integer teamId = null;
		ResponseEntity<BarChartModel> response = employeeController.getQuantityOfLevelForBarChart(branchId, groupId,
				teamId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testGetQuantityOfLevel_whenBranchGroupAndTeamNotNull() {
		Integer branchId = 1;
		Integer groupId = 1;
		Integer teamId = 1;
		ResponseEntity<BarChartModel> response = employeeController.getQuantityOfLevelForBarChart(branchId, groupId,
				teamId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testGetQuantityOfLevel_whenGroupAndTeamNotNull() {
		Integer branchId = null;
		Integer groupId = 1;
		Integer teamId = 1;
		ResponseEntity<BarChartModel> response = employeeController.getQuantityOfLevelForBarChart(branchId, groupId,
				teamId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testGetQuantityOfEachSkillForBarChart_thenSuccess() {
		Integer branchId = 1;
		Integer groupId = 1;
		Integer teamId = 1;
		List<Integer> skillIds = Arrays.asList(1, 2);
		ResponseEntity<?> response = employeeController.getPercentofSkills(branchId, groupId, teamId, skillIds);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetLevelPieChart_Sucess() {
		// Arrange
		List<IPieChartModel> pieChartModels = new ArrayList<>();
		Integer branchId = 1;
		Integer coeCoreTeamId = 2;
		Integer coe_id = 3;

		when(employeeService.getLevelPieChart(branchId, coeCoreTeamId, coe_id)).thenReturn(pieChartModels);

		// Act
		ResponseEntity<List<IPieChartModel>> response = employeeController.getLevelPieChart(branchId, coeCoreTeamId,
				coe_id);

		// Assert
		assertEquals(pieChartModels, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetLevelPieChart_WhenNotNullBranchId_Success() {
		// Arrange
		List<IPieChartModel> pieChartModels = new ArrayList<>();
		Integer branchId = 1;
		Integer coeCoreTeamId = null;
		Integer coe_id = null;

		when(employeeService.getLevelPieChart(branchId, coeCoreTeamId, coe_id)).thenReturn(pieChartModels);

		// Act
		ResponseEntity<List<IPieChartModel>> response = employeeController.getLevelPieChart(branchId, coeCoreTeamId,
				coe_id);

		// Assert
		assertEquals(pieChartModels, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetLevelPieChart_WhenNotNullCoeCoreTeam_Success() {
		// Arrange
		List<IPieChartModel> pieChartModels = new ArrayList<>();
		Integer branchId = null;
		Integer coeCoreTeamId = 1;
		Integer coe_id = null;

		when(employeeService.getLevelPieChart(branchId, coeCoreTeamId, coe_id)).thenReturn(pieChartModels);

		// Act
		ResponseEntity<List<IPieChartModel>> response = employeeController.getLevelPieChart(branchId, coeCoreTeamId,
				coe_id);

		// Assert
		assertEquals(pieChartModels, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetLevelPieChart_WhenNotNullCoeId_Success() {
		// Arrange
		List<IPieChartModel> pieChartModels = new ArrayList<>();
		Integer branchId = null;
		Integer coeCoreTeamId = null;
		Integer coe_id = 1;

		when(employeeService.getLevelPieChart(branchId, coeCoreTeamId, coe_id)).thenReturn(pieChartModels);

		// Act
		ResponseEntity<List<IPieChartModel>> response = employeeController.getLevelPieChart(branchId, coeCoreTeamId,
				coe_id);

		// Assert
		assertEquals(pieChartModels, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetLevelPieChart_WhenAllNull() {
		// Arrange
		List<IPieChartModel> pieChartModels = new ArrayList<>();
		Integer branchId = null;
		Integer coeCoreTeamId = null;
		Integer coe_id = null;

		when(employeeService.getLevelPieChart(branchId, coeCoreTeamId, coe_id)).thenReturn(pieChartModels);

		// Act
		ResponseEntity<List<IPieChartModel>> response = employeeController.getLevelPieChart(branchId, coeCoreTeamId,
				coe_id);

		// Assert
		assertEquals(pieChartModels, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

    @Test
    void testGetEmployeeDetailsByHccId_whenHccIdIsValid_thenReturnIEmployeeDetails() {
        // prepare
        String testHccId = "999999";
        IEmployeeDetails iEmployeeDetails = mock(IEmployeeDetails.class);
        // when - then
        when(employeeService.getEmployeeDetailsByHccId(anyString())).thenReturn(iEmployeeDetails);
        // invoke
        employeeController.getEmployeeDetailsByHccId(testHccId);
        // verify
        verify(employeeService).getEmployeeDetailsByHccId(testHccId);
    }

    @Test
    void testGetEmployeeDetailsByHccId_whenHccIdIsInvalid_thenThrowsCoEException() {
        // prepare
        String testHccId = "999999";
        IEmployeeDetails iEmployeeDetails = mock(IEmployeeDetails.class);
        // when - then
        when(employeeService.getEmployeeDetailsByHccId(anyString())).thenThrow(new CoEException("1", "Error"));
        // invoke & assert
        assertThrows(CoEException.class, () -> employeeController.getEmployeeDetailsByHccId(testHccId));
        // verify
        verify(employeeService).getEmployeeDetailsByHccId(testHccId);
    }
}
