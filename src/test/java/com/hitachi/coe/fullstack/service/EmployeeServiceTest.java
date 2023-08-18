package com.hitachi.coe.fullstack.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.BarChartModel;
import com.hitachi.coe.fullstack.model.DataSetBarChart;
import com.hitachi.coe.fullstack.model.EmployeeModel;
import com.hitachi.coe.fullstack.model.IBarChartDepartmentModel;
import com.hitachi.coe.fullstack.model.IEmployeeDetails;
import com.hitachi.coe.fullstack.model.IResultOfQueryBarChart;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.service.impl.EmployeeServiceImpl;
import com.hitachi.coe.fullstack.transformation.BranchModelTransformer;
import com.hitachi.coe.fullstack.transformation.CoeCoreTeamModelTransformer;
import com.hitachi.coe.fullstack.transformation.EmployeeModelTransformer;
import com.hitachi.coe.fullstack.transformation.EmployeeTransformer;
import com.hitachi.coe.fullstack.transformation.PracticeModelTransformer;

import lombok.SneakyThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-data-test.properties")
public class EmployeeServiceTest {
	@InjectMocks
	private EmployeeServiceImpl employeeService;
	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private EmployeeModel employeeModel;

	@Mock
	private EmployeeTransformer employeeTransformer;

	@Mock
	private EmployeeModelTransformer employeeModelTransformer;

	@Mock
	private PracticeModelTransformer practiceModelTransformer;

	@Mock
	private CoeCoreTeamModelTransformer coeCoreTeamModelTransformer;

	@Mock
	private BranchModelTransformer branchModelTransformer;

	public void testGetEmployeeById_ExistingEmployee() {
		// Arrange
		Integer employeeId = 1;
		Employee employee = new Employee();
		EmployeeModel expectedEmployeeModel = new EmployeeModel();
		when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
		when(employeeTransformer.apply(employee)).thenReturn(expectedEmployeeModel);
		EmployeeModel result = employeeService.getEmployeeById(employeeId);

		assertEquals(expectedEmployeeModel, result);
	}

	@Test
	@SneakyThrows(InvalidDataException.class)
	public void testGetEmployeeById_NonExistingEmployee() {
		Integer employeeId = 1;
		when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

		assertThrows(InvalidDataException.class, () -> employeeService.getEmployeeById(employeeId));
	}

	
//	TODO
//	mvn test have error: NullPointer
//	@Test
//	void createEmployee_Success() {
//		EmployeeModel em = new EmployeeModel();
//		em.setId(1);
//		em.setCreatedBy("ThoVo");
//		em.setName("DatCongNguyen");
//
//		Employee e = new Employee();
//		e.setId(1);
//		e.setCreatedBy("ThoVo");
//		e.setName("DatCongNguyen");
//
//		when(employeeModelTransformer.apply(em)).thenReturn(e);
//		when(employeeRepository.save(e)).thenReturn(e);
//
//		Integer actual = employeeService.add(em);
//		assertEquals(1, actual);
//	}

//    TODO
//    mvn test have Failures: expected: <com.hitachi.coe.fullstack.exceptions.InvalidDataException> 
//	  but was: <java.lang.NullPointer Exception>
//    @Test
//    void createEmployee_UnSuccess() {
//        EmployeeModel em = new EmployeeModel();
//
//        Throwable throwable = assertThrows(Exception.class, () -> employeeService.add(em));
//
//        assertEquals(InvalidDataException.class, throwable.getClass());
//        assertEquals("employee.null", throwable.getMessage());
//    }
	
//	TODO
//	@Test
//	void updateEmployee() {
//		int employeeId = 1;
//		EmployeeModel employeeModel = new EmployeeModel();
//		employeeModel.setId(employeeId);
//		employeeModel.setEmail("huu.vo@hitachivantar.com");
//		employeeModel.setHccId("COE01");
//		employeeModel.setLdap("HV01");
//		employeeModel.setLegalEntityHireDate(new Date("22/03/2023"));
//		employeeModel.setName("Huu Vo");
//
//		Employee existEmployee = new Employee();
//		existEmployee.setId(employeeId);
//		existEmployee.setEmail("huu.vo@hitachivantar.com");
//		existEmployee.setHccId("COE01");
//		existEmployee.setLdap("HV01");
//		existEmployee.setLegalEntityHireDate(new Date("22/03/2023"));
//		existEmployee.setName("Huu Vo");
//
//		when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existEmployee));
//		when(employeeRepository.save(any(Employee.class))).thenReturn(existEmployee);
//
//		Integer actual = employeeService.updateEmployee(employeeModel);
//
//		assertEquals(employeeId, actual);
//
//	}

	@Test
	void updateEmployee_fail_not_found_employee() {
		int employeeId = 1;
		EmployeeModel employeeModel = new EmployeeModel();
		employeeModel.setId(employeeId);

		when(employeeRepository.findById(employeeModel.getId())).thenReturn(Optional.empty());

		assertThrows(InvalidDataException.class, () -> employeeService.updateEmployee(employeeModel));
	}

