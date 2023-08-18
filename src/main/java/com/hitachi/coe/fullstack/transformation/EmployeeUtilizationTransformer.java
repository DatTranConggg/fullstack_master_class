package com.hitachi.coe.fullstack.transformation;


import com.hitachi.coe.fullstack.constant.CommonConstant;
import com.hitachi.coe.fullstack.entity.CoeUtilization;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeUtilization;
import com.hitachi.coe.fullstack.model.EmployeeUtilizationModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * The Class EmployeeUtilization is convert entity to DTO.
 * 
 * @author PhanNguyen
 *
 */
@Component
public class EmployeeUtilizationTransformer
		extends AbstractCopyPropertiesTransformer<EmployeeUtilization, EmployeeUtilizationModel>
		implements EntityToModelTransformer<EmployeeUtilization, EmployeeUtilizationModel, Integer> {
	/**
	 * Transformer DTO to entity.
	 *
	 * @param model    EmployeeUtilizationModel needs to be transferred to an entity.
	 * @param employee Employee entity from Employee table.
	 * @param coeUtilization Id from Coe Utilization table
	 * @return EmployeeUtilization entity from EmployeeUtilizationModel dto.
	 * @author tquangpham
	 */
	public EmployeeUtilization toEntity(EmployeeUtilizationModel model, Employee employee, CoeUtilization coeUtilization) {
		EmployeeUtilization employeeUtilization = new EmployeeUtilization();
		employeeUtilization.setCode(UUID.randomUUID());
		employeeUtilization.setEmployee(employee);
		employeeUtilization.setCoeUtilization(coeUtilization);
		employeeUtilization.setBillableHours(model.getBillableHours());
		employeeUtilization.setProjectCode(model.getOracleStaffedProject());
		employeeUtilization.setLoggedHours(model.getLoggedHours());
		employeeUtilization.setBillable(model.getBillable());
		employeeUtilization.setPtoOracle(model.getPtoOracle());
		employeeUtilization.setAvailableHours(model.getAvailableHours());
		employeeUtilization.setCreatedBy(CommonConstant.CREATED_BY_ADMINISTRATOR);
		employeeUtilization.setCreated(new Date());
		return employeeUtilization;
	}
}
