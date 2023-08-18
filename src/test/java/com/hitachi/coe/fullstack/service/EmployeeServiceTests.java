package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.Branch;
import com.hitachi.coe.fullstack.entity.BusinessUnit;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeLevel;
import com.hitachi.coe.fullstack.entity.Level;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.BarChartModel;
import com.hitachi.coe.fullstack.model.DataSetBarChart;
import com.hitachi.coe.fullstack.model.EmployeeImportModel;
import com.hitachi.coe.fullstack.model.EmployeeModel;
import com.hitachi.coe.fullstack.model.ExcelResponseModel;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import com.hitachi.coe.fullstack.model.IResultOfQueryBarChart;
import com.hitachi.coe.fullstack.model.ImportResponse;
import com.hitachi.coe.fullstack.repository.BranchRepository;
import com.hitachi.coe.fullstack.repository.BusinessUnitRepository;
import com.hitachi.coe.fullstack.repository.EmployeeLevelRepository;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.repository.LevelRepository;
import com.hitachi.coe.fullstack.transformation.EmployeeTransformer;
import com.hitachi.coe.fullstack.util.ExcelUtils;
import com.hitachi.coe.fullstack.util.JsonUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
public class EmployeeServiceTests {
	ExcelResponseModel listOfEmployee;
	Level level;
	Branch branchOne;
	BusinessUnit businessUnitOne;
	Branch branchTwo;
    BusinessUnit businessUnitTwo;
	EmployeeLevel employeeLevel;

	Employee employeeImport;

	@Autowired
	private EmployeeService employeeService;
	@MockBean
	private EmployeeLevelService employeeLevelService;
	@MockBean
	private EmployeeRepository employeeRepository;
	@MockBean
	private EmployeeTransformer employeeTransformer;
	@MockBean
	private LevelRepository levelRepository;
	@MockBean
	private BusinessUnitRepository businessUnitRepository;

    @MockBean
    private BranchRepository branchRepository;
	@MockBean
	private EmployeeLevelRepository employeeLevelRepository;

	@SneakyThrows
	@BeforeEach
	public void setUp(){
		Path filePath = Paths.get("src/test/resources/files/employeeAPITest.xlsx");
		byte[] content = Files.readAllBytes(filePath);
		MockMultipartFile file = new MockMultipartFile("file", "employeeAPITest.xlsx", "application/vnd.ms-excel", content);
		listOfEmployee = ExcelUtils.readFromExcel(file.getInputStream(),
				JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/EmployeeReadConfig.json")),
				EmployeeImportModel.class);
		level = new Level();
		branchOne = new Branch();
        businessUnitOne = new BusinessUnit();
		branchTwo = new Branch();
        businessUnitTwo = new BusinessUnit();
		employeeLevel = new EmployeeLevel();
		employeeImport = new Employee();

		level.setName("MCS5");
        businessUnitOne.setName("practice1");
        businessUnitOne.setCode("practice1");
		branchOne.setName("branch3");
		branchOne.setCode("B3");
        businessUnitTwo.setName("practice2");
        businessUnitTwo.setCode("practice2");
		branchTwo.setName("branch2");
		branchTwo.setCode("B2");

		employeeImport.setLdap("71269780");
		employeeImport.setHccId("125351");
		employeeImport.setEmail("a.le5@hitachivantara.com");
		employeeImport.setName("Nguyen A 1");
		employeeImport.setBranch(branchTwo);
		employeeImport.setBusinessUnit(businessUnitTwo);
		employeeImport.setLegalEntityHireDate(new Date());

		employeeLevel.setEmployee(employeeImport);
		employeeLevel.setLevel(level);
	}
	@Test
	public void testDeleteEmployeeById_OnCommonSuccess() {
		Employee employee = new Employee();
		employee.setEmail("nguyenhai");
		employee.setHccId("123456");
		employee.setLdap("78910");

		EmployeeModel employeeModel = new EmployeeModel();
		employeeModel.setEmail("nguyenhai");
		employeeModel.setHccId("123456");
		employeeModel.setLdap("78910");

		when(employeeRepository.findById(4)).thenReturn(Optional.of(employee));
		when(employeeRepository.deleteEmployeeById(anyInt())).thenReturn(employee);
		when(employeeTransformer.apply(employee)).thenReturn(employeeModel);

		EmployeeModel employeeDeleted = employeeService.deleteEmployeeById(4);
		assertEquals("nguyenhai", employeeDeleted.getEmail());
		assertEquals("123456", employeeDeleted.getHccId());
		assertEquals("78910", employeeDeleted.getLdap());

	}

