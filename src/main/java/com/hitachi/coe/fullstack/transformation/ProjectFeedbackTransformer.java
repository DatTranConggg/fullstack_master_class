package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.ProjectFeedback;
import com.hitachi.coe.fullstack.model.ProjectFeedbackModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectFeedbackTransformer extends AbstractCopyPropertiesTransformer<ProjectFeedback, ProjectFeedbackModel>
        implements EntityToModelTransformer<ProjectFeedback, ProjectFeedbackModel, Integer> {

    @Autowired
    ProjectTransformer projectTransformer;
    @Autowired
    EmployeeTransformer employeeTransformer;
    @Autowired
    EvaluationLevelTransformer evaluationLevelTransformer;

    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link ProjectFeedback}
     * @return {@link List} of {@link ProjectFeedbackModel}
     */
    public List<ProjectFeedbackModel> applyList(List<ProjectFeedback> entities) {
        if (ObjectUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply).collect(Collectors.toList());
    }

    @Override
    public ProjectFeedbackModel apply(ProjectFeedback entity) {
        ProjectFeedbackModel model = new ProjectFeedbackModel();
        model.setId(entity.getId());
        model.setFeedback(entity.getFeedback());
        model.setFeedbackDate(entity.getFeedbackDate());
        model.setProjectManager(entity.getProjectManager());
        model.setCreatedBy(entity.getCreatedBy());

        model.setProject(projectTransformer.apply(entity.getProject()));
        model.setEmployee(employeeTransformer.apply(entity.getEmployee()));
        model.setEvaluationLevel(evaluationLevelTransformer.apply(entity.getEvaluationLevel()));
        return model;
    }
}
