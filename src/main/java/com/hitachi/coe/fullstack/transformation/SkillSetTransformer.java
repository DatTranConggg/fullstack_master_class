package com.hitachi.coe.fullstack.transformation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.hitachi.coe.fullstack.entity.SkillSet;
import com.hitachi.coe.fullstack.model.SkillSetModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;

@Component
public class SkillSetTransformer extends AbstractCopyPropertiesTransformer<SkillSet, SkillSetModel>
implements EntityToModelTransformer<SkillSet, SkillSetModel, Integer>{

	/**
	 * Transformer array entities to array DTO.
	 *
	 * @param entity of entity SkillSet
	 * @return list of SkilletModel
	 */

	public List<SkillSetModel> applyList(List<SkillSet> entities) {
		if (ObjectUtils.isEmpty(entities)) {
			return Collections.emptyList();
		}

		return entities.stream().map(this::apply).collect(Collectors.toList());
	}

	/**
	 * Transformer individual entities to DTO.
	 *
	 * @param entity of entity SkillSet
	 * @return SkillsetModel
	 */

	@Override
	public SkillSetModel apply(SkillSet entity) {
		SkillSetModel model = new SkillSetModel();
		model.setId(entity.getId());
		model.setName(entity.getName());
		model.setCode(entity.getCode());
		model.setDescription(entity.getDescription());
		return model;
	}
}
