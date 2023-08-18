package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.entity.Level;
import com.hitachi.coe.fullstack.model.LevelModel;
import com.hitachi.coe.fullstack.repository.LevelRepository;
import com.hitachi.coe.fullstack.transformation.LevelTransformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
public class LevelServiceTest {
  @Autowired
  private LevelService levelService;

  @MockBean
  private LevelRepository levelRepository;
  @MockBean
  private LevelTransformer levelTransformer;
  
  @Test
  void testGetAllLevels_OnCommonSuccess() {
    Level level = new Level();
    level.setId(1);
    LevelModel levelModel = new LevelModel();
    levelModel.setId(1);
    List<Level> listLevel = new ArrayList<Level>();
    List<LevelModel> listLevelModel = new ArrayList<LevelModel>();
    listLevel.add(level);
    listLevelModel.add(levelModel);
    Mockito.when(levelRepository.getAllLevels()).thenReturn(listLevel);
    Mockito.when(levelTransformer.applyList(listLevel)).thenReturn(listLevelModel);
    
    List<LevelModel> result = levelService.getAllLevels();
    Assertions.assertTrue(result != null);
    Assertions.assertEquals(listLevelModel, result);
  }
  
}
