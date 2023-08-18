package com.hitachi.coe.fullstack.util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.model.EmployeeExportModel;
import com.hitachi.coe.fullstack.model.EmployeeImportModel;
import com.hitachi.coe.fullstack.model.EmployeeUtilizationModel;
import com.hitachi.coe.fullstack.model.ExcelConfigModel;
import com.hitachi.coe.fullstack.model.ExcelErrorDetail;
import com.hitachi.coe.fullstack.model.ExcelResponseModel;

@SpringBootTest
@TestPropertySource("classpath:application-data-test.properties")
class CsvUtilsTest {

    List<EmployeeExportModel> employeeModelList;
	
	@BeforeEach
	public void setUp() {
        employeeModelList = new ArrayList<>();
        EmployeeExportModel employeeExcelModelTest1 = EmployeeExportModel.builder().legalEntityHireDate(new Date()).branch("HN").email("ngocphuc@gmail.com").hccId("768593").ldap("89569696").level("Ultra Senior").practice("Mobile").name("Hoang Ngoc Phuc").build();
        EmployeeExportModel employeeExcelModelTest2 = EmployeeExportModel.builder().legalEntityHireDate(new Date()).branch("HCM").email("an.buihong@gmail.com").hccId("768594").ldap("89569669").level("Senior").practice("Game").name("Bui Hong An").build();
        employeeModelList.add(employeeExcelModelTest1);
        employeeModelList.add(employeeExcelModelTest2);
	}
	
	@Test
	void writeToCSV_whenSuccess_thenReturnCsvFile() throws IOException {
		String jsonPath = "EmployeeExportConfig.json";
		Stream<Path> pathStream = Files.walk(Paths.get(""));
		Path filPath = pathStream.filter(path -> path.getFileName().toString().equals(jsonPath)).findFirst()
				.orElse(null);
		pathStream.close();
		ExcelConfigModel excelConfig = JsonUtils
				.convertJsonToPojo(CsvUtils.readFileAsString(filPath.toAbsolutePath().toString()));
		ByteArrayInputStream result = CsvUtils.writeToCSV(employeeModelList, excelConfig);
		assertNotNull(result);
	}
	
	@Test
	void writeToCSV_whenListIsEmpty_thenThrowException() throws IOException {
		String jsonPath = "EmployeeExportConfig.json";
		Stream<Path> pathStream = Files.walk(Paths.get(""));
		Path filPath = pathStream.filter(path -> path.getFileName().toString().equals(jsonPath)).findFirst()
				.orElse(null);
		pathStream.close();
		List<EmployeeExportModel> emptyList = new ArrayList<>();
		ExcelConfigModel excelConfig = JsonUtils
				.convertJsonToPojo(CsvUtils.readFileAsString(filPath.toAbsolutePath().toString()));
		assertThatThrownBy(()->CsvUtils.writeToCSV(emptyList, excelConfig)).isInstanceOf(CoEException.class);
	}
	
	@Test
	void testReadCsc_whenSuccess_thenReturnData() throws IOException {
		String csvData = "LDAP,HCC ID,Employee Name,Level,Legal Entity Hire Date,Email,BU,Location\r\n"
				+ "71269780,125351,Nguyen A 1,MCS2,8/15/2000,a.le@hitachivantara.com,practice2,branch4\r\n"
				+ "71279746,125282,Le B 22,MCS5,8/15/2000,b.le@hitachivantara.com,practice2,branch4\r\n"
				+ "51260938,3,Nguyen D,MCS5,8/15/2000,abce@hitachivantara.com,practice3,branch4b\r\n"
				+ "51260936,2,Nguyen C,MCS5,8/15/2000,abcb@hitachivantara.com,practice3,branch4b\r\n";
		String jsonPath = "EmployeeReadConfig.json";
		Stream<Path> pathStream = Files.walk(Paths.get(""));
		Path filPath = pathStream.filter(path -> path.getFileName().toString().equals(jsonPath)).findFirst()
				.orElse(null);
		pathStream.close();
		ExcelConfigModel excelConfig = JsonUtils
				.convertJsonToPojo(CsvUtils.readFileAsString(filPath.toAbsolutePath().toString()));
		InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
		Class<EmployeeImportModel> tClass = EmployeeImportModel.class;
		ExcelResponseModel result = CsvUtils.readCsv(inputStream, excelConfig, tClass);
		HashMap<Integer, Object> list = result.getData();
		int totalRows = result.getTotalRows();
		List<ExcelErrorDetail> errorDetails = result.getErrorDetails();
		assertEquals(4, list.size());
		assertEquals(4, totalRows);
		assertEquals(new ArrayList<>(), errorDetails);
	}

