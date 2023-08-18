package com.hitachi.coe.fullstack.transformation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hitachi.coe.fullstack.entity.BusinessUnit;
import com.hitachi.coe.fullstack.model.BusinessUnitModel;

@SpringBootTest
public class BusinessUnitModelTransformerTest {

	@Autowired
	private BusinessUnitModelTransformer businessUnitModelTransformer;

	@Test
	public void testApply() {
		BusinessUnitModel model = new BusinessUnitModel();
		model.setCode("BU001");
		model.setDescription("Business Unit 1");
		model.setManager("John Doe");
		model.setName("Business Unit One");

		BusinessUnit result = businessUnitModelTransformer.apply(model);

		Assertions.assertEquals("BU001", result.getCode());
		Assertions.assertEquals("Business Unit 1", result.getDescription());
		Assertions.assertEquals("John Doe", result.getManager());
		Assertions.assertEquals("Business Unit One", result.getName());
	}

	@Test
	public void testApplyList() {
		BusinessUnitModel model1 = new BusinessUnitModel();
		model1.setId(1);
		model1.setCode("BU001");
		model1.setDescription("Business Unit 1");
		model1.setManager("John Doe");
		model1.setName("Business Unit One");

		BusinessUnitModel model2 = new BusinessUnitModel();
		model2.setId(2);
		model2.setCode("BU002");
		model2.setDescription("Business Unit 2");
		model2.setManager("Jane Smith");
		model2.setName("Business Unit Two");

		List<BusinessUnitModel> models = Arrays.asList(model1, model2);

		List<BusinessUnit> result = businessUnitModelTransformer.applyList(models);

		assertEquals(2, result.size());
	}

	@Test
	public void testApplyList_EmptyList() {
		List<BusinessUnitModel> models = Collections.emptyList();

		List<BusinessUnit> result = businessUnitModelTransformer.applyList(models);

		Assertions.assertTrue(result.isEmpty());
	}
}