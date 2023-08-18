package com.hitachi.coe.fullstack.transformation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hitachi.coe.fullstack.entity.CenterOfExcellence;
import com.hitachi.coe.fullstack.entity.CoeCoreTeam;
import com.hitachi.coe.fullstack.model.CenterOfExcellenceModel;

@SpringBootTest
class CenterOfExcellenceTransformerTest {
	
	@Autowired
	CenterOfExcellenceTransformer transformer;
	
	@Test
	void testApply() {
    	CenterOfExcellence centerOfExcellence = new CenterOfExcellence();
    	List<CoeCoreTeam> coeCoreTeams = new ArrayList<>();
    	centerOfExcellence.setId(1);
    	centerOfExcellence.setCreated(new Date());
    	centerOfExcellence.setCreatedBy("Administrator");
    	centerOfExcellence.setUpdated(new Date());
    	centerOfExcellence.setUpdatedBy("Administrator");
    	centerOfExcellence.setCode("177013");
    	centerOfExcellence.setName("DS");
    	centerOfExcellence.setCoeCoreTeams(coeCoreTeams);
    	CenterOfExcellenceModel model = transformer.apply(centerOfExcellence);
    	assertEquals("177013", model.getCode());
    	assertEquals(1, model.getId());
    	assertEquals("DS", model.getName());
    	assertEquals("Administrator", model.getCreatedBy());
	}
	
	@Test
	void testApplyList() {
    	CenterOfExcellence centerOfExcellence = new CenterOfExcellence();
    	List<CoeCoreTeam> coeCoreTeams = new ArrayList<>();
    	centerOfExcellence.setId(1);
    	centerOfExcellence.setCreated(new Date());
    	centerOfExcellence.setCreatedBy("Administrator");
    	centerOfExcellence.setUpdated(new Date());
    	centerOfExcellence.setUpdatedBy("Administrator");
    	centerOfExcellence.setCode("177013");
    	centerOfExcellence.setName("DS");
    	centerOfExcellence.setCoeCoreTeams(coeCoreTeams);
    	List<CenterOfExcellence> centerOfExcellences = List.of(centerOfExcellence);
    	List<CenterOfExcellenceModel> models = transformer.applyList(centerOfExcellences);
    	assertEquals(1, models.size());
	}
	
	@Test
	void testApplyList_whenEntityListEmpty_thenReturnEmptyList() {
    	List<CenterOfExcellence> centerOfExcellences = new ArrayList<>();
    	List<CenterOfExcellenceModel> models = transformer.applyList(centerOfExcellences);
    	assertEquals(0, models.size());
	}
}
