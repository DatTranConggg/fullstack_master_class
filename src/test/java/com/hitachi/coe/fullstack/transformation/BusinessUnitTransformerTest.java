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
import com.hitachi.coe.fullstack.model.BusinessUnitModel;

@SpringBootTest
class BusinessUnitTransformerTest {

	@Autowired
	private BusinessUnitTransformer businessUnitTransformer;

	@Test
	void testApplyList() {
		BusinessUnit bu1 = new BusinessUnit();
		bu1.setId(1);
		BusinessUnit bu2 = new BusinessUnit();
		bu2.setId(2);
		List<BusinessUnit> entities = Arrays.asList(bu1, bu2);

		List<BusinessUnitModel> actualModels = businessUnitTransformer.applyList(entities);

		assertEquals(2, actualModels.size());

	}

	@Test
	void testApplyListWithNullEntities() {
		List<BusinessUnit> models = Collections.emptyList();

		List<BusinessUnitModel> result = businessUnitTransformer.applyList(models);

		assertTrue(result.isEmpty());
	}
}
