package com.hitachi.coe.fullstack.service.impl;

import java.util.List;
import java.util.Optional;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeSkill;
import com.hitachi.coe.fullstack.entity.SkillSet;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.EmployeeSkillModel;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import com.hitachi.coe.fullstack.model.SkillSetModel;
import com.hitachi.coe.fullstack.repository.EmployeeSkillRepository;
import com.hitachi.coe.fullstack.repository.SkillSetRepository;
import com.hitachi.coe.fullstack.service.EmployeeSkillService;
import com.hitachi.coe.fullstack.transformation.SkillSetTransformer;
import com.hitachi.coe.fullstack.util.CommonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeSkillServiceImpl implements EmployeeSkillService {

	@Autowired
	EmployeeSkillRepository employeeSkillRepository;

	@Autowired
	SkillSetTransformer skillSetTransformer;
	
	@Autowired
	SkillSetRepository skillSetRepository;
	/**
	 * @param top to select out put limit of the list
	 * @return top skills base on users level and limit of list out put default is 6
	 * @author PhanNguyen
	 */
	@Override
	public List<SkillSetModel> getTopSkillSet(int top) {
		Pageable pageable = PageRequest.of(0, top);
		return skillSetTransformer.applyList(employeeSkillRepository.getTopSkillSet(pageable));
	}
	/**
 	* @param branchId      The ID of the branch to retrieve data for.
 	* @param coeCoreTeamId The ID of the core team to retrieve data for.
 	* @param coeId         The ID of the center of excellence to retrieve data for.
 	* @param topSkilll     The IDs of the top skills to retrieve data for.
 	* @return A list of Object arrays representing the percentage values for the skill pie chart
 	* @throws CoEException if the center of excellence is null but the core team ID
 	*                      is not, or if the list of skill IDs is null or empty.
 	* @author pdieu1                     
 	*/
	@Override
	public List<IPieChartModel> getEmployeeSkillPieChart(Integer branchId, Integer coeCoreTeamId, Integer coeId,
			List<Integer> topSkilll) {
		if (coeId == null && coeCoreTeamId != null) {
			throw new CoEException(ErrorConstant.CODE_CENTER_OF_EXCELLENCE_NULL,
					ErrorConstant.MESSAGE_CENTER_OF_EXCELLENCE_NULL);
		}
		String listId = CommonUtils.convertListIntegertoString(topSkilll);
		List<IPieChartModel> result =employeeSkillRepository.getEmployeeSkillPieChart(branchId, coeCoreTeamId, coeId, listId);
		result.removeIf(model -> model.getData() == 0); 
		return result;
		}
	
	/**
	 * function will add employeeSkill into the database
	 * 
	 * @param employee 	is the employee data which user give to the server
	 * @param employeeSkill  is the employeeSkill which user give to to server
	 * @author PhanNguyen
	 */
	@Override
	public void addEmployeeSkill(Employee employee, EmployeeSkillModel employeeSkill) {
		EmployeeSkill emSkill = new EmployeeSkill();
		Optional<SkillSet> skillSet = skillSetRepository.findById(employeeSkill.getId());
		emSkill.setEmployee(employee);
		emSkill.setSkillLevel(1);
		emSkill.setSkillSetDate(employee.getLegalEntityHireDate());
		emSkill.setCreatedBy("admin");
		emSkill.setCreated(employee.getLegalEntityHireDate());
		if (skillSet.isPresent()) {
			emSkill.setSkillSet(skillSet.get());
		} else {
			throw new InvalidDataException(ErrorConstant.CODE_LIST_ID_OF_SKILL_NULL,
					"Can not find skill set in database");
		}
		employeeSkillRepository.save(emSkill);
	}
}

