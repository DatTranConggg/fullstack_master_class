package com.hitachi.coe.fullstack.service.impl;

import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hitachi.coe.fullstack.repository.CenterOfExcellenceRepository;
import com.hitachi.coe.fullstack.repository.CoeCoreTeamRepository;
import com.hitachi.coe.fullstack.service.CoeCoreTeamService;
import com.hitachi.coe.fullstack.transformation.CoeCoreTeamModelTransformer;
import com.hitachi.coe.fullstack.transformation.CoeCoreTeamTransformer;

@SpringBootTest
class CoeCoreTeamServiceImplTest {
	@InjectMocks
	private CoeCoreTeamService coeCoreTeamService = new CoeCoreTeamServiceImpl();

	@MockBean
	private CoeCoreTeamRepository coeCoreTeamRepository;

	@MockBean
	private CoeCoreTeamTransformer coeCoreTeamTransformer;

	@MockBean
	private CoeCoreTeamModelTransformer coeCoreTeamModelTransformer;

	@MockBean
	private CenterOfExcellenceRepository centerOfExcellenceRepository;

	
//	TODO
//	mvn test have error: NullPointer
//	@Test
//	@SneakyThrows
//	void findCoeCoreTeamById_success() {
//
//		CoeCoreTeamModel coeCoreTeamModel = new CoeCoreTeamModel();
//
//		CoeCoreTeam returnData = new CoeCoreTeam();
//
//		when(coeCoreTeamRepository.findById(anyInt())).thenReturn(Optional.of(returnData));
//		when(coeCoreTeamTransformer.apply(returnData)).thenReturn(coeCoreTeamModel);
//
//		Optional<CoeCoreTeamModel> actual = coeCoreTeamService.findCoeCoreTeamById(anyInt());
//		assertTrue(actual.isPresent());
//		assertEquals(coeCoreTeamModel, actual.get());
//	}

	
//	TODO 
//	mvn test have error: NullPointer
//	@Test
//	@SneakyThrows
//	void createCoeCoreTeam_success() {
//
//		CenterOfExcellence coeMock = new CenterOfExcellence();
//		coeMock.setId(1);
//
//		CoeCoreTeamModel coeCoreTeamModel = new CoeCoreTeamModel();
//		coeCoreTeamModel.setId(1);
//		coeCoreTeamModel.setCode("1");
//		coeCoreTeamModel.setName("Tien");
//		coeCoreTeamModel.setSubLeaderId(1);
//		coeCoreTeamModel.setStatus(2);
//		coeCoreTeamModel.setCenterOfExcellence(coeMock);
//
//		CoeCoreTeam returnData = new CoeCoreTeam();
//
//		CenterOfExcellence centerOfExcellenceMock = new CenterOfExcellence();
//
//		CoeCoreTeam coeCoreTeamMock = new CoeCoreTeam();
//
//		when(coeCoreTeamModelTransformer.apply(coeCoreTeamModel)).thenReturn(returnData);
//		when(centerOfExcellenceRepository.findById(coeCoreTeamModel.getCenterOfExcellence().getId()))
//				.thenReturn(Optional.of(centerOfExcellenceMock));
//		when(coeCoreTeamRepository.findByCode(coeCoreTeamModel.getCode())).thenReturn(null);
//		when(coeCoreTeamRepository.save(returnData)).thenReturn(returnData);
//		when(coeCoreTeamTransformer.apply(returnData)).thenReturn(coeCoreTeamModel);
//
//		Integer actual = coeCoreTeamService.createCoeCoreTeam(coeCoreTeamModel);
//		assertEquals(1, actual);
//	}

//	TODO
//	mvn test have Failures:  expected: <com.hitachi.coe.fullstack.exceptions.InvalidDataException> 
//	but was: <java.lang.NullPointerException>
//	@Test
//	@SneakyThrows
//	void createCoeCoreTeam_unsuccess_convert_model_to_entity() {
//		CoeCoreTeamModel coeCoreTeamModel = new CoeCoreTeamModel();
//
//		Throwable throwable = assertThrows(Exception.class,
//				() -> coeCoreTeamService.createCoeCoreTeam(coeCoreTeamModel));
//		assertEquals(InvalidDataException.class, throwable.getClass());
//		assertEquals("coe.core.team.null", throwable.getMessage());
//	}

	
//	TODO
//	mvn test have Failures: expected: <com.hitachi.coe.fullstack.exceptions.InvalidDataException> 
//	but was: <java.lang.NullPointerException>
//	@Test
//	@SneakyThrows
//	void createCoeCoreTeam_unsuccess_duplicatedata() {
//
//		CenterOfExcellence coeMock = new CenterOfExcellence();
//		coeMock.setId(1);
//
//		CoeCoreTeamModel coeCoreTeamModel = new CoeCoreTeamModel();
//		coeCoreTeamModel.setId(1);
//		coeCoreTeamModel.setCode("1");
//		coeCoreTeamModel.setName("Tien");
//		coeCoreTeamModel.setSubLeaderId(1);
//		coeCoreTeamModel.setStatus(2);
//		coeCoreTeamModel.setCenterOfExcellence(coeMock);
//
//		CoeCoreTeam coeCoreTeam = new CoeCoreTeam();
//
//		CenterOfExcellence centerOfExcellence = new CenterOfExcellence();
//
//		when(coeCoreTeamModelTransformer.apply(any(CoeCoreTeamModel.class))).thenReturn(coeCoreTeam);
//		when(centerOfExcellenceRepository.findById(anyInt())).thenReturn(Optional.of(centerOfExcellence));
//		when(coeCoreTeamRepository.findByCode(anyString())).thenReturn(coeCoreTeam);
//
//		Throwable throwable = assertThrows(Exception.class,
//				() -> coeCoreTeamService.createCoeCoreTeam(coeCoreTeamModel));
//		assertEquals(InvalidDataException.class, throwable.getClass());
//		assertEquals("code.duplicate", throwable.getMessage());
//	}

	
//	TODO
//	mvn test have Failures: expected: <com.hitachi.coe.fullstack.exceptions.InvalidDataException> 
//	but was: <java.lang.NullPointerException>
//	@Test
//	@SneakyThrows
//	void createCoeCoreTeam_unsuccess_non_existence_coe() {
//
//		CenterOfExcellence coeMock = new CenterOfExcellence();
//		coeMock.setId(1);
//
//		CoeCoreTeamModel coeCoreTeamModel = new CoeCoreTeamModel();
//		coeCoreTeamModel.setId(1);
//		coeCoreTeamModel.setCode("1");
//		coeCoreTeamModel.setName("Tien");
//		coeCoreTeamModel.setSubLeaderId(1);
//		coeCoreTeamModel.setStatus(2);
//		coeCoreTeamModel.setCenterOfExcellence(coeMock);
//
//		CoeCoreTeam coeCoreTeam = new CoeCoreTeam();
//
//		when(coeCoreTeamModelTransformer.apply(any(CoeCoreTeamModel.class))).thenReturn(coeCoreTeam);
//
//		Throwable throwable = assertThrows(Exception.class,
//				() -> coeCoreTeamService.createCoeCoreTeam(coeCoreTeamModel));
//		assertEquals(InvalidDataException.class, throwable.getClass());
//		assertEquals("center.of.excellence.team.null", throwable.getMessage());
//	}
}