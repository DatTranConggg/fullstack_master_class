package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.Branch;
import com.hitachi.coe.fullstack.model.BranchModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.ModelToEntityTransformer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The BranchTransformer is convert entity to DTO;
 *
 * @author lphanhoangle
 */
@Component
public class BranchModelTransformer extends AbstractCopyPropertiesTransformer<BranchModel, Branch>
        implements ModelToEntityTransformer<BranchModel, Branch, Integer> {
    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link Branch}
     * @return {@link List} of {@link BranchModel}
     */
    public List<Branch> applyList(List<BranchModel> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }
}
