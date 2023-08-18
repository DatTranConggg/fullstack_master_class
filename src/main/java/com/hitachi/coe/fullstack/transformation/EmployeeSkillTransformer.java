package com.hitachi.coe.fullstack.transformation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeSkill;
import com.hitachi.coe.fullstack.model.EmployeeModel;
import com.hitachi.coe.fullstack.model.EmployeeSkillModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;

@Component
public class EmployeeSkillTransformer extends AbstractCopyPropertiesTransformer<EmployeeSkill, EmployeeSkillModel>
implements EntityToModelTransformer<EmployeeSkill, EmployeeSkillModel, Integer> {

	@Autowired
	SkillSetTransformer skillSetTransformer;
	/**
	 * Transformer array entities to array DTO.
	 *
	 * @param entities {@link List} of {@link Employee}
	 * @return {@link List} of {@link EmployeeModel}
	 */
	public List<EmployeeSkillModel> applyList(List<EmployeeSkill> entities) {
		if (null == entities || entities.isEmpty()) {
			return Collections.emptyList();
		}

		return entities.stream().map(this::apply).collect(Collectors.toList());
	}
	
	@Override
	public EmployeeSkillModel apply(EmployeeSkill entity) {
		EmployeeSkillModel model = new EmployeeSkillModel();
		model.setId(entity.getId());
		model.setSkillLevel(entity.getSkillLevel());
		model.setSkillSetDate(entity.getSkillSetDate());
		if (!ObjectUtils.isEmpty(entity.getSkillSet())) {
			model.setSkillSet(skillSetTransformer.apply(entity.getSkillSet()));
		}
		return model;
	}
}
