package com.hitachi.coe.fullstack.transformation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.hitachi.coe.fullstack.entity.Practice;
import com.hitachi.coe.fullstack.model.PracticeModel;

@SpringBootTest
class PracticeModelTransformerTest {
    @InjectMocks
    private PracticeModelTransformer practiceModelTransformer;

    @Test
    public void testApply() {
        PracticeModel model = new PracticeModel();
        model.setName(ArgumentMatchers.anyString());

        Practice result = practiceModelTransformer.apply(model);

        Assertions.assertEquals(ArgumentMatchers.anyString(), result.getName());

    }

    @Test
    public void testApplyList() {
        PracticeModel model1 = new PracticeModel();
        model1.setName(ArgumentMatchers.anyString());

        PracticeModel model2 = new PracticeModel();
        model2.setName(ArgumentMatchers.anyString());

        List<PracticeModel> models = Arrays.asList(model1, model2);

        List<Practice> result = practiceModelTransformer.applyList(models);

        Assertions.assertEquals(2, result.size());

        result.stream().map(Practice::getName)
                .forEach(name ->Assertions.assertEquals(ArgumentMatchers.anyString(), name));
    }

    @Test
    public void testApplyList_EmptyList() {
        List<PracticeModel> models = Collections.emptyList();

        List<Practice> result = practiceModelTransformer.applyList(models);

        Assertions.assertTrue(result.isEmpty());
    }

}