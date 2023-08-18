package com.hitachi.coe.fullstack.controller;


import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.model.EmployeeUtilizationModelResponse;
import com.hitachi.coe.fullstack.model.IEmployeeUTModel;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationDetail;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationDetailResponse;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationFree;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationModel;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.EmployeeUtilizationService;
import com.hitachi.coe.fullstack.util.DateFormatUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
class EmployeeUtilizationControllerTest {

	@Autowired
	private EmployeeUtilizationController employeeUtilizationController;

	@MockBean
	private EmployeeUtilizationService employeeUtilizationServiceMock;

	@Test
	void testGetUTPieChart_Success() {
		List<IPieChartModel> expectedPieChartModel = new ArrayList<IPieChartModel>();

		when(employeeUtilizationServiceMock.getUtilizationPieChart(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.anyString(), Mockito.anyString())).thenReturn(expectedPieChartModel);

		ResponseEntity<List<IPieChartModel>> result = employeeUtilizationController.getUtilizationPieChart(1, 2, 3,
				"2022-06-01", "2022-06-30");

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(expectedPieChartModel, result.getBody());
	}

	@Test
	void testGetUTPieChart_Exception() {
		when(employeeUtilizationServiceMock.getUtilizationPieChart(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.anyString(), Mockito.anyString()))
				.thenThrow(new CoEException(ErrorConstant.CODE_DATA_IS_EMPTY, ErrorConstant.MESSAGE_DATA_IS_EMPTY));

		CoEException exception = assertThrows(CoEException.class, () -> {
			employeeUtilizationController.getUtilizationPieChart(1, 2, 3, "2022-06-01", "2022-06-30");
		});
		assertEquals(CoEException.class, exception.getClass());
	}

	@Test
	void testGetEmployeesNoUtilization_whenValidData_thenSuccess() {

		List<IEmployeeUtilizationFree> iEmployeeUtilizationFreeList = new ArrayList<>();

		when(employeeUtilizationServiceMock.getListEmployeeUtilizationWithNoUT(Mockito.anyDouble())).thenReturn(iEmployeeUtilizationFreeList);

		BaseResponse<List<IEmployeeUtilizationFree>> result = employeeUtilizationController.getEmployeesNoUtilization();

		assertNotNull(result);
		assertEquals(HttpStatus.OK.value(), result.getStatus());
		assertNull(result.getMessage());
		assertEquals(iEmployeeUtilizationFreeList, result.getData());
	}

	@Test
	void testGetEmployeesNoUtilization_whenIsNull_thenThrowCoEException() {

		when(employeeUtilizationServiceMock.getListEmployeeUtilizationWithNoUT(Mockito.anyDouble()))
				.thenThrow(new CoEException(ErrorConstant.CODE_BILLABLE_IS_NULL, ErrorConstant.MESSAGE_BILLABLE_IS_NULL));

		CoEException exception = assertThrows(CoEException.class, () -> {
			employeeUtilizationController.getEmployeesNoUtilization();
		});
		assertEquals(CoEException.class, exception.getClass());
		assertEquals(ErrorConstant.MESSAGE_BILLABLE_IS_NULL, exception.getMessage());
	}

	@Test
	void testGetEmployeeUtilizationDetailByEmployeeUtilizationId_whenValidData_thenSuccess() {

        Timestamp startDate = DateFormatUtils.convertTimestampFromString("2023-08-01");
        Timestamp endDate = DateFormatUtils.convertTimestampFromString("2023-08-30");

        IEmployeeUtilizationDetail iEmployeeUtilizationDetail = mock(IEmployeeUtilizationDetail.class);
        when(iEmployeeUtilizationDetail.getHccId()).thenReturn("001");
        when(iEmployeeUtilizationDetail.getLdap()).thenReturn("1");
        when(iEmployeeUtilizationDetail.getName()).thenReturn("Nguyen Van A");
        when(iEmployeeUtilizationDetail.getEmail()).thenReturn("a.1@example.com");
        when(iEmployeeUtilizationDetail.getEmployeeUtilizationId()).thenReturn(1);
        when(iEmployeeUtilizationDetail.getProjectName()).thenReturn("Project X");
        when(iEmployeeUtilizationDetail.getStartDate()).thenReturn(startDate);
        when(iEmployeeUtilizationDetail.getEndDate()).thenReturn(endDate);
        when(iEmployeeUtilizationDetail.getBillable()).thenReturn(10.5);
        when(iEmployeeUtilizationDetail.getPtoOracle()).thenReturn(5);
        when(iEmployeeUtilizationDetail.getBillableHours()).thenReturn(40);
        when(iEmployeeUtilizationDetail.getLoggedHours()).thenReturn(45);
        when(iEmployeeUtilizationDetail.getAvailableHours()).thenReturn(0);

		when(employeeUtilizationServiceMock.getEmployeeUtilizationDetailByEmployeeUtilizationId(Mockito.anyInt())).thenReturn(iEmployeeUtilizationDetail);

		BaseResponse<IEmployeeUtilizationDetail> result = employeeUtilizationController.getEmployeeUtilizationDetailByEmployeeUtilizationId(1);

		assertNotNull(result);
		assertEquals(HttpStatus.OK.value(), result.getStatus());
		assertNull(result.getMessage());
		assertEquals(iEmployeeUtilizationDetail, result.getData());
	}

