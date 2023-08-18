package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.*;
import com.hitachi.coe.fullstack.model.BusinessUnitModel;
import com.hitachi.coe.fullstack.model.ProjectFeedbackModel;
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
public class ProjectFeedbackModelTransformer extends AbstractCopyPropertiesTransformer<ProjectFeedbackModel, ProjectFeedback>
        implements ModelToEntityTransformer<ProjectFeedbackModel, ProjectFeedback, Integer> {
    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link BusinessUnit}
     * @return {@link List} of {@link BusinessUnitModel}
     */
    public List<ProjectFeedback> applyList(List<ProjectFeedbackModel> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectFeedback apply(ProjectFeedbackModel projectFeedbackModel) {
        ProjectFeedback projectFeedback = super.apply(projectFeedbackModel);
        if(projectFeedbackModel.getProject() != null) {
            Project project = new Project();
            project.setId(projectFeedbackModel.getProject().getId());
            projectFeedback.setProject(project);
        }
        if(projectFeedbackModel.getEmployee() != null) {
            Employee employee = new Employee();
            employee.setId(projectFeedbackModel.getEmployee().getId());
            projectFeedback.setEmployee(employee);
        }
        if(projectFeedbackModel.getEvaluationLevel() != null) {
            EvaluationLevel evaluationLevel = new EvaluationLevel();
            evaluationLevel.setId(projectFeedbackModel.getEvaluationLevel().getId());
            projectFeedback.setEvaluationLevel(evaluationLevel);
        }
        projectFeedback.setCreatedBy("admin");
        return projectFeedback;
    }
}
