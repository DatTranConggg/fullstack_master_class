package com.hitachi.coe.fullstack.transformation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.hitachi.coe.fullstack.entity.Branch;
import com.hitachi.coe.fullstack.model.BranchModel;

@SpringBootTest
class BranchModelTransformerTest {
    @InjectMocks
    private BranchModelTransformer branchModelTransformer;

    @Test
    public void testApply() {
        BranchModel model = new BranchModel();
        model.setName(ArgumentMatchers.anyString());

        Branch result = branchModelTransformer.apply(model);

        Assertions.assertEquals(ArgumentMatchers.anyString(), result.getName());

    }

    @Test
    public void testApplyList() {
        BranchModel model1 = new BranchModel();
        model1.setName(ArgumentMatchers.anyString());

        BranchModel model2 = new BranchModel();
        model2.setName(ArgumentMatchers.anyString());

        List<BranchModel> models = Arrays.asList(model1, model2);

        List<Branch> result = branchModelTransformer.applyList(models);

        Assertions.assertEquals(2, result.size());

        result.stream().map(Branch::getName)
                .forEach(name ->Assertions.assertEquals(ArgumentMatchers.anyString(), name));
    }

    @Test
    public void testApplyList_EmptyList() {
        List<BranchModel> models = Collections.emptyList();

        List<Branch> result = branchModelTransformer.applyList(models);

        Assertions.assertTrue(result.isEmpty());
    }

}