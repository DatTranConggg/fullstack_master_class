package com.hitachi.coe.fullstack.controller;

import com.hitachi.coe.fullstack.enums.EmployeeType;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.model.EmployeeModel;
import com.hitachi.coe.fullstack.model.EmployeeProjectAddModel;
import com.hitachi.coe.fullstack.model.EmployeeProjectModel;
import com.hitachi.coe.fullstack.model.IEmployeeProjectDetails;
import com.hitachi.coe.fullstack.model.IEmployeeProjectModel;
import com.hitachi.coe.fullstack.model.ProjectModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.EmployeeProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeProjectControllerTest {

    @Autowired
    private EmployeeProjectController employeeProjectController;

    @MockBean
    private EmployeeProjectService employeeProjectService;

    Date date;

    Integer no;

    Integer limit;

    String sortBy;

    Boolean desc;

    Integer projectId;

    String keyword;

    String status;

    IEmployeeProjectModel iEmployeeProjectModelOne;

    IEmployeeProjectModel iEmployeeProjectModelTwo;

    EmployeeProjectAddModel employeeProjectAddModel;

    EmployeeModel employeeModel;

    ProjectModel projectModel;

    EmployeeProjectModel employeeProjectModel;

    IEmployeeProjectDetails iEmployeeProjectDetails;

    @BeforeEach
    void setUp(){
        date = new Date();
        no = 0;
        limit = 10;
        sortBy = "name";
        desc = null;
        projectId = 1;
        keyword = "Nguyen";
        status = "assign";
        iEmployeeProjectModelOne = mock(IEmployeeProjectModel.class);
        iEmployeeProjectModelTwo = mock(IEmployeeProjectModel.class);

        employeeProjectAddModel = new EmployeeProjectAddModel();
        employeeProjectAddModel.setEmployeeId(1);
        employeeProjectAddModel.setEmployeeType("Office");
        employeeProjectAddModel.setProjects(List.of(1));
        employeeProjectAddModel.setStartDate("2023-05-18");
        employeeProjectAddModel.setEndDate("2023-08-18");

        employeeModel = new EmployeeModel();
        employeeModel.setId(1);
        employeeModel.setName("Nguyen Van A");
        employeeModel.setLdap("0789");
        employeeModel.setHccId("1234");
        employeeModel.setEmail("a.1@hitachivantara.com");

        projectModel = new ProjectModel();
        projectModel.setId(1);
        projectModel.setName("FSCMS");
        projectModel.setCode("FSCMS");

        employeeProjectModel = new EmployeeProjectModel();
        employeeProjectModel.setEmployee(employeeModel);
        employeeProjectModel.setProject(projectModel);
        employeeProjectModel.setEmployeeType(EmployeeType.OFFICE);

        iEmployeeProjectDetails = mock(IEmployeeProjectDetails.class);
    }


    @Test
    public void testSearchEmployeesProjectWithStatus_whenValidData_thenSuccess() {

        when(iEmployeeProjectModelOne.getEmployeeProjectId()).thenReturn(1);
        when(iEmployeeProjectModelOne.getProjectId()).thenReturn(1);
        when(iEmployeeProjectModelOne.getEmployeeId()).thenReturn(1);
        when(iEmployeeProjectModelOne.getHccId()).thenReturn("Hcc1");
        when(iEmployeeProjectModelOne.getName()).thenReturn("Nguyen Van A");
        when(iEmployeeProjectModelOne.getEmail()).thenReturn("a.1@example.com");
        when(iEmployeeProjectModelOne.getEmployeeType()).thenReturn(1);
        when(iEmployeeProjectModelOne.getStartDate()).thenReturn(date);
        when(iEmployeeProjectModelOne.getEndDate()).thenReturn(date);
        when(iEmployeeProjectModelOne.getPmName()).thenReturn("Pm A");

        when(iEmployeeProjectModelTwo.getEmployeeProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getEmployeeId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getHccId()).thenReturn("Hcc2");
        when(iEmployeeProjectModelTwo.getName()).thenReturn("Nguyen Van B");
        when(iEmployeeProjectModelTwo.getEmail()).thenReturn("a.2@example.com");
        when(iEmployeeProjectModelTwo.getEmployeeType()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getStartDate()).thenReturn(date);
        when(iEmployeeProjectModelTwo.getEndDate()).thenReturn(date);
        when(iEmployeeProjectModelTwo.getPmName()).thenReturn("Pm B");

        List<IEmployeeProjectModel> iEmployeeProjectModels = Arrays.asList(iEmployeeProjectModelOne,iEmployeeProjectModelTwo);
        Page<IEmployeeProjectModel> mockPage = new PageImpl<>(iEmployeeProjectModels);

        when(employeeProjectService.searchEmployeesProjectWithStatus( projectId, keyword, status, no, limit, sortBy, desc)).thenReturn(mockPage);

        BaseResponse<Page<IEmployeeProjectModel>> result = employeeProjectController.searchEmployeesProjectWithStatus(keyword, projectId, status, no, limit, sortBy, desc);

        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertNull(result.getMessage());
        assertNotNull(result.getData());
        assertEquals(mockPage.getContent(), result.getData().getContent());
    }

    @Test
    public void testAssignEmployeeProject_whenValidData_thenSuccess() {

        when(employeeProjectService.assignEmployeeProjects(employeeProjectAddModel)).thenReturn(List.of(employeeProjectModel));

        BaseResponse<List<EmployeeProjectModel>> result = employeeProjectController.assignEmployeeProject(employeeProjectAddModel);

        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertNull(result.getMessage());
        assertNotNull(result.getData());
        assertEquals(List.of(employeeProjectModel), result.getData());
    }

    @Test
    public void testReleaseEmployeeProject_whenValidData_thenSuccess() {

        when(employeeProjectService.releaseEmployeeProject(any(Integer.class), any(Integer.class))).thenReturn(employeeProjectModel);

        BaseResponse<EmployeeProjectModel> result = employeeProjectController.releaseProjectEmployee(1,1);

        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertNull(result.getMessage());
        assertNotNull(result.getData());
        assertEquals(employeeProjectModel, result.getData());
    }

    @Test
    void testGetEmployeeProjectDetails_whenValidHccId_thenReturnListOfIEmployeeProjectDetails() {
        // prepare
        String hccId = "test_hccId";
        // when - then
        when(employeeProjectService.getEmployeeProjectDetailsByEmployeeHccId(hccId)).thenReturn(List.of(iEmployeeProjectDetails));
        // invoke
        BaseResponse<List<IEmployeeProjectDetails>> result = employeeProjectController.getEmployeeProjectDetails(hccId);
        // assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertNull(result.getMessage());
        assertNotNull(result.getData());
        assertEquals(List.of(iEmployeeProjectDetails), result.getData());
    }

    @Test
    void testGetEmployeeProjectDetails_whenInvalidHccId_thenThrowCoEException() {
        // prepare
        String testHccId = "test_hccId";
        // when - then
        when(employeeProjectService.getEmployeeProjectDetailsByEmployeeHccId(testHccId)).thenThrow(new CoEException("1", "Error"));
        // invoke & assert
        assertThrows(CoEException.class, () -> employeeProjectController.getEmployeeProjectDetails(testHccId));
        // verify
        verify(employeeProjectService).getEmployeeProjectDetailsByEmployeeHccId(testHccId);
    }
}
