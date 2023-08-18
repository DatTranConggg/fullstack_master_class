package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeEvaluation;
import com.hitachi.coe.fullstack.model.EmployeeEvaluationModel;
import com.hitachi.coe.fullstack.model.EmployeeModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.ModelToEntityTransformer;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class EmployeeTransformer is convert entity to DTO.
 *
 * @author lphanhoangle
 */
@Service
public class EmployeeEvaluationModelTransformer extends AbstractCopyPropertiesTransformer<EmployeeEvaluationModel, EmployeeEvaluation>
        implements ModelToEntityTransformer<EmployeeEvaluationModel, EmployeeEvaluation, Integer> {
    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link Employee}
     * @return {@link List} of {@link EmployeeModel}
     */
    public List<EmployeeEvaluation> applyList(List<EmployeeEvaluationModel> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }
}
