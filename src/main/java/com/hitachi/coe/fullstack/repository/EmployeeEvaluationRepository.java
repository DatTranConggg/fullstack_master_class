package com.hitachi.coe.fullstack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hitachi.coe.fullstack.entity.EmployeeEvaluation;
import com.hitachi.coe.fullstack.model.IEmployeeEvaluationDetails;

@Repository
public interface EmployeeEvaluationRepository extends JpaRepository<EmployeeEvaluation, Integer> {

    /**
     * This method is used to retrieve the details of the employee evaluation
     * 
     * @param hccId the employee's hccId to retrieve the employee evaluation
     *              information from the database.
     * @return a list of employee evaluations
     * @author tminhto
     * @see IEmployeeEvaluationDetails
     */
    @Query(value = "SELECT " +
            " ee.evaluation_date as evaluationDate, " +
            " ee.evaluation_info as evaluationInfo, " +
            " evaluator.name as evaluatorName, " +
            " evaluator.email as evaluatorEmail, " +
            " evaluator.hcc_id as evaluatorHccId, " +
            " el.code as evaluationLevelCode, " +
            " el.description as evaluationLevelDescription " +
            "FROM public.employee_evaluation ee  " +
            "INNER JOIN public.employee evaluator ON ee.evaluator_id = evaluator.id  " +
            "INNER JOIN public.evaluation_level el ON ee.evaluation_level_id = el.id " +
            "INNER JOIN public.employee e ON ee.employee_id = e.id " +
            "WHERE e.hcc_id = :hccId " +
            "ORDER BY ee.evaluation_date DESC", nativeQuery = true)
    List<IEmployeeEvaluationDetails> getEmployeeEvaluationDetailsByEmployeeHccId(@Param("hccId") String hccId);

}
