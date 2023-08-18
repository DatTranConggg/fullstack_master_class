package com.hitachi.coe.fullstack.transformation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.hitachi.coe.fullstack.model.ProjectSearchModel;
import org.springframework.stereotype.Component;

import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectSearchModelTransformer extends AbstractCopyPropertiesTransformer<Project, ProjectSearchModel>
        implements EntityToModelTransformer<Project, ProjectSearchModel, Integer> {

    public List<ProjectSearchModel> applyList(List<Project> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }


    @Override
    public ProjectSearchModel apply(Project project) {
        ProjectSearchModel model = new ProjectSearchModel();
        model.setId(project.getId());
        model.setCode(project.getCode());
        model.setCustomerName(project.getCustomerName());
        model.setDescription(project.getDescription());
        model.setEndDate(project.getEndDate());
        model.setName(project.getName());
        model.setProjectManager(project.getProjectManager());
        model.setStartDate(project.getStartDate());
        model.setStatus(project.getStatus().getValue());
        model.setBusinessDomainId(project.getBusinessDomain().getId());
        model.setProjectTypeId(project.getProjectType().getId());
        model.setBusinessDomainName(project.getBusinessDomain().getName());
        model.setProjectTypeName(project.getProjectType().getName());
        return model;
    }

}
