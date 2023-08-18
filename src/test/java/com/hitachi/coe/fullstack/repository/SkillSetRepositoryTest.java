package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.SkillSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("data")
public class SkillSetRepositoryTest {
    @Autowired
    SkillSetRepository skillsetrepository;
    @BeforeEach
    void setUp() {
        SkillSet skillset = new SkillSet();
        skillset.setId(1);
        skillset.setCode("Code1");
        skillset.setDescription("Descprition1");
        skillset.setName("Java");
        skillset.setCreatedBy("admin");
        skillset.setCreated(new Date());
        skillsetrepository.save(skillset);
    }
    @Test
    void testGetAllSkillSet(){
        List<SkillSet> skillSets = skillsetrepository.getAllSkillSet();
        assertNotNull(skillSets);
    }
    @Test
    public void testSearchSkillSetByName() {
        List<SkillSet> skillSets = skillsetrepository.searchSkillSetByName("Jav");
        assertNotNull(skillSets);
        assertEquals(0,skillSets.size());
    }

}
