package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.BusinessUnit;
import com.hitachi.coe.fullstack.model.BusinessUnitModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class BusinessUnitTransformer extends AbstractCopyPropertiesTransformer<BusinessUnit, BusinessUnitModel>
        implements EntityToModelTransformer<BusinessUnit, BusinessUnitModel, Integer> {


    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link BusinessUnit}
     * @return {@link List} of {@link BusinessUnitModel}
     */
    public List<BusinessUnitModel> applyList(List<BusinessUnit> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }

    @Override
    public BusinessUnitModel apply(BusinessUnit businessUnit){
        BusinessUnitModel businessUnitModel =new BusinessUnitModel();
        businessUnitModel.setId(businessUnit.getId());
        businessUnitModel.setManager(businessUnit.getManager());
        businessUnitModel.setDescription(businessUnit.getDescription());
        businessUnitModel.setName(businessUnit.getName());
        businessUnitModel.setCode(businessUnit.getCode());

        return businessUnitModel;
    }

}
