package com.hitachi.coe.fullstack.transformation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hitachi.coe.fullstack.entity.Branch;
import com.hitachi.coe.fullstack.entity.BusinessUnit;
import com.hitachi.coe.fullstack.entity.CenterOfExcellence;
import com.hitachi.coe.fullstack.entity.CoeCoreTeam;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeSkill;
import com.hitachi.coe.fullstack.entity.Location;
import com.hitachi.coe.fullstack.entity.SkillSet;
import com.hitachi.coe.fullstack.model.EmployeeModel;

@SpringBootTest
class EmployeeTransformerTests {
	@Autowired
	private EmployeeTransformer employeeTransformer;

	@Test
	void testTransformerEmployeeApply() {

		Employee employee = new Employee();
		employee.setId(1);
		employee.setEmail("email@gamil.com");
		employee.setHccId("534543");
		employee.setLdap("71009578");
		employee.setName("Name");
		employee.setLegalEntityHireDate(new Timestamp(System.currentTimeMillis()));

		Branch branch = new Branch();
		branch.setId(1);
		branch.setCode("Code");
		branch.setName("Name Branch");
		Location location = new Location();
		location.setId(1);
		branch.setLocation(location);
		employee.setBranch(branch);


		BusinessUnit businessUnit = new BusinessUnit();
		businessUnit.setId(1);
		employee.setBusinessUnit(businessUnit);

		CoeCoreTeam coeCoreTeam = new CoeCoreTeam();
		coeCoreTeam.setId(1);
		coeCoreTeam.setName("COE Name");
		coeCoreTeam.setCode("Coe Code");
		coeCoreTeam.setStatus(1);
		coeCoreTeam.setSubLeaderId(1);
		CenterOfExcellence centerOfExcellence = new CenterOfExcellence();
		centerOfExcellence.setId(1);
		coeCoreTeam.setCenterOfExcellence(centerOfExcellence);
		employee.setCoeCoreTeam(coeCoreTeam);

		EmployeeSkill employeeSkill = new EmployeeSkill();

		employeeSkill.setId(1);
		employeeSkill.setSkillSetDate(new Timestamp(System.currentTimeMillis()));
		employeeSkill.setSkillLevel(1);
		SkillSet skill = new SkillSet();
		skill.setCode("177013");
		skill.setDescription("Hello");
		skill.setName("coe");
		employeeSkill.setSkillSet(skill);

		List<EmployeeSkill> employeeSkills = Arrays.asList(employeeSkill);
		employee.setEmployeeSkills(employeeSkills);

		EmployeeModel employeeTransformed = employeeTransformer.apply(employee);
		assertEquals("email@gamil.com", employeeTransformed.getEmail());
		assertEquals("534543", employeeTransformed.getHccId());
		assertEquals("71009578", employeeTransformed.getLdap());
	}

	@Test
	void testApplyList() {

		Employee employee = new Employee();
		employee.setId(1);
		employee.setEmail("email@gamil.com");
		employee.setHccId("534543");
		employee.setLdap("71009578");
		employee.setName("Name");
		employee.setLegalEntityHireDate(new Timestamp(System.currentTimeMillis()));

		Branch branch = new Branch();
		branch.setId(1);
		branch.setCode("Code");
		branch.setName("Name Branch");
		Location location = new Location();
		location.setId(1);
		branch.setLocation(location);
		employee.setBranch(branch);


		BusinessUnit businessUnit = new BusinessUnit();
		businessUnit.setId(1);
		employee.setBusinessUnit(businessUnit);

		CoeCoreTeam coeCoreTeam = new CoeCoreTeam();
		coeCoreTeam.setId(1);
		coeCoreTeam.setName("COE Name");
		coeCoreTeam.setCode("Coe Code");
		coeCoreTeam.setStatus(1);
		coeCoreTeam.setSubLeaderId(1);
		CenterOfExcellence centerOfExcellence = new CenterOfExcellence();
		centerOfExcellence.setId(1);
		coeCoreTeam.setCenterOfExcellence(centerOfExcellence);
		employee.setCoeCoreTeam(coeCoreTeam);

		EmployeeSkill employeeSkill = new EmployeeSkill();

		employeeSkill.setId(1);
		employeeSkill.setSkillSetDate(new Timestamp(System.currentTimeMillis()));
		employeeSkill.setSkillLevel(1);
		SkillSet skill = new SkillSet();
		skill.setCode("177013");
		skill.setDescription("Hello");
		skill.setName("coe");
		employeeSkill.setSkillSet(skill);

		List<EmployeeSkill> employeeSkills = Arrays.asList(employeeSkill);
		employee.setEmployeeSkills(employeeSkills);

		List<Employee> entity = Arrays.asList(employee);

		List<EmployeeModel> result = employeeTransformer.applyList(entity);

		Assertions.assertEquals(1, result.size());
	}

	@Test
	void testApplyList_EmptyList() {
		List<Employee> models = Collections.emptyList();

		List<EmployeeModel> result = employeeTransformer.applyList(models);
		Assertions.assertTrue(result.isEmpty());
	}
}
