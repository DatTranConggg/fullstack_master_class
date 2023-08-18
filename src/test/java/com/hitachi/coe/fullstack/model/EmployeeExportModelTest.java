package com.hitachi.coe.fullstack.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmployeeExportModelTest {

    @Test
    public void testingModel() {
        EmployeeExportModel employeeExportModel = EmployeeExportModel.builder()
                .email("lam@gmail.com")
                .hccId("8797966")
                .ldap("12345")
                .name("lam")
                .legalEntityHireDate(Date.from(Instant.now()))
                .branch("VietNam")
                .practice("IoT")
                .level("Senior")
                .build();
        EmployeeExportModel noArgs = new EmployeeExportModel();
        noArgs.setBranch("VietNam");

        assertEquals("VietNam", noArgs.getBranch());
        assertNotNull(noArgs);
        assertEquals("lam@gmail.com", employeeExportModel.getEmail());
        assertEquals("8797966", employeeExportModel.getHccId());
        assertEquals("12345", employeeExportModel.getLdap());
        assertEquals("lam", employeeExportModel.getName());
        assertNotNull(employeeExportModel.getLegalEntityHireDate());
        assertEquals("VietNam", employeeExportModel.getBranch());
        assertEquals("IoT", employeeExportModel.getPractice());
        assertEquals("Senior", employeeExportModel.getLevel());
    }
}