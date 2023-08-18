package com.hitachi.coe.fullstack.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImportResponseTest {

    @Test
    public void testModelImportResponse(){

        ImportResponse response = new ImportResponse();
        response.setTotalRows(100);
        response.setSuccessRows(80);
        response.setErrorRows(20);
        response.setErrorList(Collections.emptyList());
        assertNotNull(response);
        assertEquals(100, response.getTotalRows());
        assertEquals(80, response.getSuccessRows());
        assertEquals(20, response.getErrorRows());
        assertEquals(Collections.emptyList(), response.getErrorList());
    }
}
