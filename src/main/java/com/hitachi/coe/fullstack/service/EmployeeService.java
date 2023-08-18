package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.model.EmployeeModel;
import com.hitachi.coe.fullstack.model.ImportResponse;
import com.hitachi.coe.fullstack.model.ExcelResponseModel;
import com.hitachi.coe.fullstack.model.IBarChartDepartmentModel;
import com.hitachi.coe.fullstack.model.IEmployeeDetails;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import com.hitachi.coe.fullstack.model.BarChartModel;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

/**
 * This class is a service to CRUD data for employee table
 *
 * @author nguyenhoang1
 */
public interface EmployeeService {

	/**
	 * Searches for employees based on the provided conditions.
	 * 
	 * @author nnien
	 * @param keyword         The keyword to search for. If null, no keyword
	 *                        filtering will be applied.
	 * @param businessUnitName    The businessUnit name to filter by. If null, no filtering
	 *                        by businessUnit name will be applied.
	 * @param coeCoreTeamName The COE core team name to filter by. If null, no
	 *                        filtering by COE core team name will be applied.
	 * @param branchName      The branch name to filter by. If null, no filtering by
	 *                        branch name will be applied.
	 * @param fromDateStr     The start date for filtering in string format (e.g.,
	 *                        "yyyy-MM-dd HH:mm:ss"). If null, no filtering by start
	 *                        date will be applied.
	 * @param toDateStr       The end date for filtering in string format (e.g.,
	 *                        "yyyy-MM-dd HH:mm:ss"). If null, no filtering by end
	 *                        date will be applied.
	 * @param no              The page number to retrieve.
	 * @param limit           The maximum employee of results to return per page.
	 * @param sortBy          The field to sort the results by.
	 * @param desc            The field to sort desc or asc the results.
	 * @exception Throw exception when input invalid time stamp format
	 * @return A page of employee models that match the selected conditions.
	 */
	Page<EmployeeModel> searchEmployees(String keyword, String businessUnitName, String coeCoreTeamName, String branchName,Integer status,
			String fromDateStr, String toDateStr, Integer no, Integer limit, String sortBy, Boolean desc);

	/**
	 * Update user and date change deleted Employee.
	 * @author lta
	 * @param id {@link Integer}
	 * @return {@link EmployeeModel}
	 */
    EmployeeModel deleteEmployeeById(Integer id);

	/**
	 * Imports employee data from an Excel file.
	 * @author tquangpham
	 * @param listOfEmployee the list of Excel Response Model that contains data to import into the Employee table.
	 * @return the ImportResponse object.
	 */
	ImportResponse importUpdateEmployee(ExcelResponseModel listOfEmployee);

	EmployeeModel getEmployeeById(Integer employeeId);

	Integer updateEmployee(EmployeeModel employeeModel);

	Integer add(EmployeeModel e);

	/**
	 * Retrieves the quantity of employees in each department at a certain level within an organization.
	 * @author hchantran
	 * @param branchId (optional) The ID of the branch to filter by.
	 * @param groupId (optional) The ID of the group to filter by.
	 * @param teamId (optional) The ID of the team to filter by.
	 * @return A data contains the quantity of employees in each department at a certain level.
	 */
	List<IBarChartDepartmentModel> getQuantityEmployeeOfLevel(Integer branchId, Integer groupId, Integer teamId);

	/**
	 *
	 * Retrieves the quantity of a specific skill for generating a bar chart.
	 *
	 * @param branchId The ID of the branch to which the data belongs.
	 * @param groupId  The ID of the group to which the data belongs.
	 * @param teamId   The ID of the team to which the data belongs.
	 * @param SkillIds The list Id of the skill to filter the data (optional).
	 * @return A BarChartModel object representing the quantity of the skill for the
	 *         bar chart.
	 */
	BarChartModel getQuantityOfEachSkillForBarChart(Integer branchId, Integer groupId, Integer teamId,
			List<Integer> SkillIds);
	/**
	 * Retrieves the count of employees for each level based on the provided branch, group, and team IDs.
	 *
	 * @param branchId The ID of the branch. Pass null to include all branches.
	 * @param groupId  The ID of the group. Pass null to include all groups.
	 * @param teamId   The ID of the team. Pass null to include all teams.
	 * @return A list of objects containing the label name, level name, and the total count of employees.
	 */
	BarChartModel getQuantityOfLevelForBarChart(Integer branchId, Integer groupId, Integer teamId);

	/**
	 * Retrieves data for a pie chart on the level of employees in a CoE team.
	 * @param branchId The ID of the branch.
	 * @param coeCoreTeamId The ID of the CoE team.
	 * @param coe_id The ID of the CoE.
	 * @return Data for a pie chart.
	 */
	List<IPieChartModel> getLevelPieChart(Integer branchId, Integer coeCoreTeamId, Integer coe_id);
	ImportResponse importInsertEmployee(ExcelResponseModel listOfSurveyData) throws IOException;

	/**
	 * Retrieves the details employee information by hccId
     *
	 * @param hccId the hccId string to find employee details information
	 * @return the details employee information
     * @author tminhto
	 * @see IEmployeeDetails
	 */
	IEmployeeDetails getEmployeeDetailsByHccId(String hccId);


}
