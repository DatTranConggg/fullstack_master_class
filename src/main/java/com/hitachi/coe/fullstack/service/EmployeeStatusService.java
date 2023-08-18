package com.hitachi.coe.fullstack.service;


import com.hitachi.coe.fullstack.model.EmployeeStatusModel;
/**
 * The interface EmployeeStatusService is the interface to EmployeeStatusServiceImplement.
 * 
 * @author loita
 *
 */
public interface EmployeeStatusService {
	/**
	 * Create status employee in employeeStatus entity with status 0 (deleted).
	 * 
	 * @param id {@link Integer}
	 * @return {@link EmployeeStatusModelTests}
	 */
	  public EmployeeStatusModel deleteEmployeeById(Integer id);

	
}