	@Test
	public void getQuantityEmployeeOfLevel_ValidIds_Success() {
		// Arrange
		Integer branchId = 1;
		Integer coeCoreTeamId = 1;
		Integer coe_id = 1;

		// Act
		List<IBarChartDepartmentModel> result = employeeService.getQuantityEmployeeOfLevel(branchId, coeCoreTeamId,
				coe_id);

		// Assert
		assertNotNull(result);
	}

	@Test
	public void getQuantityEmployeeOfLevel_NullBranchId_coeCoreTeamId_coe_id() {
		Integer branchId = null;
		Integer coeCoreTeamId = null;
		Integer coe_id = null;

		// Call the service method and verify the exception
		assertNotNull(employeeService.getQuantityEmployeeOfLevel(branchId, coeCoreTeamId, coe_id));

	}

	@Test
	void testGetQuantityOfEachLevelForBarChart_whenValidIds_Success() {
		// Arrange
		Integer branchId = 1;
		Integer groupId = 2;
		Integer teamId = 3;

		// Act
		BarChartModel result = employeeService.getQuantityOfLevelForBarChart(branchId, groupId, teamId);

		// Assert
		assertNotNull(result);
	}

	@Test
	void testGetQuantityOfEachLevelForBarChart_whenNullGroupId_ThrowsException() {
		Integer branchId = 1;
		Integer groupId = null;
		Integer teamId = 3;

		// Call the service method and verify the exception
		Throwable throwable = assertThrows(CoEException.class, () -> {
			employeeService.getQuantityOfLevelForBarChart(branchId, groupId, teamId);
		});
		assertEquals(CoEException.class, throwable.getClass());
	}

	@Test
	void testGetQuantityOfEachLevelForBarChart_whenAllNull_ThenSuccess() {
		Integer branchId = null;
		Integer groupId = null;
		Integer teamId = null;

		// Call the service method and verify the exception
		assertNotNull(employeeService.getQuantityOfLevelForBarChart(branchId, groupId, teamId));

	}

	@Test
	void testGetQuantityOfEachLevelForBarChart() {
		// Mock data
		Integer branchId = 1;
		Integer groupId = 2;
		Integer teamId = 3;

		List<IResultOfQueryBarChart> results = Arrays.asList(createResultOfQueryBarChart("Label1", "Level1", 5L),
				createResultOfQueryBarChart("Label1", "Level2", 8L),
				createResultOfQueryBarChart("Label2", "Level1", 3L));

		// Mock repository method
		when(employeeRepository.getQuantityOfLevelForBarChart(branchId, groupId, teamId)).thenReturn(results);

		// Expected output
		BarChartModel expectedBarChartModel = new BarChartModel();
		List<String> expectedLabelLevels = Arrays.asList("Level1", "Level2");
		List<DataSetBarChart> expectedDatasets = Arrays.asList(createDataSetBarChart("Label1", Arrays.asList(5L, 8L)),
				createDataSetBarChart("Label2", Arrays.asList(3L, 0L)));
		expectedBarChartModel.setLabels(expectedLabelLevels);
		expectedBarChartModel.setDatasets(expectedDatasets);

		employeeService.getQuantityOfLevelForBarChart(branchId, groupId, teamId);

		// Verify repository method was called
		verify(employeeRepository).getQuantityOfLevelForBarChart(branchId, groupId, teamId);

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
    void testGetEmployeeDetailsByHccId_whenHccIdIsNull_thenThrowCoEException() {
        // prepare
        String testHccId = null;
        // invoke & assert
        assertThrows(CoEException.class, () -> employeeService.getEmployeeDetailsByHccId(testHccId));
    }

    @Test
    void testGetEmployeeDetailsByHccId_whenHccIdIsNotNullAndInvalid_thenThrowCoEException() {
        // prepare
        String testHccId = "invalid_hccId_because_this_hccId_is_not_exist";
        // invoke & assert
        assertThrows(CoEException.class, () -> employeeService.getEmployeeDetailsByHccId(testHccId));
    }

    @Test
    void testGetEmployeeDetailsByHccId_whenHccIdIsValid_thenReturnIEmployeeDetails() {
        // prepare
        String testHccId = "test_hccId";
        IEmployeeDetails testIEmployeeDetails = mock(IEmployeeDetails.class);
        Employee testEmployee = new Employee();
        testEmployee.setHccId("9999");
        testEmployee.setName("test_employee");
        testEmployee.setEmail("test_employee@gmail.com");
        // when - then
        when(employeeRepository.findByHccId(anyString())).thenReturn(testEmployee);
        when(employeeService.getEmployeeDetailsByHccId(testHccId)).thenReturn(testIEmployeeDetails);
        // invoke
        employeeRepository.findByHccId(testHccId);
        // assert
        assertNotNull(employeeService.getEmployeeDetailsByHccId(testHccId));
    }
}
