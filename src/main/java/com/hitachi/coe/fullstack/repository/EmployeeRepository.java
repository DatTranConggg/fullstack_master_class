package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.model.IBarChartDepartmentModel;
import com.hitachi.coe.fullstack.model.IEmployeeDetails;
import com.hitachi.coe.fullstack.model.IResultOfQueryBarChart;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;



/**
 * The Interface EmployeeRepository is using to access Employee table.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	/**
	 * Filters employees based on the provided conditions.
	 * 
	 * @author nnien
	 * @param keyword         The keyword to search for. If null, no keyword
	 *                        filtering will be applied.
	 * 
	 * @param businessUnitName    The businessUnit name to filter by. If null, no filtering
	 *                        by businessUnit name will be applied.
	 * 
	 * @param coeCoreTeamName The COE core team name to filter by. If null, no
	 *                        filtering by COE core team name will be applied.
	 * 
	 * @param branchName      The branch name to filter by. If null, no filtering by
	 *                        branch name will be applied .
	 * 
	 * @param fromDate        The start date for filtering. If null, no filtering by
	 *                        start date will be applied.
	 * 
	 * @param toDate          The end date for filtering. If null, no filtering by
	 *                        end date will be applied.
	 * 
	 * @param page            The pagination information.
	 * @exception Throw exception when input invalid time stamp format
	 * @return A page of employees that match the selected conditions.
	 */
	@Query("SELECT e FROM Employee e " + "JOIN e.businessUnit bu JOIN e.coeCoreTeam c JOIN e.branch b "
			+ " WHERE ((:keyword is null) or (e.ldap like %:keyword%) or (e.hccId like %:keyword%) or (e.email like %:keyword%) or (e.name like %:keyword%)) "
			+ " AND ((:businessUnitName is null) or (bu.name like %:businessUnitName%)) 													"
			+ " AND ((:coeCoreTeamName is null) or (c.name like %:coeCoreTeamName%)) 											"
			+ " AND ((:branchName is null) or (b.name like %:branchName%)) 														"
			+ " AND (cast(:fromDate as date) is null or e.legalEntityHireDate >= :fromDate) 											"
			+ " AND (cast(:toDate as date) is null or (e.legalEntityHireDate <= :toDate)) 												" 
			+ "	AND e.id IN 																									"
			+ "   (SELECT es.employee.id FROM EmployeeStatus es WHERE es.status = :status  AND es.employee.id = e.id 					"
			+ "   GROUP BY es.employee.id " 
			+ "   HAVING MAX(es.statusDate) = (SELECT MAX(es2.statusDate) " + " FROM EmployeeStatus es2 "
			+ "       WHERE es2.employee.id = es.employee.id" + "   )" + ")")

	Page<Employee> filterEmployees(@Param("keyword") String keyword, @Param("businessUnitName") String businessUnitName,
			@Param("coeCoreTeamName") String coeCoreTeamName, @Param("branchName") String branchName,@Param("status") Integer status,
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable page);

	/**
	 * Delete employees form system.
	 *
	 * @author loita
	 */
	@Query(value = "UPDATE public.employee	SET updated_by= 'admin', updated_date=CURRENT_DATE WHERE id = ?1 RETURNING *", nativeQuery = true)
	Employee deleteEmployeeById(@PathVariable("id") Integer id);

	/**
	 * Finds an employee by HCC ID and LDAP.
	 *
	 * @author tquangpham
	 * @param hccId The HCC ID of the employee.
	 * @param ldap  The LDAP of the employee.
	 * @return The employee matching the given HCC ID and LDAP, or null if not
	 *         found.
	 */
	@Query("SELECT e FROM Employee e WHERE e.hccId = :hccId AND e.ldap = :ldap")
	Employee findByHccIdAndLdap(String hccId, String ldap);

	@Query("SELECT e FROM Employee e WHERE e.email = :email OR e.ldap = :ldap OR e.hccId = :hccId ")
	List<Employee> findByEmailOrLdapOrHccId(@Param("email") String email, @Param("ldap") String ldap,
			@Param("hccId") String hccId);

	/**
	 * Get the quantity of per skill bar chart based on the specified branch, group,
	 * and team IDs.
	 *
	 * @author hchantran
	 * @param branchId The ID of the branch to filter the data (optional).
	 * @param groupId  The ID of the group to filter the data (optional).
	 * @param teamId   The ID of the team to filter the data (optional).
	 * @return An array of objects representing the quantity values for the levels
	 *         bar chart.
	 */
	@Query(nativeQuery = true, value = "SELECT result.levelName as level, result.total as quantity FROM getQuantityEmployeeOfLevel(CAST(CAST(:branchId AS TEXT) AS INTEGER),"
			+ "CAST(CAST(:groupId AS TEXT) AS INTEGER), " + "CAST(CAST(:teamId AS TEXT) AS INTEGER)) AS result")
	List<IBarChartDepartmentModel> getQuantityEmployeeOfLevel(@Param("branchId") Integer branchId,
			@Param("groupId") Integer groupId, @Param("teamId") Integer teamId);

	/**
	 * Finds an employee by HCC ID.
	 *
	 * @author tquangpham
	 * @param hccId The HCC ID of the employee.
	 * @return The employee matching the given HCC ID, or null if not found.
	 */
	@Query("SELECT e FROM Employee e WHERE e.hccId = :hccId")
	Employee findByHccId(String hccId);

	/**
	 * Get the quantity of per skill bar chart based on the specified branch, group,
	 * and team IDs.
	 *
	 * @param branchId The ID of the branch to filter the data (optional).
	 * @param groupId  The ID of the group to filter the data (optional).
	 * @param teamId   The ID of the team to filter the data (optional).
	 * @param SkillIds The list Id of the skill to filter the data (optional).
	 * @return An array of objects representing the quantity values for the skill
	 *         bar chart.
	 */
	@Query(value = "SELECT result.labels as label, result.skillName as fieldName, result.total as total "
			+ "FROM getQuantityOfSkillByConditions(Cast(Cast(:branchId as text)as Integer), Cast(Cast(:groupId as text)as Integer), "
			+ "Cast(Cast(:teamId as text)as Integer), Cast(:SkillIds as text)) as result", nativeQuery = true)
	List<IResultOfQueryBarChart> getQuantityOfEachSkillForBarChart(@Param("branchId") Integer branchId,
			@Param("groupId") Integer groupId, @Param("teamId") Integer teamId, @Param("SkillIds") String SkillIds);

	/**
	 * Retrieves the count of employees for each level.
	 *
	 * @param branchId The ID of the branch. Pass null to include all branches.
	 * @param groupId  The ID of the group. Pass null to include all groups.
	 * @param teamId   The ID of the team. Pass null to include all teams.
	 * @return A list of objects containing the label name, level name, and the
	 *         total count of employees.
	 */
	@Query(nativeQuery = true, value = "SELECT results.labels as label, results.levelName as fieldName, results.total as total "
			+ "FROM getQuantityOfLevelForBarChart(CAST(CAST(:branchId AS TEXT) AS INTEGER),"
			+ "CAST(CAST(:groupId AS TEXT) AS INTEGER), " + "CAST(CAST(:teamId AS TEXT) AS INTEGER)) AS results")
	List<IResultOfQueryBarChart> getQuantityOfLevelForBarChart(@Param("branchId") Integer branchId,
			@Param("groupId") Integer groupId, @Param("teamId") Integer teamId);

	/**
	 * This method is used to get the details of the employee by the specified employee's hccId.
	 *
	 * @param hccId the employee hccId to retrieve employee details information from
	 * @return employee details information
	 * @author tminhto
	 * @see IEmployeeDetails
	 */
    @Query(value = "SELECT  \n" +
            "emp.id as id,  \n" +
            "emp.hcc_id as hccId,  \n" +
            "emp.name as name, \n" +
            "emp.email as email, \n" +
            "emp.ldap as ldap,  \n" +
            "emp.legal_entity_hire_date as legalEntityHireDate, \n" +
            "br.name as branchName, \n" +
            "center.name as centerOfExcellenceName, \n" +
            "coeCoreTeam.name as coeCoreTeamName, \n" +
            "coeCoreTeamSubLeader.name as coeCoreTeamSubLeaderName, \n" +
            "bu.name as businessUnitName, \n" +
            "le.code as levelCode,  \n" +
            "le.name as levelName,  \n" +
            "emp.coe_core_team_id as coeCoreTeamId,  \n" +
            "emp.branch_id as branchId,  \n" +
            "center.id as centerOfExcellenceId,  \n" +
            "empStatus.status as employeeStatus,  \n" +
            "string_agg(ss.name, ', ') as skillSetName,\n" +
            "t1.sum_ut as sumUtilization,\n" +
            "CASE \n" +
            "    WHEN 0 = ANY(t2.employee_type_arr) AND t1.sum_ut >= 85 THEN 'Billable'\n" +
            "    WHEN 1 = ANY(t2.employee_type_arr) AND t1.sum_ut > 0 THEN 'Non-billable'\n" +
            "    ELSE 'On-bench'\n" +
            "END AS employeeWorkingStatus\n" +
            "FROM public.employee emp  \n" +
            "LEFT JOIN public.branch br ON br.id = emp.branch_id  \n" +
            "LEFT JOIN public.coe_core_team coeCoreTeam ON coeCoreTeam.id = emp.coe_core_team_id  \n" +
            "LEFT JOIN public.employee coeCoreTeamSubLeader ON coeCoreTeamSubLeader.id = coeCoreTeam.sub_leader_id  \n" +
            "LEFT JOIN public.center_of_excellence center ON center.id = coeCoreTeam.coe_id  \n" +
            "LEFT JOIN public.business_unit bu ON bu.id = emp.business_unit_id  \n" +
            "LEFT JOIN public.employee_level el ON el.employee_id = emp.id  \n" +
            "LEFT JOIN public.level le ON le.id = el.level_id  \n" +
            "LEFT JOIN public.employee_skill es ON es.employee_id = emp.id  \n" +
            "LEFT JOIN public.skill_set ss ON ss.id = es.skill_set_id  \n" +
            "LEFT JOIN public.employee_status empStatus ON empStatus.employee_id = emp.id  \n" +
            "LEFT JOIN (\n" +
            "    SELECT \n" +
            "        ep.employee_id,\n" +
            "        SUM(ep.utilization) AS sum_ut\n" +
            "    FROM public.employee_project ep\n" +
            "    LEFT JOIN public.employee e ON e.id = ep.employee_id \n" +
            "    WHERE now() < ep.release_date\n" +
            "    GROUP BY ep.employee_id\n" +
            ") t1 ON t1.employee_id = emp.id\n" +
            "LEFT JOIN (\n" +
            "    SELECT \n" +
            "        ep.employee_id,\n" +
            "        array_agg(ep.employee_type) as employee_type_arr\n" +
            "    FROM public.employee_project ep\n" +
            "    WHERE now() < ep.release_date\n" +
            "    group by ep.employee_id\n" +
            ") t2 ON t2.employee_id = emp.id\n" +
            "WHERE emp.hcc_id = :hccId  \n" +
            "GROUP BY emp.id, emp.hcc_id, emp.name, emp.email, emp.ldap, emp.legal_entity_hire_date, br.name,  \n" +
            "center.name, coeCoreTeam.name, coeCoreTeamSubLeader.name, bu.name, le.code, le.name, el.level_date,  \n" +
            "emp.coe_core_team_id, emp.branch_id, center.id, el.level_date, empStatus.status_date, empStatus.status,\n" +
            "t1.sum_ut, t2.employee_type_arr\n" +
            "ORDER BY el.level_date DESC, empStatus.status_date DESC  \n" +
            "LIMIT 1", nativeQuery = true)
    IEmployeeDetails getEmployeeDetailsByHccId(@Param("hccId") String hccId);
}
