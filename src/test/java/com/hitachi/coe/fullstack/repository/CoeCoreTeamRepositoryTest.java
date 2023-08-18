package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.CenterOfExcellence;
import com.hitachi.coe.fullstack.entity.CoeCoreTeam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("data")
class CoeCoreTeamRepositoryTest {


    @Autowired
    private CoeCoreTeamRepository coeCoreTeamRepository;

    @Autowired
    private CenterOfExcellenceRepository centerOfExcellenceRepository;

    @Test
    void testGetCoeTeamByCoeId() {
        CenterOfExcellence coe = new CenterOfExcellence();
        coe.setCreated(new Date());
        coe.setCreatedBy("admin");
        centerOfExcellenceRepository.save(coe);
        CoeCoreTeam coeCoreTeamExpected = new CoeCoreTeam();
        coeCoreTeamExpected.setCode("1");
        coeCoreTeamExpected.setName("Angular");
        coeCoreTeamExpected.setStatus(1);
        coeCoreTeamExpected.setSubLeaderId(1);
        coeCoreTeamExpected.setCenterOfExcellence(coe);
        coeCoreTeamExpected.setEmployees(Collections.emptyList());
        coeCoreTeamExpected.setCreated(new Date());
        coeCoreTeamExpected.setCreatedBy("admin");
        List<CoeCoreTeam> coeCoreTeamsExpected = new ArrayList<CoeCoreTeam>();
        coeCoreTeamsExpected.add(coeCoreTeamExpected);

        CoeCoreTeam coeCoreTeam = new CoeCoreTeam();
        coeCoreTeam.setCode("1");
        coeCoreTeam.setName("Angular");
        coeCoreTeam.setStatus(1);
        coeCoreTeam.setSubLeaderId(1);
        coeCoreTeam.setCenterOfExcellence(coe);
        coeCoreTeam.setEmployees(Collections.emptyList());
        coeCoreTeam.setCreated(new Date());
        coeCoreTeam.setCreatedBy("admin");
        coeCoreTeamRepository.save(coeCoreTeam);

        coe = centerOfExcellenceRepository.findById(coe.getId()).orElse(null);
        assertNotNull(coe, "CenterOfExcellence with ID 1 should exist. Got null.");
        List<CoeCoreTeam> coeTeamList = coeCoreTeamRepository.getCoeTeamByCoeId(coe);
        assertNotNull(coeTeamList);
        assertEquals(coeCoreTeamsExpected.size(), coeTeamList.size());
        assertEquals(coeCoreTeamsExpected.get(0).getName(), coeTeamList.get(0).getName());
    }
}

