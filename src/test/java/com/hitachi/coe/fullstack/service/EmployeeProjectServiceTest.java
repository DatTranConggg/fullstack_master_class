package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeProject;
import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.enums.EmployeeType;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.model.EmployeeModel;
import com.hitachi.coe.fullstack.model.EmployeeProjectAddModel;
import com.hitachi.coe.fullstack.model.EmployeeProjectModel;
import com.hitachi.coe.fullstack.model.IEmployeeProjectDetails;
import com.hitachi.coe.fullstack.model.IEmployeeProjectModel;
import com.hitachi.coe.fullstack.model.ProjectModel;
import com.hitachi.coe.fullstack.repository.EmployeeProjectRepository;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.repository.ProjectRepository;
import com.hitachi.coe.fullstack.transformation.EmployeeProjectTransformer;
import com.hitachi.coe.fullstack.util.DateFormatUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class EmployeeProjectServiceTest {

    @Autowired
    EmployeeProjectService employeeProjectService;

    @MockBean
    EmployeeProjectRepository employeeProjectRepository;

    @MockBean
    EmployeeRepository employeeRepository;

    @MockBean
    ProjectRepository projectRepository;

    @MockBean
    EmployeeProjectTransformer employeeProjectTransformer;

    Timestamp date;
    Timestamp otherDate;
    Timestamp startDate;
    Timestamp endDate;

    Integer no;

    Integer limit;

    String sortBy;

    Boolean desc;

    Employee employee;

    Project project;

    EmployeeProject employeeProject;

    List<EmployeeProject> employeeProjects;

    EmployeeProjectModel employeeProjectModel;

    EmployeeModel employeeModel;

    ProjectModel projectModel;

    EmployeeProjectAddModel employeeProjectAddModel;

    IEmployeeProjectModel iEmployeeProjectModelOne;

    IEmployeeProjectModel iEmployeeProjectModelTwo;

    IEmployeeProjectDetails iEmployeeProjectDetails;

    @BeforeEach
    void setUp(){
        date = DateFormatUtils.convertTimestampFromString("2023-07-01");
        otherDate = DateFormatUtils.convertTimestampFromString("2023-10-01");
        startDate = DateFormatUtils.convertTimestampFromString("2023-07-12");
        endDate = DateFormatUtils.convertTimestampFromString("2023-09-20");
        employee = new Employee();
        employee.setId(1);
        employee.setName("nguyenhai");
        employee.setHccId("123456");
        employee.setLdap("78910");

        employeeModel = new EmployeeModel();
        employeeModel.setId(1);
        employeeModel.setName("nguyenhai");
        employeeModel.setHccId("123456");
        employeeModel.setLdap("78910");

        project = new Project();
        project.setId(1);
        project.setName("FSCMS");
        project.setStartDate(date);
        project.setEndDate(otherDate);

        projectModel = new ProjectModel();
        projectModel.setId(1);
        projectModel.setName("FSCMS");
        projectModel.setStartDate(date);
        projectModel.setEndDate(otherDate);

        employeeProject = new EmployeeProject();
        employeeProject.setId(1);
        employeeProject.setEmployee(employee);
        employeeProject.setProject(project);
        employeeProject.setEmployeeType(1);
        employeeProject.setStartDate(date);
        employeeProject.setEndDate(otherDate);

        employeeProjects = List.of(employeeProject);

        employeeProjectModel = new EmployeeProjectModel();
        employeeProjectModel.setId(1);
        employeeProjectModel.setEmployee(employeeModel);
        employeeProjectModel.setProject(projectModel);
        employeeProjectModel.setEmployeeType(EmployeeType.SHADOW);
        employeeProjectModel.setStartDate(date);
        employeeProjectModel.setEndDate(otherDate);

        employeeProjectAddModel = new EmployeeProjectAddModel();
        employeeProjectAddModel.setEmployeeId(1);
        employeeProjectAddModel.setProjects(List.of(1));
        employeeProjectAddModel.setEmployeeType("Shadow");
        employeeProjectAddModel.setStartDate("2023-07-12 00:00:00.321");
        employeeProjectAddModel.setEndDate("2023-09-20 00:00:00.123");

        no = 0;
        limit = 10;
        sortBy = "name";
        desc = true;

        iEmployeeProjectModelOne = mock(IEmployeeProjectModel.class);
        iEmployeeProjectModelTwo = mock(IEmployeeProjectModel.class);

        iEmployeeProjectDetails = mock(IEmployeeProjectDetails.class);
    }

    @Test
    public void testSearchEmployeesProjectWithStatus_whenValidData_thenSuccess(){

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

        when(employeeProjectRepository.searchEmployeesProjectWithStatus(any(Integer.class), any(String.class), any(String.class), any(PageRequest.class))).thenReturn(mockPage);

        Page<IEmployeeProjectModel> iEmployeeProjectModelPageActual = employeeProjectService.searchEmployeesProjectWithStatus(1, "Hcc", "release", no, limit, sortBy, desc);

        //Verify
        assertNotNull(iEmployeeProjectModelPageActual);
        assertEquals(iEmployeeProjectModelPageActual.getSize(), iEmployeeProjectModels.size());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(0).getEmployeeId(), iEmployeeProjectModels.get(0).getEmployeeId());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(0).getProjectId(), iEmployeeProjectModels.get(0).getEmployeeId());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(0).getEmployeeType(), iEmployeeProjectModels.get(0).getProjectId());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(0).getEmployeeProjectId(), iEmployeeProjectModels.get(0).getEmployeeProjectId());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(0).getHccId(), iEmployeeProjectModels.get(0).getHccId());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(0).getName(), iEmployeeProjectModels.get(0).getName());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(0).getEmail(), iEmployeeProjectModels.get(0).getEmail());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(0).getStartDate(), iEmployeeProjectModels.get(0).getStartDate());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(0).getEndDate(), iEmployeeProjectModels.get(0).getEndDate());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(0).getPmName(), iEmployeeProjectModels.get(0).getPmName());

        assertEquals(iEmployeeProjectModelPageActual.getContent().get(1).getEmployeeId(), iEmployeeProjectModels.get(1).getEmployeeId());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(1).getProjectId(), iEmployeeProjectModels.get(1).getEmployeeId());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(1).getEmployeeType(), iEmployeeProjectModels.get(1).getProjectId());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(1).getEmployeeProjectId(), iEmployeeProjectModels.get(1).getEmployeeProjectId());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(1).getHccId(), iEmployeeProjectModels.get(1).getHccId());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(1).getName(), iEmployeeProjectModels.get(1).getName());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(1).getEmail(), iEmployeeProjectModels.get(1).getEmail());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(1).getStartDate(), iEmployeeProjectModels.get(1).getStartDate());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(1).getEndDate(), iEmployeeProjectModels.get(1).getEndDate());
        assertEquals(iEmployeeProjectModelPageActual.getContent().get(1).getPmName(), iEmployeeProjectModels.get(1).getPmName());

    }

    @Test
    public void testAssignEmployeeProjects_whenNotAssignToAnyProject_thenSuccess(){

        Page<IEmployeeProjectModel> mockPage = new PageImpl<>(Collections.emptyList());

        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));
        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(employeeProjectRepository.searchEmployeesProjectWithStatus(any(Integer.class), any(String.class), any(String.class), any(PageRequest.class))).thenReturn(mockPage);
        when(employeeProjectRepository.findEmployeeAssignedExceptProjectById(any(Integer.class),any(Integer.class))).thenReturn(employeeProjects);
        when(employeeProjectTransformer.apply(any(EmployeeProject.class))).thenReturn(employeeProjectModel);
        when(employeeProjectRepository.saveAll(employeeProjects)).thenReturn(employeeProjects);

        List<EmployeeProjectModel> employeeProjectModelListActual = employeeProjectService.assignEmployeeProjects(employeeProjectAddModel);

        List<EmployeeProjectModel> employeeProjectModelListExpected = List.of(employeeProjectModel);

        assertNotNull(employeeProjectModelListActual);
        assertEquals(employeeProjectModelListExpected.size(), employeeProjectModelListActual.size());
        assertEquals(employeeProjectModelListExpected.get(0).getId(), employeeProjectModelListActual.get(0).getId());
        assertEquals(employeeProjectModelListExpected.get(0).getEmployee(), employeeProjectModelListActual.get(0).getEmployee());
        assertEquals(employeeProjectModelListExpected.get(0).getProject(), employeeProjectModelListActual.get(0).getProject());
        assertEquals(employeeProjectModelListExpected.get(0).getEmployeeType(), employeeProjectModelListActual.get(0).getEmployeeType());
        assertEquals(employeeProjectModelListExpected.get(0).getStartDate(), employeeProjectModelListActual.get(0).getStartDate());
        assertEquals(employeeProjectModelListExpected.get(0).getEndDate(), employeeProjectModelListActual.get(0).getEndDate());

    }

    @Test
    public void testAssignEmployeeProjects_whenAssigningToAnotherProject_thenSuccess(){

        Page<IEmployeeProjectModel> mockPage = new PageImpl<>(Collections.emptyList());

        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));
        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(employeeProjectRepository.searchEmployeesProjectWithStatus(any(Integer.class), any(String.class), any(String.class), any(PageRequest.class))).thenReturn(mockPage);
        when(employeeProjectRepository.findEmployeeAssignedExceptProjectById(any(Integer.class),any(Integer.class))).thenReturn(Collections.emptyList());
        when(employeeProjectRepository.saveAll(employeeProjects)).thenReturn(employeeProjects);

        List<EmployeeProjectModel> employeeProjectModelListActual = employeeProjectService.assignEmployeeProjects(employeeProjectAddModel);

        assertNotNull(employeeProjectModelListActual);
        assertTrue(employeeProjectModelListActual.isEmpty());
    }

    @Test
    public void testAssignEmployeeProjects_whenAlreadyAssignedToProject_thenThrowCoEException(){

        when(iEmployeeProjectModelTwo.getEmployeeProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getEmployeeId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getHccId()).thenReturn("123456");
        when(iEmployeeProjectModelTwo.getName()).thenReturn("nguyenhai");
        when(iEmployeeProjectModelTwo.getEmail()).thenReturn("a.2@example.com");
        when(iEmployeeProjectModelTwo.getEmployeeType()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getStartDate()).thenReturn(startDate);
        when(iEmployeeProjectModelTwo.getEndDate()).thenReturn(endDate);
        when(iEmployeeProjectModelTwo.getPmName()).thenReturn("Pm B");

        List<IEmployeeProjectModel> iEmployeeProjectModels = List.of(iEmployeeProjectModelTwo);
        Page<IEmployeeProjectModel> mockPage = new PageImpl<>(iEmployeeProjectModels);

        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));
        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(employeeProjectRepository.searchEmployeesProjectWithStatus(any(Integer.class), any(String.class), any(String.class), any(PageRequest.class))).thenReturn(mockPage);
        when(employeeProjectRepository.findEmployeeAssignedExceptProjectById(any(Integer.class),any(Integer.class))).thenReturn(employeeProjects);
        when(employeeProjectTransformer.apply(any(EmployeeProject.class))).thenReturn(employeeProjectModel);
        when(employeeProjectRepository.saveAll(employeeProjects)).thenReturn(employeeProjects);

        Throwable throwable = assertThrows(CoEException.class,
                () -> employeeProjectService.assignEmployeeProjects(employeeProjectAddModel));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_EMPLOYEE_PROJECT_ALREADY_ASSIGN, throwable.getMessage());

    }

    @Test
    public void testAssignEmployeeProjects_whenEmployeeNotFound_thenThrowCoEException(){


        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());


        Throwable throwable = assertThrows(CoEException.class,
                () -> employeeProjectService.assignEmployeeProjects(employeeProjectAddModel));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_EMPLOYEE_DO_NOT_EXIST, throwable.getMessage());

    }

    @Test
    public void testAssignEmployeeProjects_whenProjectNotFound_thenThrowCoEException(){

        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));
        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.empty());


        Throwable throwable = assertThrows(CoEException.class,
                () -> employeeProjectService.assignEmployeeProjects(employeeProjectAddModel));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_PROJECT_NOT_FOUND, throwable.getMessage());

    }

    @Test
    public void testAssignEmployeeProjects_whenEndDateBeforeStartDate_thenThrowCoEException(){

        when(iEmployeeProjectModelTwo.getEmployeeProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getEmployeeId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getHccId()).thenReturn("123456");
        when(iEmployeeProjectModelTwo.getName()).thenReturn("nguyenhai");
        when(iEmployeeProjectModelTwo.getEmail()).thenReturn("a.2@example.com");
        when(iEmployeeProjectModelTwo.getEmployeeType()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getStartDate()).thenReturn(startDate);
        when(iEmployeeProjectModelTwo.getEndDate()).thenReturn(endDate);
        when(iEmployeeProjectModelTwo.getPmName()).thenReturn("Pm B");

        List<IEmployeeProjectModel> iEmployeeProjectModels = List.of(iEmployeeProjectModelTwo);
        Page<IEmployeeProjectModel> mockPage = new PageImpl<>(iEmployeeProjectModels);

        employeeProjectAddModel.setStartDate("2023-07-30 00:00:00.321");
        employeeProjectAddModel.setEndDate("2023-07-20 00:00:00.123");

        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));
        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(employeeProjectRepository.searchEmployeesProjectWithStatus(any(Integer.class), any(String.class), any(String.class), any(PageRequest.class))).thenReturn(mockPage);
        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));



        Throwable throwable = assertThrows(CoEException.class,
                () -> employeeProjectService.assignEmployeeProjects(employeeProjectAddModel));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_INVALID_START_DATE_END_DATE, throwable.getMessage());

    }

    @Test
    public void testAssignEmployeeProjects_whenEndDateBeforeCurrentDate_thenThrowCoEException(){

        when(iEmployeeProjectModelTwo.getEmployeeProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getEmployeeId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getHccId()).thenReturn("123456");
        when(iEmployeeProjectModelTwo.getName()).thenReturn("nguyenhai");
        when(iEmployeeProjectModelTwo.getEmail()).thenReturn("a.2@example.com");
        when(iEmployeeProjectModelTwo.getEmployeeType()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getStartDate()).thenReturn(startDate);
        when(iEmployeeProjectModelTwo.getEndDate()).thenReturn(endDate);
        when(iEmployeeProjectModelTwo.getPmName()).thenReturn("Pm B");

        List<IEmployeeProjectModel> iEmployeeProjectModels = List.of(iEmployeeProjectModelTwo);
        Page<IEmployeeProjectModel> mockPage = new PageImpl<>(iEmployeeProjectModels);
        ;
        employeeProjectAddModel.setEndDate("2023-07-26 00:00:00.123");

        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));
        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(employeeProjectRepository.searchEmployeesProjectWithStatus(any(Integer.class), any(String.class), any(String.class), any(PageRequest.class))).thenReturn(mockPage);
        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));



        Throwable throwable = assertThrows(CoEException.class,
                () -> employeeProjectService.assignEmployeeProjects(employeeProjectAddModel));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_INVALID_END_DATE_GREATER_THAN_CURRENT, throwable.getMessage());

    }

    @Test
    public void testAssignEmployeeProjects_whenStartDateBeforeStartDateProject_thenThrowCoEException(){

        when(iEmployeeProjectModelTwo.getEmployeeProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getEmployeeId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getHccId()).thenReturn("123456");
        when(iEmployeeProjectModelTwo.getName()).thenReturn("nguyenhai");
        when(iEmployeeProjectModelTwo.getEmail()).thenReturn("a.2@example.com");
        when(iEmployeeProjectModelTwo.getEmployeeType()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getStartDate()).thenReturn(startDate);
        when(iEmployeeProjectModelTwo.getEndDate()).thenReturn(endDate);
        when(iEmployeeProjectModelTwo.getPmName()).thenReturn("Pm B");

        List<IEmployeeProjectModel> iEmployeeProjectModels = List.of(iEmployeeProjectModelTwo);
        Page<IEmployeeProjectModel> mockPage = new PageImpl<>(iEmployeeProjectModels);

        employeeProjectAddModel.setStartDate("2023-06-26 00:00:00.123");

        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));
        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(employeeProjectRepository.searchEmployeesProjectWithStatus(any(Integer.class), any(String.class), any(String.class), any(PageRequest.class))).thenReturn(mockPage);
        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));



        Throwable throwable = assertThrows(CoEException.class,
                () -> employeeProjectService.assignEmployeeProjects(employeeProjectAddModel));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_INVALID_START_DATE, throwable.getMessage());

    }

    @Test
    public void testAssignEmployeeProjects_whenEndDateAfterEndDateProject_thenThrowCoEException(){

        when(iEmployeeProjectModelTwo.getEmployeeProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getEmployeeId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getHccId()).thenReturn("123456");
        when(iEmployeeProjectModelTwo.getName()).thenReturn("nguyenhai");
        when(iEmployeeProjectModelTwo.getEmail()).thenReturn("a.2@example.com");
        when(iEmployeeProjectModelTwo.getEmployeeType()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getStartDate()).thenReturn(startDate);
        when(iEmployeeProjectModelTwo.getEndDate()).thenReturn(endDate);
        when(iEmployeeProjectModelTwo.getPmName()).thenReturn("Pm B");

        List<IEmployeeProjectModel> iEmployeeProjectModels = List.of(iEmployeeProjectModelTwo);
        Page<IEmployeeProjectModel> mockPage = new PageImpl<>(iEmployeeProjectModels);

        employeeProjectAddModel.setEndDate("2023-11-26 00:00:00.123");

        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));
        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(employeeProjectRepository.searchEmployeesProjectWithStatus(any(Integer.class), any(String.class), any(String.class), any(PageRequest.class))).thenReturn(mockPage);
        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));



        Throwable throwable = assertThrows(CoEException.class,
                () -> employeeProjectService.assignEmployeeProjects(employeeProjectAddModel));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_INVALID_END_DATE, throwable.getMessage());

    }

    @Test
    public void testReleaseEmployeeProjects_whenValidData_thenSuccess(){

        when(iEmployeeProjectModelTwo.getEmployeeProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getEmployeeId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getHccId()).thenReturn("123456");
        when(iEmployeeProjectModelTwo.getName()).thenReturn("nguyenhai");
        when(iEmployeeProjectModelTwo.getEmail()).thenReturn("a.2@example.com");
        when(iEmployeeProjectModelTwo.getEmployeeType()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getStartDate()).thenReturn(startDate);
        when(iEmployeeProjectModelTwo.getEndDate()).thenReturn(endDate);
        when(iEmployeeProjectModelTwo.getPmName()).thenReturn("Pm B");

        List<IEmployeeProjectModel> iEmployeeProjectModels = List.of(iEmployeeProjectModelTwo);
        Page<IEmployeeProjectModel> mockPage = new PageImpl<>(iEmployeeProjectModels);

        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));
        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(employeeProjectRepository.searchEmployeesProjectWithStatus(any(Integer.class), any(String.class), any(String.class), any(PageRequest.class))).thenReturn(mockPage);
        when(employeeProjectRepository.findById(any(Integer.class))).thenReturn(Optional.of(employeeProject));
        when(employeeProjectRepository.save(any(EmployeeProject.class))).thenReturn(employeeProject);
        when(employeeProjectTransformer.apply(any(EmployeeProject.class))).thenReturn(employeeProjectModel);

        EmployeeProjectModel employeeProjectModelActual = employeeProjectService.releaseEmployeeProject(1, 1);

        EmployeeProjectModel employeeProjectModelExpected = employeeProjectModel;

        assertNotNull(employeeProjectModelActual);
        assertEquals(employeeProjectModelExpected.getId(), employeeProjectModelActual.getId());
        assertEquals(employeeProjectModelExpected.getEmployee(), employeeProjectModelActual.getEmployee());
        assertEquals(employeeProjectModelExpected.getProject(), employeeProjectModelActual.getProject());
        assertEquals(employeeProjectModelExpected.getEmployeeType(), employeeProjectModelActual.getEmployeeType());
        assertEquals(employeeProjectModelExpected.getStartDate(), employeeProjectModelActual.getStartDate());
        assertEquals(employeeProjectModelExpected.getEndDate(), employeeProjectModelActual.getEndDate());

    }

    @Test
    public void testReleaseEmployeeProjects_whenEmployeeNotFound_thenThrowCoEException(){


        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());


        Throwable throwable = assertThrows(CoEException.class,
                () -> employeeProjectService.releaseEmployeeProject(1,1));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_EMPLOYEE_DO_NOT_EXIST, throwable.getMessage());

    }

    @Test
    public void testReleaseEmployeeProjects_whenProjectNotFound_thenThrowCoEException(){

        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));
        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.empty());


        Throwable throwable = assertThrows(CoEException.class,
                () -> employeeProjectService.releaseEmployeeProject(1,1));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_PROJECT_NOT_FOUND, throwable.getMessage());

    }

    @Test
    public void testReleaseEmployeeProjects_whenEndDateBeforeCurrentDate_thenThrowCoEException(){

        when(iEmployeeProjectModelTwo.getEmployeeProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getProjectId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getEmployeeId()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getHccId()).thenReturn("123456");
        when(iEmployeeProjectModelTwo.getName()).thenReturn("nguyenhai");
        when(iEmployeeProjectModelTwo.getEmail()).thenReturn("a.2@example.com");
        when(iEmployeeProjectModelTwo.getEmployeeType()).thenReturn(1);
        when(iEmployeeProjectModelTwo.getStartDate()).thenReturn(startDate);
        when(iEmployeeProjectModelTwo.getEndDate()).thenReturn(endDate);
        when(iEmployeeProjectModelTwo.getPmName()).thenReturn("Pm B");

        List<IEmployeeProjectModel> iEmployeeProjectModels = List.of(iEmployeeProjectModelTwo);
        Page<IEmployeeProjectModel> mockPage = new PageImpl<>(iEmployeeProjectModels);

        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));
        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(employeeProjectRepository.searchEmployeesProjectWithStatus(any(Integer.class), any(String.class), any(String.class), any(PageRequest.class))).thenReturn(mockPage);
        when(employeeProjectRepository.findById(any(Integer.class))).thenReturn(Optional.of(employeeProject));

        employeeProject.setStartDate(DateFormatUtils.convertTimestampFromString("9999-07-26"));

        Throwable throwable = assertThrows(CoEException.class,
                () -> employeeProjectService.releaseEmployeeProject(1,1));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_EMPLOYEE_PROJECT_NOT_START, throwable.getMessage());

    }

    @Test
    public void testReleaseEmployeeProjects_whenAlreadyReleaseToProject_thenThrowCoEException(){

        Page<IEmployeeProjectModel> mockPage = new PageImpl<>(Collections.emptyList());

        when(employeeRepository.findById(any(Integer.class))).thenReturn(Optional.of(employee));
        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(employeeProjectRepository.searchEmployeesProjectWithStatus(any(Integer.class), any(String.class), any(String.class), any(PageRequest.class))).thenReturn(mockPage);

        Throwable throwable = assertThrows(CoEException.class,
                () -> employeeProjectService.releaseEmployeeProject(1,1));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_EMPLOYEE_PROJECT_ALREADY_RELEASE, throwable.getMessage());

    }

    @Test
    void testGetEmployeeProjectDetailsByEmployeeHccId_whenValidHccId_thenReturnListOfIEmployeeProjectDetails() {
        // prepare
        String testHccId = "test_hccId";
        Employee testEmployee = new Employee();
        testEmployee.setHccId("9999");
        testEmployee.setName("test_employee");
        testEmployee.setEmail("test_employee@gmail.com");
        List<IEmployeeProjectDetails> iEmployeeProjectDetailsList = List.of(iEmployeeProjectDetails);
        // when - then
        when(employeeRepository.findByHccId(anyString())).thenReturn(testEmployee);
        when(employeeProjectRepository.getEmployeeProjectDetailsByEmployeeHccId(testHccId)).thenReturn(iEmployeeProjectDetailsList);
        // invoke
        List<IEmployeeProjectDetails> result = employeeProjectService.getEmployeeProjectDetailsByEmployeeHccId(testHccId);
        // assert
        assertNotNull(result);
        assertNotNull(testEmployee);
        assertNotNull(iEmployeeProjectDetailsList);
        // verify
        verify(employeeRepository).findByHccId(testHccId);
        verify(employeeProjectRepository).getEmployeeProjectDetailsByEmployeeHccId(testHccId);
    }

    @Test
    void testGetEmployeeProjectDetailsByEmployeeHccId_whenInvalidHccId_thenThrowCoEException() {
        // prepare
        String testHccId = "test_hccId";
        // when - then
        when(employeeRepository.findByHccId(anyString())).thenReturn(null);
        // assert
        assertThrows(CoEException.class, () -> employeeProjectService.getEmployeeProjectDetailsByEmployeeHccId(testHccId));
        // verify
        verify(employeeRepository).findByHccId(testHccId);
    }
}
