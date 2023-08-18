package com.hitachi.coe.fullstack.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitachi.coe.fullstack.model.EmployeeRoleModel;
import com.hitachi.coe.fullstack.repository.EmployeeRoleRepository;
import com.hitachi.coe.fullstack.service.EmployeeRoleService;
import com.hitachi.coe.fullstack.transformation.EmployeeRoleTransformer;

/**
 * This class provide implementation of EmployeeRoleService interface
 *
 * @author tminhto
 * @see EmployeeRoleService
 */
@Service
public class EmployeeRoleServiceImpl implements EmployeeRoleService {

    @Autowired
    private EmployeeRoleRepository employeeRoleRepository;

    @Autowired
    private EmployeeRoleTransformer transformer;

    @Override
    public List<EmployeeRoleModel> getAll() {
        return employeeRoleRepository.findAll()
                .stream()
                .map(transformer)
                .collect(Collectors.toList());
    }

}