	@Test
	public void testDeleteEmployeeById_OnCommonNotFound() {
		when(employeeRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));


		Throwable throwable = assertThrows(CoEException.class,
				() -> employeeService.deleteEmployeeById(400));


        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_EMPLOYEE_NULL, throwable.getMessage());
	}

	@Test
	public void testSearchEmployees_withKeyword() {
		String keyword = "search";
		String practiceName = "practice";
		String coeCoreTeamName = "core";
		String branchName = "branch";
		String fromDateStr = "2023-01-01 00:00:00";
		String toDateStr = "2023-12-31 00:00:00";
		Integer no = 0;
		Integer limit = 10;
		String sortBy = "name";
		Boolean desc = null;
		// Create a list of mock EmployeeEntities
		List<Employee> mockEntities = new ArrayList<>();
		// Add mock EmployeeEntities to the list
		// ...

		// Create a mock Page of EmployeeEntities
		Page<Employee> mockPage = new PageImpl<>(mockEntities);

		// Mock the repository method to return the mock page
		when(employeeRepository.filterEmployees(eq(keyword), eq(practiceName), eq(coeCoreTeamName), eq(branchName),1,
				any(Timestamp.class), any(Timestamp.class), any(PageRequest.class))).thenReturn(mockPage);

		// Invoke the method being tested
		Page<EmployeeModel> result = employeeService.searchEmployees(keyword, practiceName, coeCoreTeamName, branchName,1,
				fromDateStr, toDateStr, no, limit, sortBy, desc);
		// verify
		assertNotNull(result);

	}

	@Test
	public void testSearchEmployees_withKeyword_NullToDate() {
		// setup
		String keyword = "nien";
		String practiceName = "";
		String coeCoreTeamName = "";
		String branchName = "";
		String fromDateStr = "2023-05-13 00:00:00";
		String toDateStr = null;
		Integer no = 0;
		Integer limit = 10;
		String sortBy = "name";
		Sort sort = Sort.by(sortBy);
		Boolean desc = null;

		LocalDateTime fromDateTime = LocalDateTime.parse(fromDateStr,
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		Timestamp fromDate = Timestamp.valueOf(fromDateTime);


		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee());
		Page<Employee> page = new PageImpl<>(employees);

		when(employeeRepository.filterEmployees(keyword, practiceName, coeCoreTeamName, branchName, 1,fromDate, null,
				PageRequest.of(no, limit, sort))).thenReturn(page);
		when(employeeTransformer.apply(Mockito.isA(Employee.class))).thenReturn(new EmployeeModel());

		// execute
		Page<EmployeeModel> result = employeeService.searchEmployees(keyword, practiceName, coeCoreTeamName, branchName,1,
				fromDateStr, toDateStr, no, limit, sortBy, desc);

		// verify
		assertNotNull(result);
		assertEquals(1, result.getContent().size());

	}

	@Test
	public void testSearchEmployees_withKeyword_NullFromDate() {
		// setup
		String keyword = "nien";
		String practiceName = "";
		String coeCoreTeamName = "";
		String branchName = "";
		String toDateStr = "2023-05-14 00:00:00";
		Integer no = 0;
		Integer limit = 10;
		String sortBy = "name";
		Sort sort = Sort.by(sortBy);
		Boolean desc = null;

		LocalDateTime toDateTime = LocalDateTime.parse(toDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		Timestamp toDate = Timestamp.valueOf(toDateTime);

		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee());
		Page<Employee> page = new PageImpl<>(employees);

		when(employeeRepository.filterEmployees(keyword, practiceName, coeCoreTeamName, branchName,1, null, toDate,
				PageRequest.of(no, limit, sort))).thenReturn(page);
		when(employeeTransformer.apply(Mockito.isA(Employee.class))).thenReturn(new EmployeeModel());

		// execute
		Page<EmployeeModel> result = employeeService.searchEmployees(keyword, practiceName, coeCoreTeamName, branchName, 1,null,
				toDateStr, no, limit, sortBy, desc);

		// verify
		assertNotNull(result);
		assertEquals(1, result.getContent().size());

	}

	@Test
	public void testSearchEmployees_InvalidTimeStampException() {
		// setup conditions
		String keyword = "John";
		String practiceName = "Practice";
		String coeCoreTeamName = "Core Team";
		String branchName = "Branch";
		String fromDateStr = "2023-05-16 00:00:00";
		String toDateStr = "Invalid Date"; // Invalid date format
		Integer no = 0;
		Integer limit = 10;
		String sortBy = "name";
		Boolean desc = false;
		// Assert that InvalidTimeStampException is thrown
		assertThrows(CoEException.class, () -> {
			employeeService.searchEmployees(keyword, practiceName, coeCoreTeamName, branchName, 1,fromDateStr, toDateStr,
					no, limit, sortBy, desc);
		});

		// Verify that repository and transformer methods are not called
		verifyNoInteractions(employeeRepository);
		verifyNoInteractions(employeeTransformer);
	}
	@Test
	@SneakyThrows
	public void testImportEmployeeExcel_whenValidData_thenSuccess() {

		when(levelRepository.findAll()).thenReturn(List.of(level));
		when(branchRepository.findAll()).thenReturn(List.of(branchOne));
		when(businessUnitRepository.findAll()).thenReturn(List.of(businessUnitOne));
		when(employeeRepository.findByHccIdAndLdap("125351", "71269780")).thenReturn(employeeImport);
		when(employeeRepository.save(any(Employee.class))).thenReturn(employeeImport);
		doNothing().when(employeeLevelService).saveEmployeeLevel(any(Employee.class), any(Level.class));
		ImportResponse importEmployee = employeeService.importUpdateEmployee(listOfEmployee);

		//Verify
		assertNotNull(importEmployee);
		assertEquals(1, importEmployee.getTotalRows());
		assertEquals(1, importEmployee.getSuccessRows());
		assertEquals(0, importEmployee.getErrorRows());

	}
	@Test
	@SneakyThrows
	public void testImportEmployeeExcel_whenInvalidBranch_thenFail() {

		when(levelRepository.findAll()).thenReturn(List.of(level));
		when(branchRepository.findAll()).thenReturn(Collections.emptyList());
		when(businessUnitRepository.findAll()).thenReturn(List.of(businessUnitOne));
		when(employeeRepository.findByHccIdAndLdap("125351", "71269780")).thenReturn(employeeImport);
		when(employeeRepository.save(any(Employee.class))).thenReturn(employeeImport);
		doNothing().when(employeeLevelService).saveEmployeeLevel(any(Employee.class), any(Level.class));
		ImportResponse importEmployee = employeeService.importUpdateEmployee(listOfEmployee);

		//Verify
		assertNotNull(importEmployee);
		assertEquals(1, importEmployee.getTotalRows());
		assertEquals(0, importEmployee.getSuccessRows());
		assertEquals(1, importEmployee.getErrorRows());

	}
	@Test
	@SneakyThrows
	public void testImportEmployeeExcel_whenInvalidPractice_thenFail() {

		when(levelRepository.findAll()).thenReturn(List.of(level));
		when(branchRepository.findAll()).thenReturn(List.of(branchOne));
		when(businessUnitRepository.findAll()).thenReturn(Collections.emptyList());
		when(employeeRepository.findByHccIdAndLdap("125351", "71269780")).thenReturn(employeeImport);
		when(employeeRepository.save(any(Employee.class))).thenReturn(employeeImport);
		doNothing().when(employeeLevelService).saveEmployeeLevel(any(Employee.class), any(Level.class));
		ImportResponse importEmployee = employeeService.importUpdateEmployee(listOfEmployee);

		//Verify
		assertNotNull(importEmployee);
		assertEquals(1, importEmployee.getTotalRows());
		assertEquals(0, importEmployee.getSuccessRows());
		assertEquals(1, importEmployee.getErrorRows());

	}
	@Test
	@SneakyThrows
	public void testImportEmployeeExcel_whenInvalidLevel_thenFail() {

		when(levelRepository.findAll()).thenReturn(Collections.emptyList());
		when(branchRepository.findAll()).thenReturn(List.of(branchOne));
		when(businessUnitRepository.findAll()).thenReturn(List.of(businessUnitOne));
		when(employeeRepository.findByHccIdAndLdap("125351", "71269780")).thenReturn(employeeImport);
		when(employeeRepository.save(any(Employee.class))).thenReturn(employeeImport);
		doNothing().when(employeeLevelService).saveEmployeeLevel(any(Employee.class), any(Level.class));
		ImportResponse importEmployee = employeeService.importUpdateEmployee(listOfEmployee);

		//Verify
		assertNotNull(importEmployee);
		assertEquals(1, importEmployee.getTotalRows());
		assertEquals(0, importEmployee.getSuccessRows());
		assertEquals(1, importEmployee.getErrorRows());

	}
	@Test
	@SneakyThrows
	public void testImportEmployeeExcel_whenInvalidEmployee_thenFail() {

		when(levelRepository.findAll()).thenReturn(List.of(level));
		when(branchRepository.findAll()).thenReturn(List.of(branchOne));
		when(businessUnitRepository.findAll()).thenReturn(List.of(businessUnitOne));
		when(employeeRepository.findByHccIdAndLdap("125351", "71269780")).thenReturn(null);
		when(employeeRepository.save(any(Employee.class))).thenReturn(employeeImport);
		doNothing().when(employeeLevelService).saveEmployeeLevel(any(Employee.class), any(Level.class));
		ImportResponse importEmployee = employeeService.importUpdateEmployee(listOfEmployee);

		//Verify
		assertNotNull(importEmployee);
		assertEquals(1, importEmployee.getTotalRows());
		assertEquals(0, importEmployee.getSuccessRows());
		assertEquals(1, importEmployee.getErrorRows());

	}

	@Test
	public void testGetQuantityOfEachSkillForBarChart_ValidIds_Success() {
		// Arrange
		Integer branchId = 1;
		Integer groupId = 2;
		Integer teamId = 3;
//		String SkillIds = null;
		List<Integer> SkillIds = Arrays.asList(1, 2, 3);
		// Act
		BarChartModel result = employeeService.getQuantityOfEachSkillForBarChart(branchId, groupId, teamId, SkillIds);

		// Assert
		assertNotNull(result);
	}

	@Test
	public void testGetQuantityOfEachSkillForBarChart_WhenSkillIdsNull_ThenThrowException() {
		// Arrange
		Integer branchId = 1;
		Integer groupId = 1;
		Integer teamId = 3;
		List<Integer> SkillIds = null;

		// Call the service method and verify the exception
		Throwable throwable = assertThrows(CoEException.class, () -> {
			employeeService.getQuantityOfEachSkillForBarChart(branchId, groupId, teamId, SkillIds);
		});

		assertEquals(CoEException.class, throwable.getClass());
	}

	@Test
	public void testGetQuantityOfEachSkillForBarChart_WhenNullGroupId_ThrowsException() {
		Integer branchId = 1;
		Integer groupId = null;
		Integer teamId = 3;
		List<Integer> SkillIds = Arrays.asList(1, 2, 3);
		// Call the service method and verify the exception
		Throwable throwable = assertThrows(CoEException.class, () -> {
			employeeService.getQuantityOfEachSkillForBarChart(branchId, groupId, teamId, SkillIds);
		});
		assertEquals(CoEException.class, throwable.getClass());
	}

	@Test
	public void testGetQuantityOfEachSkillForBarChart_WhenNullBranchId_GroupId_TeamId_ThenSuccess() {
		Integer branchId = null;
		Integer groupId = null;
		Integer teamId = null;
		List<Integer> SkillIds = Arrays.asList(1, 2, 3);
		// Call the service method and verify the exception
		assertNotNull(employeeService.getQuantityOfEachSkillForBarChart(branchId, groupId, teamId, SkillIds));

	}

	@Test
	public void testGetQuantityOfEachSkillForBarChart_ThenSuccess() {
		// Mock data
		Integer branchId = 1;
		Integer groupId = 2;
		Integer teamId = 3;
		List<Integer> SkillIds = Arrays.asList(1, 2, 3);
		String listId = "1,2,3";
		List<IResultOfQueryBarChart> results = Arrays.asList(createResultOfQueryBarChart("Label1", "Skill1", 5L),
				createResultOfQueryBarChart("Label1", "Skill2", 8L),
				createResultOfQueryBarChart("Label2", "Skill1", 3L));

		// Mock repository method
		when(employeeRepository.getQuantityOfEachSkillForBarChart(branchId, groupId, teamId, listId))
				.thenReturn(results);

		// Expected output
		BarChartModel expectedBarChartModel = new BarChartModel();
		List<String> expectedLabelSkills = Arrays.asList("Skill1", "Skill2");
		List<DataSetBarChart> expectedDatasets = Arrays.asList(createDataSetBarChart("Label1", Arrays.asList(5L, 8L)),
				createDataSetBarChart("Label2", Arrays.asList(3L, 0L)));
		expectedBarChartModel.setLabels(expectedLabelSkills);
		expectedBarChartModel.setDatasets(expectedDatasets);

		employeeService.getQuantityOfEachSkillForBarChart(branchId, groupId, teamId, SkillIds);

		// Verify repository method was called
		verify(employeeRepository).getQuantityOfEachSkillForBarChart(branchId, groupId, teamId, listId);

		// Assert the expected and actual results
	}

	private IResultOfQueryBarChart createResultOfQueryBarChart(String label, String fieldName, Long total) {
		return new IResultOfQueryBarChart() {
			@Override
			public String getLabel() {
				return label;
			}

			@Override
			public String getFieldName() {
				return fieldName;
			}

			@Override
			public Long getTotal() {
				return total;
			}
		};
	}

	private DataSetBarChart createDataSetBarChart(String label, List<Long> data) {
		DataSetBarChart dataSetBarChart = new DataSetBarChart();
		dataSetBarChart.setLabel(label);
		dataSetBarChart.setData(data);
		return dataSetBarChart;
	}


	@Test
	public void testgetLevelPieChart_WhenAllNull_Success() {
		Integer branchId = null;
		Integer coeCoreTeamId = null;
		Integer coeId = null;

		List<IPieChartModel> mockData = new ArrayList<IPieChartModel>();

		when(levelRepository.piechartlevel(branchId, coeId, coeCoreTeamId))
				.thenReturn(mockData);

		List<IPieChartModel> responseEntity = levelRepository.piechartlevel(branchId, coeId,
				coeCoreTeamId);

		assertNotNull(responseEntity);
	}

	@Test
	public void testgetLevelPieChart_WhenNullCoeId_ThenThrowCoEException() {
		Integer branchId = 1;
		Integer coeCoreTeamId = 2;
		Integer coeId = null;

		List<IPieChartModel> mockData = new ArrayList<IPieChartModel>();

		when(levelRepository.piechartlevel(branchId, coeId, coeCoreTeamId))
				.thenReturn(mockData);

		List<IPieChartModel> responseEntity = levelRepository.piechartlevel(branchId, coeId,
				coeCoreTeamId);

		assertNotNull(responseEntity);
	}

	@Test
	public void testgetLevelPieChart_WhenFullParams_ThenSuccess() {
		Integer branchId = 1;
		Integer coeCoreTeamId = 2;
		Integer coeId = 3;

		List<IPieChartModel> mockData = new ArrayList<IPieChartModel>();

		when(levelRepository.piechartlevel(branchId, coeId, coeCoreTeamId))
				.thenReturn(mockData);

		List<IPieChartModel> responseEntity = levelRepository.piechartlevel(branchId, coeId,
				coeCoreTeamId);

		assertNotNull(responseEntity);
	}

	@Test
	public void testgetLevelPieChart_WhenNullCoeCoreTeamIdAndcoeId_ThenSuccess() {
		Integer branchId = 1;
		Integer coeCoreTeamId = null;
		Integer coeId = null;

		List<IPieChartModel> mockData = new ArrayList<IPieChartModel>();

		when(levelRepository.piechartlevel(branchId, coeId, coeCoreTeamId))
				.thenReturn(mockData);

		List<IPieChartModel> responseEntity = levelRepository.piechartlevel(branchId, coeId,
				coeCoreTeamId);

		assertNotNull(responseEntity);
	}

	@Test
	public void testGetLevelPieChart_EmptyData() {
		List<IPieChartModel> pieChartModels = new ArrayList<IPieChartModel>();
		Mockito.when(levelRepository.piechartlevel(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(pieChartModels);

		assertThrows(InvalidDataException.class, () -> {
			employeeService.getLevelPieChart(1, 2, 3);
		});
	}
}
