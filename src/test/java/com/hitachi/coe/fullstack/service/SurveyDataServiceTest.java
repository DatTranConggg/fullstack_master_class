package com.hitachi.coe.fullstack.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.hitachi.coe.fullstack.entity.Branch;
import com.hitachi.coe.fullstack.entity.BusinessUnit;
import com.hitachi.coe.fullstack.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.SurveyData;
import com.hitachi.coe.fullstack.entity.CoeCoreTeam;
import com.hitachi.coe.fullstack.model.ExcelResponseModel;
import com.hitachi.coe.fullstack.model.ImportResponse;
import com.hitachi.coe.fullstack.model.SurveyDataModel;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.repository.SurveyDataRepository;
import com.hitachi.coe.fullstack.service.impl.SurveyDataServiceImpl;
import com.hitachi.coe.fullstack.transformation.SurveyDataTransformer;

import lombok.SneakyThrows;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
public class SurveyDataServiceTest {
    ExcelResponseModel listOfSurveyData;
    Employee employee;
    SurveyData surveyDataOne;
    SurveyData surveyDataTwo;
    SurveyData surveyDataThree;
    Calendar calendar;

    @MockBean
    private SurveyDataRepository surveyDataRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private SurveyDataTransformer surveyDataTransformer;

    @Autowired
    private SurveyDataServiceImpl surveyDataService;
    @SneakyThrows
    @BeforeEach
    public void setUp(){

        calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JUNE, 14);
        listOfSurveyData = new ExcelResponseModel();
        HashMap<Integer, Object> data = new HashMap<>();
        SurveyDataModel surveyDataModel = new SurveyDataModel();
        surveyDataModel.setStartTime(calendar.getTime());
        surveyDataModel.setCompletionTime(calendar.getTime());
        surveyDataModel.setEmail("abc@hitachivantara.com");
        surveyDataModel.setName(" ");
        surveyDataModel.setEmployeeId("51229931");
        surveyDataModel.setFullName("abc");
        data.put(2, surveyDataModel);
        listOfSurveyData.setData(data);
        listOfSurveyData.setTotalRows(1);
        listOfSurveyData.setErrorDetails(Collections.emptyList());
        listOfSurveyData.setStatus(Status.SUCCESS);

        employee = new Employee();
        surveyDataOne = new SurveyData();
        surveyDataTwo = new SurveyData();
        surveyDataThree = new SurveyData();

        surveyDataOne.setStartTime(calendar.getTime());
        surveyDataOne.setCompletionTime(calendar.getTime());
        surveyDataOne.setEmail("abc@hitachivantara.com");
        surveyDataOne.setName(" ");
        surveyDataOne.setEmployeeId("51229931");
        surveyDataOne.setFullName("abc");

        surveyDataTwo.setStartTime(calendar.getTime());
        surveyDataTwo.setCompletionTime(calendar.getTime());
        surveyDataTwo.setEmail("abc@hitachivantara.com");
        surveyDataTwo.setName(" ");
        surveyDataTwo.setEmployeeId("512");
        surveyDataTwo.setFullName("abc");

        surveyDataThree.setStartTime(calendar.getTime());
        surveyDataThree.setCompletionTime(calendar.getTime());
        surveyDataThree.setEmail("abc123@hitachivantara.com");
        surveyDataThree.setName(" ");
        surveyDataThree.setEmployeeId("51229930");
        surveyDataThree.setFullName("abc");

        employee.setEmail("abc@hitachivantara.com");
        employee.setLdap("51229931");
        employee.setHccId("123");
        employee.setName("Nguyen B");
        employee.setLegalEntityHireDate(calendar.getTime());
        employee.setBusinessUnit(new BusinessUnit());
        employee.setBranch(new Branch());
        employee.setCoeCoreTeam(new CoeCoreTeam());
    }
    @Test
    @SneakyThrows
    public void testImportSurveyDataExcel_whenValidEmployeeIdAndEmail_thenSuccess(){
        when(surveyDataTransformer.toEntity(Mockito.isA(SurveyDataModel.class))).thenReturn(surveyDataOne);
        when(employeeRepository.findByEmailOrLdapOrHccId(surveyDataOne.getEmail(),surveyDataOne.getEmployeeId(),null)).thenReturn(List.of(employee));
        when(surveyDataRepository.getSurveyDataByEmployeeIdOrEmail(surveyDataOne.getEmail(),surveyDataOne.getEmployeeId())).thenReturn(Collections.singletonList(surveyDataOne));
        doNothing().when(surveyDataRepository).deleteAll();
        when(surveyDataRepository.saveAll(Collections.singletonList(surveyDataOne))).thenReturn(Collections.singletonList(surveyDataOne));
        ImportResponse importSurveyData = surveyDataService.importSurveyDataExcel(listOfSurveyData);
        assertNotNull(importSurveyData);
        assertEquals(1, importSurveyData.getTotalRows());
        assertEquals(1, importSurveyData.getSuccessRows());
        assertEquals(0, importSurveyData.getErrorRows());

    }
    @Test
    @SneakyThrows
    public void testImportSurveyDataExcel_whenInValidEmployeeIdAndValidEmail_thenSuccess(){
        when(surveyDataTransformer.toEntity(Mockito.isA(SurveyDataModel.class))).thenReturn(surveyDataTwo);
        when(employeeRepository.findByEmailOrLdapOrHccId(surveyDataTwo.getEmail(),surveyDataTwo.getEmployeeId(),null)).thenReturn(List.of(employee));
        when(surveyDataRepository.getSurveyDataByEmployeeIdOrEmail(surveyDataTwo.getEmail(),surveyDataTwo.getEmployeeId())).thenReturn(Collections.singletonList(surveyDataTwo));
        doNothing().when(surveyDataRepository).deleteAll();
        when(surveyDataRepository.saveAll(Collections.singletonList(surveyDataTwo))).thenReturn(Collections.singletonList(surveyDataTwo));
        ImportResponse importSurveyData = surveyDataService.importSurveyDataExcel(listOfSurveyData);
        assertNotNull(importSurveyData);
        assertEquals(1, importSurveyData.getTotalRows());
        assertEquals(1, importSurveyData.getSuccessRows());
        assertEquals(0, importSurveyData.getErrorRows());

    }
    @Test
    @SneakyThrows
    public void testImportSurveyDataExcel_whenInValidEmployeeIdAndEmail_thenFail(){
        when(surveyDataTransformer.toEntity(Mockito.isA(SurveyDataModel.class))).thenReturn(surveyDataTwo);
        when(employeeRepository.findByEmailOrLdapOrHccId(surveyDataTwo.getEmail(),surveyDataTwo.getEmployeeId(),null)).thenReturn(Collections.emptyList());
        when(surveyDataRepository.getSurveyDataByEmployeeIdOrEmail(surveyDataTwo.getEmail(),surveyDataTwo.getEmployeeId())).thenReturn(Collections.singletonList(surveyDataTwo));
        doNothing().when(surveyDataRepository).deleteAll();
        when(surveyDataRepository.saveAll(Collections.singletonList(surveyDataTwo))).thenReturn(Collections.singletonList(surveyDataTwo));
        ImportResponse importSurveyData = surveyDataService.importSurveyDataExcel(listOfSurveyData);
        assertNotNull(importSurveyData);
        assertEquals(1, importSurveyData.getTotalRows());
        assertEquals(0, importSurveyData.getSuccessRows());
        assertEquals(1, importSurveyData.getErrorRows());

    }
}
