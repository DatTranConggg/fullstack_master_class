package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.enums.Status;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelResponseModelTest {

    @Test
    public void testExcelResponseModel(){
        HashMap<Integer,Object> data = new HashMap<>();
        Integer totalRows = 1;
        List<ExcelErrorDetail> errorDetails = new ArrayList<>();
        Status status = Status.SUCCESS;

        ExcelResponseModel excelResponseModel = new ExcelResponseModel();
        ExcelResponseModel testBuilder = ExcelResponseModel.builder()
                .totalRows(totalRows)
                .errorDetails(errorDetails)
                .build();
        testBuilder.setData(data);
        testBuilder.setStatus(status);

        assertEquals(0,testBuilder.getData().size());
        assertEquals(1,testBuilder.getTotalRows());
        assertEquals(0,testBuilder.getErrorDetails().size());
        assertEquals(Status.SUCCESS,testBuilder.getStatus());
    }
}