package com.hitachi.coe.fullstack.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.hitachi.coe.fullstack.entity.Branch;
import com.hitachi.coe.fullstack.entity.Location;

@DataJpaTest
@ActiveProfiles("data")
class BranchRepositoryTest {
	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private LocationRepository locationRepository;

	@BeforeEach
	void setUp() {
		Branch branch1 = new Branch();
		Location location = new Location();
		location.setName("location1");
		location.setCreatedBy("admin");
		location.setCreated(new Date());
		locationRepository.save(location);
		branch1.setCode("BR01");
		branch1.setName("Branch 1");
		branch1.setCreatedBy("admin");
		branch1.setCreated(new Date());
		branch1.setLocation(location);
		branchRepository.save(branch1);

		Branch branch2 = new Branch();
		branch2.setCode("BR02");
		branch2.setName("Branch 2");
		branch2.setCreatedBy("admin");
		branch2.setCreated(new Date());
		branchRepository.save(branch2);
	}

	@Test
	void testGetAllBranches() {
		List<Branch> branches = branchRepository.getAllBranches();
		assertEquals(2, branches.size());
		assertEquals("Branch 1", branches.get(0).getName());
		assertEquals("BR02", branches.get(1).getCode());
	}

}

