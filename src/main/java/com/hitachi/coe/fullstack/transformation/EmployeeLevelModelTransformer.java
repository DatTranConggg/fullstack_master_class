package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.EmployeeLevel;
import com.hitachi.coe.fullstack.model.EmployeeLevelModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.ModelToEntityTransformer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This EmployeeLevelTransformer is transform Entity to DTO.
 *
 * @author lphanhoangle
 */
@Component
public class EmployeeLevelModelTransformer extends AbstractCopyPropertiesTransformer<EmployeeLevelModel, EmployeeLevel>
        implements ModelToEntityTransformer<EmployeeLevelModel, EmployeeLevel, Integer> {
    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link EmployeeLevel}
     * @return {@link List} of {@link EmployeeLevelModel}
     */
    public List<EmployeeLevel> applyList(List<EmployeeLevelModel> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }
}
