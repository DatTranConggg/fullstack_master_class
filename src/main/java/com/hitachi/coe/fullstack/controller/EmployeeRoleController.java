package com.hitachi.coe.fullstack.controller;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.coe.fullstack.model.EmployeeRoleModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.EmployeeRoleService;

@RestController
@RequestMapping("/api/v1/employee-roles")
public class EmployeeRoleController {

    @Autowired
    private EmployeeRoleService employeeRoleService;

    @GetMapping("")
    @ApiOperation(value = "This API return all employee roles records")
    public BaseResponse<List<EmployeeRoleModel>> getAllEmployeeRoles() {
        return new BaseResponse<>(HttpStatus.OK.value(), "List of Employee Roles", employeeRoleService.getAll());
    }
}
