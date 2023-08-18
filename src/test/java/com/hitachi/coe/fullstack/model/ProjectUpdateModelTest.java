package com.hitachi.coe.fullstack.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectUpdateModelTest {

    @Test
    public void testModelProjectUpdate(){

        ProjectUpdateModel projectUpdate = new ProjectUpdateModel();
        projectUpdate.setProjectId(5);
        projectUpdate.setName("FSCMS");
        projectUpdate.setCode("FSCMS");
        projectUpdate.setCustomerName("John Weak");
        projectUpdate.setDescription("description");
        projectUpdate.setStartDate("2023-05-23");
        projectUpdate.setEndDate("2023-06-23");
        projectUpdate.setProjectManager("Pm a");
        projectUpdate.setStatus(3);
        projectUpdate.setBusinessDomainId(2);
        projectUpdate.setProjectTypeId(1);
        projectUpdate.setProjectsTech(Arrays.asList(1,2,3));

        assertNotNull(projectUpdate);
        assertEquals(5, projectUpdate.getProjectId());
        assertEquals("FSCMS", projectUpdate.getCode());
        assertEquals("FSCMS", projectUpdate.getName());
        assertEquals("John Weak", projectUpdate.getCustomerName());
        assertEquals("description", projectUpdate.getDescription());
        assertEquals("2023-05-23", projectUpdate.getStartDate());
        assertEquals("2023-06-23", projectUpdate.getEndDate());
        assertEquals("Pm a", projectUpdate.getProjectManager());
        assertEquals(3, projectUpdate.getStatus());
        assertEquals(2, projectUpdate.getBusinessDomainId());
        assertEquals(1, projectUpdate.getProjectTypeId());
        assertEquals(Arrays.asList(1,2,3), projectUpdate.getProjectsTech());
    }
}
