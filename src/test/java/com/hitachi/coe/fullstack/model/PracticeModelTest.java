package com.hitachi.coe.fullstack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
public class PracticeModelTest {

	@Test
	void testPracticeModel_thenSuccess() {
		PracticeModel model = new PracticeModel();
		model.setId(1);
		model.setDescription("Description");
		model.setManager("Manager");
		model.setName("name");
		model.setCode("Code");
		model.setBusinessUnitId(1);
		model.setBusinessUnitName("BU name");
		
		assertEquals(1, model.getId());
		assertEquals("Description", model.getDescription());
		assertEquals("Manager", model.getManager());
		assertEquals("name", model.getName());
		assertEquals("Code", model.getCode());
		assertEquals(1, model.getBusinessUnitId());
		assertEquals("BU name", model.getBusinessUnitName());
	}
}
