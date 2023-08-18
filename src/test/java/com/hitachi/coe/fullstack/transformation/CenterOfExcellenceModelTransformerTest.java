package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.CenterOfExcellence;
import com.hitachi.coe.fullstack.model.CenterOfExcellenceModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CenterOfExcellenceModelTransformerTest {

    @InjectMocks
    private CenterOfExcellenceModelTransformer centerOfExcellenceModelTransformer;

    @Test
    public void testApply() {
        CenterOfExcellenceModel model = new CenterOfExcellenceModel();
        model.setCode(ArgumentMatchers.anyString());

        CenterOfExcellence result = centerOfExcellenceModelTransformer.apply(model);

        Assertions.assertEquals(ArgumentMatchers.anyString(), result.getCode());

    }

    @Test
    public void testApplyList() {
        CenterOfExcellenceModel model1 = new CenterOfExcellenceModel();
        model1.setCode(ArgumentMatchers.anyString());

        CenterOfExcellenceModel model2 = new CenterOfExcellenceModel();
        model2.setCode(ArgumentMatchers.anyString());

        List<CenterOfExcellenceModel> models = Arrays.asList(model1, model2);

        List<CenterOfExcellence> result = centerOfExcellenceModelTransformer.applyList(models);

        Assertions.assertEquals(2, result.size());

        result.stream().map(CenterOfExcellence::getCode)
                .forEach(code ->Assertions.assertEquals(ArgumentMatchers.anyString(), code));


    }

    @Test
    public void testApplyList_EmptyList() {
        List<CenterOfExcellenceModel> models = Collections.emptyList();

        List<CenterOfExcellence> result = centerOfExcellenceModelTransformer.applyList(models);

        assertTrue(result.isEmpty());
    }

}