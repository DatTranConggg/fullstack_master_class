package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.BusinessUnit;
import com.hitachi.coe.fullstack.model.BusinessUnitModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.ModelToEntityTransformer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusinessUnitModelTransformer extends AbstractCopyPropertiesTransformer<BusinessUnitModel, BusinessUnit>
        implements ModelToEntityTransformer<BusinessUnitModel, BusinessUnit, Integer> {
    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link BusinessUnit}
     * @return {@link List} of {@link BusinessUnitModel}
     */

    public List<BusinessUnit> applyList(List<BusinessUnitModel> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }


}
