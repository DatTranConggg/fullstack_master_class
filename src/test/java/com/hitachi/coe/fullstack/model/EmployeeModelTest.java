package com.hitachi.coe.fullstack.model;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
public class EmployeeModelTest {

//	@Test
//	void testModelEmployee_thenSuccess() {
//		Date date1 = mock(Date.class);
//
//		EmployeeModel employeeModel = new EmployeeModel();
//		employeeModel.setId(1);
//		employeeModel.setEmail("email@gamil.com");
//		employeeModel.setHccId("534543");
//		employeeModel.setLdap("71009578");
//		employeeModel.setName("Name");
//		employeeModel.setLegalEntityHireDate(date1);
//
//		BranchModel branchModel = new BranchModel();
//		branchModel.setId(1);
//		branchModel.setCode("Code");
//		branchModel.setLabel("label");
//		branchModel.setName("Name Branch");
//		employeeModel.setBranch(branchModel);
//
//		PracticeModel practiceModel = new PracticeModel();
//		practiceModel.setId(1);
//		practiceModel.setName("Practice");
//		practiceModel.setManager("Manager");
//		practiceModel.setBusinessUnitId(1);
//		practiceModel.setBusinessUnitName("BU Name");
//		practiceModel.setDescription("Discription");
//		employeeModel.setPractice(practiceModel);
//
//		CoeCoreTeamModel coeCoreTeamModel = new CoeCoreTeamModel();
//		coeCoreTeamModel.setId(1);
//		coeCoreTeamModel.setName("COE Name");
//		coeCoreTeamModel.setCode("Coe Code");
//		coeCoreTeamModel.setStatus(1);
//		coeCoreTeamModel.setSubLeaderId(1);
//		coeCoreTeamModel.setCenterOfExcellenceId(1);
//		coeCoreTeamModel.setCenterOfExcellenceName("Center Name");
//		employeeModel.setCoeCoreTeam(coeCoreTeamModel);
//
//		EmployeeSkillModel employeeSkillModel = new EmployeeSkillModel();
//
//		employeeSkillModel.setId(1);
//		employeeSkillModel.setSkillSetDate(new Date());
//		employeeSkillModel.setSkillLevel(1);
//		SkillSetModel skill = new SkillSetModel();
//		skill.setCode("177013");
//		skill.setDescription("Hello");
//		skill.setName("coe");
//		employeeSkillModel.setSkillSet(skill);
//
//		List<EmployeeSkillModel> employeeSkillModels = Arrays.asList(employeeSkillModel);
//		employeeModel.setEmployeeSkills(employeeSkillModels);
//
//		assertEquals(1, employeeModel.getId());
//		assertEquals("email@gamil.com", employeeModel.getEmail());
//		assertEquals("534543", employeeModel.getHccId());
//		assertEquals("71009578", employeeModel.getLdap());
//		assertEquals("Name", employeeModel.getName());
//		assertNotNull(employeeModel.getLegalEntityHireDate());
//		assertEquals(1, employeeModel.getBranch().getId());
//		assertEquals(1, employeeModel.getPractice().getId());
//		assertEquals(1, employeeModel.getCoeCoreTeam().getId());
//		assertEquals(1, employeeModel.getEmployeeSkills().size());
//
//	}
//
//	@Test
//	void testBuilderEmployeeModel_thenSuccess() {
//
//		EmployeeModelBuilder employee = EmployeeModel.builder().email("email@gamil.com").hccId("123456").ldap("ldap123")
//				.legalEntityHireDate(new Date()).name("Name").practice(mock(PracticeModel.class))
//				.coeCoreTeam(mock(CoeCoreTeamModel.class)).branch(mock(BranchModel.class))
//				.employeeSkills(mock(List.class));
//
//		assertNotNull(employee.build().getLegalEntityHireDate());
//		assertEquals("email@gamil.com", employee.build().getEmail());
//		assertEquals("123456", employee.build().getHccId());
//		assertEquals("ldap123", employee.build().getLdap());
//		assertEquals("Name", employee.build().getName());
//		assertTrue(employee.toString().contains("email@gamil.com"));
//
//	}
}
