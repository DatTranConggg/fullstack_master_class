package com.hitachi.coe.fullstack.service.impl;

import com.hitachi.coe.fullstack.model.ProjectTypeModel;
import com.hitachi.coe.fullstack.repository.ProjectTypeRepository;
import com.hitachi.coe.fullstack.service.ProjectTypeService;
import com.hitachi.coe.fullstack.transformation.ProjectTypeTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectTypeServiceImpl implements ProjectTypeService {
    @Autowired
    private ProjectTypeRepository projectTypeRepository;

    @Autowired
    private ProjectTypeTransformer projectTypeTransformer;

    @Override
    public List<ProjectTypeModel> getAllProjectType() {
        return projectTypeTransformer.applyList(projectTypeRepository.findAll());
    }
}
