package com.hitachi.coe.fullstack.controller;

import com.hitachi.coe.fullstack.constant.Constants;
import com.hitachi.coe.fullstack.model.BarChartModel;
import com.hitachi.coe.fullstack.model.EmployeeModel;
import com.hitachi.coe.fullstack.model.EmployeeStatusModel;
import com.hitachi.coe.fullstack.model.IBarChartDepartmentModel;
import com.hitachi.coe.fullstack.model.IEmployeeDetails;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.EmployeeService;
import com.hitachi.coe.fullstack.service.EmployeeStatusService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeStatusService employeeStatusService;

	@GetMapping("/search")
	@ApiOperation("This api will return list of Employee matches with one or many conditions ")
	public BaseResponse<?> searchEmployees(@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String businessUnitName, @RequestParam(required = false) String coeCoreTeamName,
			@RequestParam(required = false) String branchName, @RequestParam(required = false) Integer status,@RequestParam(required = false) String fromDateStr,
			@RequestParam(required = false) String toDateStr, @RequestParam(defaultValue = "0") Integer no,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(required = false) Boolean desc) {

			return BaseResponse.success(employeeService.searchEmployees(keyword, businessUnitName, coeCoreTeamName, branchName,status,
					fromDateStr, toDateStr, no, limit, sortBy,desc));
	}

    @GetMapping("/details/hccid/{hccId}")
    @ApiOperation("This api will return details employee information")
    public BaseResponse<IEmployeeDetails> getEmployeeDetailsByHccId(@PathVariable(name = "hccId", required = true) String hccId) {
        return BaseResponse.success(employeeService.getEmployeeDetailsByHccId(hccId));
    }

    @GetMapping("member/{employeeId}")
    @ApiOperation("This api will return employee")
    public ResponseEntity<EmployeeModel> getEmployeeById(@PathVariable(name = "employeeId", required = true) Integer employeeId) {

        return new ResponseEntity<>(employeeService.getEmployeeById(employeeId), HttpStatus.OK);
    }

    @PostMapping("member/create")
    @ApiOperation("This api add employee will employee id")
    public ResponseEntity<Object> createEmployee(@Validated @RequestBody EmployeeModel emp) {

        Map<String, String> response = new HashMap<>();
        response.put(Constants.ID, String.valueOf(employeeService.add(emp)));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("member/update")
    @ApiOperation("This api add employee will return status and employee id")
    public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeModel employeemodel) {

        Map<String, String> response = new HashMap<>();
        response.put(Constants.ID, String.valueOf(employeeService.updateEmployee(employeemodel)));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("delete-employee/{id}")
    @ApiOperation("This api will soft delete employee has id in params and save information user changed ")
    public BaseResponse<EmployeeStatusModel> deleteEmployeeById(@PathVariable("id") Integer id) {
        employeeService.deleteEmployeeById(id);
        return BaseResponse.success(employeeStatusService.deleteEmployeeById(id));
    }

    /**
     * Retrieves the quantity of employees in each department at a certain level within an organization.
     *
     * @author hchantran
     * @param branchId (optional) The ID of the branch to filter by.
     * @param groupId (optional) The ID of the group to filter by.
     * @param teamId (optional) The ID of the team to filter by.
     * @return a ResponseEntity containing the quantity of employees in each department at a certain level.
     */
    @GetMapping("/get-department-level")
    @ApiOperation("This api will calculate the quantity of each department of level for the employee")
    public ResponseEntity<List<IBarChartDepartmentModel>> getQuantityOfLevel(@RequestParam(required = false) Integer branchId, @RequestParam(required = false) Integer groupId, @RequestParam(required = false) Integer teamId) {
        return ResponseEntity.ok(employeeService.getQuantityEmployeeOfLevel(branchId, groupId, teamId));
    }

	/**
	 *
	 * Get the list of quantity for each skill, based on the provided branch, group,
	 * and team IDs.
	 *
	 * @param branchId (optional) the ID of the branch to retrieve the data
	 * @param groupId  (optional) the ID of the group within the branch to retrieve
	 *                 the data
	 * @param teamId   (optional) the ID of the team within the group to retrieve
	 *                 the data
	 * @param SkillIds (optional) The list Id of the skill to filter the data
	 *                 Use format like as '1,2,...'
	 * @return a ResponseEntity containing the list of quantity for each skill
	 */
	@GetMapping("/get-quantity-of-each-skill")
	@ApiOperation("This api will return list of quantity for each skill")
	public ResponseEntity<?> getPercentofSkills(@RequestParam(required = false) Integer branchId,
			@RequestParam(required = false) Integer groupId, @RequestParam(required = false) Integer teamId,
			@RequestParam(required = false) List<Integer> SkillIds) {
		return ResponseEntity
				.ok(employeeService.getQuantityOfEachSkillForBarChart(branchId, groupId, teamId, SkillIds));
	}

    @GetMapping("/get-quantity-level")
    @ApiOperation("This api will calculate the quantity of level for the employee")
    public ResponseEntity <BarChartModel> getQuantityOfLevelForBarChart(@RequestParam(required = false) Integer branchId, @RequestParam(required = false) Integer groupId, @RequestParam(required = false) Integer teamId) {
        return ResponseEntity.ok(employeeService.getQuantityOfLevelForBarChart(branchId, groupId, teamId));
    }

    /**
     Get pie chart data of employee level distribution from database based on filter criteria
     @param branchId Branch ID
     @param coeCoreTeamId COE Core Team ID
     @param coe_id COE ID
     @return List of pie chart model containing data for generating pie chart
     */
    @GetMapping("/level")
    public ResponseEntity<List<IPieChartModel>> getLevelPieChart(
            @RequestParam(required = false) Integer branchId,
            @RequestParam(required = false) Integer coeCoreTeamId,
            @RequestParam(required = false) Integer coe_id) {
        return ResponseEntity.ok(
                employeeService.getLevelPieChart(branchId, coeCoreTeamId, coe_id));
    }

}
