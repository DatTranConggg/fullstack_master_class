package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.model.base.AuditModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeInsertModel extends AuditModel<Integer> {

    private String ldap;

    private String hccId;

    private String employeeName;

    private String level;

    private Date legalEntityHireDate;

    private String email;

    private String businessUnit;

    private String location;

    private String team;
}
