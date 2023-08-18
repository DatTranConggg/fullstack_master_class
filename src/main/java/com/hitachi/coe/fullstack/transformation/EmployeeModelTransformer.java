package com.hitachi.coe.fullstack.transformation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hitachi.coe.fullstack.entity.Branch;
import com.hitachi.coe.fullstack.entity.BusinessUnit;
import com.hitachi.coe.fullstack.entity.CoeCoreTeam;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.model.EmployeeModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.ModelToEntityTransformer;

/**
 * The class EmployeeTransformer is convert entity to DTO.
 *
 * @author lphanhoangle
 */
@Component
public class EmployeeModelTransformer extends AbstractCopyPropertiesTransformer<EmployeeModel, Employee>
        implements ModelToEntityTransformer<EmployeeModel, Employee, Integer> {
	
	
    /**
     * Transformer array DTO to array entities.
     *
     * @param entities {@link List} of {@link Employee}
     * @return {@link List} of {@link EmployeeModel}
     */
    public List<Employee> applyList(List<EmployeeModel> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }
    
    @Override
    public Employee apply(EmployeeModel employeeModel) {
    	Employee employee = super.apply(employeeModel);
    	if(employeeModel.getBusinessUnit() != null) {
    		BusinessUnit businessUnit = new BusinessUnit();
    		businessUnit.setId(employeeModel.getBusinessUnit().getId());
    		employee.setBusinessUnit(businessUnit);
    	}
    	if(employeeModel.getBranch() != null) {
    		Branch branch = new Branch();
    		branch.setId(employeeModel.getBranch().getId());
    		employee.setBranch(branch);
    	}
    	if(employeeModel.getCoeCoreTeam() != null) {
    		CoeCoreTeam coe = new CoeCoreTeam();
    		coe.setId(employeeModel.getCoeCoreTeam().getId());
    		employee.setCoeCoreTeam(coe);
    	}
		employee.setId(employeeModel.getId());
    	employee.setCreatedBy("admin");
    	return employee;
    }
    
}
