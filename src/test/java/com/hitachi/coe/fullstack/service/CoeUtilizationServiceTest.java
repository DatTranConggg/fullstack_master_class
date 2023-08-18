package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.CoeUtilization;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.repository.CoeUtilizationRepository;
import com.hitachi.coe.fullstack.service.impl.CoeUtilizationServiceImpl;
import com.hitachi.coe.fullstack.transformation.CoeUtilizationTransformer;
import com.hitachi.coe.fullstack.util.DateFormatUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class CoeUtilizationServiceTest {
	@MockBean
	CoeUtilizationRepository coeUtilizationRepository;
	
	@MockBean
	CoeUtilizationTransformer coeUtilizationTransformer;

	@InjectMocks
	CoeUtilizationServiceImpl coeUtilizationService;

	CoeUtilization coeUtilization;

	@BeforeEach
	void setUp(){
		coeUtilization = new CoeUtilization();
		coeUtilization.setId(1);
		coeUtilization.setDuration("01 Aug 2023 - 30 Aug 2023");
		coeUtilization.setStartDate(DateFormatUtils.convertDateFromString("01 Aug 2023", "dd MMM uuuu"));
		coeUtilization.setEndDate(DateFormatUtils.convertDateFromString("30 Aug 2023", "dd MMM uuuu"));
		coeUtilization.setTotalUtilization(60.8);
	}
//	@Test
//	void testGetAllCoeUtilization_whenSuccess_thenReturnListCoeUtilization() {
//		CoeUtilization coeUtilization1 = new CoeUtilization();
//		CoeUtilization coeUtilization2 = new CoeUtilization();
//		CoeUtilization coeUtilization3 = new CoeUtilization();
//
//		List<CoeUtilization> coeUtilizations = List.of(coeUtilization1, coeUtilization2, coeUtilization3);
//		Mockito.when(coeUtilizationRepository.findAll()).thenReturn(coeUtilizations);
//		Mockito.when(coeUtilizationTransformer.applyList(coeUtilizations)).thenReturn(new ArrayList<>(Arrays.asList(new CoeUtilizationModel(), new CoeUtilizationModel(), new CoeUtilizationModel())));
//		List<CoeUtilizationModel> expectedCoeUtilizations = coeUtilizationTransformer.applyList(coeUtilizations);
//		Mockito.when(coeUtilizationService.getAllCoeUtilization()).thenReturn(expectedCoeUtilizations);
//		List<CoeUtilizationModel> result = coeUtilizationService.getAllCoeUtilization();
//		Assertions.assertNotNull(result);
//		Assertions.assertEquals(coeUtilizations.size(), result.size());
//	}

	@Test
	public void testSaveCoEUtilizationFromDuration_whenValidData_thenSuccess(){
		String duration = "Duration: 01 Aug 2023 - 30 Aug 2023";
		String dateImport = "01 Aug 2023";
		String format = "dd MMM uuuu";
		Double totalUT = 60.8;

		CoeUtilization coeUtilizationActual = coeUtilizationService.saveCoEUtilizationFromDuration(format, duration, dateImport, totalUT);

		assertNotNull(coeUtilizationActual);
		assertEquals(coeUtilization.getStartDate(), coeUtilizationActual.getStartDate());
		assertEquals(coeUtilization.getEndDate(), coeUtilizationActual.getEndDate());
		assertEquals(coeUtilization.getDuration(), coeUtilizationActual.getDuration());
		assertEquals(coeUtilization.getTotalUtilization(), coeUtilizationActual.getTotalUtilization());
	}

	@Test
	public void testSaveCoEUtilizationFromDuration_whenInvalidDurationFormat_thenThrowCoEException(){
		String duration = "Duration:15 Aug 2023 - 30 Aug 2023";
		String dateImport = "01 Aug 2023";
		String format = "dd MMM uuuu";
		Double totalUT = 60.8;


		Throwable throwable = assertThrows(CoEException.class,
				() -> coeUtilizationService.saveCoEUtilizationFromDuration(format, duration, dateImport, totalUT));

		//Verify
		assertEquals(CoEException.class, throwable.getClass());
		assertEquals(ErrorConstant.MESSAGE_INVALID_FORMAT_DURATION, throwable.getMessage());
	}

	@Test
	public void testSaveCoEUtilizationFromDuration_whenDurationIsMissing_thenThrowCoEException(){
		String duration = "Duration:  - 30 Aug 2023";
		String dateImport = "01 Aug 2023";
		String format = "dd MMM uuuu";
		Double totalUT = 60.8;

		Throwable throwable = assertThrows(CoEException.class,
				() -> coeUtilizationService.saveCoEUtilizationFromDuration(format, duration, dateImport, totalUT));

		//Verify
		assertEquals(CoEException.class, throwable.getClass());
		assertEquals(ErrorConstant.MESSAGE_DURATION_IS_REQUIRED, throwable.getMessage());
	}

	@Test
	public void testSaveCoEUtilizationFromDuration_whenStartDateEqualEndDate_thenThrowCoEException(){
		String duration = "Duration: 01 Aug 2023 - 01 Aug 2023";
		String dateImport = "01 Aug 2023";
		String format = "dd MMM uuuu";
		Double totalUT = 60.8;

		Throwable throwable = assertThrows(CoEException.class,
				() -> coeUtilizationService.saveCoEUtilizationFromDuration(format, duration, dateImport, totalUT));

		//Verify
		assertEquals(CoEException.class, throwable.getClass());
		assertEquals(ErrorConstant.MESSAGE_INVALID_START_DATE_END_DATE, throwable.getMessage());
	}

	@Test
	public void testSaveCoEUtilizationFromDuration_whenStartDateNotMatchImportDate_thenThrowCoEException(){
		String duration = "Duration: 15 Aug 2023 - 30 Aug 2023";
		String dateImport = "01 Jan 2023";
		String format = "dd MMM uuuu";
		Double totalUT = 60.8;

		Throwable throwable = assertThrows(CoEException.class,
				() -> coeUtilizationService.saveCoEUtilizationFromDuration(format, duration, dateImport, totalUT));

		//Verify
		assertEquals(CoEException.class, throwable.getClass());
		assertEquals(ErrorConstant.MESSAGE_INVALID_DATE_NOT_MATCH, throwable.getMessage());
	}

	@Test
	public void testSaveCoEUtilizationFromDuration_whenStartDateNotStartOfTheMonth_thenThrowCoEException(){
		String duration = "Duration: 15 Jan 2023 - 30 Jan 2023";
		String dateImport = "01 Jan 2023";
		String format = "dd MMM uuuu";
		Double totalUT = 60.8;

		Throwable throwable = assertThrows(CoEException.class,
				() -> coeUtilizationService.saveCoEUtilizationFromDuration(format, duration, dateImport, totalUT));

		//Verify
		assertEquals(CoEException.class, throwable.getClass());
		assertEquals(ErrorConstant.MESSAGE_INVALID_DATE_IMPORT, throwable.getMessage());
	}
}
