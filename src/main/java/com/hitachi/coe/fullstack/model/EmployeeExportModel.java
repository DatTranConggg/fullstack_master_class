package com.hitachi.coe.fullstack.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeExportModel {

    private String hccId;

    private String ldap;

    private String name;

    private String email;

    private Date legalEntityHireDate;

    private String practice;

    private String branch;

    private String level;
}
