package com.hitachi.coe.fullstack.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUtilizationModelTest{

    Integer no;

    private String name;

    private String level;

    private String location;

    private String bu;

    private String coe;

    private BigDecimal billable;

    private Integer loggedHours;

    private String oracleStaggedProject;

    private String timeSheet;

    private String email;

    private Integer availableHours;

    private Integer billableHours;

    private Integer ptoOracle;

    private Date startDate;

    private Integer hccId;
}

