package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.EmployeeUtilization;
import com.hitachi.coe.fullstack.model.IEmployeeUTModel;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationDetail;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationDetailResponse;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationFree;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationModel;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


/**
 * The Interface EmployeeUtilizationRepository is used to access EmployeeUtilization table.
 */
@Repository
public interface EmployeeUtilizationRepository extends JpaRepository<EmployeeUtilization, Integer> {

	/**

	Get data for utilization pie chart by percentage distribution of utilization hours of employees.
	@param branchId branch id (optional)
	@param coeCoreTeamId coe core team id of employee (optional)
	@param coeId coe id of employee (optional)
	@param fromDate start date of utilization time (optional)
	@param toDate end date of utilization time (optional)
	@return List of Object[], each Object[] includes utilization range and number of employees in that range.
	@author lta 
	*/
	@Query(value = "SELECT labels as label, amount as data FROM get_utilization_pie_chart(CAST(CAST(:branchId AS TEXT )AS INTEGER), CAST(CAST(:coeId AS TEXT )AS INTEGER),CAST(CAST(:coeCoreTeamId AS TEXT )AS INTEGER) ,:fromDate, :toDate);", nativeQuery = true)
	List<IPieChartModel> getUtilizationPieChart( Integer branchId, Integer coeId, Integer coeCoreTeamId, @Param("fromDate") Timestamp fromDate, @Param("toDate") Timestamp toDate);

	/**
	 * Get list of employees in utilization.
	 *
	 * @param keyword       The keyword to search for.
	 * @param branchId      The branch id of Branch.
	 * @param coeCoreTeamId The coeCoreTeam id of CoE Core Team.
	 * @param coeTeamId     The Center Of Excellence id of Center Of Excellence.
	 * @return A List of IEmployeeUTModel interface that match the selected conditions.
	 * @author tquangpham
	 */
	@Query(value = "SELECT eu.id as employeeUtilizationId, em.hcc_id as hccId, em.name as name, em.email as email, em.ldap as ldap, eu.billable as billable, cu.duration as duration, cu.start_date AS startDate, cu.end_date AS endDate, cu.created_date AS createdDate "
			+ "FROM employee_utilization eu "
			+ "JOIN coe_utilization cu ON cu.id = eu.coe_utilization_id "
			+ "JOIN (SELECT MAX(cou.end_date) AS max_end_date FROM coe_utilization cou) coeUt "
			+ "ON coeUt.max_end_date = cu.end_date "
			+ "JOIN employee em ON eu.employee_id = em.id "
			+ "JOIN branch br ON em.branch_id = br.id "
			+ "JOIN coe_core_team ct ON em.coe_core_team_id = ct.id "
			+ "JOIN center_of_excellence coe ON ct.coe_id = coe.id "
			+ "WHERE (:keyword IS NULL or em.ldap LIKE CONCAT('%', :keyword, '%') or em.hcc_id LIKE CONCAT('%', :keyword, '%') or em.email LIKE CONCAT('%', :keyword, '%') or em.name LIKE CONCAT('%', :keyword, '%')) "
			+ "AND (:branchId IS NULL OR br.id = CAST(CAST(:branchId AS TEXT) AS INTEGER)) "
			+ "AND (:coeCoreTeamId IS NULL OR ct.id = CAST(CAST(:coeCoreTeamId AS TEXT) AS INTEGER)) "
			+ "AND (:coeTeamId IS NULL OR coe.id = CAST(CAST(:coeTeamId AS TEXT) AS INTEGER)) "
			, nativeQuery = true)
	List<IEmployeeUTModel> searchEmployeeUtilization(@Param("keyword") String keyword, @Param("branchId") Integer branchId,
													 @Param("coeCoreTeamId") Integer coeCoreTeamId, @Param("coeTeamId") Integer coeTeamId);


	/**
	 * @param hccId from employee table
	 * @return List of IEmployeeUtilizationModel base on the column follow the
	 *         query.
	 * @author tquangpham
	 */
	@Query(nativeQuery = true, value = "SELECT empUt.available_hours AS availableHours, empUt.billable_hours AS billableHours, "
			+ "empUt.pto_oracle AS ptoOracle, empUt.logged_hours AS loggedHours, "
			+ "empUt.billable AS billable, ut.start_date AS startDate, "
			+ "ut.end_date AS endDate, ut.duration AS Duration, empUt.created_date AS lockTime, "
			+ "pj.name as projectName "
			+ "FROM employee_utilization empUt "
			+ "JOIN employee emp ON emp.id = empUt.employee_id "
			+ "JOIN coe_utilization ut ON ut.id = empUt.coe_utilization_id "
			+ "JOIN project pj ON empUt.project_code = pj.code "
			+ "WHERE emp.hcc_id = CAST(:hccId AS TEXT) ")
	List<IEmployeeUtilizationModel> getEmployeeUtilizationDetailByHccId(@Param("hccId") String hccId);

	/**
	 * Retrieves an employees utilization detail.
	 *
	 * @param id from employee utilization table
	 * @return List of IEmployeeUtilizationDetail base on the column follow the
	 *         query.
	 * @author tquangpham
	 */
	@Query(nativeQuery = true, value = "SELECT pj.name AS projectName, pj.project_manager AS projectManager, pj.start_date AS startDate, "
			+ "pj.end_date AS endDate, eu.billable AS billable "
			+ "FROM employee_utilization eu, project pj "
			+ "WHERE eu.project_code = pj.code AND eu.id = :id")
	IEmployeeUtilizationDetailResponse getProjectInformationByEmployeeUtilizationId(@Param("id") Integer id);

	/**
	 * Retrieves a list of employees that have no utilization.
	 *
	 * @param billableThreshold The ID of the branch. Pass null to include all branches.
	 * @return the list of employees that have no utilization.
	 * @author tquangpham
	 */
	@Query(value = "SELECT hccid AS hccId, id AS employeeId, employeeName, email, branchName, businessName, teamName, daysFree FROM getEmployeeUtilizationFree(:billableThreshold)", nativeQuery = true)
	List<IEmployeeUtilizationFree> getEmployeesUtilizationNoUT(@Param("billableThreshold") Double billableThreshold);


	/**
	 * Retrieves an employees utilization detail.
	 *
	 * @param id The ID of employee utilization table.
	 * @return the list of employees utilization detail.
	 * @author tquangpham
	 */
	@Query(value = "SELECT e.ldap, e.hcc_id AS hccId, e.name, e.email, " +
			"eu.id AS employeeUtilizationId, " +
			"p.name AS projectName, cu.start_date AS startDate, cu.end_date AS endDate, " +
			"eu.billable, eu.pto_oracle AS ptoOracle, eu.billable_hours AS billableHours, " +
			"eu.logged_hours AS loggedHours, eu.available_hours AS availableHours, cu.duration AS duration " +
			"FROM project p " +
			"JOIN employee_utilization eu ON eu.project_code = p.code " +
			"JOIN coe_utilization cu ON cu.id = eu.coe_utilization_id " +
			"JOIN employee e ON e.id = eu.employee_id " +
			"WHERE eu.id = :id", nativeQuery = true)
	IEmployeeUtilizationDetail getEmployeeUtilizationDetailById(@Param("id") Integer id);
}
