package com.hitachi.coe.fullstack.controller;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.model.BusinessDomainModel;
import com.hitachi.coe.fullstack.model.ProjectModel;
import com.hitachi.coe.fullstack.model.ProjectTypeModel;
import com.hitachi.coe.fullstack.model.ProjectUpdateModel;
import com.hitachi.coe.fullstack.model.SkillSetModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.ProjectService;
import com.hitachi.coe.fullstack.util.DateFormatUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ProjectControllerTest {

    @Autowired
    private ProjectController projectController;

    @MockBean
    private ProjectService projectService;

    Date date;

    Date currentDate;

    BusinessDomainModel businessDomain;

    ProjectTypeModel projectType;

    SkillSetModel skillSet1;

    SkillSetModel skillSet2;

    ProjectModel projectModel;

    ProjectUpdateModel projectUpdate;

    @BeforeEach
    void setUp(){

        date = DateFormatUtils.convertTimestampFromString("2023-05-23");
        currentDate = DateFormatUtils.convertTimestampFromString("2023-06-23");

        businessDomain = new BusinessDomainModel();
        businessDomain.setId(1);
        businessDomain.setName("Retail");

        projectType = new ProjectTypeModel();
        projectType.setId(1);
        projectType.setName("OnSemi");

        skillSet1 = new SkillSetModel();
        skillSet1.setId(1);
        skillSet1.setName("Java");

        skillSet2 = new SkillSetModel();
        skillSet2.setId(2);
        skillSet2.setName("C");

        projectModel = new ProjectModel();
        projectModel.setCode("1");
        projectModel.setCustomerName("Nguyen A");
        projectModel.setDescription("description");
        projectModel.setEndDate(date);
        projectModel.setName("OnSemi");
        projectModel.setProjectManager("Pm A");
        projectModel.setStartDate(date);
        projectModel.setStatus(1);
        projectModel.setBusinessDomain(businessDomain);
        projectModel.setProjectType(projectType);
        projectModel.setSkillSets(Arrays.asList(skillSet1, skillSet2));

        projectUpdate = new ProjectUpdateModel();
        projectUpdate.setProjectId(5);
        projectUpdate.setName("OnSemi");
        projectUpdate.setCode("1");
        projectUpdate.setCustomerName("Nguyen A");
        projectUpdate.setDescription("description");
        projectUpdate.setStartDate("2023-05-23");
        projectUpdate.setEndDate("2023-06-23");
        projectUpdate.setProjectManager("Pm a");
        projectUpdate.setStatus(3);
        projectUpdate.setBusinessDomainId(2);
        projectUpdate.setProjectTypeId(1);
        projectUpdate.setProjectsTech(Arrays.asList(1,2,3));
    }

    @Test
    public void testGetProjectDetailById_whenValidData_thenSuccess() {

        when(projectService.getProjectDetailById(1)).thenReturn(projectModel);

        BaseResponse<ProjectModel> result = projectController.getProjectDetailById(1);

        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertNull(result.getMessage());
        assertNotNull(result.getData());
        assertEquals(projectModel, result.getData());
    }

    @Test
    public void testGetProjectDetailById_whenIsNull_thenThrowCoEException() {

        when(projectService.getProjectDetailById(1))
                .thenThrow(new CoEException(ErrorConstant.CODE_PROJECT_NOT_FOUND, ErrorConstant.MESSAGE_PROJECT_NOT_FOUND));

        CoEException exception = assertThrows(CoEException.class, () -> {
            projectController.getProjectDetailById(1);
        });
        assertEquals(CoEException.class, exception.getClass());
        assertEquals(ErrorConstant.MESSAGE_PROJECT_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testUpdateProject_whenValidData_thenSuccess() {

        when(projectService.update(projectUpdate)).thenReturn(projectModel);

        BaseResponse<ProjectModel> result = projectController.updateProject(projectUpdate);

        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertNull(result.getMessage());
        assertNotNull(result.getData());
        assertEquals(projectModel, result.getData());
    }

    @Test
    public void testUpdateProjectStatus_whenValidData_thenSuccess() {

        when(projectService.updateProjectStatus(any(Integer.class), any(Integer.class))).thenReturn(projectModel);

        BaseResponse<ProjectModel> result = projectController.updateProjectStatus(1,3);

        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertNull(result.getMessage());
        assertNotNull(result.getData());
        assertEquals(projectModel, result.getData());
    }

    @Test
    public void testCloseProject_whenValidData_thenSuccess() {

        when(projectService.closeProject(any(Integer.class))).thenReturn(projectModel);

        BaseResponse<ProjectModel> result = projectController.closeProject(1);

        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertNull(result.getMessage());
        assertNotNull(result.getData());
        assertEquals(projectModel, result.getData());
    }
}