package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.constant.CommonConstant;
import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.BusinessDomain;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.entity.ProjectStatus;
import com.hitachi.coe.fullstack.entity.ProjectTech;
import com.hitachi.coe.fullstack.entity.ProjectType;
import com.hitachi.coe.fullstack.entity.SkillSet;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.model.BusinessDomainModel;
import com.hitachi.coe.fullstack.model.IProjectModel;
import com.hitachi.coe.fullstack.model.ProjectModel;
import com.hitachi.coe.fullstack.model.ProjectTypeModel;
import com.hitachi.coe.fullstack.model.ProjectUpdateModel;
import com.hitachi.coe.fullstack.model.SkillSetModel;
import com.hitachi.coe.fullstack.repository.BusinessDomainRepository;
import com.hitachi.coe.fullstack.repository.ProjectRepository;
import com.hitachi.coe.fullstack.repository.ProjectTypeRepository;
import com.hitachi.coe.fullstack.repository.SkillSetRepository;
import com.hitachi.coe.fullstack.transformation.BusinessDomainTransformer;
import com.hitachi.coe.fullstack.transformation.ProjectTransformer;
import com.hitachi.coe.fullstack.transformation.ProjectTypeTransformer;
import com.hitachi.coe.fullstack.transformation.SkillSetTransformer;
import com.hitachi.coe.fullstack.util.DateFormatUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ProjectServiceTests {

    @Autowired
    ProjectService projectService;

    @MockBean
    ProjectRepository projectRepository;

    @MockBean
    BusinessDomainRepository businessDomainRepository;

    @MockBean
    ProjectTypeRepository projectTypeRepository;

    @MockBean
    ProjectTechService projectTechService;

    @MockBean
    SkillSetRepository skillSetRepository;

    @MockBean
    ProjectTransformer projectTransformer;

    @MockBean
    BusinessDomainTransformer businessDomainTransformer;

    @MockBean
    ProjectTypeTransformer projectTypeTransformer;

    @MockBean
    SkillSetTransformer skillSetTransformer;

    Employee employee;

    Project project;

    BusinessDomain businessDomain;

    ProjectType projectType;

    BusinessDomainModel businessDomainModel;

    ProjectTypeModel projectTypeModel;

    ProjectModel projectModel;

    IProjectModel iProjectModelOne;

    IProjectModel iProjectModelTwo;

    Timestamp date;

    Timestamp currentDate;

    Timestamp startDate;

    Timestamp endDate;

    SkillSet skillSet;

    SkillSetModel skillSetModel;

    ProjectUpdateModel projectUpdateModel;

    @BeforeEach
    void setUp(){

        date = new Timestamp(System.currentTimeMillis());
        currentDate = new Timestamp(System.currentTimeMillis());
        startDate = DateFormatUtils.convertTimestampFromString("2023-07-12");
        endDate = DateFormatUtils.convertTimestampFromString("2023-09-20");


        employee = new Employee();
        employee.setId(1);
        employee.setName("nguyenhai");
        employee.setHccId("123456");
        employee.setLdap("78910");

        businessDomain = new BusinessDomain();
        businessDomain.setId(1);

        businessDomainModel = new BusinessDomainModel();
        businessDomainModel.setId(1);

        projectTypeModel = new ProjectTypeModel();
        projectTypeModel.setId(1);

        projectType = new ProjectType();
        projectType.setId(1);

        skillSet = new SkillSet();
        skillSet.setId(1);

        skillSetModel = new SkillSetModel();
        skillSetModel.setId(1);
        skillSetModel.setName("C");
        skillSetModel.setCode("C");
        skillSetModel.setDescription("C description");

        project = new Project();
        project.setId(1);
        project.setName("FSCMS");
        project.setDescription("FSCMS");
        project.setCustomerName("Nguyen Van A");
        project.setProjectManager("Nguyen Van B");
        project.setStatus(ProjectStatus.OPEN);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setUpdated(currentDate);
        project.setUpdatedBy(CommonConstant.CREATED_BY_ADMINISTRATOR);
        project.setBusinessDomain(businessDomain);
        project.setProjectType(projectType);

        projectModel = new ProjectModel();
        projectModel.setId(1);
        projectModel.setName("FSCMS");
        projectModel.setStartDate(date);
        projectModel.setEndDate(currentDate);
        projectModel.setCode("ABC");
        projectModel.setCustomerName("Nguyen ABC");
        projectModel.setDescription("ABC");
        projectModel.setProjectManager("Nguyen ABCD");
        projectModel.setStatus(1);
        projectModel.setBusinessDomain(businessDomainModel);
        projectModel.setProjectType(projectTypeModel);
        projectModel.setSkillSets(List.of(skillSetModel));


        projectUpdateModel = new ProjectUpdateModel();
        projectUpdateModel.setProjectId(1);
        projectUpdateModel.setName("ABC");
        projectUpdateModel.setCode("ABC");
        projectUpdateModel.setCustomerName("Nguyen ABC");
        projectUpdateModel.setDescription("ABC");
        projectUpdateModel.setStartDate("2023-07-12 00:00:00.321");
        projectUpdateModel.setEndDate("2023-09-20 00:00:00.123");
        projectUpdateModel.setProjectManager("Nguyen ABCD");
        projectUpdateModel.setStatus(1);
        projectUpdateModel.setBusinessDomainId(1);
        projectUpdateModel.setProjectTypeId(1);
        projectUpdateModel.setProjectsTech(List.of(1));

        iProjectModelOne = mock(IProjectModel.class);
        iProjectModelTwo = mock(IProjectModel.class);

    }


    @Test
    public void testGetProjectDetail_whenValidData_thenSuccess(){

        when(iProjectModelOne.getProjectId()).thenReturn(1);
        when(iProjectModelOne.getCode()).thenReturn("1");
        when(iProjectModelOne.getProjectName()).thenReturn("OnSemi");
        when(iProjectModelOne.getProjectTypeId()).thenReturn(1);
        when(iProjectModelOne.getStartDate()).thenReturn(date);
        when(iProjectModelOne.getEndDate()).thenReturn(date);
        when(iProjectModelOne.getProjectManager()).thenReturn("Pm A");
        when(iProjectModelOne.getStatus()).thenReturn(1);
        when(iProjectModelOne.getBusinessDomainId()).thenReturn(1);
        when(iProjectModelOne.getSkillSetId()).thenReturn(1);
        when(iProjectModelOne.getDescription()).thenReturn("A software development project for the finance domain");
        when(iProjectModelOne.getCustomerName()).thenReturn("Nguyen A");

        when(iProjectModelTwo.getProjectId()).thenReturn(1);
        when(iProjectModelTwo.getCode()).thenReturn("1");
        when(iProjectModelTwo.getProjectName()).thenReturn("OnSemi");
        when(iProjectModelTwo.getProjectTypeId()).thenReturn(1);
        when(iProjectModelTwo.getStartDate()).thenReturn(date);
        when(iProjectModelTwo.getEndDate()).thenReturn(date);
        when(iProjectModelTwo.getProjectManager()).thenReturn("Pm A");
        when(iProjectModelTwo.getStatus()).thenReturn(1);
        when(iProjectModelTwo.getBusinessDomainId()).thenReturn(1);
        when(iProjectModelTwo.getSkillSetId()).thenReturn(1);
        when(iProjectModelTwo.getDescription()).thenReturn("A software development project for the finance domain");
        when(iProjectModelTwo.getCustomerName()).thenReturn("Nguyen A");

        List<IProjectModel> iProjectModels = Arrays.asList(iProjectModelOne,iProjectModelTwo);

        when(projectRepository.getProjectDetailById(any(Integer.class))).thenReturn(iProjectModels);
        when(projectTypeRepository.findById(any(Integer.class))).thenReturn(Optional.of(projectType));
        when(businessDomainRepository.findById(any(Integer.class))).thenReturn(Optional.of(businessDomain));
        when(businessDomainTransformer.apply(any(BusinessDomain.class))).thenReturn(businessDomainModel);
        when(skillSetRepository.findById(any(Integer.class))).thenReturn(Optional.of(skillSet));
        when(skillSetTransformer.apply(any(SkillSet.class))).thenReturn(skillSetModel);
        when(projectTypeTransformer.apply(any(ProjectType.class))).thenReturn(projectTypeModel);

        ProjectModel projectListActual = projectService.getProjectDetailById(1);

        //Verify
        assertNotNull(projectListActual);
        assertEquals(iProjectModels.get(0).getProjectId(), projectListActual.getId());
        assertEquals(iProjectModels.get(0).getCode(), projectListActual.getCode());
        assertEquals(iProjectModels.get(0).getProjectName(), projectListActual.getName());
        assertEquals(iProjectModels.get(0).getProjectTypeId(), projectListActual.getProjectType().getId());
        assertEquals(iProjectModels.get(0).getSkillSetId(), projectListActual.getSkillSets().get(0).getId());
        assertEquals(iProjectModels.get(0).getBusinessDomainId(), projectListActual.getBusinessDomain().getId());
        assertEquals(iProjectModels.get(0).getProjectManager(), projectListActual.getProjectManager());
        assertEquals(iProjectModels.get(0).getDescription(), projectListActual.getDescription());
        assertEquals(iProjectModels.get(0).getStartDate(), projectListActual.getStartDate());
        assertEquals(iProjectModels.get(0).getEndDate(), projectListActual.getEndDate());
        assertEquals(iProjectModels.get(0).getStatus(), projectListActual.getStatus());
        assertEquals(iProjectModels.get(0).getCustomerName(), projectListActual.getCustomerName());

        assertEquals(iProjectModels.get(1).getProjectId(), projectListActual.getId());
        assertEquals(iProjectModels.get(1).getCode(), projectListActual.getCode());
        assertEquals(iProjectModels.get(1).getProjectName(), projectListActual.getName());
        assertEquals(iProjectModels.get(1).getProjectTypeId(), projectListActual.getProjectType().getId());
        assertEquals(iProjectModels.get(1).getSkillSetId(), projectListActual.getSkillSets().get(1).getId());
        assertEquals(iProjectModels.get(1).getBusinessDomainId(), projectListActual.getBusinessDomain().getId());
        assertEquals(iProjectModels.get(1).getProjectManager(), projectListActual.getProjectManager());
        assertEquals(iProjectModels.get(1).getDescription(), projectListActual.getDescription());
        assertEquals(iProjectModels.get(1).getStartDate(), projectListActual.getStartDate());
        assertEquals(iProjectModels.get(1).getEndDate(), projectListActual.getEndDate());
        assertEquals(iProjectModels.get(1).getStatus(), projectListActual.getStatus());
        assertEquals(iProjectModels.get(1).getCustomerName(), projectListActual.getCustomerName());


    }

    @Test
    public void testGetProjectDetail_whenBusinessDomainNotFound_thenThrowCoEException(){

        when(iProjectModelOne.getProjectId()).thenReturn(1);
        when(iProjectModelOne.getCode()).thenReturn("1");
        when(iProjectModelOne.getProjectName()).thenReturn("OnSemi");
        when(iProjectModelOne.getProjectTypeId()).thenReturn(1);
        when(iProjectModelOne.getStartDate()).thenReturn(date);
        when(iProjectModelOne.getEndDate()).thenReturn(date);
        when(iProjectModelOne.getProjectManager()).thenReturn("Pm A");
        when(iProjectModelOne.getStatus()).thenReturn(1);
        when(iProjectModelOne.getBusinessDomainId()).thenReturn(1);
        when(iProjectModelOne.getSkillSetId()).thenReturn(1);
        when(iProjectModelOne.getDescription()).thenReturn("A software development project for the finance domain");
        when(iProjectModelOne.getCustomerName()).thenReturn("Nguyen A");

        when(iProjectModelTwo.getProjectId()).thenReturn(1);
        when(iProjectModelTwo.getCode()).thenReturn("1");
        when(iProjectModelTwo.getProjectName()).thenReturn("OnSemi");
        when(iProjectModelTwo.getProjectTypeId()).thenReturn(1);
        when(iProjectModelTwo.getStartDate()).thenReturn(date);
        when(iProjectModelTwo.getEndDate()).thenReturn(date);
        when(iProjectModelTwo.getProjectManager()).thenReturn("Pm A");
        when(iProjectModelTwo.getStatus()).thenReturn(1);
        when(iProjectModelTwo.getBusinessDomainId()).thenReturn(1);
        when(iProjectModelTwo.getSkillSetId()).thenReturn(1);
        when(iProjectModelTwo.getDescription()).thenReturn("A software development project for the finance domain");
        when(iProjectModelTwo.getCustomerName()).thenReturn("Nguyen A");

        List<IProjectModel> iProjectModels = Arrays.asList(iProjectModelOne,iProjectModelTwo);

        when(projectRepository.getProjectDetailById(any(Integer.class))).thenReturn(iProjectModels);
        when(projectTypeRepository.findById(any(Integer.class))).thenReturn(Optional.of(projectType));
        when(businessDomainRepository.findById(any(Integer.class))).thenReturn(Optional.empty());


        Throwable throwable = assertThrows(CoEException.class,
                () -> projectService.getProjectDetailById(1));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_BUSINESS_DOMAIN_DO_NOT_EXIST, throwable.getMessage());

    }

    @Test
    public void testGetProjectDetail_whenBusinessDomainIsNull_thenSuccess(){

        when(iProjectModelOne.getProjectId()).thenReturn(1);
        when(iProjectModelOne.getCode()).thenReturn("1");
        when(iProjectModelOne.getProjectName()).thenReturn("OnSemi");
        when(iProjectModelOne.getProjectTypeId()).thenReturn(1);
        when(iProjectModelOne.getStartDate()).thenReturn(date);
        when(iProjectModelOne.getEndDate()).thenReturn(date);
        when(iProjectModelOne.getProjectManager()).thenReturn("Pm A");
        when(iProjectModelOne.getStatus()).thenReturn(1);
        when(iProjectModelOne.getBusinessDomainId()).thenReturn(null);
        when(iProjectModelOne.getSkillSetId()).thenReturn(1);
        when(iProjectModelOne.getDescription()).thenReturn("A software development project for the finance domain");
        when(iProjectModelOne.getCustomerName()).thenReturn("Nguyen A");

        when(iProjectModelTwo.getProjectId()).thenReturn(1);
        when(iProjectModelTwo.getCode()).thenReturn("1");
        when(iProjectModelTwo.getProjectName()).thenReturn("OnSemi");
        when(iProjectModelTwo.getProjectTypeId()).thenReturn(1);
        when(iProjectModelTwo.getStartDate()).thenReturn(date);
        when(iProjectModelTwo.getEndDate()).thenReturn(date);
        when(iProjectModelTwo.getProjectManager()).thenReturn("Pm A");
        when(iProjectModelTwo.getStatus()).thenReturn(1);
        when(iProjectModelTwo.getBusinessDomainId()).thenReturn(null);
        when(iProjectModelTwo.getSkillSetId()).thenReturn(1);
        when(iProjectModelTwo.getDescription()).thenReturn("A software development project for the finance domain");
        when(iProjectModelTwo.getCustomerName()).thenReturn("Nguyen A");

        List<IProjectModel> iProjectModels = Arrays.asList(iProjectModelOne,iProjectModelTwo);

        when(projectRepository.getProjectDetailById(any(Integer.class))).thenReturn(iProjectModels);
        when(projectTypeRepository.findById(any(Integer.class))).thenReturn(Optional.of(projectType));
        when(businessDomainRepository.findById(any(Integer.class))).thenReturn(Optional.of(businessDomain));
        when(businessDomainTransformer.apply(any(BusinessDomain.class))).thenReturn(businessDomainModel);
        when(skillSetRepository.findById(any(Integer.class))).thenReturn(Optional.of(skillSet));
        when(skillSetTransformer.apply(any(SkillSet.class))).thenReturn(skillSetModel);
        when(projectTypeTransformer.apply(any(ProjectType.class))).thenReturn(projectTypeModel);

        ProjectModel projectListActual = projectService.getProjectDetailById(1);

        //Verify
        assertNotNull(projectListActual);
        assertEquals(iProjectModels.get(0).getProjectId(), projectListActual.getId());
        assertEquals(iProjectModels.get(0).getCode(), projectListActual.getCode());
        assertEquals(iProjectModels.get(0).getProjectName(), projectListActual.getName());
        assertEquals(iProjectModels.get(0).getProjectTypeId(), projectListActual.getProjectType().getId());
        assertEquals(iProjectModels.get(0).getSkillSetId(), projectListActual.getSkillSets().get(0).getId());
        assertEquals(iProjectModels.get(0).getBusinessDomainId(), projectListActual.getBusinessDomain().getId());
        assertEquals(iProjectModels.get(0).getProjectManager(), projectListActual.getProjectManager());
        assertEquals(iProjectModels.get(0).getDescription(), projectListActual.getDescription());
        assertEquals(iProjectModels.get(0).getStartDate(), projectListActual.getStartDate());
        assertEquals(iProjectModels.get(0).getEndDate(), projectListActual.getEndDate());
        assertEquals(iProjectModels.get(0).getStatus(), projectListActual.getStatus());
        assertEquals(iProjectModels.get(0).getCustomerName(), projectListActual.getCustomerName());

        assertEquals(iProjectModels.get(1).getProjectId(), projectListActual.getId());
        assertEquals(iProjectModels.get(1).getCode(), projectListActual.getCode());
        assertEquals(iProjectModels.get(1).getProjectName(), projectListActual.getName());
        assertEquals(iProjectModels.get(1).getProjectTypeId(), projectListActual.getProjectType().getId());
        assertEquals(iProjectModels.get(1).getSkillSetId(), projectListActual.getSkillSets().get(1).getId());
        assertEquals(iProjectModels.get(1).getBusinessDomainId(), projectListActual.getBusinessDomain().getId());
        assertEquals(iProjectModels.get(1).getProjectManager(), projectListActual.getProjectManager());
        assertEquals(iProjectModels.get(1).getDescription(), projectListActual.getDescription());
        assertEquals(iProjectModels.get(1).getStartDate(), projectListActual.getStartDate());
        assertEquals(iProjectModels.get(1).getEndDate(), projectListActual.getEndDate());
        assertEquals(iProjectModels.get(1).getStatus(), projectListActual.getStatus());
        assertEquals(iProjectModels.get(1).getCustomerName(), projectListActual.getCustomerName());

    }

    @Test
    public void testGetProjectDetail_whenProjectTypeNotFound_thenThrowCoEException(){

        when(iProjectModelOne.getProjectId()).thenReturn(1);
        when(iProjectModelOne.getCode()).thenReturn("1");
        when(iProjectModelOne.getProjectName()).thenReturn("OnSemi");
        when(iProjectModelOne.getProjectTypeId()).thenReturn(1);
        when(iProjectModelOne.getStartDate()).thenReturn(date);
        when(iProjectModelOne.getEndDate()).thenReturn(date);
        when(iProjectModelOne.getProjectManager()).thenReturn("Pm A");
        when(iProjectModelOne.getStatus()).thenReturn(1);
        when(iProjectModelOne.getBusinessDomainId()).thenReturn(1);
        when(iProjectModelOne.getSkillSetId()).thenReturn(1);
        when(iProjectModelOne.getDescription()).thenReturn("A software development project for the finance domain");
        when(iProjectModelOne.getCustomerName()).thenReturn("Nguyen A");

        when(iProjectModelTwo.getProjectId()).thenReturn(1);
        when(iProjectModelTwo.getCode()).thenReturn("1");
        when(iProjectModelTwo.getProjectName()).thenReturn("OnSemi");
        when(iProjectModelTwo.getProjectTypeId()).thenReturn(1);
        when(iProjectModelTwo.getStartDate()).thenReturn(date);
        when(iProjectModelTwo.getEndDate()).thenReturn(date);
        when(iProjectModelTwo.getProjectManager()).thenReturn("Pm A");
        when(iProjectModelTwo.getStatus()).thenReturn(1);
        when(iProjectModelTwo.getBusinessDomainId()).thenReturn(1);
        when(iProjectModelTwo.getSkillSetId()).thenReturn(1);
        when(iProjectModelTwo.getDescription()).thenReturn("A software development project for the finance domain");
        when(iProjectModelTwo.getCustomerName()).thenReturn("Nguyen A");

        List<IProjectModel> iProjectModels = Arrays.asList(iProjectModelOne,iProjectModelTwo);

        when(projectRepository.getProjectDetailById(any(Integer.class))).thenReturn(iProjectModels);
        when(projectTypeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(businessDomainRepository.findById(any(Integer.class))).thenReturn(Optional.of(businessDomain));


        Throwable throwable = assertThrows(CoEException.class,
                () -> projectService.getProjectDetailById(1));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_PROJECT_TYPE_DO_NOT_EXIST, throwable.getMessage());

    }

    @Test
    public void testGetProjectDetail_whenSKillSetNotFound_thenThrowCoEException(){

        when(iProjectModelOne.getProjectId()).thenReturn(1);
        when(iProjectModelOne.getCode()).thenReturn("1");
        when(iProjectModelOne.getProjectName()).thenReturn("OnSemi");
        when(iProjectModelOne.getProjectTypeId()).thenReturn(1);
        when(iProjectModelOne.getStartDate()).thenReturn(date);
        when(iProjectModelOne.getEndDate()).thenReturn(date);
        when(iProjectModelOne.getProjectManager()).thenReturn("Pm A");
        when(iProjectModelOne.getStatus()).thenReturn(1);
        when(iProjectModelOne.getBusinessDomainId()).thenReturn(1);
        when(iProjectModelOne.getSkillSetId()).thenReturn(1);
        when(iProjectModelOne.getDescription()).thenReturn("A software development project for the finance domain");
        when(iProjectModelOne.getCustomerName()).thenReturn("Nguyen A");

        when(iProjectModelTwo.getProjectId()).thenReturn(1);
        when(iProjectModelTwo.getCode()).thenReturn("1");
        when(iProjectModelTwo.getProjectName()).thenReturn("OnSemi");
        when(iProjectModelTwo.getProjectTypeId()).thenReturn(1);
        when(iProjectModelTwo.getStartDate()).thenReturn(date);
        when(iProjectModelTwo.getEndDate()).thenReturn(date);
        when(iProjectModelTwo.getProjectManager()).thenReturn("Pm A");
        when(iProjectModelTwo.getStatus()).thenReturn(1);
        when(iProjectModelTwo.getBusinessDomainId()).thenReturn(1);
        when(iProjectModelTwo.getSkillSetId()).thenReturn(1);
        when(iProjectModelTwo.getDescription()).thenReturn("A software development project for the finance domain");
        when(iProjectModelTwo.getCustomerName()).thenReturn("Nguyen A");

        List<IProjectModel> iProjectModels = Arrays.asList(iProjectModelOne,iProjectModelTwo);

        when(projectRepository.getProjectDetailById(any(Integer.class))).thenReturn(iProjectModels);
        when(projectTypeRepository.findById(any(Integer.class))).thenReturn(Optional.of(projectType));
        when(businessDomainRepository.findById(any(Integer.class))).thenReturn(Optional.of(businessDomain));
        when(businessDomainTransformer.apply(any(BusinessDomain.class))).thenReturn(businessDomainModel);
        when(skillSetRepository.findById(any(Integer.class))).thenReturn(Optional.empty());


        Throwable throwable = assertThrows(CoEException.class,
                () -> projectService.getProjectDetailById(1));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_SKILL_SET_DO_NOT_EXIST, throwable.getMessage());

    }

    @Test
    public void testGetProjectDetail_whenIsNull_thenThrowCoEException(){

        Throwable throwable = assertThrows(CoEException.class,
                () -> projectService.getProjectDetailById(1));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_PROJECT_NOT_FOUND, throwable.getMessage());
    }

    @Test
    public void testUpdate_whenValidData_thenSuccess(){

        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(businessDomainRepository.findById(any(Integer.class))).thenReturn(Optional.of(businessDomain));
        when(projectTypeRepository.findById(any(Integer.class))).thenReturn(Optional.of(projectType));
        when(projectRepository.findByCode(any(String.class))).thenReturn(project);
        doNothing().when(projectTechService).deleteProjectTechByProject(any(Integer.class));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(skillSetRepository.findById(any(Integer.class))).thenReturn(Optional.of(skillSet));
        doNothing().when(projectTechService).saveProjectTech(any(ProjectTech.class));
        when(projectTransformer.apply(any(Project.class))).thenReturn(projectModel);

        ProjectModel projectModelActual = projectService.update(projectUpdateModel);

        assertNotNull(projectModelActual);
        assertEquals(projectModel.getSkillSets().size(), projectModelActual.getSkillSets().size());
        assertEquals(projectModel.getCode(), projectModelActual.getCode());
        assertEquals(projectModel.getName(), projectModelActual.getName());
        assertEquals(projectModel.getDescription(), projectModelActual.getDescription());
        assertEquals(projectModel.getCustomerName(), projectModelActual.getCustomerName());
        assertEquals(projectModel.getProjectManager(), projectModelActual.getProjectManager());
        assertEquals(projectModel.getStatus(), projectModelActual.getStatus());
        assertEquals(projectModel.getStartDate(), projectModelActual.getStartDate());
        assertEquals(projectModel.getEndDate(), projectModelActual.getEndDate());
        assertEquals(projectModel.getUpdated(), projectModelActual.getUpdated());
        assertEquals(projectModel.getUpdatedBy(), projectModelActual.getUpdatedBy());
        assertEquals(projectModel.getBusinessDomain(), projectModelActual.getBusinessDomain());
        assertEquals(projectModel.getProjectType(), projectModelActual.getProjectType());
        assertEquals(projectModel.getSkillSets().get(0).getCode(), projectModelActual.getSkillSets().get(0).getCode());
        assertEquals(projectModel.getSkillSets().get(0).getDescription(), projectModelActual.getSkillSets().get(0).getDescription());
        assertEquals(projectModel.getSkillSets().get(0).getName(), projectModelActual.getSkillSets().get(0).getName());

    }

    @Test
    public void testUpdate_whenProjectNotFound_thenThrowCoEException(){

        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(businessDomainRepository.findById(any(Integer.class))).thenReturn(Optional.of(businessDomain));
        when(projectTypeRepository.findById(any(Integer.class))).thenReturn(Optional.of(projectType));
        when(projectRepository.findByCode(any(String.class))).thenReturn(project);


        Throwable throwable = assertThrows(CoEException.class,
                () -> projectService.update(projectUpdateModel));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_PROJECT_NOT_FOUND, throwable.getMessage());

    }

    @Test
    public void testUpdate_whenEndDateBeforeStartDate_thenThrowCoEException(){

        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(businessDomainRepository.findById(any(Integer.class))).thenReturn(Optional.of(businessDomain));
        when(projectTypeRepository.findById(any(Integer.class))).thenReturn(Optional.of(projectType));
        when(projectRepository.findByCode(any(String.class))).thenReturn(project);

        projectUpdateModel.setStartDate("2023-07-12 00:00:00.321");
        projectUpdateModel.setEndDate("2023-07-11 00:00:00.123");


        Throwable throwable = assertThrows(CoEException.class,
                () -> projectService.update(projectUpdateModel));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_INVALID_START_DATE_END_DATE, throwable.getMessage());

    }

    @Test
    public void testUpdate_whenProjectTypeNotFound_thenThrowCoEException(){

        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(businessDomainRepository.findById(any(Integer.class))).thenReturn(Optional.of(businessDomain));
        when(projectTypeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(projectRepository.findByCode(any(String.class))).thenReturn(project);


        Throwable throwable = assertThrows(CoEException.class,
                () -> projectService.update(projectUpdateModel));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_PROJECT_TYPE_DO_NOT_EXIST, throwable.getMessage());

    }

    @Test
    public void testUpdate_whenBusinessDomainNotFound_thenThrowCoEException(){

        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(businessDomainRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(projectTypeRepository.findById(any(Integer.class))).thenReturn(Optional.of(projectType));
        when(projectRepository.findByCode(any(String.class))).thenReturn(project);


        Throwable throwable = assertThrows(CoEException.class,
                () -> projectService.update(projectUpdateModel));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_BUSINESS_DOMAIN_DO_NOT_EXIST, throwable.getMessage());

    }

    @Test
    public void testUpdate_whenSkillSetNotFound_thenThrowCoEException(){

        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(businessDomainRepository.findById(any(Integer.class))).thenReturn(Optional.of(businessDomain));
        when(projectTypeRepository.findById(any(Integer.class))).thenReturn(Optional.of(projectType));
        when(projectRepository.findByCode(any(String.class))).thenReturn(project);
        doNothing().when(projectTechService).deleteProjectTechByProject(any(Integer.class));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(skillSetRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(CoEException.class,
                () -> projectService.update(projectUpdateModel));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_SKILL_SET_DO_NOT_EXIST, throwable.getMessage());

    }

    @Test
    public void testUpdate_whenDuplicateProjectCode_thenThrowCoEException(){

        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(businessDomainRepository.findById(any(Integer.class))).thenReturn(Optional.of(businessDomain));
        when(projectTypeRepository.findById(any(Integer.class))).thenReturn(Optional.of(projectType));
        when(projectRepository.findByCode(any(String.class))).thenReturn(null);
        doNothing().when(projectTechService).deleteProjectTechByProject(any(Integer.class));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(skillSetRepository.findById(any(Integer.class))).thenReturn(Optional.of(skillSet));
        doNothing().when(projectTechService).saveProjectTech(any(ProjectTech.class));
        when(projectTransformer.apply(any(Project.class))).thenReturn(projectModel);

        ProjectModel projectModelActual = projectService.update(projectUpdateModel);

        assertNotNull(projectModelActual);
        assertEquals(projectModel.getSkillSets().size(), projectModelActual.getSkillSets().size());
        assertEquals(projectModel.getCode(), projectModelActual.getCode());
        assertEquals(projectModel.getName(), projectModelActual.getName());
        assertEquals(projectModel.getDescription(), projectModelActual.getDescription());
        assertEquals(projectModel.getCustomerName(), projectModelActual.getCustomerName());
        assertEquals(projectModel.getProjectManager(), projectModelActual.getProjectManager());
        assertEquals(projectModel.getStatus(), projectModelActual.getStatus());
        assertEquals(projectModel.getStartDate(), projectModelActual.getStartDate());
        assertEquals(projectModel.getEndDate(), projectModelActual.getEndDate());
        assertEquals(projectModel.getUpdated(), projectModelActual.getUpdated());
        assertEquals(projectModel.getUpdatedBy(), projectModelActual.getUpdatedBy());
        assertEquals(projectModel.getBusinessDomain(), projectModelActual.getBusinessDomain());
        assertEquals(projectModel.getProjectType(), projectModelActual.getProjectType());
        assertEquals(projectModel.getSkillSets().get(0).getCode(), projectModelActual.getSkillSets().get(0).getCode());
        assertEquals(projectModel.getSkillSets().get(0).getDescription(), projectModelActual.getSkillSets().get(0).getDescription());
        assertEquals(projectModel.getSkillSets().get(0).getName(), projectModelActual.getSkillSets().get(0).getName());

    }

    @Test
    public void testUpdateProjectStatus_whenValidData_thenSuccess(){

        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectTransformer.apply(any(Project.class))).thenReturn(projectModel);

        ProjectModel projectModelActual = projectService.updateProjectStatus(1,1);

        assertNotNull(projectModelActual);
        assertEquals(projectModel.getStatus(), projectModelActual.getStatus());
        assertEquals(projectModel.getUpdated(), projectModelActual.getUpdated());
        assertEquals(projectModel.getUpdatedBy(), projectModelActual.getUpdatedBy());

    }

    @Test
    public void testUpdateProjectStatus_whenProjectNotFound_thenThrowCoEException(){

        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(CoEException.class,
                () -> projectService.updateProjectStatus(1,1));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_PROJECT_NOT_FOUND, throwable.getMessage());

    }

    @Test
    public void testCloseProject_whenValidData_thenSuccess(){

        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectTransformer.apply(any(Project.class))).thenReturn(projectModel);

        ProjectModel projectModelActual = projectService.closeProject(1);

        assertNotNull(projectModelActual);
        assertEquals(projectModel.getSkillSets().size(), projectModelActual.getSkillSets().size());
        assertEquals(projectModel.getCode(), projectModelActual.getCode());
        assertEquals(projectModel.getName(), projectModelActual.getName());
        assertEquals(projectModel.getDescription(), projectModelActual.getDescription());
        assertEquals(projectModel.getCustomerName(), projectModelActual.getCustomerName());
        assertEquals(projectModel.getProjectManager(), projectModelActual.getProjectManager());
        assertEquals(projectModel.getStatus(), projectModelActual.getStatus());
        assertEquals(projectModel.getStartDate(), projectModelActual.getStartDate());
        assertEquals(projectModel.getEndDate(), projectModelActual.getEndDate());
        assertEquals(projectModel.getUpdated(), projectModelActual.getUpdated());
        assertEquals(projectModel.getUpdatedBy(), projectModelActual.getUpdatedBy());
        assertEquals(projectModel.getBusinessDomain(), projectModelActual.getBusinessDomain());
        assertEquals(projectModel.getProjectType(), projectModelActual.getProjectType());
        assertEquals(projectModel.getSkillSets().get(0).getCode(), projectModelActual.getSkillSets().get(0).getCode());
        assertEquals(projectModel.getSkillSets().get(0).getDescription(), projectModelActual.getSkillSets().get(0).getDescription());
        assertEquals(projectModel.getSkillSets().get(0).getName(), projectModelActual.getSkillSets().get(0).getName());

    }

    @Test
    public void testCloseProject_whenProjectNotFound_thenThrowCoEException(){

        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(CoEException.class,
                () -> projectService.closeProject(1));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_PROJECT_NOT_FOUND, throwable.getMessage());

    }

    @Test
    public void testCloseProject_whenProjectAlreadyClose_thenThrowCoEException(){

        project.setStatus(ProjectStatus.CLOSE);
        when(projectRepository.findById(any(Integer.class))).thenReturn(Optional.of(project));

        Throwable throwable = assertThrows(CoEException.class,
                () -> projectService.closeProject(1));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_PROJECT_ALREADY_CLOSE, throwable.getMessage());

    }
}
