package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.Level;
import com.hitachi.coe.fullstack.model.LevelModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class LevelModelTransformerTest {

    @InjectMocks
    private LevelModelTransformer levelModelTransformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApplyList() {
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
        List<LevelModel> models = Arrays.asList(model1, model2);
        List<Level> result = levelModelTransformer.applyList(models);

        // Verify the results
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void testApplyListEmptyList() {
        // Test the applyList method with an empty list
        List<LevelModel> models = Collections.emptyList();
        List<Level> result = levelModelTransformer.applyList(models);

        // Verify the result is an empty list
        Assertions.assertTrue(result.isEmpty());
    }
}
