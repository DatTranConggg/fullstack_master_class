package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.model.base.AuditModel;

import lombok.Data;

@Data
public class EmployeeRoleModel extends AuditModel<Integer> {
    private Integer id;

    private String code;

    private String name;

    private String description;
}
