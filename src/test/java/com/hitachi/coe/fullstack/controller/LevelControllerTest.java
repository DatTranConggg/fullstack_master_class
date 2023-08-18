package com.hitachi.coe.fullstack.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.coe.fullstack.model.LevelModel;
import com.hitachi.coe.fullstack.service.LevelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-data-test.properties")
public class LevelControllerTest {
  @Autowired
  private MockMvc mvc;
  @Autowired
  private LevelController levelController;

  @MockBean
  private LevelService levelService;

  @Test
  void testGetAllUsers_OnCommonSuccess() throws Exception {
    String url = "/api/v1/levels";
    LevelModel levelModel = new LevelModel();
    levelModel.setId(1);
    levelModel.setName("C1");
    levelModel.setCode("C1");
    levelModel.setDescription("This is C1");
    List<LevelModel> listLevel = new ArrayList<>();
    listLevel.add(levelModel);
    when(levelService.getAllLevels()).thenReturn(listLevel);
    
    ResponseEntity<List<LevelModel>> allLevelsSearched = levelController.getAllLevels();
    assertEquals(HttpStatus.OK, allLevelsSearched.getStatusCode());
  
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    ObjectMapper mapper = new ObjectMapper();
    List<LevelModel> actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<LevelModel>>() {});
    assertEquals(actual, listLevel);
  }

}
