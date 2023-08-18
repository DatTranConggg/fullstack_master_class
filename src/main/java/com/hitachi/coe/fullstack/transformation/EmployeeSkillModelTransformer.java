package com.hitachi.coe.fullstack.transformation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeSkill;
import com.hitachi.coe.fullstack.entity.SkillSet;
import com.hitachi.coe.fullstack.model.EmployeeModel;
import com.hitachi.coe.fullstack.model.EmployeeSkillModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.ModelToEntityTransformer;

/**
 * The class EmployeeTransformer is convert entity to DTO.
 *
 * @author lphanhoangle
 */
@Service
public class EmployeeSkillModelTransformer extends AbstractCopyPropertiesTransformer<EmployeeSkillModel, EmployeeSkill>
        implements ModelToEntityTransformer<EmployeeSkillModel, EmployeeSkill, Integer> {
	
    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link Employee}
     * @return {@link List} of {@link EmployeeModel}
     */
    public List<EmployeeSkill> applyList(List<EmployeeSkillModel> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }
    
	@Override
	public EmployeeSkill apply(EmployeeSkillModel model) {
		EmployeeSkill entity = new EmployeeSkill();
		entity.setId(model.getId());
		entity.setSkillLevel(model.getSkillLevel());
		entity.setSkillSetDate(model.getSkillSetDate());
		if(!ObjectUtils.isEmpty(entity.getSkillSet())) {
			SkillSet skillSet = new SkillSet();
			skillSet.setId(model.getSkillSet().getId());
			skillSet.setCode(model.getSkillSet().getCode());
			skillSet.setName(model.getSkillSet().getName());
			skillSet.setDescription(model.getSkillSet().getDescription());
			entity.setSkillSet(skillSet);
		}
		return entity;
	}
}
