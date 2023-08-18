package com.hitachi.coe.fullstack.service;

import java.util.List;

import com.hitachi.coe.fullstack.model.EmployeeRoleModel;

/**
 * This class use to provide CRUD interfaces on EmployeeRole entity, EmployeeRoleModel model
 *
 * @see com.hitachi.coe.fullstack.entity.EmployeeRole
 * @see EmployeeRoleModel
 */
public interface EmployeeRoleService {

    /**
     * This method use to get a list of EmployeeRoleModel
     *
     * @return a list of EmployeeRoleModel
     * @author tminhto
     * @see EmployeeRoleModel
     */
    List<EmployeeRoleModel> getAll();

}
