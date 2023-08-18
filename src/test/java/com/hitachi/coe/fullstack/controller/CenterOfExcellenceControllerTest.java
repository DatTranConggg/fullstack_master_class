package com.hitachi.coe.fullstack.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.hitachi.coe.fullstack.model.CenterOfExcellenceModel;
import com.hitachi.coe.fullstack.service.CenterOfExcellenceService;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
class CenterOfExcellenceControllerTest {
	@Autowired
	CenterOfExcellenceController coeController;
	@MockBean
	CenterOfExcellenceService coeService;

	@Test
	void testgetCoe_whenSuccess_thenReturnListCoe() {
		CenterOfExcellenceModel coe1 = new CenterOfExcellenceModel();
		CenterOfExcellenceModel coe2 = new CenterOfExcellenceModel();
		CenterOfExcellenceModel coe3 = new CenterOfExcellenceModel();
		List<CenterOfExcellenceModel> mockCoe = new ArrayList<>();
		mockCoe.add(coe1);
		mockCoe.add(coe2);
		mockCoe.add(coe3);
		Mockito.when(coeService.getAllCenterOfExcellence()).thenReturn(mockCoe);
		ResponseEntity<List<CenterOfExcellenceModel>> status = coeController.getCenterOfExcellence();
		assertEquals(200, status.getStatusCodeValue());
		assertEquals(3, status.getBody().size());
	}
}
