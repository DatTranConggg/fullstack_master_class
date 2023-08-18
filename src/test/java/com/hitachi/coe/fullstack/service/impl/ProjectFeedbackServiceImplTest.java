package com.hitachi.coe.fullstack.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.hitachi.coe.fullstack.entity.*;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.*;
import com.hitachi.coe.fullstack.repository.*;
import com.hitachi.coe.fullstack.service.CoeCoreTeamService;
import com.hitachi.coe.fullstack.service.ProjectFeedbackService;
import com.hitachi.coe.fullstack.transformation.CoeCoreTeamModelTransformer;
import com.hitachi.coe.fullstack.transformation.CoeCoreTeamTransformer;
import com.hitachi.coe.fullstack.transformation.ProjectFeedbackModelTransformer;
import com.hitachi.coe.fullstack.transformation.ProjectFeedbackTransformer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ProjectFeedbackServiceImplTest {
    @Autowired
    private ProjectFeedbackService projectFeedbackService;
    @MockBean
    private ProjectFeedbackRepository projectFeedbackRepository;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private EvaluationLevelRepository evaluationLevelRepository;

    @MockBean
    private ProjectFeedbackModelTransformer projectFeedbackModelTransformer;

    @MockBean
    private ProjectFeedbackTransformer projectFeedbackTransformer;

    @Test
    @SneakyThrows
    void addFeedbackSuccess(){
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setId(1);
        ProjectModel projectModel = new ProjectModel();
        projectModel.setId(1);
        EvaluationLevelModel evaluationLevelModel = new EvaluationLevelModel();
        evaluationLevelModel.setId(1);

        ProjectFeedbackModel projectFeedbackModel = new ProjectFeedbackModel();
        projectFeedbackModel.setId(1);
        projectFeedbackModel.setProject(projectModel);
        projectFeedbackModel.setEvaluationLevel(evaluationLevelModel);
        projectFeedbackModel.setEmployee(employeeModel);

        Project project = new Project();
        project.setId(1);
        EvaluationLevel evaluationLevel = new EvaluationLevel();
        evaluationLevel.setId(1);
        Employee employee = new Employee();
        employee.setId(1);

        ProjectFeedback projectFeedback = new ProjectFeedback();
        projectFeedback.setId(1);
        projectFeedback.setEmployee(employee);
        projectFeedback.setEvaluationLevel(evaluationLevel);
        projectFeedback.setProject(project);

        Project projectMock = new Project();
        project.setId(1);
        EvaluationLevel evaluationLevelMock = new EvaluationLevel();
        evaluationLevel.setId(1);
        Employee employeeMock = new Employee();
        employee.setId(1);

        ProjectFeedback projectFeedbackMock = new ProjectFeedback();
        projectFeedbackMock.setId(1);
        projectFeedbackMock.setEmployee(employeeMock);
        projectFeedbackMock.setProject(projectMock);
        projectFeedbackMock.setEvaluationLevel(evaluationLevelMock);

        when(projectRepository.findById(projectFeedbackModel.getProject().getId())).thenReturn(Optional.of(project));
        when(employeeRepository.findById(projectFeedbackModel.getEmployee().getId())).thenReturn(Optional.of(new Employee()));
        when(evaluationLevelRepository.findById(projectFeedbackModel.getEvaluationLevel().getId())).thenReturn(Optional.of(new EvaluationLevel()));
        when(projectFeedbackModelTransformer.apply(projectFeedbackModel)).thenReturn(projectFeedback);
        when(projectFeedbackRepository.save(projectFeedback)).thenReturn(projectFeedbackMock);

        Integer actual = projectFeedbackService.add(projectFeedbackModel);
        assertEquals(1, actual);
    }

    @Test
    @SneakyThrows
    void addFeedbackErrorEmployee(){
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setId(1);
        ProjectModel projectModel = new ProjectModel();
        projectModel.setId(1);
        EvaluationLevelModel evaluationLevelModel = new EvaluationLevelModel();
        evaluationLevelModel.setId(1);

        ProjectFeedbackModel projectFeedbackModel = new ProjectFeedbackModel();
        projectFeedbackModel.setId(1);
        projectFeedbackModel.setProject(projectModel);
        projectFeedbackModel.setEvaluationLevel(evaluationLevelModel);
        projectFeedbackModel.setEmployee(employeeModel);

        Project project = new Project();
        project.setId(1);
        EvaluationLevel evaluationLevel = new EvaluationLevel();
        evaluationLevel.setId(1);
        Employee employee = new Employee();
        employee.setId(1);

        ProjectFeedback projectFeedback = new ProjectFeedback();
        projectFeedback.setId(1);
        projectFeedback.setEmployee(employee);
        projectFeedback.setEvaluationLevel(evaluationLevel);
        projectFeedback.setProject(project);

        Project projectMock = new Project();
        project.setId(1);
        EvaluationLevel evaluationLevelMock = new EvaluationLevel();
        evaluationLevel.setId(1);
        Employee employeeMock = new Employee();
        employee.setId(1);

        ProjectFeedback projectFeedbackMock = new ProjectFeedback();
        projectFeedbackMock.setId(1);
        projectFeedbackMock.setEmployee(employeeMock);
        projectFeedbackMock.setProject(projectMock);
        projectFeedbackMock.setEvaluationLevel(evaluationLevelMock);

        //when(projectRepository.findById(projectFeedbackModel.getProject().getId())).thenReturn(null);
        when(employeeRepository.findById(projectFeedbackModel.getEmployee().getId())).thenReturn(Optional.of(new Employee()));
        when(evaluationLevelRepository.findById(projectFeedbackModel.getEvaluationLevel().getId())).thenReturn(Optional.of(new EvaluationLevel()));
        when(projectFeedbackModelTransformer.apply(projectFeedbackModel)).thenReturn(projectFeedback);
        when(projectFeedbackRepository.save(projectFeedback)).thenReturn(projectFeedbackMock);

        Throwable throwable = assertThrows(Exception.class, () -> projectFeedbackService.add(projectFeedbackModel));
        assertEquals(InvalidDataException.class, throwable.getClass());
        assertEquals("Project not exist.", throwable.getMessage());
    }

    @Test
    @SneakyThrows
    void AddFeedbackError (){
        ProjectFeedbackModel coeCoreTeamModel = new ProjectFeedbackModel();

        Throwable throwable = assertThrows(Exception.class, () -> projectFeedbackService.add(coeCoreTeamModel));
        assertEquals(NullPointerException.class, throwable.getClass());
    }

}