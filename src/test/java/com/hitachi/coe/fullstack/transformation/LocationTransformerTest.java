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

import com.hitachi.coe.fullstack.entity.Location;
import com.hitachi.coe.fullstack.model.LocationModel;


@SpringBootTest
 class LocationTransformerTest {

    @InjectMocks
    private LocationTransformer locationTransformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApplyList() {
        // Create sample Level entities
        Location location1 = new Location();
        location1.setId(1);
        location1.setName("Name 1");
        location1.setCode("Code 1");

        Location location2 = new Location();
        location2.setId(2);
        location2.setName("Name 2");
        location2.setCode("Code 2");

        // Create sample LevelModels
        LocationModel model1 = new LocationModel();
        model1.setId(1);
        model1.setName("Name 1");
        model1.setCode("Code 1");

        LocationModel model2 = new LocationModel();
        model2.setId(2);
        model2.setName("Name 2");
        model2.setCode("Code 2");

        // Test the applyList method
        List<Location> locations = Arrays.asList(location1, location2);
        List<LocationModel> result = locationTransformer.applyList(locations);

        // Verify the results
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(model1, result.get(0));
        Assertions.assertEquals(model2, result.get(1));
    }

    @Test
    void testApplyListEmpty() {
        // Test the applyList method with an empty list
        List<Location> locations = Collections.emptyList();
        List<LocationModel> result = locationTransformer.applyList(locations);

        // Verify the result is an empty list
        Assertions.assertTrue(result.isEmpty());
    }
}
