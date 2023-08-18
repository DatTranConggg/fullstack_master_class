package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.entity.Branch;
import com.hitachi.coe.fullstack.model.BranchModel;
import com.hitachi.coe.fullstack.repository.BranchRepository;
import com.hitachi.coe.fullstack.repository.LocationRepository;
import com.hitachi.coe.fullstack.service.impl.BranchServiceImpl;
import com.hitachi.coe.fullstack.transformation.BranchModelTransformer;
import com.hitachi.coe.fullstack.transformation.BranchTransformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BranchServiceTest {

	@InjectMocks
	BranchServiceImpl branchService;

	@Mock
	LocationRepository locationRepository;

	@Mock
	BranchRepository branchRepository;

	@Mock
	BranchTransformer branchTransformer;

	@Mock
	BranchModelTransformer branchModelTransformer;


	@Test
	void  testGetAllBranches_whenSuccess_thenResutnListOfBranches() {
		Branch test1 = new Branch();
		Branch test2 = new Branch();
		Branch test3 = new Branch();
		List<Branch> mockBranches = new ArrayList<>();
		mockBranches.add(test1);
		mockBranches.add(test3);
		mockBranches.add(test2);
		when(branchRepository.getAllBranches()).thenReturn(mockBranches);
		when(branchTransformer.applyList(mockBranches)).thenReturn(new ArrayList<>(Arrays.asList(new BranchModel(), new BranchModel(), new BranchModel())));
		List<BranchModel> expectedBranches = branchTransformer.applyList(mockBranches);
		when(branchService.getAllBranches()).thenReturn(expectedBranches);
		List<BranchModel> result = branchService.getAllBranches();
		Assertions.assertNotNull(result);
		assertEquals(mockBranches.size(), result.size());
	}

	@Test
	void testGetAllBranches_whenBranchIsEmpty_thenReturnEmptyListOfBranches() {
		List<Branch> mockBranches = new ArrayList<>();
		when(branchRepository.getAllBranches()).thenReturn(mockBranches);
		List<BranchModel> result = branchService.getAllBranches();
		Assertions.assertNotNull(result);
		assertEquals(mockBranches.size(), result.size());
	}
}
