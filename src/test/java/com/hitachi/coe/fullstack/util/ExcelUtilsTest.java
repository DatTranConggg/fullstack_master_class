package com.hitachi.coe.fullstack.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.coe.fullstack.model.EmployeeExcelModelTest;
import com.hitachi.coe.fullstack.model.EmployeeUtilizationModelTest;
import com.hitachi.coe.fullstack.model.ExcelConfigModel;
import com.hitachi.coe.fullstack.model.ExcelResponseModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExcelUtilsTest {

    String jsonString;

    ExcelConfigModel excelConfigModel;

    List<EmployeeExcelModelTest> employeeModelList;

    List<EmployeeUtilizationModelTest> employeeUtilizationModels;

    FileInputStream fileInputStream;

    ObjectMapper mapper;


    @BeforeEach
    public void setUp() {
        employeeModelList = new ArrayList<>();
        EmployeeExcelModelTest employeeExcelModelTest1 = EmployeeExcelModelTest.builder().legalEntityHireDate(new Date()).location("HN").email("lam@gg.com").hccId("768593").ldap("89569696").level("Senior").bu("Ha Noi").employeeName("Tony Teo").build();
        EmployeeExcelModelTest employeeExcelModelTest2 = EmployeeExcelModelTest.builder().legalEntityHireDate(new Date()).location("VN").email("lam@gg.com").hccId("768593").ldap("89569696").level("Senior").bu("Ho Chi Minh").employeeName("Michael Mung").build();
        employeeModelList.add(employeeExcelModelTest1);
        employeeModelList.add(employeeExcelModelTest2);
    }

    @Test
    public void writeEmployeeToExcel() throws IOException {
        jsonString = JsonUtils.readFileAsString("/json/EmployeeWriteConfig.json");
        excelConfigModel = JsonUtils.convertJsonToPojo(jsonString);
        ByteArrayInputStream result = ExcelUtils.writeToExcel(employeeModelList, excelConfigModel);
        Assertions.assertNotNull(result);
    }

    @Test
    public void writeEmployeeUtilizationToExcel() throws IOException {
        initUtilization();
        jsonString = JsonUtils.readFileAsString("/json/EmployeeUtilizationWriteConfig.json");
        excelConfigModel = JsonUtils.convertJsonToPojo(jsonString);
        ByteArrayInputStream result = ExcelUtils.writeToExcel(employeeUtilizationModels, excelConfigModel);
        Assertions.assertNotNull(result);
    }

    @Test
    public void readFromEmployeeUtilizationExcel() throws Exception {
        jsonString = JsonUtils.readFileAsString("/json/EmployeeUtilizationReadConfig.json");
        excelConfigModel = JsonUtils.convertJsonToPojo(jsonString);
        fileInputStream = new FileInputStream("src/test/resources/files/EmployeeUtilizationTest.xlsx");
        ExcelResponseModel result = ExcelUtils.readFromExcel(fileInputStream, excelConfigModel, EmployeeUtilizationModelTest.class);

        Assertions.assertEquals(2, result.getData().size());
    }

    @Test
    public void readFromEmployeeExcel() throws Exception {
        jsonString = JsonUtils.readFileAsString("/json/EmployeeReadConfig.json");
        excelConfigModel = JsonUtils.convertJsonToPojo(jsonString);
        fileInputStream = new FileInputStream("src/test/resources/files/employeeTest.xlsx");
        ExcelResponseModel result = ExcelUtils.readFromExcel(fileInputStream, excelConfigModel, EmployeeExcelModelTest.class);

        Assertions.assertEquals(1, result.getData().size());
    }

    @Test
    public void readFromEmployeeUtilizationExcelErrorCase() throws Exception {
        jsonString = JsonUtils.readFileAsString("/json/EmployeeUtilizationReadConfig.json");
        excelConfigModel = JsonUtils.convertJsonToPojo(jsonString);
        fileInputStream = new FileInputStream("src/test/resources/files/EmployeeUtilizationTestErrorCase.xlsx");
        ExcelResponseModel result = ExcelUtils.readFromExcel(fileInputStream, excelConfigModel, EmployeeUtilizationModelTest.class);

        Assertions.assertEquals(2, result.getData().size());
    }

    public void initUtilization() {
        employeeUtilizationModels = new ArrayList<>();

        EmployeeUtilizationModelTest employeeUtilizationModel = EmployeeUtilizationModelTest.builder().availableHours(1345).name("Tony Teo").location("HCM").email("hitachisasuke.com").timeSheet("Missing TimeSheet").build();
        EmployeeUtilizationModelTest employeeUtilizationModel2 = EmployeeUtilizationModelTest.builder().availableHours(1345).name("Tony Teo").location("HCM").email("hitachisasuke.com").timeSheet("Missing TimeSheet").build();
        EmployeeUtilizationModelTest employeeUtilizationModel3 = EmployeeUtilizationModelTest.builder().availableHours(1345).name("Tony Teo").location("HCM").email("hitachisasuke.com").startDate(new Date()).build();

        employeeUtilizationModels.add(employeeUtilizationModel);
        employeeUtilizationModels.add(employeeUtilizationModel2);
        employeeUtilizationModels.add(employeeUtilizationModel3);
    }
}