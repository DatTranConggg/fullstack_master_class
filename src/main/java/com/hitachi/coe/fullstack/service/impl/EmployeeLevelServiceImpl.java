package com.hitachi.coe.fullstack.service.impl;

import com.hitachi.coe.fullstack.constant.CommonConstant;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeLevel;
import com.hitachi.coe.fullstack.entity.Level;
import com.hitachi.coe.fullstack.repository.EmployeeLevelRepository;
import com.hitachi.coe.fullstack.service.EmployeeLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class EmployeeLevelServiceImpl implements EmployeeLevelService {
    @Autowired
    EmployeeLevelRepository employeeLevelRepository;

    @Override
    public void saveEmployeeLevel(Employee employee, Level level) {
        EmployeeLevel employeeLevel = new EmployeeLevel();
        employeeLevel.setLevelDate(new Timestamp(System.currentTimeMillis()));
        employeeLevel.setEmployee(employee);
        employeeLevel.setLevel(level);
        employeeLevel.setCreatedBy(CommonConstant.CREATED_BY_ADMINISTRATOR);
        employeeLevel.setCreated(new Date());
        employeeLevelRepository.save(employeeLevel);
    }
}
