package com.hitachi.coe.fullstack.service;

import java.util.List;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.model.EmployeeSkillModel;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import com.hitachi.coe.fullstack.model.SkillSetModel;

public interface EmployeeSkillService {
	
	/**
	 * @param limitSkill to select out put limit of the list
	 * @return top skills base on users level and limit of list out put default is 6
	 * @author PhanNguyen
	 */
	List<SkillSetModel> getTopSkillSet(int limitSkill);
	
	 /**
	 * @param branchId      The ID of the branch to retrieve data for.
	 * @param coeCoreTeamId The ID of the core team to retrieve data for.
	 * @param coeId         The ID of the center of excellence to retrieve data for.
	 * @param topSkill      The IDs of the top skills to retrieve data for.
	 * @return A list of PieChartModel objects representing the skill distribution of employees for the pie chart.
	 * @author pdieu1
	 */
		List<IPieChartModel> getEmployeeSkillPieChart(Integer branchId, Integer coeCoreTeamId, Integer coeId,
				List<Integer> topSkill);
		
		/**
		 * function will add employeeSkill into the database
		 * 
		 * @param employee 	is the employee data which user give to the server
		 * @param employeeSkill  is the employeeSkill which user give to to server
		 * @author PhanNguyen
		 */
		void addEmployeeSkill(Employee employee, EmployeeSkillModel employeeSkill);
		
}
