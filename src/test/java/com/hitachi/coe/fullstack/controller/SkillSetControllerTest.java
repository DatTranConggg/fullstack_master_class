package com.hitachi.coe.fullstack.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.hitachi.coe.fullstack.model.SkillSetModel;
import com.hitachi.coe.fullstack.service.SkillSetService;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-data-test.properties")
class SkillSetControllerTest {

	@InjectMocks
	private SkillSetController skillSetController;
	
	@MockBean
	private SkillSetService skillSetService;
	
	@Test
	void testGetSkillSet_whenSuccess_thenReturnListSkillSet() {
		SkillSetModel skill1 = new SkillSetModel();
		SkillSetModel skill2 = new SkillSetModel();
		SkillSetModel skill3 = new SkillSetModel();
		List<SkillSetModel> mockSkillSet = new ArrayList<>(Arrays.asList(skill1, skill2, skill3));
		Mockito.when(skillSetService.searchSkillSetByName("J")).thenReturn(mockSkillSet);
		ResponseEntity<List<SkillSetModel>> status = skillSetController.searchSkillSetByName("J");
		assertEquals(200, status.getStatusCodeValue());
		assertEquals(3, status.getBody().size());
	}
}
