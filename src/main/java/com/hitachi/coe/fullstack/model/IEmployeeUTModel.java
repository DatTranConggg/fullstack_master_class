package com.hitachi.coe.fullstack.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hitachi.coe.fullstack.util.DateTimeSerializer;

import java.util.Date;

/**
 * Represents an IEmployeeUTModel interface
 */
public interface IEmployeeUTModel {

    Integer getEmployeeUtilizationId();

    String getHccId();

    String getName();

    String getEmail();

    String getLdap();

    Double getBillable();

    String getDuration();

    @JsonSerialize(using = DateTimeSerializer.class)
    Date getStartDate();

    @JsonSerialize(using = DateTimeSerializer.class)
    Date getEndDate();

    Date getCreatedDate();
}
