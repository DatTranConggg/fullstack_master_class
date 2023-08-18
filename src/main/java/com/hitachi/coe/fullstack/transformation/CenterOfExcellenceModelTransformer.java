package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.CenterOfExcellence;
import com.hitachi.coe.fullstack.model.CenterOfExcellenceModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.ModelToEntityTransformer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This CenterOfExcellenceTransformer is transform entity to DTO.
 *
 * @author lphanhoangle
 */
@Component
public class CenterOfExcellenceModelTransformer extends AbstractCopyPropertiesTransformer<CenterOfExcellenceModel, CenterOfExcellence>
        implements ModelToEntityTransformer<CenterOfExcellenceModel, CenterOfExcellence, Integer> {

    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link CenterOfExcellence}
     * @return {@link List} of {@link CenterOfExcellenceModel}
     */
    public List<CenterOfExcellence> applyList(List<CenterOfExcellenceModel> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }
}
