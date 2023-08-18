package com.hitachi.coe.fullstack.controller;

import java.util.List;

import com.hitachi.coe.fullstack.constant.Constants;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import com.hitachi.coe.fullstack.model.SkillSetModel;
import com.hitachi.coe.fullstack.service.EmployeeSkillService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee-skill")
public class EmployeeSkillController {

	@Autowired
	EmployeeSkillService employeeSkillService;

	/**
	 * @param top to select out put limit of the list
	 * @return top skills base on users level and limit of list out put default is 6
	 * @author PhanNguyen
	 */
	@GetMapping("/top-skill")
	ResponseEntity<List<SkillSetModel>> getTopSkillSet(
			@RequestParam(defaultValue = Constants.DEFAULT_NUMBER_TOP_SKILL) Integer top) {
		return new ResponseEntity<>(employeeSkillService.getTopSkillSet(top), HttpStatus.OK);
	}
    /**
	 * Retrieves a pie chart model representing the distribution of top skills among
	 * employees, optionally filtered by branch, COE/core team, and core.
	 *
	 * @param branchId      The ID of the branch to filter by. Optional, defaults to
	 *                      null.
	 * @param coeCoreTeamId The ID of the COE/core team to filter by. Optional,
	 *                      defaults to null.
	 * @param coreId        The ID of the core to filter by. Optional, defaults to
	 *                      null.
	 * @param topSkill 		The name of the top skill to filter employees by. Required.                      
	 * @return A ResponseEntity containing a PieChartModel representing the
	 *         distribution of top skills among employees.
	 * @author PhanNguyen
	 */
	@GetMapping("/skill-pie-chart")
	public ResponseEntity<List<IPieChartModel>> getEmployeeSkillPieChart(@RequestParam(required = false) Integer branchId,
			@RequestParam(required = false) Integer coeCoreTeamId, @RequestParam(required = false) Integer coeId,
			@RequestParam(required = false) List<Integer> topSkill) {
		return ResponseEntity.ok(employeeSkillService.getEmployeeSkillPieChart(branchId, coeCoreTeamId, coeId,
				topSkill)); 

	}
}
