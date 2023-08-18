package com.hitachi.coe.fullstack.transformation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hitachi.coe.fullstack.entity.SkillSet;
import com.hitachi.coe.fullstack.model.SkillSetModel;

@SpringBootTest
public class SkillSetTransformerTest {

	@Autowired
	private SkillSetTransformer skillSetTransformer;
	
	@Test
	void testSkillSetTransformer_Apply_thenSuccess() {
		SkillSet skillSet = new SkillSet();
		skillSet.setId(1);
		skillSet.setCode("Code");
		skillSet.setDescription("Description");
		skillSet.setName("Name");
		
		SkillSetModel model = skillSetTransformer.apply(skillSet);
		assertEquals(1, model.getId());
	}
	
	@Test
	void testSkillSetTransformer_ApplyList_thenSuccess() {
		SkillSet skillSet = new SkillSet();
		skillSet.setId(1);
		skillSet.setCode("Code");
		skillSet.setDescription("Description");
		skillSet.setName("Name");
		List<SkillSet> list = Arrays.asList(skillSet);
		List<SkillSetModel> skillSetModels = skillSetTransformer.applyList(list);
		assertEquals(1, skillSetModels.size());
	}

	@Test
	void testSkillSetTransformer_ApplyList_EmptyList() {
		List<SkillSet> skillSets = Collections.emptyList();
		List<SkillSetModel> result = skillSetTransformer.applyList(skillSets);

		assertTrue(result.isEmpty());
	}
}
