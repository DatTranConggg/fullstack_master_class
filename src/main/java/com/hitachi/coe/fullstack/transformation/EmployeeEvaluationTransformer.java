package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.EmployeeEvaluation;
import com.hitachi.coe.fullstack.model.EmployeeEvaluationModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This EmployeeEvaluationTransformer is tranform Entity to DTO.
 *
 * @author lphanhoangle
 */
@Component
public class EmployeeEvaluationTransformer extends AbstractCopyPropertiesTransformer<EmployeeEvaluation, EmployeeEvaluationModel>
        implements EntityToModelTransformer<EmployeeEvaluation, EmployeeEvaluationModel, Integer> {
    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link EmployeeEvaluation}
     * @return {@link List} of {@link EmployeeEvaluationModel}
     */
    public List<EmployeeEvaluationModel> applyList(List<EmployeeEvaluation> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }
}
