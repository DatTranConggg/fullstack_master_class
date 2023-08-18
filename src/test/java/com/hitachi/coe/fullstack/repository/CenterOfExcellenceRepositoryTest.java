package com.hitachi.coe.fullstack.repository;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.hitachi.coe.fullstack.entity.CenterOfExcellence;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@DataJpaTest
@ActiveProfiles("data")
public class CenterOfExcellenceRepositoryTest {
    @Autowired
    CenterOfExcellenceRepository centerOfExcellenceRepository;

    CenterOfExcellence centerOfExcellence;

    @BeforeEach
    void setUp() {
        centerOfExcellence = new CenterOfExcellence();
        centerOfExcellence.setCode("FS");
        centerOfExcellence.setName("Full-stack");
        centerOfExcellence.setCreatedBy("Hung Chan Tran");
        centerOfExcellence.setCreated(new Date());
        centerOfExcellenceRepository.save(centerOfExcellence);
    }

    @Test
    public void testGetCenterOfExcellencesById() {
        CenterOfExcellence coe = centerOfExcellenceRepository.getCenterOfExcellencesById(centerOfExcellence.getId());
        assertNotNull(coe);
        assertEquals(centerOfExcellence.getId(), coe.getId());
    }

    @Test
    public void testGetCenterOfExcellences() {
        List<CenterOfExcellence> coes = centerOfExcellenceRepository.getCenterOfExcellences();
        assertNotNull(coes);
        assertEquals(1, coes.size());
        assertEquals("FS", coes.get(0).getCode());
        assertEquals("Full-stack", coes.get(0).getName());
    }
}