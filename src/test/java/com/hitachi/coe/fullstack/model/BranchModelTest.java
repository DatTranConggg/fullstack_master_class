package com.hitachi.coe.fullstack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
class BranchModelTest {

	@Test
	void testBranchModel() {
		BranchModel model = new BranchModel();
		model.setId(1);
		model.setCode("177013");
		model.setCreated(new Date());
		model.setCreatedBy("Administrator");
		model.setLabel("Đà Nẵng - Tòa nhà Phi Long, 52 đường Nguyễn Văn Linh, Phường Nam Dương, Quận Hải Châu, Thành phố Đà Nẵng");
		model.setName("Da Nang");
		model.setUpdated(new Date());
		model.setUpdatedBy("me");
		model.getLabel();
		model.getName();
		String code = model.getCode();
		int id = model.getId();
		assertEquals("177013", code);
		assertEquals(1, id);
	}
}
