package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.Practice;
import com.hitachi.coe.fullstack.model.PracticeModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class PracticeTransformer is convert entity to DTO.
 *
 * @author dang.chien
 */
@Service
public class PracticeTransformer extends AbstractCopyPropertiesTransformer<Practice, PracticeModel>
		implements EntityToModelTransformer<Practice, PracticeModel, Integer> {

	
	@Autowired
	BusinessUnitTransformer businessUnitTransformer;
	/**
	 * Transformer array entities to array DTO.
	 *
	 * @param entities {@link List} of {@link Practice}
	 * @return {@link List} of {@link PracticeModel}
	 */
	public List<PracticeModel> applyList(List<Practice> entities) {
		if (null == entities || entities.isEmpty()) {
			return Collections.emptyList();
		}

		return entities.stream().map(this::apply).collect(Collectors.toList());
	}

	@Override
	public PracticeModel apply(Practice practice) {
		PracticeModel model = new PracticeModel();
		model.setId(practice.getId());
		model.setCode(practice.getCode());
		model.setDescription(practice.getDescription());
		model.setName(practice.getName());
		model.setBusinessUnitId(practice.getBusinessUnit().getId());
		model.setBusinessUnitName(practice.getBusinessUnit().getName());
		model.setManager(practice.getManager());
		model.setCreatedBy(practice.getCreatedBy());
		return model;
	}
}
