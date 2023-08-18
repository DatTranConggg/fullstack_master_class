package com.hitachi.coe.fullstack.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.hitachi.coe.fullstack.entity.CenterOfExcellence;
import com.hitachi.coe.fullstack.model.CenterOfExcellenceModel;
import com.hitachi.coe.fullstack.repository.CenterOfExcellenceRepository;
import com.hitachi.coe.fullstack.service.impl.CenterOfExcellenceServiceImpl;
import com.hitachi.coe.fullstack.transformation.CenterOfExcellenceTransformer;

@SpringBootTest
@TestPropertySource("classpath:application-data-test.properties")
public class CenterOfExcellenceServiceTest {

	@Mock
	private CenterOfExcellenceRepository centerOfExcellenceRepository;

	@Mock
	private CenterOfExcellenceTransformer centerOfExcellenceTransformer;

	@InjectMocks
	private CenterOfExcellenceServiceImpl centerOfExcellenceService;

	@Test
	void testGetAllCenterOfExcellence_whenSuccess_thenReturnListOfCenterOfExcellence() {
		List<CenterOfExcellence> mockCenterOfExcellences = new ArrayList<>(
				Arrays.asList(new CenterOfExcellence(), new CenterOfExcellence(), new CenterOfExcellence()));
		Mockito.when(centerOfExcellenceRepository.getCenterOfExcellences()).thenReturn(mockCenterOfExcellences);

		Mockito.when(centerOfExcellenceTransformer.applyList(mockCenterOfExcellences)).thenReturn(new ArrayList<>(Arrays
				.asList(new CenterOfExcellenceModel(), new CenterOfExcellenceModel(), new CenterOfExcellenceModel())));
		List<CenterOfExcellenceModel> models = centerOfExcellenceTransformer.applyList(mockCenterOfExcellences);
		Mockito.when(centerOfExcellenceService.getAllCenterOfExcellence())
				.thenReturn(models);
		List<CenterOfExcellenceModel> result = centerOfExcellenceService.getAllCenterOfExcellence();
		Assertions.assertNotNull(result);
		Assertions.assertEquals(mockCenterOfExcellences.size(), result.size());
	}

	@Test
	void testGetAllCenterOfExcellence_whenEmpty_thenReturnEmptyList() {
		List<CenterOfExcellence> mockCenterOfExcellences = new ArrayList<>();

		Mockito.when(centerOfExcellenceRepository.getCenterOfExcellences()).thenReturn(mockCenterOfExcellences);

		List<CenterOfExcellenceModel> result = centerOfExcellenceService.getAllCenterOfExcellence();

		Assertions.assertNotNull(result);
		Assertions.assertTrue(result.isEmpty());
	}
}
