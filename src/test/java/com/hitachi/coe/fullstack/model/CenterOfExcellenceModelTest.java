package com.hitachi.coe.fullstack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
class CenterOfExcellenceModelTest {
	@Test
	void testCenterOfExcellenceModel() {
		CenterOfExcellenceModel model = new CenterOfExcellenceModel();
		List<CoeCoreTeamModel> coe = new ArrayList<>();
		model.setCode("STA");
		model.setId(1);
		model.setName("Solution Technical Architecture");
		model.setCreated(new Date());
		model.setCreatedBy("Administrator");
		model.setUpdated(new Date());
		model.setUpdatedBy("me");
		String code = model.getCode();
		model.setCoeCoreTeamModel(coe);
		int id = model.getId();
		model.getCoeCoreTeamModel();
		model.getName();
		assertEquals("STA", code);
		assertEquals(1, id);
	}
}
