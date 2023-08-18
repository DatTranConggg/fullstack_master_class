package com.hitachi.coe.fullstack.transformation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hitachi.coe.fullstack.entity.BusinessDomain;
import com.hitachi.coe.fullstack.entity.Practice;
import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.entity.ProjectStatus;
import com.hitachi.coe.fullstack.model.BusinessDomainModel;
import com.hitachi.coe.fullstack.model.ProjectModel;
 
@SpringBootTest
public class BusinessDomainTransformerTest {
	@Autowired
	private BusinessDomainTransformer businessDomainTransformer;

	@Test
	void testTransformerBusinessDomainApply() {
		BusinessDomain businessDomain = new BusinessDomain();
		businessDomain.setId(10);
		Random random = new Random();
		long randomMilliseconds = random.nextLong();
		Date randomDate = new Date(randomMilliseconds);
		Practice practice = new Practice();
		practice.setId(15);
		Project project1 = new Project();
		project1.setCode("project 1");
		project1.setId(20);
		project1.setProjectManager("Uchiha Itachi");
		project1.setEndDate(randomDate);
		project1.setName("project heheh");
		project1.setStartDate(randomDate);
		project1.setStatus(ProjectStatus.CLOSE);
		project1.setBusinessDomain(businessDomain);

		businessDomain.setCode("abcd");
		businessDomain.setDescription("abcd domain");
		businessDomain.setName("abcd.com");
		businessDomain.setPractice(practice);
		businessDomain.setProjects(Arrays.asList(project1));

		BusinessDomainModel result = businessDomainTransformer.apply(businessDomain);

		assertEquals("abcd", result.getCode());
		assertEquals(10, result.getId());
		assertEquals("abcd domain", result.getDescription());
		assertEquals("abcd.com", result.getName());
		assertEquals(15, result.getPracticeId());
		assertEquals(20, result.getProjects().get(0).getId());
		assertEquals("project 1", result.getProjects().get(0).getCode());
		assertEquals("Uchiha Itachi", result.getProjects().get(0).getProjectManager());
		assertEquals(randomDate, result.getProjects().get(0).getEndDate());
	}

	@Test
	void testTransformerBusinessDomainApplyList() {
		// Tạo danh sách các đối tượng BusinessDomain
		List<BusinessDomain> businessDomains = new ArrayList<>();
		BusinessDomain businessDomain1 = new BusinessDomain();
		businessDomain1.setId(10);
		businessDomain1.setCode("abcd");
		businessDomain1.setDescription("abcd domain");
		businessDomain1.setName("abcd.com");
		Practice practice = new Practice();
		practice.setId(15);
		businessDomain1.setPractice(practice);
		Project project1 = new Project();
		project1.setCode("project 1");
		project1.setId(20);
		project1.setProjectManager("Uchiha Itachi");
		Date randomDate = new Date();
		project1.setEndDate(randomDate);
		project1.setName("project heheh");
		project1.setStartDate(randomDate);
		project1.setStatus(ProjectStatus.CLOSE);
		project1.setBusinessDomain(businessDomain1);
		businessDomain1.setProjects(Arrays.asList(project1));
		businessDomains.add(businessDomain1);

		List<BusinessDomainModel> businessDomainModels = businessDomainTransformer.applyList(businessDomains);

		assertEquals(1, businessDomainModels.size());
		BusinessDomainModel businessDomainModel = businessDomainModels.get(0);
		assertEquals("abcd", businessDomainModel.getCode());
		assertEquals(10, businessDomainModel.getId());
		assertEquals("abcd domain", businessDomainModel.getDescription());
		assertEquals("abcd.com", businessDomainModel.getName());
		assertEquals(15, businessDomainModel.getPracticeId());
		assertEquals(1, businessDomainModel.getProjects().size());
		ProjectModel projectModel = businessDomainModel.getProjects().get(0);
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
		List<BusinessDomain> models = Collections.emptyList();

		List<BusinessDomainModel> result = businessDomainTransformer.applyList(models);

		Assertions.assertTrue(result.isEmpty());
	}
}