    @Test
    void testGetProjectInformationByEmployeeUtilizationId_whenValidData_thenSuccess() {

        Timestamp startDate = DateFormatUtils.convertTimestampFromString("2023-08-01");
        Timestamp endDate = DateFormatUtils.convertTimestampFromString("2023-08-30");

        IEmployeeUtilizationDetailResponse mockEmployeeUtilization = mock(IEmployeeUtilizationDetailResponse.class);
        when(mockEmployeeUtilization.getProjectName()).thenReturn("FSCMS");
        when(mockEmployeeUtilization.getProjectManager()).thenReturn("Pm A");
        when(mockEmployeeUtilization.getStartDate()).thenReturn(startDate);
        when(mockEmployeeUtilization.getEndDate()).thenReturn(endDate);
        when(mockEmployeeUtilization.getBillable()).thenReturn(60.5);

        when(employeeUtilizationServiceMock.getProjectInformationByEmployeeUtilizationId(Mockito.anyInt())).thenReturn(mockEmployeeUtilization);

        BaseResponse<IEmployeeUtilizationDetailResponse> result = employeeUtilizationController.getProjectInformationByEmployeeUtilizationId(1);

        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertNull(result.getMessage());
        assertEquals(mockEmployeeUtilization, result.getData());
    }

    @Test
    void testGetEmployeeUtilizationDetailByHccId_whenValidData_thenSuccess() {

        Timestamp startDate = DateFormatUtils.convertTimestampFromString("2023-08-01");
        Timestamp endDate = DateFormatUtils.convertTimestampFromString("2023-08-30");

        IEmployeeUtilizationModel mockEmployeeUtilization = mock(IEmployeeUtilizationModel.class);
		EmployeeUtilizationModelResponse employeeUtilizationModelResponse = new EmployeeUtilizationModelResponse();
        when(mockEmployeeUtilization.getProjectName()).thenReturn("Project X");
        when(mockEmployeeUtilization.getStartDate()).thenReturn(startDate);
        when(mockEmployeeUtilization.getEndDate()).thenReturn(endDate);
        when(mockEmployeeUtilization.getBillable()).thenReturn(10.5);
        when(mockEmployeeUtilization.getPtoOracle()).thenReturn(5);
        when(mockEmployeeUtilization.getBillableHours()).thenReturn(40);
        when(mockEmployeeUtilization.getLoggedHours()).thenReturn(45);
        when(mockEmployeeUtilization.getAvailableHours()).thenReturn(0);
        when(mockEmployeeUtilization.getDuration()).thenReturn("01 Aug 2023 - 31 Aug 2023");
        when(mockEmployeeUtilization.getLockTime()).thenReturn(endDate);

		employeeUtilizationModelResponse.setEmployeeUtilizationModels(List.of(mockEmployeeUtilization));
		employeeUtilizationModelResponse.setAvgPtoOracle(5.0);
		employeeUtilizationModelResponse.setAvgBillableHours(40.0);
		employeeUtilizationModelResponse.setAvgBillable(10.5);
		employeeUtilizationModelResponse.setAvgLoggedHours(45.0);
		employeeUtilizationModelResponse.setAvgAvailableHours(0.0);

        when(employeeUtilizationServiceMock.getEmployeeUtilizationDetailByHccId(Mockito.anyString())).thenReturn(employeeUtilizationModelResponse);

        BaseResponse<EmployeeUtilizationModelResponse> result = employeeUtilizationController.getEmployeeUtilizationDetailByHccId("123456");

        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertNull(result.getMessage());
        assertEquals(employeeUtilizationModelResponse, result.getData());
    }

	@Test
	public void testSearchEmployeeUtilization_whenValidData_thenSuccess() {

		IEmployeeUTModel iEmployeeUTModelOne = mock(IEmployeeUTModel.class);

		IEmployeeUTModel iEmployeeUTModelTwo = mock(IEmployeeUTModel.class);

		Integer no = 0;
		Integer limit = 10;
		String sortBy = "name";
		Boolean desc = true;

		Timestamp startDate = DateFormatUtils.convertTimestampFromString("2023-08-01");
		Timestamp endDate = DateFormatUtils.convertTimestampFromString("2023-08-30");

		when(iEmployeeUTModelOne.getEmployeeUtilizationId()).thenReturn(1);
		when(iEmployeeUTModelOne.getHccId()).thenReturn("Hcc1");
		when(iEmployeeUTModelOne.getName()).thenReturn("Nguyen Van B");
		when(iEmployeeUTModelOne.getEmail()).thenReturn("a.1@example.com");
		when(iEmployeeUTModelOne.getLdap()).thenReturn("Ldap1");
		when(iEmployeeUTModelOne.getBillable()).thenReturn(50.5);
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
		Page<IEmployeeUTModel> mockPage = new PageImpl<>(iEmployeeUTModels);

		when(employeeUtilizationServiceMock.searchEmployeeUtilization(any(String.class), any(String.class), any(Integer.class), any(Integer.class), any(Integer.class), any(Integer.class), any(Integer.class), any(String.class), any(Boolean.class))).thenReturn(mockPage);

		BaseResponse<Page<IEmployeeUTModel>> result = employeeUtilizationController.searchEmployeeUtilization("Van", "60", 1, 1, 1, no, limit, sortBy, desc);

		assertNotNull(result);
		assertEquals(HttpStatus.OK.value(), result.getStatus());
		assertNull(result.getMessage());
		assertNotNull(result.getData());
		assertEquals(mockPage.getContent(), result.getData().getContent());
	}
}
