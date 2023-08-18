package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.model.ExcelResponseModel;
import com.hitachi.coe.fullstack.model.ImportResponse;

import java.io.IOException;
/**
 * This class is a service to import data from file to Survey data table in database.
 *
 * @author tquangpham
 */
public interface SurveyDataService {
	/**
	 * Imports survey data from an Excel file.
	 * @author tquangpham
	 * @param listOfSurveyData the list of Excel Response Model that contains data to import into the SurveyData table.
	 * @return the ImportResponse object.
	 */
	ImportResponse importSurveyDataExcel(ExcelResponseModel listOfSurveyData) throws IOException;

}
