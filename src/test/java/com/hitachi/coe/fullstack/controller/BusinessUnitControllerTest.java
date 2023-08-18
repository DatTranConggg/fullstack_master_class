package com.hitachi.coe.fullstack.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.hitachi.coe.fullstack.service.BusinessUnitService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-data-test.properties")
class BusinessUnitControllerTest {
	@Autowired
	private MockMvc mvc;

	@InjectMocks
	private BusinessUnitController businessUnitController;

	@MockBean
	private BusinessUnitService businessUnitService;

//	private static String asJsonString(final Object obj) {
//		try {
//			return new ObjectMapper().writeValueAsString(obj);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	
//	TODO 
//	mvn test have Failures: No value at JSON path "$.id"
//	@Test
//	@SneakyThrows
//	void testController() {
//
//		String url = "/api/v1/business-unit/create";
//		BusinessUnitModel businessUnitModel = new BusinessUnitModel();
//		businessUnitModel.setId(1);
//		when(businessUnitService.add(any(BusinessUnitModel.class))).thenReturn(1);
//		mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(asJsonString(businessUnitModel)))
//				.andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty()).andExpect(jsonPath("$.id", is("1")))
//				.andReturn();
//
//	}

	
//	TODO
//	mvn test have Failures: No value at JSON path "$.id"
//	@Test
//	@SneakyThrows
//	void testUpdateBusinessUnit1() {
//
//		String url = "/api/v1/business-unit/update";
//		BusinessUnitModel businessUnitModel = new BusinessUnitModel();
//		businessUnitModel.setId(1);
//		int updatedId = 1;
//		when(businessUnitService.updateBusinessUnit(businessUnitModel)).thenReturn(updatedId);
//		mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(asJsonString(businessUnitModel)))
//				.andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty()).andExpect(jsonPath("$.id", is("1")))
//				.andReturn();
//
//	}

	/*
	 * @Test void testUpdateBusinessUnit() {
	 * 
	 * BusinessUnitModel businessUnitModel = new BusinessUnitModel();
	 * businessUnitModel.setId(1);
	 * 
	 * int updatedId = 1;
	 * 
	 * when(businessUnitService.updateBusinessUnit(businessUnitModel)).thenReturn(
	 * updatedId); ResponseEntity<Object> responseEntity =
	 * businessUnitController.updateBusinessUnit(businessUnitModel);
	 * assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	 * 
	 * }
	 */

}
