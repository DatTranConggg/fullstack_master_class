package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.ProjectTech;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.repository.ProjectTechRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ProjectTechServiceTest {

    @Autowired
    ProjectTechService projectTechService;

    @MockBean
    ProjectTechRepository projectTechRepository;

    @Test
    public void testDeleteProjectTechByProject_whenValidData_thenSuccess(){

        ProjectTech projectTech = new ProjectTech();
        projectTech.setId(1);
        List<ProjectTech> projectTechList = List.of(projectTech);

        when(projectTechRepository.findByProjectId(any(Integer.class))).thenReturn(projectTechList);
        doNothing().when(projectTechRepository).deleteByProject(any(Integer.class));

        projectTechService.deleteProjectTechByProject(1);

        verify(projectTechRepository, times(1)).findByProjectId(1);
        verify(projectTechRepository, times(1)).deleteByProject(1);
    }

    @Test
    public void testDeleteProjectTechByProject_whenProjectIsNull_thenSuccess(){

        Throwable throwable = assertThrows(CoEException.class,
                () -> projectTechService.deleteProjectTechByProject(null));

        //Verify
        assertEquals(CoEException.class, throwable.getClass());
        assertEquals(ErrorConstant.MESSAGE_PROJECT_NULL, throwable.getMessage());
    }

    @Test
    public void testDeleteProjectTechByProject_whenProjectTechNotExist_thenSuccess(){

        when(projectTechRepository.findByProjectId(any(Integer.class))).thenReturn(Collections.emptyList());

        projectTechService.deleteProjectTechByProject(1);

        verify(projectTechRepository, times(1)).findByProjectId(1);
        verify(projectTechRepository, times(0)).deleteByProject(1);
    }

}
