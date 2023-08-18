package com.hitachi.coe.fullstack.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.coe.fullstack.model.CoeCoreTeamModel;
import com.hitachi.coe.fullstack.service.CoeCoreTeamService;

import lombok.SneakyThrows;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-data-test.properties")
class CoeCoreTeamControllerTest {
    @Autowired
	private MockMvc mvc;

	@MockBean
	private CoeCoreTeamService coeCoreTeamService;

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
//
//	@Test
//	@SneakyThrows
//	void testController() {
//		String url = "/api/v1/coe-core-team/create";
//		CoeCoreTeamModel coeCoreTeamModel = new CoeCoreTeamModel();
//		coeCoreTeamModel.setCode("t1");
//		coeCoreTeamModel.setName("Tienu");
//		coeCoreTeamModel.setStatus(1);
//		coeCoreTeamModel.setSubLeaderId(1);
//		coeCoreTeamModel.setCenterOfExcellence(new CenterOfExcellence());
//		when(coeCoreTeamService.createCoeCoreTeam(any(CoeCoreTeamModel.class))).thenReturn(1);
//		mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(asJsonString(coeCoreTeamModel)))
//				.andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty()).andExpect(jsonPath("$.id", is("1")))
//				.andReturn();
//	}
//
//	@Test
//	@SneakyThrows
//	void testUpdateCoeCoreTeamController() {
//		String url = "/api/v1/coe-core-team/update";
//		CoeCoreTeamModel coeCoreTeamModel = new CoeCoreTeamModel();
//		coeCoreTeamModel.setId(2);
//		coeCoreTeamModel.setCode("vcci");
//		coeCoreTeamModel.setName("duyanh");
//		coeCoreTeamModel.setStatus(0);
//		coeCoreTeamModel.setSubLeaderId(0);
//		coeCoreTeamModel.setCenterOfExcellence(new CenterOfExcellence());
//		when(coeCoreTeamService.updateCoeCoreTeam(any(CoeCoreTeamModel.class))).thenReturn(2);
//		mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(asJsonString(coeCoreTeamModel)))
//				.andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty()).andExpect(jsonPath("$.id", is("2")))
//				.andReturn();
//	}
//
//	@Test
//	@SneakyThrows
//	void deleteCoeCoreTeamController() {
//		String url = "/api/v1/coe-core-team/delete/-1";
//		when(coeCoreTeamService.deleteCoeCoreTeam(anyInt())).thenReturn(true);
//		mvc.perform(delete(url)).andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty())
//				.andExpect(jsonPath("$.result", is("true"))).andReturn();
//	}
//
	@Test
	@SneakyThrows
    void testGetCoeCoreTeamByCoreId_whenSuccess_thenReturnList() {
    	String url = "/api/v1/coe-core-team/list-coe-team/22";
    	CoeCoreTeamModel coe1 = new CoeCoreTeamModel();
    	CoeCoreTeamModel coe2 = new CoeCoreTeamModel();
    	CoeCoreTeamModel coe3 = new CoeCoreTeamModel();
    	List<CoeCoreTeamModel> mockCoe = new ArrayList<>();
    	mockCoe.add(coe1);
    	mockCoe.add(coe2);
    	mockCoe.add(coe3);
    	when(coeCoreTeamService.getAllCoeTeamByCoeId(anyInt())).thenReturn(mockCoe);
    	mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)
    			.content(asJsonString(mockCoe))).andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty()).andReturn();
    }
	@Test
	@SneakyThrows
    void testgetCoeCoreTeam_whenSuccess_thenReturnList() {
    	String url = "/api/v1/coe-core-team/list-coe-team";
    	CoeCoreTeamModel coe1 = new CoeCoreTeamModel();
    	CoeCoreTeamModel coe2 = new CoeCoreTeamModel();
    	CoeCoreTeamModel coe3 = new CoeCoreTeamModel();
    	List<CoeCoreTeamModel> mockCoe = new ArrayList<>();
    	mockCoe.add(coe1);
    	mockCoe.add(coe2);
    	mockCoe.add(coe3);
    	when(coeCoreTeamService.getAllCoeTeam()).thenReturn(mockCoe);
    	mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)
    			.content(asJsonString(mockCoe))).andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty()).andReturn();
    }
}
