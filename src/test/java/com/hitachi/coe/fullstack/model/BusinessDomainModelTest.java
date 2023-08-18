package com.hitachi.coe.fullstack.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusinessDomainModelTest {

	@Test
	public void testModelBusinessDomain() {
		PracticeModel practiceModel = new PracticeModel();
		practiceModel.setId(11);
		practiceModel.setCode("hihi");
		practiceModel.setDescription("hehe");
		practiceModel.setManager("nguyen van teo");

		Random random = new Random();
		long randomMilliseconds = random.nextLong();
		Date randomDate = new Date(randomMilliseconds);
		ProjectModel projectModel1 = new ProjectModel();
		projectModel1.setCode("projet1");
		projectModel1.setCustomerName("Nguyen van A");
		projectModel1.setDescription("project vip pro");
		projectModel1.setEndDate(randomDate);
		projectModel1.setName("project 1 A");
		projectModel1.setProjectManager("Nguyen van B");
		projectModel1.setStartDate(randomDate);
		//projectModel1.setStatus(0);
		
		Random random2 = new Random();
		long randomMilliseconds2 = random2.nextLong();
		Date randomDate2 = new Date(randomMilliseconds2);
		ProjectModel projectModel2 = new ProjectModel();
		projectModel2.setCode("projet2");
		projectModel2.setCustomerName("Nguyen van C");
		projectModel2.setDescription("project vip pro v2");
		projectModel2.setEndDate(randomDate2);
		projectModel2.setName("project 2 A");
		projectModel2.setProjectManager("Nguyen van D");
		projectModel2.setStartDate(randomDate2);
		//projectModel2.setStatus(0);
		
		
		List<ProjectModel> listProject = new ArrayList<ProjectModel>();
		listProject.add(projectModel1);
		listProject.add(projectModel2);
		BusinessDomainModel businessDomainModel = new BusinessDomainModel();
		businessDomainModel.setId(20);
		businessDomainModel.setCode("abc");
		businessDomainModel.setDescription("domain model");
		businessDomainModel.setName("abc.com");
		businessDomainModel.setPracticeId(15);
		businessDomainModel.setProjects(listProject);
		
		assertEquals(20, businessDomainModel.getId());
		assertEquals("abc", businessDomainModel.getCode());
		assertEquals("domain model", businessDomainModel.getDescription());
		assertEquals("abc.com",businessDomainModel.getName());
		assertEquals(15,businessDomainModel.getPracticeId());
		assertEquals(listProject, businessDomainModel.getProjects());
	}
}
