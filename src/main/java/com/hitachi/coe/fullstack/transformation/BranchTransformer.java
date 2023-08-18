package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.Branch;
import com.hitachi.coe.fullstack.model.BranchModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The BranchTransformer is convert entity to DTO;
 *
 * @author lphanhoangle
 */
@Component
public class BranchTransformer extends AbstractCopyPropertiesTransformer<Branch, BranchModel>
        implements EntityToModelTransformer<Branch, BranchModel, Integer> {

    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link Branch}
     * @return {@link List} of {@link BranchModel}
     */
	public List<BranchModel> applyList(List<Branch> entities) {
		if (ObjectUtils.isEmpty(entities)) {
			return Collections.emptyList();
		}

		return entities.stream().map(this::apply).collect(Collectors.toList());
	}

	@Override
	public BranchModel apply(Branch entity) {
		BranchModel model = new BranchModel();
		String nameLocation = entity.getName() + " - " + entity.getLocation().getName();
		model.setId(entity.getId());
		model.setName(entity.getName());
		model.setCode(entity.getCode());
		model.setLabel(nameLocation);
		return model;
	}
}
