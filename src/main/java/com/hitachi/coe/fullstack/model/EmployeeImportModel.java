package com.hitachi.coe.fullstack.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

/**
 * Represents an Employee Excel model.
 */
@Getter
@Setter
@NoArgsConstructor
public class EmployeeImportModel {

    private String hccId;

    private String ldap;

    private String employeeName;

    private String email;

    private Date legalEntityHireDate;

    private String businessUnit;

    private String branch;

    private String level;

    private String team;

}
