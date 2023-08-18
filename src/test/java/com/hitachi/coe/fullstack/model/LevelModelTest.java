package com.hitachi.coe.fullstack.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LevelModelTest {

  @Test
  void testLevelModel() {
    // Create a LevelModel object
    LevelModel levelModel = new LevelModel();
    levelModel.setCode("CODE123");
    levelModel.setDescription("Description");
    levelModel.setName("Level 1");

    // Perform assertions
    Assertions.assertEquals("CODE123", levelModel.getCode());
    Assertions.assertEquals("Description", levelModel.getDescription());
    Assertions.assertEquals("Level 1", levelModel.getName());
  }
}
