package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.SurveyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface EmployeeRepository is used to access survey_data table.
 */
@Repository
public interface SurveyDataRepository extends JpaRepository<SurveyData, Integer>{

	/**
	 * Filters Survey data based on the provided conditions.
	 * @author tquangpham
	 * @param employeeId The LDAP of the employee.
	 * @param email The email of the employee.
	 * @return A list of Survey data that match the selected conditions.
	 */
	@Query(value="select sd from SurveyData sd where sd.employeeId = :employeeId or sd.email = :email")
	List<SurveyData> getSurveyDataByEmployeeIdOrEmail(String employeeId, String email);

}


