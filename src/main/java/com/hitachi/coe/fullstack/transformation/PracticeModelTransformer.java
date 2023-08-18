package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.BusinessUnit;
import com.hitachi.coe.fullstack.entity.Practice;
import com.hitachi.coe.fullstack.model.BusinessUnitModel;
import com.hitachi.coe.fullstack.model.PracticeModel;
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
public class PracticeModelTransformer extends AbstractCopyPropertiesTransformer<PracticeModel, Practice>
        implements ModelToEntityTransformer<PracticeModel, Practice, Integer> {
    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link BusinessUnit}
     * @return {@link List} of {@link BusinessUnitModel}
     */
    public List<Practice> applyList(List<PracticeModel> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }
}
