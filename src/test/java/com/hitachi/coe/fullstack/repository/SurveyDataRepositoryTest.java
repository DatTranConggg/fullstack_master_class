package com.hitachi.coe.fullstack.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hitachi.coe.fullstack.entity.SurveyData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("data")
public class SurveyDataRepositoryTest {
    @Autowired
    SurveyDataRepository surveyDataRepository;

    SurveyData surveyOne;

    Calendar calendar;
    @BeforeEach
    public void setUp(){
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2023, Calendar.JUNE, 14);
        surveyOne = new SurveyData();
    	surveyOne.setStartTime(calendar.getTime());
    	surveyOne.setCompletionTime(new Date());
    	surveyOne.setEmail("a1@hitachivantara.com");
    	surveyOne.setName("");
    	surveyOne.setEmployeeId("12");
    	surveyOne.setFullName("Nguyen A");
    	surveyDataRepository.save(surveyOne);
    }
    @Test
    public void testGetSurveyDataByEmployeeIdOrEmail_whenValidEmployeeIdAndEmail_thenReturnListSurveyData(){
        List<SurveyData> surveyDataList = surveyDataRepository.getSurveyDataByEmployeeIdOrEmail(surveyOne.getEmployeeId(), surveyOne.getEmail());
        assertNotNull(surveyDataList);
        assertEquals(surveyOne.getEmployeeId(), surveyDataList.get(0).getEmployeeId());
        assertEquals(surveyOne.getEmail(), surveyDataList.get(0).getEmail());
    }
    @Test
    public void testGetSurveyDataByEmployeeIdOrEmail_whenValidEmployeeIdAndInvalidEmail_thenReturnListSurveyData(){
        List<SurveyData> surveyDataList = surveyDataRepository.getSurveyDataByEmployeeIdOrEmail(surveyOne.getEmployeeId(),"Invalid@hitachivantara.com");
        assertNotNull(surveyDataList);
        assertEquals(surveyOne.getEmployeeId(), surveyDataList.get(0).getEmployeeId());
        assertEquals(surveyOne.getEmail(), surveyDataList.get(0).getEmail());
    }
    @Test
    public void testGetSurveyDataByEmployeeIdOrEmail_whenInValidEmployeeIdAndEmail_thenEmptyList(){
        List<SurveyData> surveyDataList = surveyDataRepository.getSurveyDataByEmployeeIdOrEmail(null,null);
        assertNotNull(surveyDataList);
        assertEquals(0,surveyDataList.size());
    }
}
