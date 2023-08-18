package com.hitachi.coe.fullstack.transformation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hitachi.coe.fullstack.entity.CoeCoreTeam;
import com.hitachi.coe.fullstack.model.CoeCoreTeamModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;

import lombok.RequiredArgsConstructor;

/**
 * This CoeCoreTeamTransformer is transform entity to DTO.
 *
 * @author lphanhoangle
 */
@Component
@RequiredArgsConstructor
public class CoeCoreTeamTransformer extends AbstractCopyPropertiesTransformer<CoeCoreTeam, CoeCoreTeamModel>
		implements EntityToModelTransformer<CoeCoreTeam, CoeCoreTeamModel, Integer> {

	/**
	 * Transformer array entities to array DTO.
	 *
	 * @param entities {@link List} of {@link CoeCoreTeam}
	 * @return {@link List} of {@link CoeCoreTeamModel}
	 */
	public List<CoeCoreTeamModel> applyList(List<CoeCoreTeam> entities) {
		if (null == entities || entities.isEmpty()) {
			return Collections.emptyList();
		}

		return entities.stream().map(this::apply).collect(Collectors.toList());
	}

	@Override
	public CoeCoreTeamModel apply(CoeCoreTeam coeCoreTeam) {
		CoeCoreTeamModel model = new CoeCoreTeamModel();
		model.setId(coeCoreTeam.getId());
    	model.setCreated(coeCoreTeam.getCreated());
    	model.setCreatedBy(coeCoreTeam.getCreatedBy());
    	model.setUpdated(coeCoreTeam.getUpdated());
    	model.setUpdatedBy(coeCoreTeam.getUpdatedBy());
    	model.setCode(coeCoreTeam.getCode());
		model.setName(coeCoreTeam.getName());
		model.setStatus(coeCoreTeam.getStatus());
		model.setSubLeaderId(coeCoreTeam.getSubLeaderId());
		model.setCenterOfExcellenceId(coeCoreTeam.getCenterOfExcellence().getId());
		model.setCenterOfExcellenceName(coeCoreTeam.getCenterOfExcellence().getName());
		return model;
	}
}
