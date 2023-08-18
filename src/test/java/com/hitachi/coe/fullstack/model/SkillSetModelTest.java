package com.hitachi.coe.fullstack.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
class SkillSetModelTest {
	@Test
	void testSkillSetModel() {
		String code = "177013";
		String description = "Hello";
		String name = "coe";
		
		SkillSetModel skill = new SkillSetModel();
		skill.setCode(code);
		skill.setDescription(description);
		skill.setName(name);
		
		assertEquals("177013", skill.getCode());
		assertEquals("coe", skill.getName());
		assertEquals("Hello", skill.getDescription());
	}
}
