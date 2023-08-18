package com.hitachi.coe.fullstack.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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

import com.hitachi.coe.fullstack.model.BranchModel;
import com.hitachi.coe.fullstack.service.BranchService;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-data-test.properties")
class BranchControllerTest {

	@InjectMocks
	private BranchController branchController;

	@MockBean
	private BranchService branchService;

    @Test
    void testGetBranches_whenSuccess_thenReturnListBranches() {
		BranchModel test1 = new BranchModel();
		BranchModel test2 = new BranchModel();
		BranchModel test3 = new BranchModel();
		List<BranchModel> mockBranches = new ArrayList<>();
		mockBranches.add(test1);
		mockBranches.add(test3);
		mockBranches.add(test2);
		Mockito.when(branchService.getAllBranches()).thenReturn(mockBranches);
		ResponseEntity<List<BranchModel>> status = branchController.getBranches();
		assertEquals(200, status.getStatusCodeValue());
		assertEquals(3, status.getBody().size());
    }
}
