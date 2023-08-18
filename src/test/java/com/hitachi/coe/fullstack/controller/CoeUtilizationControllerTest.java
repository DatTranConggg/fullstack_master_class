package com.hitachi.coe.fullstack.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.hitachi.coe.fullstack.model.CoeUtilizationModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.CoeUtilizationService;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-data-test.properties")
public class CoeUtilizationControllerTest {
	
	@InjectMocks
	private CoeUtilizationController coeUtilizationcontroller;
	
	@MockBean
	private CoeUtilizationService coeUtilizationService;
	
	@Test
	void testGetListCoeUtilization_whenSuccess_thenReturnListOfCoeUtilization() {
		CoeUtilizationModel model1 = new CoeUtilizationModel();
		CoeUtilizationModel model2 = new CoeUtilizationModel();
		CoeUtilizationModel model3 = new CoeUtilizationModel();
		List<CoeUtilizationModel> models = List.of(model1, model2, model3);
		Mockito.when(coeUtilizationService.getAllCoeUtilization()).thenReturn(models);
		BaseResponse<List<CoeUtilizationModel>> status = coeUtilizationcontroller.getListCoeUtilization();
		assertEquals(3, status.getData().size());
	}
}
