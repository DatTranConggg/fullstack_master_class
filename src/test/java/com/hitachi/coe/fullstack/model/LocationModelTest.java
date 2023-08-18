package com.hitachi.coe.fullstack.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LocationModelTest {
    @Test
    void testLocationModel() {
        LocationModel locationModel = new LocationModel();
        locationModel.setCode("CODE123");
        locationModel.setName("Location 1");

        Assertions.assertEquals("CODE123", locationModel.getCode());
        Assertions.assertEquals("Location 1", locationModel.getName());
    }
}
