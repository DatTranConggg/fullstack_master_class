package com.hitachi.coe.fullstack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.coe.fullstack.model.ExportRequest;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.service.ExportService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ExportController.class)
public class ExportControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ExportService exportService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    void testExportController() throws Exception {
        ExportRequest exportRequest = ExportRequest.builder()
                .keyWord(null)
                .typeFile("xlsx")
                .practiceName(null)
                .coeCoreTeamName(null)
                .branchName(null)
                .fromDateStr("2023-04-05")
                .toDateStr("2023-04-05")
                .pageNo(0)
                .itemPerPage(10)
                .sortBy("name")
                .isAscending(true)
                .build();
        mvc.perform(post("/api/v1/export").contentType("application/json").content(asJsonString(exportRequest))).andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}