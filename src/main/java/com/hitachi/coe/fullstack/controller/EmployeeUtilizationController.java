package com.hitachi.coe.fullstack.controller;


import com.hitachi.coe.fullstack.constant.CommonConstant;
import com.hitachi.coe.fullstack.model.EmployeeUtilizationModelResponse;
import com.hitachi.coe.fullstack.model.IEmployeeUTModel;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationDetail;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationDetailResponse;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationFree;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.EmployeeUtilizationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee-utilization")
public class EmployeeUtilizationController {

	@Autowired
	EmployeeUtilizationService employeeUtilizationService;


	@GetMapping("/get-pie-chart")
	public ResponseEntity<List<IPieChartModel>> getUtilizationPieChart(@RequestParam(value="branchId", required=false) Integer branchId,
																	   @RequestParam(value="coeCoreTeamId", required=false) Integer coeCoreTeamId,
																	   @RequestParam(value="coeId", required=false) Integer coeId,
																	   @RequestParam(value="fromDate", required=true) String fromDate,
																	   @RequestParam(value="toDate", required=true) String toDate) {
	
		return ResponseEntity.ok(employeeUtilizationService.getUtilizationPieChart(branchId, coeId,coeCoreTeamId,fromDate,toDate));
	}

	/**
	 * This API search employees in utilization.
	 *
	 * @param keyword       The keyword to search for.
	 * @param billable      The billable to search for.
	 * @param branchId      The branch id of Branch.
	 * @param coeCoreTeamId The coeCoreTeam id of CoE Core Team.
	 * @param coeTeamId     The Center Of Excellence id of Center Of Excellence.
	 * @param no            The page number to retrieve.
	 * @param limit         The maximum employee of results to return per page.
	 * @param sortBy        The field to sort the results by.
	 * @param desc          The field to sort desc or asc the results.
	 * @return BaseResponse containing the list of employees.
	 * @author tquangpham
	 */
	@GetMapping("/")
	public BaseResponse<Page<IEmployeeUTModel>> searchEmployeeUtilization(@RequestParam(required = false) String keyword, @RequestParam(required = false) String billable, @RequestParam(required = false) Integer branchId,
																		  @RequestParam(required = false) Integer coeCoreTeamId, @RequestParam(required = false) Integer coeTeamId,
																		  @RequestParam(defaultValue = "0") Integer no, @RequestParam(defaultValue = "10") Integer limit,
																		  @RequestParam(defaultValue = "name") String sortBy, @RequestParam(required = false) Boolean desc) {
		return new BaseResponse<>(HttpStatus.OK.value(), null, employeeUtilizationService.searchEmployeeUtilization(keyword, billable, branchId, coeCoreTeamId, coeTeamId, no, limit, sortBy,desc));
	}

	/**
	 * This method is used to create an API to get information of employees utilization detail by employee hccId.
	 *
	 * @param hccId is employee utilization Id from employee utilization table
	 * @return a list that contains average numbers for statistics and a list of employee utilization by employee hccId
	 * @author tquangpham
	 */

	@GetMapping("/detail-HCCID/{hccId}")
	public BaseResponse<EmployeeUtilizationModelResponse> getEmployeeUtilizationDetailByHccId(@PathVariable("hccId") String hccId) {
		return new BaseResponse<>(HttpStatus.OK.value(), null, employeeUtilizationService.getEmployeeUtilizationDetailByHccId(hccId));
	}

	/**
	 * This method is used to create an API to get list of employees that have no utilization.
	 *
	 * @return A BaseResponse object with a status code, a message and data.
	 * @author tquangpham
	 */
	@GetMapping("/no-utilization")
	@ApiOperation("This API will return a list of employees with no utilization.")
	public BaseResponse<List<IEmployeeUtilizationFree>> getEmployeesNoUtilization() {
		return new BaseResponse<>(HttpStatus.OK.value(), null,employeeUtilizationService.getListEmployeeUtilizationWithNoUT(CommonConstant.BILLABLE_THRESHOLD));
	}

	/**
	 * This method is used to create an API to get information of employees utilization detail.
	 *
	 * @param employeeUtilizationId  id of employee utilization to get employee utilization details information
	 * @return A BaseResponse object with a status code, a message and data.
	 * @author tquangpham
	 */
	@GetMapping("/detail/{employeeUtilizationId}")
	@ApiOperation("This API will return an employee utilization detail.")
	public BaseResponse<IEmployeeUtilizationDetail> getEmployeeUtilizationDetailByEmployeeUtilizationId(@PathVariable Integer employeeUtilizationId) {
		return new BaseResponse<>(HttpStatus.OK.value(), null,employeeUtilizationService.getEmployeeUtilizationDetailByEmployeeUtilizationId(employeeUtilizationId));
	}

	/**
	 * This method is used to create an API to get information of project information by employee utilization.
	 *
	 * @param employeeUtilizationId  id of employee utilization to get employee utilization details information
	 * @return A BaseResponse object with a status code, a message and data.
	 * @author tquangpham
	 */
	@GetMapping("/detail-project/{employeeUtilizationId}")
	@ApiOperation("This API will return an project information by employee utilization.")
	public BaseResponse<IEmployeeUtilizationDetailResponse> getProjectInformationByEmployeeUtilizationId(@PathVariable Integer employeeUtilizationId) {
		return new BaseResponse<>(HttpStatus.OK.value(), null,employeeUtilizationService.getProjectInformationByEmployeeUtilizationId(employeeUtilizationId));
	}
}
