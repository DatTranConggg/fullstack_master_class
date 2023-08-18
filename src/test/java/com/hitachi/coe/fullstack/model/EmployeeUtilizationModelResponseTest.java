package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.util.DateFormatUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeUtilizationModelResponseTest {
    @Test
    public void testModelEmployeeUtilizationModelResponse() {

        Timestamp startDate = DateFormatUtils.convertTimestampFromString("2023-08-01");
        Timestamp endDate = DateFormatUtils.convertTimestampFromString("2023-08-30");

        IEmployeeUtilizationModel mockEmployeeUtilization = mock(IEmployeeUtilizationModel.class);
        EmployeeUtilizationModelResponse expected = new EmployeeUtilizationModelResponse();

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

        List<IEmployeeUtilizationModel> expectedEmployeeUtilizationModels = List.of(mockEmployeeUtilization);
        expected.setEmployeeUtilizationModels(expectedEmployeeUtilizationModels);
        expected.setAvgPtoOracle(5.0);
        expected.setAvgBillableHours(40.0);
        expected.setAvgBillable(10.5);
        expected.setAvgLoggedHours(45.0);
        expected.setAvgAvailableHours(0.0);

        EmployeeUtilizationModelResponse result = new EmployeeUtilizationModelResponse();

        result.setEmployeeUtilizationModels(expectedEmployeeUtilizationModels);
        result.setAvgPtoOracle(5.0);
        result.setAvgBillableHours(40.0);
        result.setAvgBillable(10.5);
        result.setAvgLoggedHours(45.0);
        result.setAvgAvailableHours(0.0);

        Assertions.assertNotNull(result);
        assertEquals(expectedEmployeeUtilizationModels.size(), result.getEmployeeUtilizationModels().size());
        assertEquals(expectedEmployeeUtilizationModels.get(0).getAvailableHours(), result.getEmployeeUtilizationModels().get(0).getAvailableHours());
        assertEquals(expectedEmployeeUtilizationModels.get(0).getBillable(), result.getEmployeeUtilizationModels().get(0).getBillable());
        assertEquals(expectedEmployeeUtilizationModels.get(0).getBillableHours(), result.getEmployeeUtilizationModels().get(0).getBillableHours());
        assertEquals(expectedEmployeeUtilizationModels.get(0).getLockTime(), result.getEmployeeUtilizationModels().get(0).getLockTime());
        assertEquals(expectedEmployeeUtilizationModels.get(0).getDuration(), result.getEmployeeUtilizationModels().get(0).getDuration());
        assertEquals(expectedEmployeeUtilizationModels.get(0).getLoggedHours(), result.getEmployeeUtilizationModels().get(0).getLoggedHours());
        assertEquals(expectedEmployeeUtilizationModels.get(0).getProjectName(), result.getEmployeeUtilizationModels().get(0).getProjectName());
        assertEquals(expectedEmployeeUtilizationModels.get(0).getStartDate(), result.getEmployeeUtilizationModels().get(0).getStartDate());
        assertEquals(expectedEmployeeUtilizationModels.get(0).getEndDate(), result.getEmployeeUtilizationModels().get(0).getEndDate());
        assertEquals(expected.getAvgBillable(), result.getAvgBillable());
        assertEquals(expected.getAvgBillableHours(), result.getAvgBillableHours());
        assertEquals(expected.getAvgPtoOracle(), result.getAvgPtoOracle());
        assertEquals(expected.getAvgLoggedHours(), result.getAvgLoggedHours());
        assertEquals(expected.getAvgAvailableHours(), result.getAvgAvailableHours());
    }
}
