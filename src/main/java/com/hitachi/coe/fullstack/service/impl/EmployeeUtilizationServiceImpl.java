package com.hitachi.coe.fullstack.service.impl;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.CoeUtilization;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeUtilization;
import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.model.EmployeeUtilizationModel;
import com.hitachi.coe.fullstack.model.EmployeeUtilizationModelResponse;
import com.hitachi.coe.fullstack.model.ExcelConfigModel;
import com.hitachi.coe.fullstack.model.ExcelErrorDetail;
import com.hitachi.coe.fullstack.model.ExcelResponseModel;
import com.hitachi.coe.fullstack.model.IEmployeeUTModel;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationDetail;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationDetailResponse;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationFree;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationModel;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import com.hitachi.coe.fullstack.model.ImportResponse;
import com.hitachi.coe.fullstack.model.common.ErrorLineModel;
import com.hitachi.coe.fullstack.model.common.ErrorModel;
import com.hitachi.coe.fullstack.repository.CoeUtilizationRepository;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.repository.EmployeeUtilizationRepository;
import com.hitachi.coe.fullstack.repository.ProjectRepository;
import com.hitachi.coe.fullstack.service.CoeUtilizationService;
import com.hitachi.coe.fullstack.service.EmployeeUtilizationService;
import com.hitachi.coe.fullstack.transformation.EmployeeUtilizationTransformer;
import com.hitachi.coe.fullstack.util.CsvUtils;
import com.hitachi.coe.fullstack.util.DateFormatUtils;
import com.hitachi.coe.fullstack.util.ExcelUtils;
import com.hitachi.coe.fullstack.util.JsonUtils;
import com.hitachi.coe.fullstack.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class EmployeeUtilizationServiceImpl implements EmployeeUtilizationService {

	@Autowired
	private EmployeeUtilizationTransformer employeeUtilizationtransformer;

	@Autowired
	private EmployeeUtilizationRepository employeeUtilizationRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private CoeUtilizationRepository coeUtilizationRepository;
	
	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private CoeUtilizationService coeUtilizationService;

	@Override
	@Transactional(rollbackFor = {CoEException.class, Exception.class})
	public ImportResponse importEmployeeUtilization(ExcelResponseModel listOfEmployeeUT, String fileType, InputStream stream, String strDate) throws IOException {

		final ExcelConfigModel employeeUtilizationJsonConfigModel = JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/EmployeeUtilizationReadConfig.json"));
		final Integer row = employeeUtilizationJsonConfigModel.getCellRow();
		final Integer column = employeeUtilizationJsonConfigModel.getCellColumn();
		final String formatDate = employeeUtilizationJsonConfigModel.getCellInputFormat();
		final String sheetName = employeeUtilizationJsonConfigModel.getStyle().getSheetName();
		String duration;

		if (row == null || column == null || ObjectUtils.isEmpty(formatDate) || ObjectUtils.isEmpty(sheetName)) {
			throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_MISSING_ATTRIBUTE_JSON_CONFIG);
		}

		final ImportResponse importResponse = new ImportResponse();
		final List<EmployeeUtilization> employeeUtilizationList = new ArrayList<>();
		final HashMap<Integer, Object> dataList = listOfEmployeeUT.getData();
		final List<EmployeeUtilizationModel> employeeUtilizationModels = dataList.values().stream().map(EmployeeUtilizationModel.class::cast).collect(Collectors.toList());
		final List<ExcelErrorDetail> errorList = listOfEmployeeUT.getErrorDetails();
		int totalRows = listOfEmployeeUT.getTotalRows();

		if (fileType.equals("excel")) {
			try {
				duration = ExcelUtils.getSpecificCellStringValue(stream, row, column, sheetName);
			} catch (CoEException cex) {
				duration = "";
			}
			errorList.remove(errorList.size() - 1);
			totalRows--;
		} else {
			duration = CsvUtils.getSpecificCellStringValue(stream, row, column);
		}

		int sumOfBillableHours = employeeUtilizationModels.stream()
				.mapToInt(EmployeeUtilizationModel::getBillableHours)
				.sum();
		int sumOfAvailableHours = employeeUtilizationModels.stream()
				.mapToInt(EmployeeUtilizationModel::getAvailableHours)
				.sum();

		final Double totalUtilization = Math.round(((double) sumOfBillableHours / sumOfAvailableHours) * 1000.0) / 10.0;
		final CoeUtilization coeUtilization = coeUtilizationService.saveCoEUtilizationFromDuration(formatDate, duration.trim(), strDate, totalUtilization);
		final List<ErrorModel> errorModelList = ErrorModel.importErrorDetails(errorList);

		for (Map.Entry<Integer, Object> entry : dataList.entrySet()) {
			final EmployeeUtilizationModel employeeUtilizationModel = (EmployeeUtilizationModel) entry.getValue();
			final Employee employee = employeeRepository.findByHccId(String.valueOf(employeeUtilizationModel.getHccId()));
			final EmployeeUtilization employeeUtilization = employeeUtilizationtransformer.toEntity(employeeUtilizationModel,
					employee, coeUtilization);
			final Project project = projectRepository.findByCode(employeeUtilizationModel.getOracleStaffedProject());
			final List<ErrorLineModel> errorLineModels = new ArrayList<>();

			if (ObjectUtils.isEmpty(employee)) {
				errorLineModels.add(new ErrorLineModel("ID", ErrorConstant.MESSAGE_HCC_ID_DO_NOT_EXIST));

			}

			if (ObjectUtils.isEmpty(project)) {
				errorLineModels.add(new ErrorLineModel("Oracle staffed Project", "Oracle staffed Project do not exist - " + employeeUtilizationModel.getOracleStaffedProject()));
			}

			if (!errorLineModels.isEmpty()){
				errorModelList.add(new ErrorModel(entry.getKey(), errorLineModels));
			}
			employeeUtilizationList.add(employeeUtilization);
		}
		if (!errorModelList.isEmpty()) {
			ErrorModel.sortModelsByLine(errorModelList);
			importResponse.setTotalRows(totalRows);
			importResponse.setErrorRows(errorModelList.size());
			importResponse.setSuccessRows(0);
			importResponse.setErrorList(errorModelList);
			return importResponse;
		}

		coeUtilizationRepository.save(coeUtilization);
		employeeUtilizationRepository.saveAll(employeeUtilizationList);
		importResponse.setTotalRows(totalRows);
		importResponse.setErrorRows(errorList.size());
		importResponse.setSuccessRows(dataList.size());
		importResponse.setErrorList(errorModelList);
		return importResponse;
	}

	@Override
	public List<IPieChartModel> getUtilizationPieChart(Integer branchId, Integer coeId, Integer coeCoreTeamId,
			String fromDateStr, String toDateStr) {
		if (coeId == null && (coeCoreTeamId != null)) {
			throw new CoEException(ErrorConstant.CODE_DATA_IS_EMPTY, ErrorConstant.MESSAGE_DATA_IS_EMPTY);

		}
		Timestamp fromDate = DateFormatUtils.convertTimestampFromString(fromDateStr);
		Timestamp toDate = DateFormatUtils.convertTimestampFromString(toDateStr);

		return employeeUtilizationRepository.getUtilizationPieChart(branchId, coeId, coeCoreTeamId, fromDate, toDate);
	}

	@Override
	public Page<IEmployeeUTModel> searchEmployeeUtilization(String keyword, String billable, Integer branchId, Integer coeCoreTeamId,
															Integer coeTeamId, Integer no, Integer limit, String sortBy, Boolean desc) {
		if (coeTeamId == null && (coeCoreTeamId != null)) {
			throw new CoEException(ErrorConstant.CODE_DATA_IS_EMPTY, ErrorConstant.MESSAGE_DATA_IS_EMPTY);
		}

		final String searchKw = StringUtil.removeUnknownSymbol(keyword);
		List<IEmployeeUTModel> employeeUtilizationList = employeeUtilizationRepository.searchEmployeeUtilization(searchKw, branchId, coeCoreTeamId, coeTeamId);

		if (employeeUtilizationList.isEmpty()) {
			return new PageImpl<>(Collections.emptyList());
		}

		final Date maxDateCreated = employeeUtilizationList.stream().map(IEmployeeUTModel::getCreatedDate).max(Date::compareTo).orElseThrow(RuntimeException::new);

		Sort sort = Sort.by(sortBy);

		if (desc != null) {
			sort = sort.descending();
		}

		final Pageable pageable = PageRequest.of(no, limit, sort);

		try {
			final double value = Double.parseDouble(billable);
			employeeUtilizationList = employeeUtilizationList.stream().filter(empUt ->
							empUt.getCreatedDate().equals(maxDateCreated)
									&& (empUt.getBillable() >= value - 5
									&& empUt.getBillable() <= value + 10)).collect(Collectors.toList());

		} catch (NumberFormatException | NullPointerException e) {
			employeeUtilizationList = employeeUtilizationList.stream().filter(empUt ->
					empUt.getCreatedDate().equals(maxDateCreated)).collect(Collectors.toList());
		}

		int start = (int) pageable.getOffset();
		int end = Math.min((start + PageRequest.of(no, limit, sort).getPageSize()), employeeUtilizationList.size());

		return new PageImpl<>(employeeUtilizationList.subList(start, end), pageable, employeeUtilizationList.size());
	}

	@Override
	public EmployeeUtilizationModelResponse getEmployeeUtilizationDetailByHccId(String hccId) {

		List<IEmployeeUtilizationModel> iEmployeeUtilizationModels = employeeUtilizationRepository.getEmployeeUtilizationDetailByHccId(hccId);

		if (iEmployeeUtilizationModels.isEmpty()){
			return new EmployeeUtilizationModelResponse();
		}

		EmployeeUtilizationModelResponse employeeUtilizationModelResponse = new EmployeeUtilizationModelResponse();

		double avgAvailableHours = iEmployeeUtilizationModels.stream()
				.mapToDouble(IEmployeeUtilizationModel::getAvailableHours)
				.average()
				.orElse(0.0);
		double avgBillableHours = iEmployeeUtilizationModels.stream()
				.mapToDouble(IEmployeeUtilizationModel::getBillableHours)
				.average()
				.orElse(0.0);
		double avgPtoOracle = iEmployeeUtilizationModels.stream()
				.mapToDouble(IEmployeeUtilizationModel::getPtoOracle)
				.average()
				.orElse(0.0);
		double avgLoggedHours = iEmployeeUtilizationModels.stream()
				.mapToDouble(IEmployeeUtilizationModel::getLoggedHours)
				.average()
				.orElse(0.0);
		double avgBillable = iEmployeeUtilizationModels.stream()
				.mapToDouble(IEmployeeUtilizationModel::getBillable)
				.average()
				.orElse(0.0);

		employeeUtilizationModelResponse.setAvgBillable(Math.round((avgBillable * 10.0)) / 10.0);
		employeeUtilizationModelResponse.setAvgBillableHours(Math.round((avgBillableHours * 10.0)) / 10.0);
		employeeUtilizationModelResponse.setAvgAvailableHours(Math.round((avgAvailableHours * 10.0)) / 10.0);
		employeeUtilizationModelResponse.setAvgLoggedHours(Math.round((avgLoggedHours * 10.0)) / 10.0);
		employeeUtilizationModelResponse.setAvgPtoOracle(Math.round((avgPtoOracle * 10.0)) / 10.0);
		employeeUtilizationModelResponse.setEmployeeUtilizationModels(iEmployeeUtilizationModels);

		return employeeUtilizationModelResponse;
	}

	@Override
	public IEmployeeUtilizationDetailResponse getProjectInformationByEmployeeUtilizationId(Integer empUtId) {

		IEmployeeUtilizationDetailResponse result = employeeUtilizationRepository.getProjectInformationByEmployeeUtilizationId(empUtId);

		if(ObjectUtils.isEmpty(result)) throw new CoEException(ErrorConstant.CODE_EMPLOYEE_UTILIZATION_ID_NOT_FOUND, ErrorConstant.MESSAGE_EMPLOYEE_UTILIZATION_ID_NOT_FOUND);

		return result;
	}


	@Override
	public List<IEmployeeUtilizationFree> getListEmployeeUtilizationWithNoUT(Double billableThreshold) {

		if (billableThreshold == null) {
			throw new CoEException(ErrorConstant.CODE_BILLABLE_IS_NULL, ErrorConstant.MESSAGE_BILLABLE_IS_NULL);
		}
		return employeeUtilizationRepository.getEmployeesUtilizationNoUT(billableThreshold);
	}

	@Override
	public IEmployeeUtilizationDetail getEmployeeUtilizationDetailByEmployeeUtilizationId(Integer id){

		IEmployeeUtilizationDetail result = employeeUtilizationRepository.getEmployeeUtilizationDetailById(id);

		if(ObjectUtils.isEmpty(result)) throw new CoEException(ErrorConstant.CODE_EMPLOYEE_UTILIZATION_ID_NOT_FOUND, ErrorConstant.MESSAGE_EMPLOYEE_UTILIZATION_ID_NOT_FOUND);

		return result;
	}
}
