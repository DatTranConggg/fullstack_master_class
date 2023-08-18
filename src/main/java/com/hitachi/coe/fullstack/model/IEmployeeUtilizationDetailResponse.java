package com.hitachi.coe.fullstack.model;

import java.util.Date;

public interface IEmployeeUtilizationDetailResponse {
	String getProjectName();

	String getProjectManager();

	Date getStartDate();

	Date getEndDate();

	Double getBillable();
}
