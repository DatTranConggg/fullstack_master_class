package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.SurveyData;
import com.hitachi.coe.fullstack.model.SurveyDataModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class SurveyDataTransformerTest {
	@Autowired
    private SurveyDataTransformer surveyDataTransformer;
    
    @Test
    public void testToEntitySurveydata_whenValidData_thenSuccess() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JUNE, 14);
        
        // Create a SurveyDataModel object
        SurveyDataModel surveyModel = new SurveyDataModel();
        
        // Set the properties of the SurveyDataModel object using the setter methods
        surveyModel.setStartTime(calendar.getTime());
        surveyModel.setCompletionTime(calendar.getTime());
        surveyModel.setEmail("");
        surveyModel.setName("");
        surveyModel.setEmployeeId("123");
        surveyModel.setFullName("Nguyen A");

        // Call the toEntity method of the SurveyDataTransformer class to convert the SurveyDataModel object to a SurveyData object
        SurveyData surveyDataExpected = surveyDataTransformer.toEntity(surveyModel);

        // Check whether the properties of the SurveyData object returned by the toEntity method match the expected values
        assertEquals("123", surveyDataExpected.getEmployeeId());
        assertEquals("Nguyen A", surveyDataExpected.getFullName());
        assertEquals(calendar.getTime(), surveyDataExpected.getStartTime());
    }

}
