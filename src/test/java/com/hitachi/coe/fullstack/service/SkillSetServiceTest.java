package com.hitachi.coe.fullstack.service;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import com.hitachi.coe.fullstack.entity.SkillSet;
import com.hitachi.coe.fullstack.model.SkillSetModel;
import com.hitachi.coe.fullstack.repository.SkillSetRepository;
import com.hitachi.coe.fullstack.transformation.SkillSetTransformer;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-data-test.properties")
class SkillSetServiceTest {

	@Autowired
	SkillSetService skillSetService;

	@MockBean
	private SkillSetRepository skillSetRepository;
	@MockBean
	private SkillSetTransformer skillSetTransformer;

	 @Test
	    void testGetSkillSetByName_whenSuccess_thenReturnListSkillByName() {
	        String searchName = "s";
 
	        SkillSet skill1 = new SkillSet();
	        skill1.setName("Vue.js");
	        SkillSet skill2 = new SkillSet();
	        skill2.setName("Node.js");
	        List<SkillSet> skillSets = Arrays.asList(skill1, skill2);

	        Mockito.when(skillSetRepository.searchSkillSetByName(searchName)).thenReturn(skillSets);

	        SkillSetModel skillModel1 = new SkillSetModel();
	        skillModel1.setName("Vue.js");
	        SkillSetModel skillModel2 = new SkillSetModel();
	        skillModel2.setName("Node.js");
	        List<SkillSetModel> skillModels = Arrays.asList(skillModel1, skillModel2);
	        Mockito.when(skillSetService.searchSkillSetByName(searchName)).thenReturn(skillModels);
	        List<SkillSetModel> result = skillSetService.searchSkillSetByName(searchName);
	        System.out.println(skillModels.size());
	        System.out.println(result.size());
			Assertions.assertEquals(skillModels.get(0).getName(), result.get(0).getName());
			Assertions.assertEquals(skillModels.size(), result.size());
	    }
}
