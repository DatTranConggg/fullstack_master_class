package com.hitachi.coe.fullstack.transformation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hitachi.coe.fullstack.entity.BusinessUnit;
import com.hitachi.coe.fullstack.entity.Practice;
import com.hitachi.coe.fullstack.model.PracticeModel;

@SpringBootTest
public class PracticeTransformerTest {

	@Autowired
	private PracticeTransformer practiceTransformer;
	
	@Test 
	void testPracticeTransformer_Apply_thenSucces() {
		Practice practice = new Practice();
		practice.setId(1);
		practice.setCode("Code");
		practice.setDescription("Description");
		practice.setManager("Manager");
		practice.setName("Name");
		
		BusinessUnit businessUnit = new BusinessUnit();
		businessUnit.setId(1);
		practice.setBusinessUnit(businessUnit);
		
		PracticeModel model = practiceTransformer.apply(practice);
		assertEquals(1, model.getId());
	}
	
	@Test 
	void testPracticeTransformer_ApplyList_thenSucces() {
		Practice practice = new Practice();
		practice.setId(1);
		practice.setCode("Code");
		practice.setDescription("Description");
		practice.setManager("Manager");
		practice.setName("Name");
		BusinessUnit businessUnit = new BusinessUnit();
		businessUnit.setId(1);
		practice.setBusinessUnit(businessUnit);
		
		List<Practice> list = Arrays.asList(practice);
		List<PracticeModel> practiceModels = practiceTransformer.applyList(list);
		assertEquals(1, practiceModels.size());
	}

	@Test
	public void testPracticeTransformer_ApplyList_EmptyList() {
		List<Practice> listPractices = Collections.emptyList();

		List<PracticeModel> result = practiceTransformer.applyList(listPractices);

		assertTrue(result.isEmpty());
	}
	
}
