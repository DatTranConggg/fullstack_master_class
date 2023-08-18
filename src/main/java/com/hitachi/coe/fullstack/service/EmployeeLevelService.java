package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.Level;

public interface EmployeeLevelService {
    /**
     * Save employee level to database.
     * @author tquangpham
     * @param employee is the employee of the Employee Entity.
     * @param level is the level of the Level Entity.
     */
    void saveEmployeeLevel(Employee employee, Level level) ;
}
