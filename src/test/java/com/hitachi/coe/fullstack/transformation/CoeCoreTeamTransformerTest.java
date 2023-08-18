package com.hitachi.coe.fullstack.transformation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hitachi.coe.fullstack.entity.CenterOfExcellence;
import com.hitachi.coe.fullstack.entity.CoeCoreTeam;
import com.hitachi.coe.fullstack.model.CoeCoreTeamModel;

@SpringBootTest
public class CoeCoreTeamTransformerTest {
	
	@Autowired
	private CoeCoreTeamTransformer coeCoreTeamTransformer;
	
	@Test
	void testCoeCoreTeamTransformer_Apply_thenSuccess() {
		CoeCoreTeam coeCoreTeam = new CoeCoreTeam();
		coeCoreTeam.setId(1);
		coeCoreTeam.setCode("code");
		coeCoreTeam.setName("Name");
		coeCoreTeam.setStatus(1);
		coeCoreTeam.setSubLeaderId(1);
		
		CenterOfExcellence centerOfExcellence = new CenterOfExcellence();
		centerOfExcellence.setId(1);
		
		coeCoreTeam.setCenterOfExcellence(centerOfExcellence);
		
		CoeCoreTeamModel coeCoreTeamModel = coeCoreTeamTransformer.apply(coeCoreTeam);
		assertEquals(1, coeCoreTeamModel.getId());
	}
	
	@Test
	void testCoeCoreTeamTransformer_ApplyList_thenSuccess() {
		CoeCoreTeam coeCoreTeam = new CoeCoreTeam();
		coeCoreTeam.setId(1);
		coeCoreTeam.setCode("code");
		coeCoreTeam.setName("Name");
		coeCoreTeam.setStatus(1);
		coeCoreTeam.setSubLeaderId(1);
		
		CenterOfExcellence centerOfExcellence = new CenterOfExcellence();
		centerOfExcellence.setId(1);
		
		coeCoreTeam.setCenterOfExcellence(centerOfExcellence);
		List<CoeCoreTeam> coeCoreTeams = Arrays.asList(coeCoreTeam);
		List<CoeCoreTeamModel> coeCoreTeamModels = coeCoreTeamTransformer.applyList(coeCoreTeams);
		assertEquals(1, coeCoreTeamModels.size());
	}

	@Test
	void testCoeCoreTeamTransformer_ApplyList_EmptyList() {
		List<CoeCoreTeam> coeCoreTeams = Collections.emptyList();
		List<CoeCoreTeamModel> result = coeCoreTeamTransformer.applyList(coeCoreTeams);

		assertTrue(result.isEmpty());
	}
}
