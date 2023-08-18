package com.hitachi.coe.fullstack.repository;

import java.util.List;

import com.hitachi.coe.fullstack.entity.EmployeeSkill;
import com.hitachi.coe.fullstack.entity.SkillSet;
import com.hitachi.coe.fullstack.model.IPieChartModel;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Integer> {
	
	/**
	 * @param pageable to select out put limit of the list
	 * @return top skills base on users level and limit of list out put default is 6
	 * @author PhanNguyen
	 */
	@Query(value = "SELECT skill" 
			+ " FROM EmployeeSkill empSkill JOIN SkillSet skill ON empSkill.skillSet.id = skill.id" 
			+ " GROUP BY skill.name, skill.id"
			+ " ORDER BY SUM(empSkill.skillLevel) DESC ")
	List<SkillSet> getTopSkillSet(Pageable pageable);

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
	 * @param topSkill 		The name of the top skill to filter employees by.
	 * @return A list of PieChartModel objects containing the skill data.
	 * @author pdieu1
	 */

	@Query(nativeQuery = true, value = "SELECT name as label, total_employees  as data "
			+ "FROM get_skill_pie_chart( CAST(CAST(:branchId AS TEXT) AS INTEGER), "
			+ "CAST(CAST(:coeCoreTeamId AS TEXT) AS INTEGER), "
			+ "CAST(CAST(:coeId AS TEXT) AS INTEGER),"
			+ "CAST(:topSkill AS TEXT))")
	List<IPieChartModel> getEmployeeSkillPieChart(@Param("branchId") Integer branchId,
			@Param("coeCoreTeamId") Integer coeCoreTeamId, @Param("coeId") Integer coeId,
			@Param("topSkill") String topSkill);

}
