package com.hitachi.coe.fullstack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hitachi.coe.fullstack.model.common.ErrorLineModel;
import com.hitachi.coe.fullstack.model.common.ErrorModel;

public class ErrorModelTest {

    ErrorLineModel errorLineModel;

    @BeforeEach
    void setUp(){
        errorLineModel= new ErrorLineModel();
        errorLineModel.setField("Email");
        errorLineModel.setMessage("Invalid email");
    }

    @Test
    public void testModelError_whenNoArgumentsConstructor_thenSuccess(){

        ErrorModel errorModel = new ErrorModel();
        errorModel.setLine(1);
        errorModel.setErrorLineList(List.of(errorLineModel));

        assertNotNull(errorModel);
        assertEquals(1, errorModel.getLine());
        assertEquals(List.of(errorLineModel), errorModel.getErrorLineList());
    }

    @Test
    public void testModelError_whenAllArgumentsConstructor_thenSuccess(){

        ErrorModel errorModel = new ErrorModel(1, List.of(errorLineModel));

        assertNotNull(errorModel);
        assertEquals(1, errorModel.getLine());
        assertEquals(List.of(errorLineModel), errorModel.getErrorLineList());
    }

    @Test
    public void testModelError_whenImportErrorDetails_thenSuccess(){
        ExcelErrorDetail excelErrorDetail = new ExcelErrorDetail();
        HashMap<String, String> value = new HashMap<>();
        value.put("Email", "Invalid email");
        excelErrorDetail.setRowIndex(1);
        excelErrorDetail.setDetails(value);
        List<ExcelErrorDetail> errorList = List.of(excelErrorDetail);

        List<ErrorModel> errorModelList = ErrorModel.importErrorDetails(errorList);

        assertNotNull(errorModelList);
        assertEquals(1, errorModelList.size());
    }

    @Test
    public void testModelError_whenErrorSurveyDataDetails_thenSuccess(){

        ErrorModel errorModel = ErrorModel.errorSurveyDataDetails(1);

        assertNotNull(errorModel);
        assertEquals(1, errorModel.getLine());
        assertEquals(2, errorModel.getErrorLineList().size());
    }

    @Test
    public void testModelError_whenSortModelsByLine_thenSuccess(){
        List<ErrorModel> models = Arrays.asList(new ErrorModel(3, List.of(errorLineModel)), new ErrorModel(1, List.of(errorLineModel)), new ErrorModel(2, List.of(errorLineModel)));
        ErrorModel.sortModelsByLine(models);

        assertNotNull(models);
        assertEquals(1, models.get(0).getLine());
        assertEquals(2, models.get(1).getLine());
        assertEquals(3, models.get(2).getLine());

    }
}
