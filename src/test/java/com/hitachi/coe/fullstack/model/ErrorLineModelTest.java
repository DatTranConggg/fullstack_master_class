package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.model.common.ErrorLineModel;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ErrorLineModelTest {

    @Test
    public void testModelErrorLine_whenNoArgumentsConstructor_thenSuccess(){
        ErrorLineModel errorLineModel = new ErrorLineModel();
        errorLineModel.setField("Email");
        errorLineModel.setMessage("Invalid email");

        assertNotNull(errorLineModel);
        assertEquals("Email", errorLineModel.getField());
        assertEquals("Invalid email", errorLineModel.getMessage());
    }

    @Test
    public void testModelErrorLine_whenAllArgumentsConstructor_thenSuccess(){

        ErrorLineModel errorLineModel = new ErrorLineModel("Ldap", "Wrong Ldap");

        assertNotNull(errorLineModel);
        assertEquals("Ldap", errorLineModel.getField());
        assertEquals("Wrong Ldap", errorLineModel.getMessage());
    }

    @Test
    public void testModelErrorLine_whenImportEmployeeErrorDetails_thenSuccess(){
        List<String> emptyList = Collections.emptyList();

        List<ErrorLineModel> errorLineModelList = ErrorLineModel.importEmployeeErrorDetails(null, emptyList , emptyList, emptyList, "HVN DN", "DS", "SC1");

        assertNotNull(errorLineModelList);
        assertEquals(4, errorLineModelList.size());
    }
}
