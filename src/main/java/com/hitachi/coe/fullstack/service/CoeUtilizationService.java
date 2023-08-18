package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.entity.CoeUtilization;
import com.hitachi.coe.fullstack.model.CoeUtilizationModel;

import java.util.List;

public interface CoeUtilizationService {
	/**
	 * @return list of CoE Utilization
	 * @author PhanNguyen
	 */
	List<CoeUtilizationModel> getAllCoeUtilization();

	/**
	 * Save CoE Utilization from a given duration text.
	 *
	 * @param duration duration of CoE Utilization
	 * @param formatDate format of Date
	 * @param dateImportStr date for Import
	 * @param totalUtilization Total Utilization of CoE Utilization
	 * @return CoeUtilization
	 * @author tquangpham
	 */
	CoeUtilization saveCoEUtilizationFromDuration(String formatDate, String duration, String dateImportStr, Double totalUtilization);
}
