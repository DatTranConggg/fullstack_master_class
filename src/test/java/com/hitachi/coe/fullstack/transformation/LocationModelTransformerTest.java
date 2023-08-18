package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.Location;
import com.hitachi.coe.fullstack.model.LocationModel;
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
class LocationModelTransformerTest {

    @InjectMocks
    private LocationModelTransformer locationTransformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApplyList() {
        // Create sample LocationModels
        LocationModel model1 = new LocationModel();
        model1.setId(1);
        model1.setName("Name 1");
        model1.setCode("Code 1");

        LocationModel model2 = new LocationModel();
        model2.setId(2);
        model2.setName("Name 2");
        model2.setCode("Code 2");

        // Test the applyList method
        List<LocationModel> models = Arrays.asList(model1, model2);
        List<Location> result = locationTransformer.applyList(models);

        // Verify the results
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void testApplyListEmptyList() {
        // Test the applyList method with an empty list
        List<LocationModel> models = Collections.emptyList();
        List<Location> result = locationTransformer.applyList(models);

        // Verify the result is an empty list
        Assertions.assertTrue(result.isEmpty());
    }
}
