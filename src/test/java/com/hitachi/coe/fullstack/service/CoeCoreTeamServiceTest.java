package com.hitachi.coe.fullstack.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import com.hitachi.coe.fullstack.constant.StatusConstant;
import com.hitachi.coe.fullstack.entity.CenterOfExcellence;
import com.hitachi.coe.fullstack.entity.CoeCoreTeam;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.CoeCoreTeamModel;
import com.hitachi.coe.fullstack.repository.CenterOfExcellenceRepository;
import com.hitachi.coe.fullstack.repository.CoeCoreTeamRepository;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.transformation.CenterOfExcellenceTransformer;
import com.hitachi.coe.fullstack.transformation.CoeCoreTeamTransformer;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-data-test.properties")
public class CoeCoreTeamServiceTest {
	@MockBean
	private EmployeeRepository employeeRepository;

	@MockBean
	private CoeCoreTeamRepository coeCoreTeamRepository;

	@Autowired
	private CoeCoreTeamService coeCoreTeamService;

	@MockBean
	private CoeCoreTeamTransformer coeCoreTeamTransformer;

	@MockBean
	private CenterOfExcellenceTransformer centerOfExcellenceTransformer;

	@MockBean
	private CenterOfExcellenceRepository centerOfExcellenceRepository;

	@Test
	public void testRemoveMembersFromCoeCoreTeam() {
		// Mock data
		List<Integer> employeeIds = new ArrayList<>();
		employeeIds.add(1);
		employeeIds.add(2);

		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee());
		employees.add(new Employee());

		CoeCoreTeam existingCoeCoreTeam = new CoeCoreTeam();

		// Mock the behavior of repositories
		when(employeeRepository.findAllById(employeeIds)).thenReturn(employees);
		when(coeCoreTeamRepository.findById(0)).thenReturn(java.util.Optional.of(existingCoeCoreTeam));
		when(employeeRepository.saveAll(employees)).thenReturn(employees);

		int removedCount = coeCoreTeamService.removeMembersFromCoeCoreTeam(employeeIds);

		assertEquals(2, removedCount);
		for (Employee emp : employees) {
			assertEquals(existingCoeCoreTeam, emp.getCoeCoreTeam());
		}
	}

//	TODO
//	@Test
//	void updateCoeCoreTeam() {
//		CenterOfExcellence centerOfExcellence = new CenterOfExcellence();
//		centerOfExcellence.setId(1);
//		int coeCoreTeamId = 1;
//		CoeCoreTeamModel coeCoreTeamModel = new CoeCoreTeamModel();
//		coeCoreTeamModel.setId(coeCoreTeamId);
//		coeCoreTeamModel.setCode("0123");
//		coeCoreTeamModel.setName("Ngoc");
//		coeCoreTeamModel.setStatus(0);
//		coeCoreTeamModel.setCenterOfExcellence(centerOfExcellence);
//
//		CoeCoreTeam exitCoeCoreTeam = new CoeCoreTeam();
//		exitCoeCoreTeam.setId(234324324);
//		coeCoreTeamModel.setCode("01233333333");
//		coeCoreTeamModel.setName("Ngoc");
//		coeCoreTeamModel.setStatus(0);
//
//		when(coeCoreTeamRepository.findById(anyInt())).thenReturn(Optional.of(exitCoeCoreTeam));
//		when(coeCoreTeamRepository.save(exitCoeCoreTeam)).thenReturn(exitCoeCoreTeam);
//
//		when(coeCoreTeamTransformer.apply(exitCoeCoreTeam)).thenReturn(coeCoreTeamModel);
//
//		when(centerOfExcellenceRepository.findById(anyInt())).thenReturn(Optional.of(centerOfExcellence));
//
//		Integer actual = coeCoreTeamService.updateCoeCoreTeam(coeCoreTeamModel);
//		assertEquals(234324324, actual);
//	}

