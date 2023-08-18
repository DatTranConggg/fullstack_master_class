package com.hitachi.coe.fullstack.model;

import java.util.Date;

/**
 * Represents an IProjectModel interface
 */
public interface IProjectModel {

    Integer getProjectId();

    Integer getBusinessDomainId();

    Integer getSkillSetId();

    Integer getProjectTypeId();

    String getCode();

    String getProjectName();

    Date getStartDate();

    Date getEndDate();

    String getProjectManager();

    Integer getStatus();

    String getDescription();

    String getCustomerName();

}
