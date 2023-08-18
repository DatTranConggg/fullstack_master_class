package com.hitachi.coe.fullstack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
public class EmployeeSkillModelTest {

	@Test
	void testEmployeeSkillModel_thenSuccess() {
		EmployeeSkillModel employeeSkillModel = new EmployeeSkillModel();

		employeeSkillModel.setId(1);
		employeeSkillModel.setSkillSetDate(new Timestamp(System.currentTimeMillis()));
		employeeSkillModel.setSkillLevel(1);
		SkillSetModel skill = new SkillSetModel();
		skill.setCode("177013");
		skill.setDescription("Hello");
		skill.setName("coe");
		employeeSkillModel.setSkillSet(skill);

		assertEquals(employeeSkillModel.getId(), 1);
		assertEquals(employeeSkillModel.getSkillLevel(), 1);
		assertNotNull(employeeSkillModel.getSkillSetDate());
		assertEquals(employeeSkillModel.getSkillSet().getCode(), "177013");
	}
}
