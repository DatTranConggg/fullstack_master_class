package com.hitachi.coe.fullstack.service.impl;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.SurveyData;
import com.hitachi.coe.fullstack.model.ExcelConfigModel;
import com.hitachi.coe.fullstack.model.ExcelResponseModel;
import com.hitachi.coe.fullstack.model.ImportResponse;
import com.hitachi.coe.fullstack.model.ExcelErrorDetail;
import com.hitachi.coe.fullstack.model.SurveyDataModel;
import com.hitachi.coe.fullstack.model.common.ErrorModel;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.repository.SurveyDataRepository;
import com.hitachi.coe.fullstack.service.SurveyDataService;
import com.hitachi.coe.fullstack.transformation.SurveyDataTransformer;
import com.hitachi.coe.fullstack.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SurveyDataServiceImpl implements SurveyDataService {

	@Autowired
	private SurveyDataRepository surveyDataRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private SurveyDataTransformer surveyDataTransformer;

	@Override
	public ImportResponse importSurveyDataExcel(ExcelResponseModel listOfSurveyData) throws IOException {
		ImportResponse importResponse = new ImportResponse();
		ExcelConfigModel surveyDataJsonConfigModel = JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/SurveyDataReadConfig.json"));
		HashMap<Integer, Object> dataList = listOfSurveyData.getData();
		List<ExcelErrorDetail> errorList = listOfSurveyData.getErrorDetails();
		List<ErrorModel> errorModelList = ErrorModel.importErrorDetails(errorList);
		HashMap<Integer, SurveyData> validateData = new HashMap<>();

		for (Map.Entry<Integer, Object> entry : dataList.entrySet()) {
			SurveyDataModel surveyDataModel = (SurveyDataModel) entry.getValue();
			SurveyData surveyData = surveyDataTransformer.toEntity(surveyDataModel);
			List<Employee> employeeList = employeeRepository.findByEmailOrLdapOrHccId
					(surveyData.getEmail(), surveyData.getEmployeeId(), null);
			surveyData.setName(surveyDataJsonConfigModel.getFileName());
			if (employeeList.size() < 2 && !ObjectUtils.isEmpty(employeeList)) {
				List<SurveyData> surveyDataDuplicateList = surveyDataRepository
					.getSurveyDataByEmployeeIdOrEmail(employeeList.get(0).getLdap(), employeeList.get(0).getEmail());
				if (!ObjectUtils.isEmpty(surveyDataDuplicateList)) {
					surveyDataRepository.deleteAll(surveyDataDuplicateList);
				}
				validateData.put(employeeList.get(0).getId(), surveyData);
			} else {
				errorModelList.add(ErrorModel.errorSurveyDataDetails(entry.getKey()));
			}
		}

		List<SurveyData> surveyDataList = new ArrayList<>(validateData.values());
		surveyDataRepository.saveAll(surveyDataList);
		ErrorModel.sortModelsByLine(errorModelList);
		importResponse.setTotalRows(listOfSurveyData.getTotalRows());
		importResponse.setErrorRows(errorModelList.size());
		importResponse.setSuccessRows(surveyDataList.size());
		importResponse.setErrorList(errorModelList);
		return importResponse;
	}

}
