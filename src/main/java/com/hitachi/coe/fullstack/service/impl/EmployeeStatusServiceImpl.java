package com.hitachi.coe.fullstack.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hitachi.coe.fullstack.entity.EmployeeStatus;
import com.hitachi.coe.fullstack.model.EmployeeStatusModel;
import com.hitachi.coe.fullstack.repository.EmployeeStatusRepository;
import com.hitachi.coe.fullstack.service.EmployeeStatusService;
import com.hitachi.coe.fullstack.transformation.EmployeeStatusTransformer;

@Service
@Transactional
public class EmployeeStatusServiceImpl implements EmployeeStatusService {
	private EmployeeStatusRepository employeeStatusRepository;
	EmployeeStatusTransformer employeeStatusTransformer;

	public EmployeeStatusServiceImpl(EmployeeStatusTransformer employeeStatusTransformer,
			EmployeeStatusRepository employeeStatusRepository) {
		this.employeeStatusRepository = employeeStatusRepository;
		this.employeeStatusTransformer = employeeStatusTransformer;
	}

	@Override
	public EmployeeStatusModel deleteEmployeeById(Integer id) {

		EmployeeStatus employeeStatus = employeeStatusRepository.createDeleteStatusEmployee(id);
		return employeeStatusTransformer.apply(employeeStatus);
	}
}
