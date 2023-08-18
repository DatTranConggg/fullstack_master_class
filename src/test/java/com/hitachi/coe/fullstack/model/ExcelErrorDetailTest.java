package com.hitachi.coe.fullstack.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ExcelErrorDetailTest {

    @Test
    public void testExcelErrorDetails(){
        HashMap<String,String> details = new HashMap<>();
        ExcelErrorDetail excelErrorDetail = new ExcelErrorDetail(1,details);
        assertEquals(1,excelErrorDetail.getRowIndex());
        assertEquals(0,excelErrorDetail.getDetails().size());

        ExcelErrorDetail excelErrorDetail1 = ExcelErrorDetail.builder().details(details).rowIndex(1).build();
        assertEquals(1,excelErrorDetail1.getRowIndex());
        assertEquals(0,excelErrorDetail1.getDetails().size());
    }

}