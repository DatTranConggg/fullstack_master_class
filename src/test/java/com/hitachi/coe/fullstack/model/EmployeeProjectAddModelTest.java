package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.util.DateFormatUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeProjectAddModelTest {

    Project project;

    Timestamp date;

    Timestamp startDate;

    Timestamp endDate;

    IEmployeeProjectModel iEmployeeProjectModel;

    @BeforeEach
    void setUp(){

        date = DateFormatUtils.convertTimestampFromString("2023-07-01");
        startDate = DateFormatUtils.convertTimestampFromString("2023-07-12");
        endDate = DateFormatUtils.convertTimestampFromString("2023-09-20");

        project = new Project();
        project.setId(1);
        project.setName("FSCMS");
        project.setDescription("FSCMS");
        project.setStartDate(DateFormatUtils.convertTimestampFromString("2023-07-12"));
        project.setEndDate(DateFormatUtils.convertTimestampFromString("2023-09-20"));

        iEmployeeProjectModel = mock(IEmployeeProjectModel.class);

    }

    @Test
    public void testModelEmployeeProjectAdd(){

        EmployeeProjectAddModel employee = new EmployeeProjectAddModel();
        employee.setEmployeeId(1);
        employee.setEmployeeType("Office");
        employee.setProjects(List.of(1));
        employee.setStartDate("2023-05-18");
        employee.setEndDate("2023-08-18");

        assertNotNull(employee);
        assertEquals(1, employee.getEmployeeId());
        assertEquals("Office", employee.getEmployeeType());
        assertEquals(List.of(1), employee.getProjects());
        assertEquals("2023-05-18", employee.getStartDate());
        assertEquals("2023-08-18", employee.getEndDate());

    }

    @Test
    public void testValidateEmployeeProject_whenAllValidate_thenNothing(){

        Page<IEmployeeProjectModel> isAssignedProject = new PageImpl<>(Collections.emptyList());

        assertDoesNotThrow(() -> EmployeeProjectAddModel.validateEmployeeProject(project, isAssignedProject, startDate, endDate));

    }

    @Test
    public void testValidateEmployeeProject_whenEmployeeAlreadyAssign_thenThrowCoEException(){

        when(iEmployeeProjectModel.getEmployeeProjectId()).thenReturn(1);
        when(iEmployeeProjectModel.getProjectId()).thenReturn(1);
        when(iEmployeeProjectModel.getEmployeeId()).thenReturn(1);
        when(iEmployeeProjectModel.getHccId()).thenReturn("Hcc2");
        when(iEmployeeProjectModel.getName()).thenReturn("Nguyen Van B");
        when(iEmployeeProjectModel.getEmail()).thenReturn("a.2@example.com");
        when(iEmployeeProjectModel.getEmployeeType()).thenReturn(1);
        when(iEmployeeProjectModel.getStartDate()).thenReturn(DateFormatUtils.convertTimestampFromString("2023-07-12"));
        when(iEmployeeProjectModel.getEndDate()).thenReturn(DateFormatUtils.convertTimestampFromString("2023-07-30"));
        when(iEmployeeProjectModel.getPmName()).thenReturn("Pm B");

        Page<IEmployeeProjectModel> isAssignedProject = new PageImpl<>(List.of(iEmployeeProjectModel));

        Throwable throwable = assertThrows(CoEException.class,
                () -> EmployeeProjectAddModel.validateEmployeeProject(project, isAssignedProject, startDate, endDate));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_EMPLOYEE_PROJECT_ALREADY_ASSIGN, throwable.getMessage());

    }

    @Test
    public void testValidateEmployeeProject_whenStartDateAfterEndDate_thenThrowCoEException(){

        Page<IEmployeeProjectModel> isAssignedProject = new PageImpl<>(Collections.emptyList());

        startDate = DateFormatUtils.convertTimestampFromString("2023-07-22");
        endDate = DateFormatUtils.convertTimestampFromString("2023-07-20");

        Throwable throwable = assertThrows(CoEException.class,
                () -> EmployeeProjectAddModel.validateEmployeeProject(project, isAssignedProject, startDate, endDate));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_INVALID_START_DATE_END_DATE, throwable.getMessage());

    }

    @Test
    public void testValidateEmployeeProject_whenEndDateBeforeCurrentDate_thenThrowCoEException(){

        Page<IEmployeeProjectModel> isAssignedProject = new PageImpl<>(Collections.emptyList());

        endDate = DateFormatUtils.convertTimestampFromString("2023-07-20");

        Throwable throwable = assertThrows(CoEException.class,
                () -> EmployeeProjectAddModel.validateEmployeeProject(project, isAssignedProject, startDate, endDate));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_INVALID_END_DATE_GREATER_THAN_CURRENT, throwable.getMessage());

    }

    @Test
    public void testValidateEmployeeProject_whenStartDateNotBetweenStartDateAndEndDateOfProject_thenThrowCoEException(){

        Page<IEmployeeProjectModel> isAssignedProject = new PageImpl<>(Collections.emptyList());

        startDate = DateFormatUtils.convertTimestampFromString("2023-07-11");

        Throwable throwable = assertThrows(CoEException.class,
                () -> EmployeeProjectAddModel.validateEmployeeProject(project, isAssignedProject, startDate, endDate));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_INVALID_START_DATE, throwable.getMessage());

    }

    @Test
    public void testValidateEmployeeProject_whenEndDateNotBetweenStartDateAndEndDateOfProject_thenThrowCoEException(){

        Page<IEmployeeProjectModel> isAssignedProject = new PageImpl<>(Collections.emptyList());

        endDate = DateFormatUtils.convertTimestampFromString("2023-09-21");

        Throwable throwable = assertThrows(CoEException.class,
                () -> EmployeeProjectAddModel.validateEmployeeProject(project, isAssignedProject, startDate, endDate));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_INVALID_END_DATE, throwable.getMessage());

    }
}
