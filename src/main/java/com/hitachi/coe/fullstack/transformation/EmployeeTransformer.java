package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeLevel;
import com.hitachi.coe.fullstack.model.EmployeeExportModel;
import com.hitachi.coe.fullstack.model.EmployeeModel;
import com.hitachi.coe.fullstack.model.EmployeeSkillModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class EmployeeTransformer is convert entity to DTO.
 *
 * @author lphanhoangle
 */
@Component
public class EmployeeTransformer extends AbstractCopyPropertiesTransformer<Employee, EmployeeModel>
		implements EntityToModelTransformer<Employee, EmployeeModel, Integer> {

	@Autowired
	PracticeTransformer practiceTransformer;

	@Autowired
	BranchTransformer branchTransformer;

	@Autowired
	CoeCoreTeamTransformer coeCoreTeamTransformer;
	
	@Autowired
	BusinessUnitTransformer businessUnitTransformer;
	
	@Autowired
	EmployeeSkillTransformer employeeSkillTransformer;

	/**
	 * Transformer array entities to array DTO.
	 *
	 * @param entities {@link List} of {@link Employee}
	 * @return {@link List} of {@link EmployeeModel}
	 */
	public List<EmployeeModel> applyList(List<Employee> entities) {
		if (null == entities || entities.isEmpty()) {
			return Collections.emptyList();
		}

		return entities.stream().map(this::apply).collect(Collectors.toList());
	}

	public EmployeeExportModel convertEmployeeToExportModel(Employee employee) {
		return EmployeeExportModel.builder().branch(employee.getBranch().getName())
				.practice(employee.getBusinessUnit().getName())
				.level(employee.getEmployeeLevels().stream().findFirst().orElse(new EmployeeLevel()).getLevel()
						.getName())
				.hccId(employee.getHccId()).email(employee.getEmail()).name(employee.getName()).ldap(employee.getLdap())
				.legalEntityHireDate(employee.getLegalEntityHireDate()).build();
	}

	@Override
	public EmployeeModel apply(Employee employee) {
		EmployeeModel employeeModel = new EmployeeModel();
		employeeModel.setId(employee.getId());
		employeeModel.setHccId(employee.getHccId());
		employeeModel.setEmail(employee.getEmail());
		employeeModel.setLdap(employee.getLdap());
		employeeModel.setName(employee.getName());
		employeeModel.setLegalEntityHireDate(employee.getLegalEntityHireDate());
		employeeModel.setCreatedBy(employee.getCreatedBy());

		employeeModel.setBranch(branchTransformer.apply(employee.getBranch()));
		employeeModel.setBusinessUnit(businessUnitTransformer.apply(employee.getBusinessUnit()));
		employeeModel.setCoeCoreTeam(coeCoreTeamTransformer.apply(employee.getCoeCoreTeam()));
		
		if (!ObjectUtils.isEmpty(employee.getEmployeeSkills())) {
			List<EmployeeSkillModel> employeeSkillModel = new ArrayList<>();
			employeeSkillModel = employee.getEmployeeSkills().stream()
					.map(employeeSkill -> employeeSkillTransformer.apply(employeeSkill)).collect(Collectors.toList());
			employeeModel.setEmployeeSkills(employeeSkillModel);
		}

		return employeeModel;
	}
}
