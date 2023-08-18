package com.hitachi.coe.fullstack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.coe.fullstack.model.EmployeeModel;
import com.hitachi.coe.fullstack.model.EvaluationLevelModel;
import com.hitachi.coe.fullstack.model.ProjectFeedbackModel;
import com.hitachi.coe.fullstack.model.ProjectModel;
import com.hitachi.coe.fullstack.service.ProjectFeedbackService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectFeedbackController.class)
class ProjectFeedbackControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectFeedbackService projectFeedbackService;

    @Test
    public void testCreateProjectFeedback_Success() throws Exception {

        ProjectFeedbackModel projectFeedbackModel = new ProjectFeedbackModel();
        projectFeedbackModel.setId(1);
        projectFeedbackModel.setFeedback("Hello world");
        projectFeedbackModel.setFeedbackDate(new Date());
        projectFeedbackModel.setProjectManager(("Harry"));
        projectFeedbackModel.setProject(new ProjectModel());
        projectFeedbackModel.setEmployee(new EmployeeModel());
        projectFeedbackModel.setEvaluationLevel(new EvaluationLevelModel());

        Mockito.when(projectFeedbackService.add(Mockito.any(ProjectFeedbackModel.class))).thenReturn(123);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/project-feedback/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectFeedbackModel)));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data.id").value("123"));
    }

    @Test
    public void testCreateProjectFeedback_Error_employee() throws Exception {

        ProjectFeedbackModel projectFeedbackModel = new ProjectFeedbackModel();

        ResultActions resultActions = mockMvc.perform(post("/api/v1/project-feedback/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectFeedbackModel)));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Employee cannot be null"));
    }
}