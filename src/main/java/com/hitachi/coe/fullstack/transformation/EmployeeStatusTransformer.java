package com.hitachi.coe.fullstack.transformation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeStatus;
import com.hitachi.coe.fullstack.model.EmployeeStatusModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;

import org.springframework.stereotype.Component;

/**
 * The Class EmployeeStatusTransformer is convert entity to DTO.
 * 
 * @author loita
 *
 */
@Component
public class EmployeeStatusTransformer extends AbstractCopyPropertiesTransformer<EmployeeStatus, EmployeeStatusModel>
		implements EntityToModelTransformer<EmployeeStatus, EmployeeStatusModel, Integer> {
	/**
	 * Transformer array entities to array DTO.
	 * 
	 * @param entities {@link List} of {@link EmployeeStatus}
	 * @return {@link List} of {@link EmployeeStatusModel}
	 */
	public List<EmployeeStatusModel> applyList(List<EmployeeStatus> entities) {
		
		if (null == entities || entities.isEmpty()) {
			return Collections.emptyList();
		}

		return entities.stream().map(this::apply)
				.collect(Collectors.toList());
	}
	
	@Override
	public EmployeeStatusModel apply(EmployeeStatus entity) {
		Employee employee = entity.getEmployee();
		EmployeeStatusModel model = new EmployeeStatusModel();
		model.setId(entity.getId());
		model.setStatus(entity.getStatus());
		model.setStatusDate(entity.getStatusDate());
		model.setEmployeeId(employee.getId());
		model.setEmployeeName(employee.getName());
		return model;
	}
	
}
