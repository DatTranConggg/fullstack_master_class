package com.hitachi.coe.fullstack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
public class CoeCoreTeamModelTest {

	@Test
	void testModelCoeCoreTeam_thenSuccess() {
		CoeCoreTeamModel coeCoreTeamModel = new CoeCoreTeamModel();
		coeCoreTeamModel.setId(1);
		coeCoreTeamModel.setCode("Code");
		coeCoreTeamModel.setName("Coe Name");
		coeCoreTeamModel.setStatus(1);
		coeCoreTeamModel.setSubLeaderId(1);
		coeCoreTeamModel.setCenterOfExcellenceId(1);
		coeCoreTeamModel.setCenterOfExcellenceName("Center Name");

		assertEquals(1, coeCoreTeamModel.getId());
		assertEquals("Code", coeCoreTeamModel.getCode());
		assertEquals("Coe Name", coeCoreTeamModel.getName());
		assertEquals(1, coeCoreTeamModel.getStatus());
		assertEquals(1, coeCoreTeamModel.getSubLeaderId());
		assertEquals(1, coeCoreTeamModel.getCenterOfExcellenceId());
		assertEquals("Center Name", coeCoreTeamModel.getCenterOfExcellenceName());
	}

	@Test
	void testAllAgruCoeCoreTeamModel_thenSuccess() {
		CoeCoreTeamModel coeCoreTeamModel = new CoeCoreTeamModel("code", "name", 1, 1, 1, "Center name");

		assertEquals("code", coeCoreTeamModel.getCode());
	}
}
