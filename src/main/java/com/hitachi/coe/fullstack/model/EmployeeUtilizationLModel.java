package com.hitachi.coe.fullstack.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeUtilizationLModel {

    private Integer id;

    private String hccId;

    private String ldapId;

    private String name;

    private String coeUtName;

    private Double billable;

    private String email;

    private Date startDate;

    private Date endDate;
}
