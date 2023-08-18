package com.hitachi.coe.fullstack.model;

import java.util.Date;

public interface IEmployeeUtilizationModel {
	
	Integer getAvailableHours();

	Integer getBillableHours();
	
	Integer getPtoOracle();
	
	Integer getLoggedHours();

	Double getBillable();
	
	Date getStartDate();
	
	Date getEndDate();
	
	String getDuration();
	
	Date getLockTime();
	
	String getProjectName();
}
