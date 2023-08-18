package com.hitachi.coe.fullstack.model;

import java.util.Date;

public interface IEmployeeDetails {
    Integer getId();

    String getHccId();

    String getName();

    String getEmail();

    String getLdap();

    Date getLegalEntityHireDate();

    String getBranchName();

    String getCenterOfExcellenceName();

    String getCoeCoreTeamName();

    String getCoeCoreTeamSubLeaderName();

    String getBusinessUnitName();

    String getLevelCode();

    String getLevelName();

    Integer getCoeCoreTeamId();

    Integer getBranchId();

    Integer getCenterOfExcellenceId();

    String getSkillSetName();
    
    Integer getEmployeeStatus();

    Integer getSumUtilization();

    String getEmployeeWorkingStatus();
}
