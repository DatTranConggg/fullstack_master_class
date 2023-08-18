package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.*;
import com.hitachi.coe.fullstack.model.BusinessUnitModel;
import com.hitachi.coe.fullstack.model.ProjectModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.ModelToEntityTransformer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class BusinessUnitTransformer is convert entity to DTO.
 *
 * @author lphanhoangle
 */
@Component
public class ProjectModelTransformer extends AbstractCopyPropertiesTransformer<ProjectModel, Project>
        implements ModelToEntityTransformer<ProjectModel, Project, Integer> {
    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link BusinessUnit}
     * @return {@link List} of {@link BusinessUnitModel}
     */
    public List<Project> applyList(List<ProjectModel> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }

    @Override
    public Project apply(ProjectModel projectModel) {
        Project project = super.apply(projectModel);
        ProjectStatus status = ProjectStatus.valueOf(projectModel.getStatus());
        project.setStatus(status);
        if(projectModel.getProjectType() != null) {
            ProjectType projectType = new ProjectType();
            projectType.setId(projectModel.getProjectType().getId());
            project.setProjectType(projectType);
        }
        if(projectModel.getBusinessDomain() != null) {
            BusinessDomain businessDomain = new BusinessDomain();
            businessDomain.setId(projectModel.getBusinessDomain().getId());
            project.setBusinessDomain(businessDomain);
        }
        project.setId(projectModel.getId());
        project.setCreatedBy("admin");
        return project;
    }
}
