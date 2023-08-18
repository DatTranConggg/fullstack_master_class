package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.ProjectType;
import com.hitachi.coe.fullstack.model.ProjectTypeModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectTypeTransformer extends AbstractCopyPropertiesTransformer<ProjectType, ProjectTypeModel>
        implements EntityToModelTransformer<ProjectType, ProjectTypeModel, Integer> {

    public List<ProjectTypeModel> applyList(List<ProjectType> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply).collect(Collectors.toList());
    }

    @Override
    public ProjectTypeModel apply(ProjectType projectType) {
        ProjectTypeModel projectTypeModel = new ProjectTypeModel();
        projectTypeModel.setId(projectType.getId());
        projectTypeModel.setCode(projectType.getCode());
        projectTypeModel.setName(projectType.getName());
        projectTypeModel.setCreated(projectType.getCreated());
        projectTypeModel.setCreatedBy(projectType.getCreatedBy());

        return projectTypeModel;
    }
}