	@Test
	void testReadCsc_whenFileEmpty_thenThrowException() throws IOException {
		String csvData = "";
		String jsonPath = "EmployeeReadConfig.json";
		Stream<Path> pathStream = Files.walk(Paths.get(""));
		Path filPath = pathStream.filter(path -> path.getFileName().toString().equals(jsonPath)).findFirst()
				.orElse(null);
		pathStream.close();
		ExcelConfigModel excelConfig = JsonUtils
				.convertJsonToPojo(CsvUtils.readFileAsString(filPath.toAbsolutePath().toString()));
		InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
		Class<EmployeeImportModel> tClass = EmployeeImportModel.class;
		assertThatThrownBy(() -> CsvUtils.readCsv(inputStream, excelConfig, tClass)).isInstanceOf(CoEException.class);
	}
	@Test
	void testReadCSV_whenRowIsBlank_thenIgnoreAndAddTheNextRow() throws IOException {
		String csvData = "Duration: 01 Mar 2023 - 31 Mar 2023,,,184,VN working hours,,,,,,,,,,,,,,,\r\n"
				+ ",,,176,JP working hours (Holiday: 21-Mar),,,,,,,,,,,,,,,\r\n"
				+ ",,,184,US working hours,,,,180,20,332,54.20%,,,,,,,,\r\n"
				+ "No.,ID,Name,Level,Start date,Location,BU,CoE,Billable hours,Time off,Available Hours,%Billable,Logged hours,Oracle staffed Project,Timesheet status,Email,,,Resources,%\r\n"
				+ "1,125351,Ta A,SMCS1,1-Oct-14,JP Tokyo,DM,Other,123,20,156,0.00%,100, N/A ,Missing Timesheet,a.ta@hitachivantara.com,,Billable >=80%,1,100.00%\r\n"
				+ "2,125282,Nguyen D,MSP3,1-Mar-15,JP Tokyo,EMB,Other,180,123,176,%,150, N/A ,Missing Timesheet,d.nguyen@hitachivantara.com,,Billable <80%,0,0.00%\r\n"
				+ ",,,,,,,,,,,,,,,,,,,,";
		String jsonPath = "EmployeeUtilizationReadConfig.json";
		Stream<Path> pathStream = Files.walk(Paths.get(""));
		Path filPath = pathStream.filter(path -> path.getFileName().toString().equals(jsonPath)).findFirst()
				.orElse(null);
		pathStream.close();
		ExcelConfigModel excelConfig = JsonUtils
				.convertJsonToPojo(CsvUtils.readFileAsString(filPath.toAbsolutePath().toString()));
		InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
		Class<EmployeeUtilizationModel> tClass = EmployeeUtilizationModel.class;
		ExcelResponseModel result = CsvUtils.readCsv(inputStream, excelConfig, tClass);
		HashMap<Integer, Object> list = result.getData();
		int totalRows = result.getTotalRows();
		List<ExcelErrorDetail> errorDetails = result.getErrorDetails();
		assertEquals(2, list.size());
		assertEquals(2, totalRows);
		assertEquals(0, errorDetails.size());
	}
	@Test
	void testReadCSV_whenFieldDateIsError_thenReturnDataAndErrorAdded() throws IOException {
		String csvData ="LDAP,HCC ID,Employee Name,Level,Legal Entity Hire Date,Email,BU,Location\r\n"
				+ "71269780,125351,Nguyen A 1,MCS2,8/15/2000,a.le@hitachivantara.com,practice2,branch4\r\n"
				+ "71279746,125282,Le B 22,MCS5,hi,b.le@hitachivantara.com,practice2,branch4\r\n"
				+ "51260938,3,Nguyen D,MCS5,8/15/2000,abce@hitachivantara.com,practice3,branch4b\r\n"
				+ "51260936,2,Nguyen C,MCS5,hELLO,abcb@hitachivantara.com,practice3,branch4b\r\n"; 
		String jsonPath = "EmployeeReadConfig.json";
		Stream<Path> pathStream = Files.walk(Paths.get(""));
		Path filPath = pathStream.filter(path -> path.getFileName().toString().equals(jsonPath)).findFirst()
				.orElse(null);
		pathStream.close();
		ExcelConfigModel excelConfig = JsonUtils
				.convertJsonToPojo(CsvUtils.readFileAsString(filPath.toAbsolutePath().toString()));
		InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
		Class<EmployeeImportModel> tClass = EmployeeImportModel.class;
		ExcelResponseModel result = CsvUtils.readCsv(inputStream, excelConfig, tClass);
		HashMap<Integer, Object> list = result.getData();
		int totalRows = result.getTotalRows();

		List<ExcelErrorDetail> errors = new ArrayList<>();
		ExcelErrorDetail excelError = new ExcelErrorDetail();
		HashMap<String, String> detail = new HashMap<>();
		detail.put(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_INVALID_DATE);
		excelError.setRowIndex(3);
		excelError.setDetails(detail);
		errors.add(excelError);
		List<ExcelErrorDetail> errorDetails = result.getErrorDetails();
		assertEquals(2, list.size());
		assertEquals(4, totalRows);
		assertEquals(2, errorDetails.size());
		assertEquals(errors.get(0).getRowIndex(), errorDetails.get(0).getRowIndex());
	}
	@Test
	void testReadCSV_whenFieldNumberIsError_thenReturnDataAndErrorAdded() throws IOException {
		String csvData = "Duration: 01 Mar 2023 - 31 Mar 2023,,,184,VN working hours,,,,,,,,,,,,,,,\r\n"
				+ ",,,hello,JP working hours (Holiday: 21-Mar),,,,,,,,,,,,,,,\r\n"
				+ ",,,184,US working hours,,,,180,20,332,54.20%,,,,,,,,\r\n"
				+ "No.,ID,Name,Level,Start date,Location,BU,CoE,Billable hours,Time off,Available Hours,%Billable,Logged hours,Oracle staffed Project,Timesheet status,Email,,,Resources,%\r\n"
				+ "1,xinchao,Ta A,SMCS1,1-Oct-14,JP Tokyo,DM,Other,123,20,156,0.00%,100, N/A ,Missing Timesheet,a.ta@hitachivantara.com,,Billable >=80%,1,100.00%\r\n"
				+ "2,125282,Nguyen D,MSP3,1-Mar-15,JP Tokyo,EMB,Other,180,123,176,102.30%,150, N/A ,Missing Timesheet,d.nguyen@hitachivantara.com,,Billable <80%,0,0.00%\r\n"
				+ ",,,,,,,,,,,,,,,,,,,,";
		String jsonPath = "EmployeeUtilizationReadConfig.json";
		Stream<Path> pathStream = Files.walk(Paths.get(""));
		Path filPath = pathStream.filter(path -> path.getFileName().toString().equals(jsonPath)).findFirst()
				.orElse(null);
		pathStream.close();
		ExcelConfigModel excelConfig = JsonUtils
				.convertJsonToPojo(CsvUtils.readFileAsString(filPath.toAbsolutePath().toString()));
		InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
		Class<EmployeeUtilizationModel> tClass = EmployeeUtilizationModel.class;
		ExcelResponseModel result = CsvUtils.readCsv(inputStream, excelConfig, tClass);
		HashMap<Integer, Object> list = result.getData();
		int totalRows = result.getTotalRows();

		List<ExcelErrorDetail> errors = new ArrayList<>();
		ExcelErrorDetail excelError = new ExcelErrorDetail();
		HashMap<String, String> detail = new HashMap<>();
		detail.put(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_INVALID_DATE);
		excelError.setRowIndex(5);
		excelError.setDetails(detail);
		errors.add(excelError);
		List<ExcelErrorDetail> errorDetails = result.getErrorDetails();
		assertEquals(1, list.size());
		assertEquals(2, totalRows);
		assertEquals(1, errorDetails.size());
		assertEquals(errors.get(0).getRowIndex(), errorDetails.get(0).getRowIndex());
	}
	@Test
	void testReadCSV_whenFieldIsRequireButNull_thenReturnDataAndErrorAdded() throws IOException {
		String csvData = "LDAP,HCC ID,Employee Name,Level,Legal Entity Hire Date,Email,BU,Location\r\n"
				+ "71279746,125351,Nguyen A 1,MCS5,8/15/2000,a.le@hitachivantara.com,practice3,branch1" + "\r\n"
				+ "71279746,,Le B 22,MCS4,8/15/2000,b.le@hitachivantara.com,practice2,branch3";
		String jsonPath = "EmployeeReadConfig.json";
		Stream<Path> pathStream = Files.walk(Paths.get(""));
		Path filPath = pathStream.filter(path -> path.getFileName().toString().equals(jsonPath)).findFirst()
				.orElse(null);
		pathStream.close();
		ExcelConfigModel excelConfig = JsonUtils
				.convertJsonToPojo(CsvUtils.readFileAsString(filPath.toAbsolutePath().toString()));
		InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
		Class<EmployeeImportModel> tClass = EmployeeImportModel.class;
		ExcelResponseModel result = CsvUtils.readCsv(inputStream, excelConfig, tClass);
		HashMap<Integer, Object> list = result.getData();
		int totalRows = result.getTotalRows();

		List<ExcelErrorDetail> errors = new ArrayList<>();
		ExcelErrorDetail excelError = new ExcelErrorDetail();
		HashMap<String, String> detail = new HashMap<>();
		detail.put(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_INVALID_DATE);
		excelError.setRowIndex(3);
		excelError.setDetails(detail);
		errors.add(excelError);
		List<ExcelErrorDetail> errorDetails = result.getErrorDetails();
		assertEquals(1, list.size());
		assertEquals(2, totalRows);
		assertEquals(1, errorDetails.size());
		assertEquals(errors.get(0).getRowIndex(), errorDetails.get(0).getRowIndex());
	}
	
