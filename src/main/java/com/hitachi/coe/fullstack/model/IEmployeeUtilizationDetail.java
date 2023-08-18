package com.hitachi.coe.fullstack.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hitachi.coe.fullstack.util.DateTimeSerializer;

import java.util.Date;

/**
 * Represents an IEmployeeUtilizationDetail interface
 */
public interface IEmployeeUtilizationDetail {

    String getHccId();

    String getLdap();

    String getName();

    String getEmail();

    Integer getEmployeeUtilizationId();

    String getProjectName();

    @JsonSerialize(using = DateTimeSerializer.class)
    Date getStartDate();
    @JsonSerialize(using = DateTimeSerializer.class)
    Date getEndDate();

    Double getBillable();

    Integer getPtoOracle();

    Integer getBillableHours();

    Integer getLoggedHours();

    Integer getAvailableHours();

    String getDuration();
}