//	TODO
//	@Test
//	void deleteCoeCoreTeam_succes() {
//		CoeCoreTeamModel coeCoreTeamModel = new CoeCoreTeamModel();
//		coeCoreTeamModel.setId(1);
//
//		CoeCoreTeam coeCoreTeamMock = new CoeCoreTeam();
//		coeCoreTeamMock.setId(1);
//		coeCoreTeamMock.setStatus(StatusConstant.STATUS_ACTIVE);
//
//		when(coeCoreTeamRepository.findById(coeCoreTeamModel.getId())).thenReturn(Optional.of(coeCoreTeamMock));
//		when(coeCoreTeamRepository.save(coeCoreTeamMock)).thenReturn(coeCoreTeamMock);
//		coeCoreTeamService.deleteCoeCoreTeam(coeCoreTeamModel.getId());
//
//		verify(coeCoreTeamRepository).findById(coeCoreTeamModel.getId());
//		verify(coeCoreTeamRepository).save(coeCoreTeamMock);
//	}

	@Test
	void deleteCoeCoreTeam_failed_status() {
		CoeCoreTeamModel coeCoreTeamModel = new CoeCoreTeamModel();
		coeCoreTeamModel.setId(1);

		CoeCoreTeam coeCoreTeamMock = new CoeCoreTeam();
		coeCoreTeamMock.setId(1);
		coeCoreTeamMock.setStatus(StatusConstant.STATUS_DELETED);

		when(coeCoreTeamRepository.findById(coeCoreTeamModel.getId())).thenReturn(Optional.of(coeCoreTeamMock));

		assertThrows(InvalidDataException.class, () -> coeCoreTeamService.deleteCoeCoreTeam(coeCoreTeamModel.getId()));
	}

	@Test
	void deleteCoeCoreTeam_failed_not_found() {
		CoeCoreTeamModel coeCoreTeamModel = new CoeCoreTeamModel();
		coeCoreTeamModel.setId(1);

		when(coeCoreTeamRepository.findById(coeCoreTeamModel.getId())).thenReturn(Optional.ofNullable(null));

		assertThrows(InvalidDataException.class, () -> coeCoreTeamService.deleteCoeCoreTeam(coeCoreTeamModel.getId()));
	}

	@Test
	void testAddMembersToCoeCoreTeam() {
		List<Integer> employeeListId = new ArrayList<>();
		employeeListId.add(1);
		employeeListId.add(2);
		System.out.println(employeeListId);
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee());
		employees.add(new Employee());

		CoeCoreTeam existingCoeCoreTeam = new CoeCoreTeam();
		// Mock the behavior of repositories
		when(employeeRepository.findAllById(employeeListId)).thenReturn(employees);
		when(coeCoreTeamRepository.findById(any())).thenReturn(java.util.Optional.of(existingCoeCoreTeam));
		when(employeeRepository.saveAll(employees)).thenReturn(employees);

		int addCount = coeCoreTeamService.addMembersToCoeCoreTeam(1, employeeListId);
		assertEquals(2, addCount);

		for (Employee emp : employees) {
			assertEquals(existingCoeCoreTeam, emp.getCoeCoreTeam());
		}
	}

	@Test
	void testGetCenterOfExcellencesById_whenNull_thenReturnEmptyList() {
		Integer coeId = 1;
		Mockito.when(centerOfExcellenceRepository.getCenterOfExcellencesById(coeId)).thenReturn(null);
		List<CoeCoreTeamModel> result = coeCoreTeamService.getAllCoeTeamByCoeId(coeId);
		Assertions.assertNotNull(result);
		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	void testGetCenterOfExcellencesById_whenSuccess_thenReturnListOfCoe() {
		Integer coeId = 1;
		CenterOfExcellence coe = new CenterOfExcellence();
		Mockito.when(centerOfExcellenceRepository.getCenterOfExcellencesById(coeId)).thenReturn(coe);
		List<CoeCoreTeam> coeCoreTeams = new ArrayList<>();
		Mockito.when(coeCoreTeamRepository.getCoeTeamByCoeId(coe)).thenReturn(coeCoreTeams);
		CoeCoreTeamModel test1 = new CoeCoreTeamModel();
		test1.setCode("177013");
		CoeCoreTeamModel test2 = new CoeCoreTeamModel();
		test2.setCode("4299");
		CoeCoreTeamModel test3 = new CoeCoreTeamModel();
		test3.setCode("228922");
		List<CoeCoreTeamModel> transformedModels = new ArrayList<>(Arrays.asList(test1, test2, test3));
		Mockito.when(coeCoreTeamTransformer.applyList(coeCoreTeams)).thenReturn(transformedModels);
		List<CoeCoreTeamModel> result = coeCoreTeamService.getAllCoeTeamByCoeId(coeId);
		Assertions.assertEquals(transformedModels, result);
		Assertions.assertEquals(result.get(0).getCode(), "177013");
	}
}