	@Test
	void testReadCSV_whenFieldEmailError_thenReturnDataAndErrorAdded() throws IOException {
		String csvData = "LDAP,HCC ID,Employee Name,Level,Legal Entity Hire Date,Email,BU,Location\r\n"
				+ "71279746,125351,Nguyen A 1,MCS5,8/15/2000,a.le@hitachivantara.com,practice3,branch1" + "\r\n"
				+ "71279746,125282,Le B 22,MCS4,8/15/2000,thisisemail:d,practice2,branch3";
		String jsonPath = "EmployeeReadConfig.json";
		Stream<Path> pathStream = Files.walk(Paths.get(""));
		Path filPath = pathStream.filter(path -> path.getFileName().toString().equals(jsonPath)).findFirst()
				.orElse(null);
		pathStream.close();
		ExcelConfigModel excelConfig = JsonUtils
				.convertJsonToPojo(CsvUtils.readFileAsString(filPath.toAbsolutePath().toString()));
		InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
		Class<EmployeeImportModel> tClass = EmployeeImportModel.class;
		ExcelResponseModel result = CsvUtils.readCsv(inputStream, excelConfig, tClass);
		HashMap<Integer, Object> list = result.getData();
		int totalRows = result.getTotalRows();

		List<ExcelErrorDetail> errors = new ArrayList<>();
		ExcelErrorDetail excelError = new ExcelErrorDetail();
		HashMap<String, String> detail = new HashMap<>();
		detail.put(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_INVALID_DATE);
		excelError.setRowIndex(3);
		excelError.setDetails(detail);
		errors.add(excelError);
		assertEquals(2, list.size());
		assertEquals(2, totalRows);
	}
	
