package com.hitachi.coe.fullstack.transformation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hitachi.coe.fullstack.entity.Branch;
import com.hitachi.coe.fullstack.entity.Location;
import com.hitachi.coe.fullstack.model.BranchModel;

@SpringBootTest
class BranchTransformerTest {
	
	@Autowired
	BranchTransformer transformer;
	
	@Test
	void testApply() {
		Location location = new Location();
		location.setId(1);
		location.setName("HCM");
		Branch entity = new Branch();
		entity.setLocation(location);
		entity.setId(1);
		entity.setCode("177013");
		entity.setName("HCM");
		BranchModel model =  transformer.apply(entity);
		assertEquals(1, model.getId());
		assertEquals("177013", model.getCode());
		assertEquals("HCM", model.getName());
	}
	
	@Test
	void testListApply() {
		Location location = new Location();
		location.setId(1);
		location.setName("HCM");
		Branch entity1 = new Branch();
		entity1.setLocation(location);
		Branch entity2 = new Branch();
		entity2.setLocation(location);
		List<Branch> entities = new ArrayList<>(Arrays.asList(entity1, entity2));
		List<BranchModel> models = transformer.applyList(entities);
		assertEquals(2, models.size());
	}
	
	@Test
	void testApplyList_whenEntityListEmpty_thenReturnEmptyList() {
		List<Branch> emptyList = new ArrayList<>();
		List<BranchModel> models = transformer.applyList(emptyList);
		assertEquals(0, models.size());
	}
}
