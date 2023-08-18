package com.hitachi.coe.fullstack.transformation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.hitachi.coe.fullstack.entity.CoeCoreTeam;
import com.hitachi.coe.fullstack.model.CoeCoreTeamModel;

@SpringBootTest
class CoeCoreTeamModelTransformerTest {

    @InjectMocks
    private CoeCoreTeamModelTransformer coeCoreTeamModelTransformer;

    @Test
    public void testApply() {
        CoeCoreTeamModel model = new CoeCoreTeamModel();
        model.setCode(ArgumentMatchers.anyString());

        CoeCoreTeam result = coeCoreTeamModelTransformer.apply(model);

        Assertions.assertEquals(ArgumentMatchers.anyString(), result.getCode());

    }

    @Test
    public void testApplyList() {
        CoeCoreTeamModel model1 = new CoeCoreTeamModel();
        model1.setCode(ArgumentMatchers.anyString());

        CoeCoreTeamModel model2 = new CoeCoreTeamModel();
        model2.setCode(ArgumentMatchers.anyString());

        List<CoeCoreTeamModel> models = Arrays.asList(model1, model2);

        List<CoeCoreTeam> result = coeCoreTeamModelTransformer.applyList(models);

        Assertions.assertEquals(2, result.size());

        result.stream().map(CoeCoreTeam::getCode)
                .forEach(name ->Assertions.assertEquals(ArgumentMatchers.anyString(), name));

    }

    @Test
    public void testApplyList_EmptyList() {
        List<CoeCoreTeamModel> models = Collections.emptyList();

        List<CoeCoreTeam> result = coeCoreTeamModelTransformer.applyList(models);

        assertTrue(result.isEmpty());
    }

}