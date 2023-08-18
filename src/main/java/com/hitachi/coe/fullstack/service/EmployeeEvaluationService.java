package com.hitachi.coe.fullstack.service;

import java.util.List;

import com.hitachi.coe.fullstack.model.IEmployeeEvaluationDetails;

public interface EmployeeEvaluationService {
    /**
     * This method is used to get the details of the employee evaluation
     * 
     * @param hccId the employee's hccId to get the evaluation details information
     * @return a list of employee evaluations
     * @author tminhto
     * @see IEmployeeEvaluationDetails
     */
    List<IEmployeeEvaluationDetails> getEmployeeEvaluationDetailsByEmployeeHccId(String hccId);
}
