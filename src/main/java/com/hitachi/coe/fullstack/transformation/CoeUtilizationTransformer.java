package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.CoeCoreTeam;
import com.hitachi.coe.fullstack.entity.CoeUtilization;
import com.hitachi.coe.fullstack.model.CoeCoreTeamModel;
import com.hitachi.coe.fullstack.model.CoeUtilizationModel;
import com.hitachi.coe.fullstack.model.EmployeeUtilizationModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CoeUtilizationTransformer extends AbstractCopyPropertiesTransformer<CoeUtilization, CoeUtilizationModel>
		implements EntityToModelTransformer<CoeUtilization, CoeUtilizationModel, Integer> {
	
	private final EmployeeUtilizationTransformer employeeUtiliaztionTransformer;
	/**
	 * Transformer array entities to array DTO.
	 *
	 * @param entities {@link List} of {@link CoeCoreTeam}
	 * @return {@link List} of {@link CoeCoreTeamModel}
	 */
	public List<CoeUtilizationModel> applyList(List<CoeUtilization> entities) {
		if (null == entities || entities.isEmpty()) {
			return Collections.emptyList();
		}

		return entities.stream().map(this::apply).collect(Collectors.toList());
	}
	
	@Override
	public CoeUtilizationModel apply(CoeUtilization orig) {
		CoeUtilizationModel coeUtilizationModel = new CoeUtilizationModel();
		List<EmployeeUtilizationModel> employeeUtilizations = orig.getEmployeeUtilizations().stream().map(employeeUtiliaztionTransformer::apply).collect(Collectors.toList());
		coeUtilizationModel.setId(orig.getId());
		coeUtilizationModel.setCreated(orig.getCreated());
		coeUtilizationModel.setCreatedBy(orig.getCreatedBy());
		coeUtilizationModel.setUpdated(orig.getUpdated());
		coeUtilizationModel.setUpdatedBy(orig.getUpdatedBy());
		coeUtilizationModel.setDuration(orig.getDuration());
		coeUtilizationModel.setStartDate(orig.getStartDate());
		coeUtilizationModel.setEndDate(orig.getEndDate());
		coeUtilizationModel.setTotalUtilization(orig.getTotalUtilization());
		coeUtilizationModel.setEmployeeUtilizations(employeeUtilizations);
		return coeUtilizationModel;
	}
}
