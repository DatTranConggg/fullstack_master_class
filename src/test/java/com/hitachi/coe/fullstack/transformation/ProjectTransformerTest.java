package com.hitachi.coe.fullstack.transformation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hitachi.coe.fullstack.entity.BusinessDomain;
import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.entity.ProjectStatus;
import com.hitachi.coe.fullstack.model.ProjectModel;

@SpringBootTest
public class ProjectTransformerTest {
	@Autowired
	ProjectTransformer projectTransformer;
	@Test
	void testApply() {
	    Project project = new Project();
	    project.setId(20);
	    project.setCode("project 1");
	    project.setCustomerName("ABC Company");
	    project.setDescription("Project for ABC Company");
	    Date startDate = new Date();
	    Date endDate = new Date(startDate.getTime() + 86400000); // Thêm một ngày
	    project.setStartDate(startDate);
	    project.setEndDate(endDate);
	    project.setName("ABC Project");
	    project.setProjectManager("Uchiha Itachi");
	    project.setStatus(ProjectStatus.CLOSE);
	    BusinessDomain businessDomain = new BusinessDomain();
	    businessDomain.setId(10);
	    project.setBusinessDomain(businessDomain);
	    
	    ProjectModel projectModel = projectTransformer.apply(project);
	    
	    assertEquals(20, projectModel.getId());
	    assertEquals("project 1", projectModel.getCode());
	    assertEquals("ABC Company", projectModel.getCustomerName());
	    assertEquals("Project for ABC Company", projectModel.getDescription());
	    assertEquals(endDate, projectModel.getEndDate());
	    assertEquals("ABC Project", projectModel.getName());
	    assertEquals("Uchiha Itachi", projectModel.getProjectManager());
	    assertEquals(startDate, projectModel.getStartDate());
	    //assertEquals(0, projectModel.getStatus());
	    //assertEquals(10, projectModel.getBusinessDomainId());
	}
	
	@Test
	void testProjectTransformerApplyList() {
	    List<Project> projects = new ArrayList<>();
	    Random random = new Random();
	    long randomMilliseconds = random.nextLong();
	    Date randomDate = new Date(randomMilliseconds);
	    BusinessDomain businessDomain = new BusinessDomain();
	    businessDomain.setId(10);
	    Project project1 = new Project();
	    project1.setCode("project 1");
	    project1.setId(20);
	    project1.setProjectManager("Uchiha Itachi");
	    project1.setEndDate(randomDate);
	    project1.setName("project heheh");
	    project1.setStartDate(randomDate);
	    project1.setStatus(ProjectStatus.CLOSE);
	    project1.setBusinessDomain(businessDomain);
	    projects.add(project1); 
    List<ProjectModel> projectModels = projectTransformer.applyList(projects);
	    
	    // Kiểm tra kết quả
	    assertEquals(1, projectModels.size());
	    ProjectModel projectModel = projectModels.get(0);
	    assertEquals("project 1", projectModel.getCode());
	    assertEquals(20, projectModel.getId());
	    assertEquals("Uchiha Itachi", projectModel.getProjectManager());
	    assertEquals(randomDate, projectModel.getEndDate());
	    assertEquals("project heheh", projectModel.getName());
	    assertEquals(randomDate, projectModel.getStartDate());
	    //assertEquals(0, projectModel.getStatus());
	    //assertEquals(10, projectModel.getBusinessDomainId());
	}
	
	@Test
	public void testTransformerBusinessDomainApplyList_EmptyList() {
		List<Project> models = Collections.emptyList();

		List<ProjectModel> result = projectTransformer.applyList(models);

		Assertions.assertTrue(result.isEmpty());
	}
}
