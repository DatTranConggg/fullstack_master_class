package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.constant.CommonConstant;
import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.Branch;
import com.hitachi.coe.fullstack.entity.BusinessUnit;
import com.hitachi.coe.fullstack.entity.CoeUtilization;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeUtilization;
import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.model.EmployeeUtilizationModel;
import com.hitachi.coe.fullstack.model.EmployeeUtilizationModelResponse;
import com.hitachi.coe.fullstack.model.ExcelResponseModel;
import com.hitachi.coe.fullstack.model.IEmployeeUTModel;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationDetail;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationDetailResponse;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationFree;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationModel;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import com.hitachi.coe.fullstack.model.ImportResponse;
import com.hitachi.coe.fullstack.repository.CoeUtilizationRepository;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.repository.EmployeeUtilizationRepository;
import com.hitachi.coe.fullstack.repository.ProjectRepository;
import com.hitachi.coe.fullstack.service.impl.EmployeeUtilizationServiceImpl;
import com.hitachi.coe.fullstack.transformation.EmployeeUtilizationTransformer;
import com.hitachi.coe.fullstack.util.CsvUtils;
import com.hitachi.coe.fullstack.util.DateFormatUtils;
import com.hitachi.coe.fullstack.util.ExcelUtils;
import com.hitachi.coe.fullstack.util.JsonUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class EmployeeUtilizationServiceTest {

    Employee employee;
    CoeUtilization coeUtilization;
    EmployeeUtilization employeeUtilization;

    Project project;

    IEmployeeUtilizationDetail iEmployeeUtilizationDetailOne;

    IEmployeeUtilizationDetail iEmployeeUtilizationDetailTwo;

    IEmployeeUtilizationFree iEmployeeUtilizationFreeOne;

    IEmployeeUtilizationFree iEmployeeUtilizationFreeTwo;

    IEmployeeUTModel iEmployeeUTModelOne;

    IEmployeeUTModel iEmployeeUTModelTwo;

    Date date;

    Timestamp startDate;

    Timestamp endDate;

    Integer no;

    Integer limit;

    String sortBy;

    Boolean desc;

    @MockBean
    private EmployeeUtilizationTransformer employeeUtilizationtransformer;

    @MockBean
    private EmployeeUtilizationRepository employeeUtilizationRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private CoeUtilizationRepository coeUtilizationRepository;

    @Autowired
    private EmployeeUtilizationServiceImpl employeeUtilizationService;

    @MockBean
    private CoeUtilizationService coeUtilizationService;

    @MockBean
	private ProjectRepository projectRepository;

    @BeforeEach
    public void setUp(){
        coeUtilization = new CoeUtilization();
        employee = new Employee();
        employeeUtilization = new EmployeeUtilization();
        project = new Project();
        project.setId(1);

        coeUtilization.setId(1);
        coeUtilization.setDuration("15 Aug 2023 - 30 Aug 2023");
        coeUtilization.setStartDate(DateFormatUtils.convertDateFromString("15 Aug 2023", "dd MMM uuuu"));
        coeUtilization.setEndDate(DateFormatUtils.convertDateFromString("30 Aug 2023", "dd MMM uuuu"));
        coeUtilization.setTotalUtilization(60.8);

        employee.setEmail("a.nguyen5@hitachivantara.com");
        employee.setLdap("71269780");
        employee.setHccId("125351");
        employee.setName("Nguyen A 1");
        employee.setLegalEntityHireDate(new Timestamp(System.currentTimeMillis()));
        employee.setBusinessUnit(new BusinessUnit());
        employee.setBranch(new Branch());

        employeeUtilization.setCode(UUID.randomUUID());
        employeeUtilization.setEmployee(employee);
        employeeUtilization.setCoeUtilization(coeUtilization);
        employeeUtilization.setBillableHours(150);
        employeeUtilization.setLoggedHours(100);
        employeeUtilization.setBillable(96.2);
        employeeUtilization.setPtoOracle(20);
        employeeUtilization.setAvailableHours(156);

        date = new Date(System.currentTimeMillis());
        iEmployeeUtilizationDetailOne = mock(IEmployeeUtilizationDetail.class);
        iEmployeeUtilizationDetailTwo = mock(IEmployeeUtilizationDetail.class);
        iEmployeeUtilizationFreeOne = mock(IEmployeeUtilizationFree.class);
        iEmployeeUtilizationFreeTwo = mock(IEmployeeUtilizationFree.class);
        iEmployeeUTModelOne = mock(IEmployeeUTModel.class);
        iEmployeeUTModelTwo = mock(IEmployeeUTModel.class);

        no = 0;
        limit = 10;
        sortBy = "name";
        desc = true;

        startDate = DateFormatUtils.convertTimestampFromString("2023-08-01");
        endDate = DateFormatUtils.convertTimestampFromString("2023-08-30");
    }

	@Test
	void testGetProjectByProjectCode_whenSuccess_thenReturnListProject() {
		List<Project> projects = Arrays.asList(new Project(),
				new Project());

		when(projectRepository.filterProjects(any(), any(), any(), any(), any(), any(), any(), any()))
				.thenReturn(new PageImpl<>(projects));
		Page<Project> result = projectRepository.filterProjects("177013", null, null, null, null, null, null,
				null);

		verify(projectRepository).filterProjects("177013", null, null, null, null, null, null, null);
		assertEquals(projects.size(), result.getContent().size());
	}
    @Test
    @SneakyThrows
    public void testImportEmployeeUtilizationCsv_whenValidData_thenSuccess(){
        Path filePath = Paths.get("src/test/resources/files/UT tracking_updated_Test_1_csv.csv");
        MockMultipartFile file = new MockMultipartFile("file", "UT tracking_updated_Test_1_csv.csv", "text/csv", Files.readAllBytes(filePath));
        ExcelResponseModel listOfEmployeeUT = CsvUtils.readCsv(
                file.getInputStream(),
                JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/EmployeeUtilizationReadConfig.json")),
                EmployeeUtilizationModel.class);

        when(coeUtilizationService.saveCoEUtilizationFromDuration(any(String.class), any(String.class), any(String.class), any(Double.class))).thenReturn(coeUtilization);
        when(employeeRepository.findByHccId(any(String.class))).thenReturn(employee);
        when(employeeUtilizationtransformer.toEntity(any(EmployeeUtilizationModel.class), any(Employee.class), any(CoeUtilization.class))).thenReturn(employeeUtilization);
        when(projectRepository.findByCode(any(String.class))).thenReturn(project);
        when(coeUtilizationRepository.save(any(CoeUtilization.class))).thenReturn(coeUtilization);
        when(employeeUtilizationRepository.saveAll(Collections.singletonList(employeeUtilization))).thenReturn(Collections.singletonList(employeeUtilization));

        //Verify
        ImportResponse importEmployeeUtilization = employeeUtilizationService.importEmployeeUtilization(listOfEmployeeUT, "csv", file.getInputStream(), "01 Mar 2023");
        assertNotNull(importEmployeeUtilization);
        assertEquals(1, importEmployeeUtilization.getTotalRows());
        assertEquals(1, importEmployeeUtilization.getSuccessRows());
        assertEquals(0, importEmployeeUtilization.getErrorRows());

    }

    @Test
    @SneakyThrows
    public void testImportEmployeeUtilizationExcel_whenValidData_thenSuccess(){
        Path filePath = Paths.get("src/test/resources/files/UT tracking_updated_Test_1.xlsx");
        MockMultipartFile file = new MockMultipartFile("file", "UT tracking_updated_Test_1.xlsx", "application/vnd.ms-excel", Files.readAllBytes(filePath));
        ExcelResponseModel listOfEmployeeUT = ExcelUtils.readFromExcel(
                file.getInputStream(),
                JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/EmployeeUtilizationReadConfig.json")),
                EmployeeUtilizationModel.class);

        when(coeUtilizationService.saveCoEUtilizationFromDuration(any(String.class), any(String.class), any(String.class), any(Double.class))).thenReturn(coeUtilization);
        when(employeeRepository.findByHccId(any(String.class))).thenReturn(employee);
        when(employeeUtilizationtransformer.toEntity(any(EmployeeUtilizationModel.class), any(Employee.class), any(CoeUtilization.class))).thenReturn(employeeUtilization);
        when(projectRepository.findByCode(any(String.class))).thenReturn(project);
        when(coeUtilizationRepository.save(any(CoeUtilization.class))).thenReturn(coeUtilization);
        when(employeeUtilizationRepository.saveAll(Collections.singletonList(employeeUtilization))).thenReturn(Collections.singletonList(employeeUtilization));
        //Verify
        ImportResponse importEmployeeUtilization = employeeUtilizationService.importEmployeeUtilization(listOfEmployeeUT, "excel", file.getInputStream(), "01 Mar 2023");
        assertNotNull(importEmployeeUtilization);
        assertEquals(1, importEmployeeUtilization.getTotalRows());
        assertEquals(1, importEmployeeUtilization.getSuccessRows());
        assertEquals(0, importEmployeeUtilization.getErrorRows());

    }

    @Test
    @SneakyThrows
    public void testImportEmployeeUtilizationExcel_whenMissingBillableHours_thenFail(){
        Path filePath = Paths.get("src/test/resources/files/UT tracking_updated_Test_2.xlsx");
        MockMultipartFile file = new MockMultipartFile("file", "UT tracking_updated_Test_2.xlsx", "application/vnd.ms-excel", Files.readAllBytes(filePath));
        ExcelResponseModel listOfEmployeeUT = ExcelUtils.readFromExcel(
                file.getInputStream(),
                JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/EmployeeUtilizationReadConfig.json")),
                EmployeeUtilizationModel.class);

        when(coeUtilizationService.saveCoEUtilizationFromDuration(any(String.class), any(String.class), any(String.class), any(Double.class))).thenReturn(coeUtilization);
        when(employeeRepository.findByHccId(any(String.class))).thenReturn(employee);
        when(employeeUtilizationtransformer.toEntity(any(EmployeeUtilizationModel.class), any(Employee.class), any(CoeUtilization.class))).thenReturn(employeeUtilization);
        when(projectRepository.findByCode(any(String.class))).thenReturn(project);
        when(coeUtilizationRepository.save(any(CoeUtilization.class))).thenReturn(coeUtilization);
        when(employeeUtilizationRepository.saveAll(Collections.singletonList(employeeUtilization))).thenReturn(Collections.singletonList(employeeUtilization));

        //Verify
        ImportResponse importEmployeeUtilization = employeeUtilizationService.importEmployeeUtilization(listOfEmployeeUT, "excel", file.getInputStream(), "01 Mar 2023");
        assertNotNull(importEmployeeUtilization);
        assertEquals(1, importEmployeeUtilization.getTotalRows());
        assertEquals(0, importEmployeeUtilization.getSuccessRows());
        assertEquals(1, importEmployeeUtilization.getErrorRows());

    }


    @Test
    @SneakyThrows
    public void testImportEmployeeUtilizationExcel_whenInvalidEmployee_thenFail(){
        Path filePath= Paths.get("src/test/resources/files/UT tracking_updated_Test_1.xlsx");
        MockMultipartFile file = new MockMultipartFile("file", "UT tracking_updated_Test_1.xlsx", "application/vnd.ms-excel", Files.readAllBytes(filePath));
        ExcelResponseModel listOfEmployeeUT = ExcelUtils.readFromExcel(
                file.getInputStream(),
                JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/EmployeeUtilizationReadConfig.json")),
                EmployeeUtilizationModel.class);

        when(coeUtilizationService.saveCoEUtilizationFromDuration(any(String.class), any(String.class), any(String.class), any(Double.class))).thenReturn(coeUtilization);
        when(employeeRepository.findByHccId(any(String.class))).thenReturn(null);
        when(employeeUtilizationtransformer.toEntity(any(EmployeeUtilizationModel.class), any(Employee.class), any(CoeUtilization.class))).thenReturn(employeeUtilization);
        when(projectRepository.findByCode(any(String.class))).thenReturn(project);
        when(coeUtilizationRepository.save(any(CoeUtilization.class))).thenReturn(coeUtilization);
        when(employeeUtilizationRepository.saveAll(Collections.singletonList(employeeUtilization))).thenReturn(Collections.singletonList(employeeUtilization));

        ImportResponse importEmployeeUtilization = employeeUtilizationService.importEmployeeUtilization(listOfEmployeeUT, "excel", file.getInputStream(), "01 Mar 2023");
        assertNotNull(importEmployeeUtilization);
        assertEquals(1, importEmployeeUtilization.getTotalRows());
        assertEquals(0, importEmployeeUtilization.getSuccessRows());
        assertEquals(1, importEmployeeUtilization.getErrorRows());
    }

    @Test
    @SneakyThrows
    public void testImportEmployeeUtilizationExcel_whenInvalidProject_thenFail(){
        Path filePath= Paths.get("src/test/resources/files/UT tracking_updated_Test_1.xlsx");
        MockMultipartFile file = new MockMultipartFile("file", "UT tracking_updated_Test_1.xlsx", "application/vnd.ms-excel", Files.readAllBytes(filePath));
        ExcelResponseModel listOfEmployeeUT = ExcelUtils.readFromExcel(
                file.getInputStream(),
                JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/EmployeeUtilizationReadConfig.json")),
                EmployeeUtilizationModel.class);

        when(coeUtilizationService.saveCoEUtilizationFromDuration(any(String.class), any(String.class), any(String.class), any(Double.class))).thenReturn(coeUtilization);
        when(employeeRepository.findByHccId(any(String.class))).thenReturn(employee);
        when(employeeUtilizationtransformer.toEntity(any(EmployeeUtilizationModel.class), any(Employee.class), any(CoeUtilization.class))).thenReturn(employeeUtilization);
        when(projectRepository.findByCode(any(String.class))).thenReturn(null);
        when(coeUtilizationRepository.save(any(CoeUtilization.class))).thenReturn(coeUtilization);
        when(employeeUtilizationRepository.saveAll(Collections.singletonList(employeeUtilization))).thenReturn(Collections.singletonList(employeeUtilization));

        ImportResponse importEmployeeUtilization = employeeUtilizationService.importEmployeeUtilization(listOfEmployeeUT, "excel", file.getInputStream(), "01 Mar 2023");
        assertNotNull(importEmployeeUtilization);
        assertEquals(1, importEmployeeUtilization.getTotalRows());
        assertEquals(0, importEmployeeUtilization.getSuccessRows());
        assertEquals(1, importEmployeeUtilization.getErrorRows());
    }

    @Test
    public void testGetUtilizationPieChart_onSuccess() {
        Integer branchId = 1;
        Integer coeId = 2;
        Integer coeCoreteamId = 3;
        String fromDateStr = "2023-06-15";
        String toDateStr = "2023-06-30";
        Timestamp fromDateTime = DateFormatUtils.convertTimestampFromString(fromDateStr);
        Timestamp toDateTime = DateFormatUtils.convertTimestampFromString(toDateStr);

        // Mock the repository call
        List<IPieChartModel> mockData = new ArrayList<>();

        when(employeeUtilizationRepository.getUtilizationPieChart(branchId, coeId, coeCoreteamId, fromDateTime,
                toDateTime)).thenReturn(mockData);
        // Call the service method
        List<IPieChartModel> responseEntity = employeeUtilizationService.getUtilizationPieChart(branchId, coeId,
                coeCoreteamId, fromDateStr, toDateStr);

        assertNotNull(responseEntity);
    }

    @Test
    public void testGetUtilizationPieChart_AllNull_onSuccess() {
        String fromDateStr = "2023-06-15";
        String toDateStr = "2023-06-30";
        Timestamp fromDateTime = DateFormatUtils.convertTimestampFromString(fromDateStr);
        Timestamp toDateTime = DateFormatUtils.convertTimestampFromString(toDateStr);

        // Mock the repository call
        List<IPieChartModel> mockData = new ArrayList<>();

        when(employeeUtilizationRepository.getUtilizationPieChart(null, null, null, fromDateTime,
                toDateTime)).thenReturn(mockData);
        // Call the service method
        List<IPieChartModel> responseEntity = employeeUtilizationService.getUtilizationPieChart(null, null,
                null, fromDateStr, toDateStr);

        assertNotNull(responseEntity);
    }

    @Test
    public void testGetUtilizationPieChart_BranchIdValid_onSuccess() {
        Integer branchId = 1;
        String fromDateStr = "2023-06-15";
        String toDateStr = "2023-06-30";
        Timestamp fromDateTime = DateFormatUtils.convertTimestampFromString(fromDateStr);
        Timestamp toDateTime = DateFormatUtils.convertTimestampFromString(toDateStr);

        // Mock the repository call
        List<IPieChartModel> mockData = new ArrayList<>();

        when(employeeUtilizationRepository.getUtilizationPieChart(branchId, null, null, fromDateTime,
                toDateTime)).thenReturn(mockData);
// Call the service method
        List<IPieChartModel> responseEntity = employeeUtilizationService.getUtilizationPieChart(branchId, null,
                null, fromDateStr, toDateStr);

        assertNotNull(responseEntity);
    }

    @Test
    public void testGetUtilizationPieChart_BranchIdValid_CoeIdValid_onSuccess() {
        Integer branchId = 1;
        Integer coeId = 2;
        String fromDateStr = "2023-06-15";
        String toDateStr = "2023-06-30";

        Timestamp fromDateTime = DateFormatUtils.convertTimestampFromString(fromDateStr);
        Timestamp toDateTime = DateFormatUtils.convertTimestampFromString(toDateStr);

        // Mock the repository call
        List<IPieChartModel> mockData = new ArrayList<>();

        when(employeeUtilizationRepository.getUtilizationPieChart(branchId, coeId, null, fromDateTime,
                toDateTime)).thenReturn(mockData);
        // Call the service method
        List<IPieChartModel> responseEntity = employeeUtilizationService.getUtilizationPieChart(branchId, coeId,
                null, fromDateStr, toDateStr);

        assertNotNull(responseEntity);
    }

    @Test
    public void testGetUtilizationPieChart_NullBranchId_ThrowsException() {
        Integer coeId = 2;
        Integer coeCoreteamId = 3;
        String fromDateStr = "2023-06-15";
        String toDateStr = "2023-06-30";
        Timestamp fromDateTime = DateFormatUtils.convertTimestampFromString(fromDateStr);
        Timestamp toDateTime = DateFormatUtils.convertTimestampFromString(toDateStr);

        // Mock the repository call
        List<IPieChartModel> mockData = new ArrayList<>();

        when(employeeUtilizationRepository.getUtilizationPieChart(null, coeId, coeCoreteamId, fromDateTime,
                toDateTime)).thenReturn(mockData);
        // Call the service method
        List<IPieChartModel> responseEntity = employeeUtilizationService.getUtilizationPieChart(null, coeId,
                coeCoreteamId, fromDateStr, toDateStr);

        assertNotNull(responseEntity);
    }

    @Test
    public void testGetUtilizationPieChart_NullCoeId_ThrowsException() {

        Integer branchId = 2;

        Integer coeCoreTeamId = 3;

        String fromDateStr = "2023-06-15";
        String toDateStr = "2023-06-30";

        Throwable throwable = assertThrows(CoEException.class, () -> employeeUtilizationService.getUtilizationPieChart(branchId, null, coeCoreTeamId, fromDateStr, toDateStr));

        assertEquals(CoEException.class, throwable.getClass());

    }

    @Test
    public void testGetUtilizationPieChart_NullBranchIdNullCoeId_ThrowsException() {

        Integer coeCoreTeamId = 3;
        String fromDateStr = "2023-06-15";
        String toDateStr = "2023-06-30";

        assertThrows(CoEException.class, () -> employeeUtilizationService.getUtilizationPieChart(null, null, coeCoreTeamId, fromDateStr, toDateStr));
    }

    @Test
    public void testGetUtilizationPieChart_WrongDateFormat_ThrowsException() {

        Integer coeCoreTeamId = 3;
        String fromDateStr = "2023-06-15abc";
        String toDateStr = "2023-06-30abc";

        assertThrows(CoEException.class, () -> employeeUtilizationService.getUtilizationPieChart(null, null, coeCoreTeamId, fromDateStr, toDateStr));
    }

    @Test
    public void testGetListEmployeeUtilizationWithNoUT_whenValidData_thenSuccess(){

        when(iEmployeeUtilizationFreeOne.getHccId()).thenReturn("Hcc1");
        when(iEmployeeUtilizationFreeOne.getEmployeeId()).thenReturn(1);
        when(iEmployeeUtilizationFreeOne.getEmployeeName()).thenReturn("Nguyen Van A");
        when(iEmployeeUtilizationFreeOne.getEmail()).thenReturn("a.1@example.com");
        when(iEmployeeUtilizationFreeOne.getBranchName()).thenReturn("Branch a");
        when(iEmployeeUtilizationFreeOne.getBusinessName()).thenReturn("Business unit a");
        when(iEmployeeUtilizationFreeOne.getTeamName()).thenReturn("Team a");
        when(iEmployeeUtilizationFreeOne.getDaysFree()).thenReturn(25);

        when(iEmployeeUtilizationFreeTwo.getHccId()).thenReturn("Hcc2");
        when(iEmployeeUtilizationFreeTwo.getEmployeeId()).thenReturn(2);
        when(iEmployeeUtilizationFreeTwo.getEmployeeName()).thenReturn("Nguyen Van B");
        when(iEmployeeUtilizationFreeTwo.getEmail()).thenReturn("aa.2@example.com");
        when(iEmployeeUtilizationFreeTwo.getBranchName()).thenReturn("Branch b");
        when(iEmployeeUtilizationFreeTwo.getBusinessName()).thenReturn("Business unit b");
        when(iEmployeeUtilizationFreeTwo.getTeamName()).thenReturn("Team b");
        when(iEmployeeUtilizationFreeTwo.getDaysFree()).thenReturn(27);

        List<IEmployeeUtilizationFree> iEmployeeUtilizationFreeList = Arrays.asList(iEmployeeUtilizationFreeOne,iEmployeeUtilizationFreeTwo);

        when(employeeUtilizationRepository.getEmployeesUtilizationNoUT(CommonConstant.BILLABLE_THRESHOLD)).thenReturn(iEmployeeUtilizationFreeList);

        List<IEmployeeUtilizationFree> iEmployeeUtilizationFreeListResult = employeeUtilizationService.getListEmployeeUtilizationWithNoUT(CommonConstant.BILLABLE_THRESHOLD);

        verify(employeeUtilizationRepository).getEmployeesUtilizationNoUT(CommonConstant.BILLABLE_THRESHOLD);

        assertNotNull(iEmployeeUtilizationFreeListResult);
        assertEquals(iEmployeeUtilizationFreeList.size(), iEmployeeUtilizationFreeListResult.size());
        assertEquals(iEmployeeUtilizationFreeList.get(0).getHccId(), iEmployeeUtilizationFreeListResult.get(0).getHccId());
        assertEquals(iEmployeeUtilizationFreeList.get(0).getEmployeeId(), iEmployeeUtilizationFreeListResult.get(0).getEmployeeId());
        assertEquals(iEmployeeUtilizationFreeList.get(0).getEmployeeName(), iEmployeeUtilizationFreeListResult.get(0).getEmployeeName());
        assertEquals(iEmployeeUtilizationFreeList.get(0).getEmail(), iEmployeeUtilizationFreeListResult.get(0).getEmail());
        assertEquals(iEmployeeUtilizationFreeList.get(0).getBusinessName(), iEmployeeUtilizationFreeListResult.get(0).getBusinessName());
        assertEquals(iEmployeeUtilizationFreeList.get(0).getBranchName(), iEmployeeUtilizationFreeListResult.get(0).getBranchName());
        assertEquals(iEmployeeUtilizationFreeList.get(0).getTeamName(), iEmployeeUtilizationFreeListResult.get(0).getTeamName());
        assertEquals(iEmployeeUtilizationFreeList.get(0).getDaysFree(), iEmployeeUtilizationFreeListResult.get(0).getDaysFree());

        assertEquals(iEmployeeUtilizationFreeList.get(1).getHccId(), iEmployeeUtilizationFreeListResult.get(1).getHccId());
        assertEquals(iEmployeeUtilizationFreeList.get(1).getEmployeeId(), iEmployeeUtilizationFreeListResult.get(1).getEmployeeId());
        assertEquals(iEmployeeUtilizationFreeList.get(1).getEmployeeName(), iEmployeeUtilizationFreeListResult.get(1).getEmployeeName());
        assertEquals(iEmployeeUtilizationFreeList.get(1).getEmail(), iEmployeeUtilizationFreeListResult.get(1).getEmail());
        assertEquals(iEmployeeUtilizationFreeList.get(1).getBusinessName(), iEmployeeUtilizationFreeListResult.get(1).getBusinessName());
        assertEquals(iEmployeeUtilizationFreeList.get(1).getBranchName(), iEmployeeUtilizationFreeListResult.get(1).getBranchName());
        assertEquals(iEmployeeUtilizationFreeList.get(1).getTeamName(), iEmployeeUtilizationFreeListResult.get(1).getTeamName());
        assertEquals(iEmployeeUtilizationFreeList.get(1).getDaysFree(), iEmployeeUtilizationFreeListResult.get(1).getDaysFree());

    }

    @Test
    public void testGetListEmployeeUtilizationWithNoUT_whenIsNull_thenThrowCoEException(){

        Throwable throwable = assertThrows(CoEException.class,
                () -> employeeUtilizationService.getListEmployeeUtilizationWithNoUT(null));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_BILLABLE_IS_NULL, throwable.getMessage());
    }

    @Test
    public void testGetEmployeeUtilizationDetailByEmployeeUtilizationId_whenValidData_thenSuccess(){

        when(iEmployeeUtilizationDetailOne.getHccId()).thenReturn("001");
        when(iEmployeeUtilizationDetailOne.getLdap()).thenReturn("1");
        when(iEmployeeUtilizationDetailOne.getName()).thenReturn("Nguyen Van A");
        when(iEmployeeUtilizationDetailOne.getEmail()).thenReturn("a.1@example.com");
        when(iEmployeeUtilizationDetailOne.getEmployeeUtilizationId()).thenReturn(1);
        when(iEmployeeUtilizationDetailOne.getProjectName()).thenReturn("Project X");
        when(iEmployeeUtilizationDetailOne.getStartDate()).thenReturn(date);
        when(iEmployeeUtilizationDetailOne.getEndDate()).thenReturn(date);
        when(iEmployeeUtilizationDetailOne.getBillable()).thenReturn(10.5);
        when(iEmployeeUtilizationDetailOne.getPtoOracle()).thenReturn(5);
        when(iEmployeeUtilizationDetailOne.getBillableHours()).thenReturn(40);
        when(iEmployeeUtilizationDetailOne.getLoggedHours()).thenReturn(45);
        when(iEmployeeUtilizationDetailOne.getAvailableHours()).thenReturn(0);

        when(employeeUtilizationRepository.getEmployeeUtilizationDetailById(any(Integer.class))).thenReturn(iEmployeeUtilizationDetailOne);

        IEmployeeUtilizationDetail iEmployeeUtilizationDetail = employeeUtilizationService.getEmployeeUtilizationDetailByEmployeeUtilizationId(1);

        verify(employeeUtilizationRepository).getEmployeeUtilizationDetailById(any(Integer.class));

        assertNotNull(iEmployeeUtilizationDetail);
        assertEquals(iEmployeeUtilizationDetail.getHccId(), iEmployeeUtilizationDetailOne.getHccId());
        assertEquals(iEmployeeUtilizationDetail.getLdap(), iEmployeeUtilizationDetailOne.getLdap());
        assertEquals(iEmployeeUtilizationDetail.getEmail(), iEmployeeUtilizationDetailOne.getEmail());
        assertEquals(iEmployeeUtilizationDetail.getName(), iEmployeeUtilizationDetailOne.getName());
        assertEquals(iEmployeeUtilizationDetail.getHccId(), iEmployeeUtilizationDetailOne.getHccId());
        assertEquals(iEmployeeUtilizationDetail.getStartDate(), iEmployeeUtilizationDetailOne.getStartDate());
        assertEquals(iEmployeeUtilizationDetail.getEndDate(), iEmployeeUtilizationDetailOne.getEndDate());
        assertEquals(iEmployeeUtilizationDetail.getBillable(), iEmployeeUtilizationDetailOne.getBillable());
        assertEquals(iEmployeeUtilizationDetail.getPtoOracle(), iEmployeeUtilizationDetailOne.getPtoOracle());
        assertEquals(iEmployeeUtilizationDetail.getBillableHours(), iEmployeeUtilizationDetailOne.getBillableHours());
        assertEquals(iEmployeeUtilizationDetail.getLoggedHours(), iEmployeeUtilizationDetailOne.getLoggedHours());
        assertEquals(iEmployeeUtilizationDetail.getAvailableHours(), iEmployeeUtilizationDetailOne.getAvailableHours());
        assertEquals(iEmployeeUtilizationDetail.getProjectName(), iEmployeeUtilizationDetailOne.getProjectName());

    }

    @Test
    public void testGetEmployeeUtilizationDetailByEmployeeUtilizationId_whenIsNull_thenThrowCoEException(){

        when(employeeUtilizationRepository.getEmployeeUtilizationDetailById(any(Integer.class))).thenReturn(null);

        Throwable throwable = assertThrows(CoEException.class,
                () -> employeeUtilizationService.getEmployeeUtilizationDetailByEmployeeUtilizationId(120));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_EMPLOYEE_UTILIZATION_ID_NOT_FOUND, throwable.getMessage());
    }

	@Test
	void testSearchEmployeeUtilization_whenCoeTeamIdNull_thenThrowException() {

		Throwable throwable = assertThrows(CoEException.class,
				() -> employeeUtilizationService.searchEmployeeUtilization(null, "60", null, 1, null,  no, limit, sortBy, desc));
		assertEquals(CoEException.class, throwable.getClass());
		assertEquals(ErrorConstant.MESSAGE_DATA_IS_EMPTY, throwable.getMessage());
	}

    @Test
    void testSearchEmployeeUtilization_whenEmptyContent_thenSuccess() {


        when(employeeUtilizationRepository.searchEmployeeUtilization(any(String.class), any(Integer.class), any(Integer.class), any(Integer.class))).thenReturn(Collections.emptyList());

        Page<IEmployeeUTModel> iEmployeeUTModelPageActual = employeeUtilizationService.searchEmployeeUtilization("a", "60", 1, 1, 1, no, limit, sortBy, desc);

        //Verify
        assertNotNull(iEmployeeUTModelPageActual);
        assertTrue(iEmployeeUTModelPageActual.getContent().isEmpty());

    }

    @Test
    void testSearchEmployeeUtilization_whenInvalidInputBillable_thenSuccess() {

        when(iEmployeeUTModelOne.getEmployeeUtilizationId()).thenReturn(1);
        when(iEmployeeUTModelOne.getHccId()).thenReturn("Hcc1");
        when(iEmployeeUTModelOne.getName()).thenReturn("Nguyen Van B");
        when(iEmployeeUTModelOne.getEmail()).thenReturn("a.1@example.com");
        when(iEmployeeUTModelOne.getLdap()).thenReturn("Ldap1");
        when(iEmployeeUTModelOne.getBillable()).thenReturn(58.5);
        when(iEmployeeUTModelOne.getDuration()).thenReturn("01 Aug 2023 - 30 Aug 2023");
        when(iEmployeeUTModelOne.getStartDate()).thenReturn(startDate);
        when(iEmployeeUTModelOne.getEndDate()).thenReturn(endDate);
        when(iEmployeeUTModelOne.getCreatedDate()).thenReturn(endDate);


        when(iEmployeeUTModelTwo.getEmployeeUtilizationId()).thenReturn(1);
        when(iEmployeeUTModelTwo.getHccId()).thenReturn("Hcc2");
        when(iEmployeeUTModelTwo.getName()).thenReturn("Nguyen Van A");
        when(iEmployeeUTModelTwo.getEmail()).thenReturn("a.2@example.com");
        when(iEmployeeUTModelTwo.getLdap()).thenReturn("Ldap2");
        when(iEmployeeUTModelTwo.getBillable()).thenReturn(60.5);
        when(iEmployeeUTModelTwo.getDuration()).thenReturn("01 Aug 2023 - 30 Aug 2023");
        when(iEmployeeUTModelTwo.getStartDate()).thenReturn(startDate);
        when(iEmployeeUTModelTwo.getEndDate()).thenReturn(endDate);
        when(iEmployeeUTModelTwo.getCreatedDate()).thenReturn(endDate);

        List<IEmployeeUTModel> iEmployeeUTModels = Arrays.asList(iEmployeeUTModelOne,iEmployeeUTModelTwo);

        when(employeeUtilizationRepository.searchEmployeeUtilization(any(String.class), any(Integer.class), any(Integer.class), any(Integer.class))).thenReturn(iEmployeeUTModels);

        Page<IEmployeeUTModel> iEmployeeUTModelPageActual = employeeUtilizationService.searchEmployeeUtilization("Van", "abc", 1, 1, 1, no, limit, sortBy, desc);

        //Verify
        assertNotNull(iEmployeeUTModelPageActual);
        assertEquals(iEmployeeUTModels.size(), iEmployeeUTModelPageActual.getContent().size());
        assertEquals(iEmployeeUTModels.get(0).getEmployeeUtilizationId(), iEmployeeUTModelPageActual.getContent().get(0).getEmployeeUtilizationId());
        assertEquals(iEmployeeUTModels.get(0).getHccId(), iEmployeeUTModelPageActual.getContent().get(0).getHccId());
        assertEquals(iEmployeeUTModels.get(0).getName(), iEmployeeUTModelPageActual.getContent().get(0).getName());
        assertEquals(iEmployeeUTModels.get(0).getEmail(), iEmployeeUTModelPageActual.getContent().get(0).getEmail());
        assertEquals(iEmployeeUTModels.get(0).getDuration(), iEmployeeUTModelPageActual.getContent().get(0).getDuration());
        assertEquals(iEmployeeUTModels.get(0).getStartDate(), iEmployeeUTModelPageActual.getContent().get(0).getStartDate());
        assertEquals(iEmployeeUTModels.get(0).getEndDate(), iEmployeeUTModelPageActual.getContent().get(0).getEndDate());
        assertEquals(iEmployeeUTModels.get(0).getCreatedDate(), iEmployeeUTModelPageActual.getContent().get(0).getCreatedDate());

        assertEquals(iEmployeeUTModels.get(1).getEmployeeUtilizationId(), iEmployeeUTModelPageActual.getContent().get(1).getEmployeeUtilizationId());
        assertEquals(iEmployeeUTModels.get(1).getHccId(), iEmployeeUTModelPageActual.getContent().get(1).getHccId());
        assertEquals(iEmployeeUTModels.get(1).getName(), iEmployeeUTModelPageActual.getContent().get(1).getName());
        assertEquals(iEmployeeUTModels.get(1).getEmail(), iEmployeeUTModelPageActual.getContent().get(1).getEmail());
        assertEquals(iEmployeeUTModels.get(1).getDuration(), iEmployeeUTModelPageActual.getContent().get(1).getDuration());
        assertEquals(iEmployeeUTModels.get(1).getStartDate(), iEmployeeUTModelPageActual.getContent().get(1).getStartDate());
        assertEquals(iEmployeeUTModels.get(1).getEndDate(), iEmployeeUTModelPageActual.getContent().get(1).getEndDate());
        assertEquals(iEmployeeUTModels.get(1).getCreatedDate(), iEmployeeUTModelPageActual.getContent().get(1).getCreatedDate());

    }

    @Test
    void testSearchEmployeeUtilization_whenValidContent_thenSuccess() {

        when(iEmployeeUTModelOne.getEmployeeUtilizationId()).thenReturn(1);
        when(iEmployeeUTModelOne.getHccId()).thenReturn("Hcc1");
        when(iEmployeeUTModelOne.getName()).thenReturn("Nguyen Van B");
        when(iEmployeeUTModelOne.getEmail()).thenReturn("a.1@example.com");
        when(iEmployeeUTModelOne.getLdap()).thenReturn("Ldap1");
        when(iEmployeeUTModelOne.getBillable()).thenReturn(58.5);
        when(iEmployeeUTModelOne.getDuration()).thenReturn("01 Aug 2023 - 30 Aug 2023");
        when(iEmployeeUTModelOne.getStartDate()).thenReturn(startDate);
        when(iEmployeeUTModelOne.getEndDate()).thenReturn(endDate);
        when(iEmployeeUTModelOne.getCreatedDate()).thenReturn(endDate);


        when(iEmployeeUTModelTwo.getEmployeeUtilizationId()).thenReturn(1);
        when(iEmployeeUTModelTwo.getHccId()).thenReturn("Hcc2");
        when(iEmployeeUTModelTwo.getName()).thenReturn("Nguyen Van A");
        when(iEmployeeUTModelTwo.getEmail()).thenReturn("a.2@example.com");
        when(iEmployeeUTModelTwo.getLdap()).thenReturn("Ldap2");
        when(iEmployeeUTModelTwo.getBillable()).thenReturn(60.5);
        when(iEmployeeUTModelTwo.getDuration()).thenReturn("01 Aug 2023 - 30 Aug 2023");
        when(iEmployeeUTModelTwo.getStartDate()).thenReturn(startDate);
        when(iEmployeeUTModelTwo.getEndDate()).thenReturn(endDate);
        when(iEmployeeUTModelTwo.getCreatedDate()).thenReturn(endDate);

        List<IEmployeeUTModel> iEmployeeUTModels = Arrays.asList(iEmployeeUTModelOne,iEmployeeUTModelTwo);

        when(employeeUtilizationRepository.searchEmployeeUtilization(any(String.class), any(Integer.class), any(Integer.class), any(Integer.class))).thenReturn(iEmployeeUTModels);

        Page<IEmployeeUTModel> iEmployeeUTModelPageActual = employeeUtilizationService.searchEmployeeUtilization("Van", "60", 1, 1, 1, no, limit, sortBy, desc);

        //Verify
        assertNotNull(iEmployeeUTModelPageActual);
        assertEquals(iEmployeeUTModels.size(), iEmployeeUTModelPageActual.getContent().size());
        assertEquals(iEmployeeUTModels.get(0).getEmployeeUtilizationId(), iEmployeeUTModelPageActual.getContent().get(0).getEmployeeUtilizationId());
        assertEquals(iEmployeeUTModels.get(0).getHccId(), iEmployeeUTModelPageActual.getContent().get(0).getHccId());
        assertEquals(iEmployeeUTModels.get(0).getName(), iEmployeeUTModelPageActual.getContent().get(0).getName());
        assertEquals(iEmployeeUTModels.get(0).getEmail(), iEmployeeUTModelPageActual.getContent().get(0).getEmail());
        assertEquals(iEmployeeUTModels.get(0).getDuration(), iEmployeeUTModelPageActual.getContent().get(0).getDuration());
        assertEquals(iEmployeeUTModels.get(0).getStartDate(), iEmployeeUTModelPageActual.getContent().get(0).getStartDate());
        assertEquals(iEmployeeUTModels.get(0).getEndDate(), iEmployeeUTModelPageActual.getContent().get(0).getEndDate());
        assertEquals(iEmployeeUTModels.get(0).getCreatedDate(), iEmployeeUTModelPageActual.getContent().get(0).getCreatedDate());

        assertEquals(iEmployeeUTModels.get(1).getEmployeeUtilizationId(), iEmployeeUTModelPageActual.getContent().get(1).getEmployeeUtilizationId());
        assertEquals(iEmployeeUTModels.get(1).getHccId(), iEmployeeUTModelPageActual.getContent().get(1).getHccId());
        assertEquals(iEmployeeUTModels.get(1).getName(), iEmployeeUTModelPageActual.getContent().get(1).getName());
        assertEquals(iEmployeeUTModels.get(1).getEmail(), iEmployeeUTModelPageActual.getContent().get(1).getEmail());
        assertEquals(iEmployeeUTModels.get(1).getDuration(), iEmployeeUTModelPageActual.getContent().get(1).getDuration());
        assertEquals(iEmployeeUTModels.get(1).getStartDate(), iEmployeeUTModelPageActual.getContent().get(1).getStartDate());
        assertEquals(iEmployeeUTModels.get(1).getEndDate(), iEmployeeUTModelPageActual.getContent().get(1).getEndDate());
        assertEquals(iEmployeeUTModels.get(1).getCreatedDate(), iEmployeeUTModelPageActual.getContent().get(1).getCreatedDate());
    }

	@Test
	void testGetProjectInformationByEmployeeUtilizationId_whenValidData_thenSuccess() {
        IEmployeeUtilizationDetailResponse mockEmployeeUtilization = mock(IEmployeeUtilizationDetailResponse.class);
        when(mockEmployeeUtilization.getProjectName()).thenReturn("FSCMS");
        when(mockEmployeeUtilization.getProjectManager()).thenReturn("Pm A");
        when(mockEmployeeUtilization.getStartDate()).thenReturn(startDate);
        when(mockEmployeeUtilization.getEndDate()).thenReturn(endDate);
        when(mockEmployeeUtilization.getBillable()).thenReturn(60.5);

		when(employeeUtilizationRepository.getProjectInformationByEmployeeUtilizationId(any(Integer.class)))
				.thenReturn(mockEmployeeUtilization);

        IEmployeeUtilizationDetailResponse result = employeeUtilizationService.getProjectInformationByEmployeeUtilizationId(1);
		Assertions.assertNotNull(result);
        assertEquals(mockEmployeeUtilization.getBillable(), result.getBillable());
        assertEquals(mockEmployeeUtilization.getProjectName(), result.getProjectName());
        assertEquals(mockEmployeeUtilization.getProjectManager(), result.getProjectManager());
        assertEquals(mockEmployeeUtilization.getStartDate(), result.getStartDate());
        assertEquals(mockEmployeeUtilization.getEndDate(), result.getEndDate());
	}

    @Test
    void testGetProjectInformationByEmployeeUtilizationId_whenIdNotFound_thenThrowCoEException() {

        when(employeeUtilizationRepository.getProjectInformationByEmployeeUtilizationId(any(Integer.class))).thenReturn(null);

        Throwable throwable = assertThrows(CoEException.class,
                () -> employeeUtilizationService.getProjectInformationByEmployeeUtilizationId(12));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_EMPLOYEE_UTILIZATION_ID_NOT_FOUND, throwable.getMessage());
    }

	@Test
	void testGetEmployeeUtilizationDetailByHccId_whenValidData_thenSuccess() {

        IEmployeeUtilizationModel mockEmployeeUtilization = mock(IEmployeeUtilizationModel.class);
        EmployeeUtilizationModelResponse employeeUtilizationModelResponse = new EmployeeUtilizationModelResponse();

        when(mockEmployeeUtilization.getProjectName()).thenReturn("Project X");
        when(mockEmployeeUtilization.getStartDate()).thenReturn(date);
        when(mockEmployeeUtilization.getEndDate()).thenReturn(date);
        when(mockEmployeeUtilization.getBillable()).thenReturn(10.5);
        when(mockEmployeeUtilization.getPtoOracle()).thenReturn(5);
        when(mockEmployeeUtilization.getBillableHours()).thenReturn(40);
        when(mockEmployeeUtilization.getLoggedHours()).thenReturn(45);
        when(mockEmployeeUtilization.getAvailableHours()).thenReturn(0);
        when(mockEmployeeUtilization.getDuration()).thenReturn("01 Aug 2023 - 31 Aug 2023");
        when(mockEmployeeUtilization.getLockTime()).thenReturn(date);

        employeeUtilizationModelResponse.setEmployeeUtilizationModels(List.of(mockEmployeeUtilization));
        employeeUtilizationModelResponse.setAvgPtoOracle(5.0);
        employeeUtilizationModelResponse.setAvgBillableHours(40.0);
        employeeUtilizationModelResponse.setAvgBillable(10.5);
        employeeUtilizationModelResponse.setAvgLoggedHours(45.0);
        employeeUtilizationModelResponse.setAvgAvailableHours(0.0);

        List<IEmployeeUtilizationModel> expected = List.of(mockEmployeeUtilization);
        when(employeeUtilizationRepository.getEmployeeUtilizationDetailByHccId(any(String.class))).thenReturn(expected);

        EmployeeUtilizationModelResponse result = employeeUtilizationService.getEmployeeUtilizationDetailByHccId("123456");

        Assertions.assertNotNull(result);
		assertEquals(expected.size(), result.getEmployeeUtilizationModels().size());
		assertEquals(expected.get(0).getAvailableHours(), result.getEmployeeUtilizationModels().get(0).getAvailableHours());
		assertEquals(expected.get(0).getBillable(), result.getEmployeeUtilizationModels().get(0).getBillable());
		assertEquals(expected.get(0).getBillableHours(), result.getEmployeeUtilizationModels().get(0).getBillableHours());
		assertEquals(expected.get(0).getLockTime(), result.getEmployeeUtilizationModels().get(0).getLockTime());
		assertEquals(expected.get(0).getDuration(), result.getEmployeeUtilizationModels().get(0).getDuration());
		assertEquals(expected.get(0).getLoggedHours(), result.getEmployeeUtilizationModels().get(0).getLoggedHours());
		assertEquals(expected.get(0).getProjectName(), result.getEmployeeUtilizationModels().get(0).getProjectName());
		assertEquals(expected.get(0).getStartDate(), result.getEmployeeUtilizationModels().get(0).getStartDate());
		assertEquals(expected.get(0).getEndDate(), result.getEmployeeUtilizationModels().get(0).getEndDate());
		assertEquals(employeeUtilizationModelResponse.getAvgBillable(), result.getAvgBillable());
		assertEquals(employeeUtilizationModelResponse.getAvgBillableHours(), result.getAvgBillableHours());
		assertEquals(employeeUtilizationModelResponse.getAvgPtoOracle(), result.getAvgPtoOracle());
		assertEquals(employeeUtilizationModelResponse.getAvgLoggedHours(), result.getAvgLoggedHours());
		assertEquals(employeeUtilizationModelResponse.getAvgAvailableHours(), result.getAvgAvailableHours());

	}

    @Test
    void testGetEmployeeUtilizationDetailByHccId_whenEmptyContent_thenSuccess() {

        when(employeeUtilizationRepository.getEmployeeUtilizationDetailByHccId(any(String.class))).thenReturn(Collections.emptyList());

        EmployeeUtilizationModelResponse result = employeeUtilizationService.getEmployeeUtilizationDetailByHccId("123456");

        Assertions.assertNotNull(result);

    }
}
