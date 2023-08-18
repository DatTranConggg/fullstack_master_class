package com.hitachi.coe.fullstack.transformation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hitachi.coe.fullstack.entity.EmployeeSkill;
import com.hitachi.coe.fullstack.entity.SkillSet;
import com.hitachi.coe.fullstack.model.EmployeeSkillModel;

@SpringBootTest
public class EmployeeSkillTransformerTest {

	@Autowired
	private EmployeeSkillTransformer employeeSkillTransformer;
	
	@Test 
	void testEmployeeSkillTransformer_Apply_thenSuccess() {
		EmployeeSkill employeeSkill = new EmployeeSkill();
		employeeSkill.setId(1);
		employeeSkill.setSkillLevel(1);
		employeeSkill.setSkillSetDate(new Timestamp(System.currentTimeMillis()));
		
		SkillSet skillSet = new SkillSet();
		skillSet.setId(1);
		employeeSkill.setSkillSet(skillSet);
		
		EmployeeSkillModel employeeSkillModel = employeeSkillTransformer.apply(employeeSkill);
		
		assertEquals(1, employeeSkillModel.getId());
	}
	
	@Test 
	void testEmployeeSkillTransformer_ApplyList_thenSuccess() {
		EmployeeSkill employeeSkill = new EmployeeSkill();
		employeeSkill.setId(1);
		employeeSkill.setSkillLevel(1);
		employeeSkill.setSkillSetDate(new Timestamp(System.currentTimeMillis()));
		
		SkillSet skillSet = new SkillSet();
		skillSet.setId(1);
		employeeSkill.setSkillSet(skillSet);
		
		List<EmployeeSkill> employeeSkills = Arrays.asList(employeeSkill);
		List<EmployeeSkillModel> employeeSkillModels = employeeSkillTransformer.applyList(employeeSkills);
		
		assertEquals(1, employeeSkillModels.size());
	}

	@Test
	void testEmployeeSkillTransformer_ApplyList_EmptyList() {
		List<EmployeeSkill> employeeSkills = Collections.emptyList();
		List<EmployeeSkillModel> result = employeeSkillTransformer.applyList(employeeSkills);

		assertTrue(result.isEmpty());
	}
}
