package com.hitachi.coe.fullstack.transformation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.hitachi.coe.fullstack.entity.Level;
import com.hitachi.coe.fullstack.model.LevelModel;

@SpringBootTest
class LevelTransformerTest {

    @InjectMocks
    private LevelTransformer levelTransformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApplyList() {
        // Create sample Level entities
        Level level1 = new Level();
        level1.setId(1);
        level1.setName("Name 1");
        level1.setCode("Code 1");
        level1.setDescription("Description 1");

        Level level2 = new Level();
        level2.setId(2);
        level2.setName("Name 2");
        level2.setCode("Code 2");
        level2.setDescription("Description 2");

        // Create sample LevelModels
        LevelModel model1 = new LevelModel();
        model1.setId(1);
        model1.setName("Name 1");
        model1.setCode("Code 1");
        model1.setDescription("Description 1");

        LevelModel model2 = new LevelModel();
        model2.setId(2);
        model2.setName("Name 2");
        model2.setCode("Code 2");
        model2.setDescription("Description 2");


        // Test the applyList method
        List<Level> levels = Arrays.asList(level1, level2);
        List<LevelModel> result = levelTransformer.applyList(levels);

        // Verify the results
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(model1, result.get(0));
        Assertions.assertEquals(model2, result.get(1));
    }

    @Test
    void testApplyListEmptyList() {
        // Test the applyList method with an empty list
        List<Level> levels = Collections.emptyList();
        List<LevelModel> result = levelTransformer.applyList(levels);

        // Verify the result is an empty list
        Assertions.assertTrue(result.isEmpty());
    }
}
