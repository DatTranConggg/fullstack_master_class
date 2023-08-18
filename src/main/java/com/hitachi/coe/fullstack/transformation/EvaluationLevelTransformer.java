package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.EvaluationLevel;
import com.hitachi.coe.fullstack.model.EvaluationLevelModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This EvaluationLevelTransformer is transform Entity to DTO
 *
 * @author lphanhoangle
 */
@Component
public class EvaluationLevelTransformer extends AbstractCopyPropertiesTransformer<EvaluationLevel, EvaluationLevelModel>
        implements EntityToModelTransformer<EvaluationLevel, EvaluationLevelModel, Integer> {
    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link EvaluationLevel}
     * @return {@link List} of {@link EvaluationLevelModel}
     */
    public List<EvaluationLevelModel> applyList(List<EvaluationLevel> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }
}