	@Test
	void testReadCSV_whenFileIsWrong_thenThrowException() throws IOException {
		String csvData = "Duration: 01 Mar 2023 - 31 Mar 2023,,,184,VN working hours,,,,,,,,,,,,,,,\r\n"
				+ ",,,hello,JP working hours (Holiday: 21-Mar),,,,,,,,,,,,,,,\r\n"
				+ ",,,184,US working hours,,,,180,20,332,54.20%,,,,,,,,\r\n"
				+ "No.,ID,Name,Level,Start date,Location,BU,CoE,Billable hours,Time off,Available Hours,%Billable,Logged hours,Oracle staffed Project,Timesheet status,Email,,,Resources,%\r\n"
				+ "1,xinchao,Ta A,SMCS1,1-Oct-14,JP Tokyo,DM,Other,123,20,156,0.00%,100, N/A ,Missing Timesheet,a.ta@hitachivantara.com,,Billable >=80%,1,100.00%\r\n"
				+ "2,125282,Nguyen D,MSP3,1-Mar-15,JP Tokyo,EMB,Other,180,123,176,102.30%,150, N/A ,Missing Timesheet,d.nguyen@hitachivantara.com,,Billable <80%,0,0.00%\r\n"
				+ ",,,,,,,,,,,,,,,,,,,,";
		String jsonPath = "EmployeeReadConfig.json";
		Stream<Path> pathStream = Files.walk(Paths.get(""));
		Path filPath = pathStream.filter(path -> path.getFileName().toString().equals(jsonPath)).findFirst()
				.orElse(null);
		pathStream.close();
		ExcelConfigModel excelConfig = JsonUtils.convertJsonToPojo(CsvUtils.readFileAsString(filPath.toAbsolutePath().toString()));
		InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
		Class<EmployeeImportModel> tClass = EmployeeImportModel.class;
		assertThatThrownBy(() -> CsvUtils.readCsv(inputStream, excelConfig, tClass)).isInstanceOf(CoEException.class);
	}
}