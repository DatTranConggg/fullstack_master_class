package com.hitachi.coe.fullstack.service.impl;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.Branch;
import com.hitachi.coe.fullstack.entity.BusinessUnit;
import com.hitachi.coe.fullstack.entity.CoeCoreTeam;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeSkill;
import com.hitachi.coe.fullstack.entity.EmployeeStatus;
import com.hitachi.coe.fullstack.entity.Level;
import com.hitachi.coe.fullstack.entity.SkillSet;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.BarChartModel;
import com.hitachi.coe.fullstack.model.DataSetBarChart;
import com.hitachi.coe.fullstack.model.EmployeeImportModel;
import com.hitachi.coe.fullstack.model.EmployeeInsertModel;
import com.hitachi.coe.fullstack.model.EmployeeModel;
import com.hitachi.coe.fullstack.model.EmployeeSkillModel;
import com.hitachi.coe.fullstack.model.ExcelErrorDetail;
import com.hitachi.coe.fullstack.model.ExcelResponseModel;
import com.hitachi.coe.fullstack.model.IBarChartDepartmentModel;
import com.hitachi.coe.fullstack.model.IEmployeeDetails;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import com.hitachi.coe.fullstack.model.IResultOfQueryBarChart;
import com.hitachi.coe.fullstack.model.ImportResponse;
import com.hitachi.coe.fullstack.model.common.ErrorLineModel;
import com.hitachi.coe.fullstack.model.common.ErrorModel;
import com.hitachi.coe.fullstack.repository.BranchRepository;
import com.hitachi.coe.fullstack.repository.BusinessUnitRepository;
import com.hitachi.coe.fullstack.repository.CoeCoreTeamRepository;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.repository.EmployeeSkillRepository;
import com.hitachi.coe.fullstack.repository.EmployeeStatusRepository;
import com.hitachi.coe.fullstack.repository.LevelRepository;
import com.hitachi.coe.fullstack.repository.SkillSetRepository;
import com.hitachi.coe.fullstack.service.EmployeeLevelService;
import com.hitachi.coe.fullstack.service.EmployeeService;
import com.hitachi.coe.fullstack.service.EmployeeSkillService;
import com.hitachi.coe.fullstack.transformation.EmployeeModelTransformer;
import com.hitachi.coe.fullstack.transformation.EmployeeTransformer;
import com.hitachi.coe.fullstack.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hitachi.coe.fullstack.constant.Constants.YEAR_MONTH_DAY_FORMAT;

/**
 * The Class EmployeeServiceImpl write Logic for employeeService.
 *
 * @author loita
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private LevelRepository levelRepository;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeTransformer employeeTransformer;

	@Autowired
	private CoeCoreTeamRepository coeCoreTeamRepository;

	@Autowired
	private EmployeeModelTransformer employeeModelTransformer;

	@Autowired
	private EmployeeLevelService employeeLevelService;

	@Autowired
	private EmployeeStatusRepository employeeStatusRepository;

	@Autowired
	private EmployeeSkillRepository employeeSkillRepository;

	@Autowired
	private SkillSetRepository skillSetRepository;

	@Autowired
	private EmployeeSkillService employeeSkillService;
	
	@Autowired
	private BusinessUnitRepository businessUnitRepository;
	
	@Override
	public EmployeeModel deleteEmployeeById(Integer id) {
		Optional<Employee> employeeOption = employeeRepository.findById(id);
		if (!employeeOption.isPresent()) {
			throw new CoEException(ErrorConstant.CODE_EMPLOYEE_NULL, ErrorConstant.MESSAGE_EMPLOYEE_NULL);
		}
		Employee employeeRepo = employeeRepository.deleteEmployeeById(id);
		return employeeTransformer.apply(employeeRepo);
	}

	@Override
	public Page<EmployeeModel> searchEmployees(String keyword, String businessUnitName, String coeCoreTeamName,
			String branchName,Integer status, String fromDateStr, String toDateStr, Integer no, Integer limit, String sortBy,
			Boolean desc) {

		// sort by field of employee
		Sort sort = Sort.by(sortBy);
		if (desc != null) {
			sort = sort.descending();
		}
		Date fromDate = CommonUtils.convertStringToDate(fromDateStr,YEAR_MONTH_DAY_FORMAT);
		Date toDate = CommonUtils.convertStringToDate(toDateStr,YEAR_MONTH_DAY_FORMAT);
		// return page of list employees
		return employeeRepository.filterEmployees(keyword, businessUnitName, coeCoreTeamName, branchName, status,fromDate, toDate,
				PageRequest.of(no, limit, sort)).map(p -> employeeTransformer.apply(p));// convert from
		// employeeEntity to
		// employeeModel
	}

	@Override
	public ImportResponse importUpdateEmployee(ExcelResponseModel listOfEmployee) {

		ImportResponse importResponse = new ImportResponse();
		int totalSuccess = 0;
		HashMap<Integer, Object> dataList = listOfEmployee.getData();
		List<ExcelErrorDetail> errorList = listOfEmployee.getErrorDetails();
		List<ErrorModel> errorModelList = ErrorModel.importErrorDetails(errorList);
		List<Branch> branchList = branchRepository.findAll();
		List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
		List<Level> levelList = levelRepository.findAll();
		List<String> branchesName = branchList.stream().map(Branch::getName).collect(Collectors.toList());
		List<String> businessUnitsName = businessUnitList.stream().map(BusinessUnit::getName).collect(Collectors.toList());
		List<String> levelsName = levelList.stream().map(Level::getName).collect(Collectors.toList());

		for (Map.Entry<Integer, Object> entry : dataList.entrySet()) {
			EmployeeImportModel em = (EmployeeImportModel) entry.getValue();
			Employee employee = employeeRepository.findByHccIdAndLdap(em.getHccId(), em.getLdap());

			if (!ObjectUtils.isEmpty(employee) && branchesName.contains(em.getBranch())
					&& businessUnitsName.contains(em.getBusinessUnit()) && levelsName.contains(em.getLevel())) {

				if (!employee.getBranch().getName().equals(em.getBranch())) {
					employee.setBranch(branchList.get(branchesName.indexOf(em.getBranch())));
				}
				if (!employee.getBusinessUnit().getName().equals(em.getBusinessUnit())) {
					employee.setBusinessUnit(businessUnitList.get(businessUnitsName.indexOf(em.getBusinessUnit())));
				}
				totalSuccess++;
				employeeLevelService.saveEmployeeLevel(employee, levelList.get(levelsName.indexOf(em.getLevel())));
				employeeRepository.save(employee);
			} else {
				errorModelList.add(new ErrorModel(entry.getKey(), ErrorLineModel.importEmployeeErrorDetails(employee,
						branchesName, businessUnitsName, levelsName, em.getBranch(), em.getBusinessUnit(), em.getLevel())));
			}
		}
		ErrorModel.sortModelsByLine(errorModelList);
		importResponse.setTotalRows(listOfEmployee.getTotalRows());
		importResponse.setErrorRows(errorModelList.size());
		importResponse.setSuccessRows(totalSuccess);
		importResponse.setErrorList(errorModelList);
		return importResponse;
	}

	@Override
	public EmployeeModel getEmployeeById(Integer employeeId) {

		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (employee.isEmpty()) {
			throw new InvalidDataException(ErrorConstant.CODE_EMPLOYEE_NOT_FOUND,
					ErrorConstant.MESSAGE_EMPLOYEE_NOT_FOUND);
		}
		EmployeeModel employeeModel = employeeTransformer.apply(employee.get());
		employeeModel.setEmployeeLatestStatus(searchLatestStatus(employee.get()));
		return employeeModel;
	}

	@Override
	public Integer add(EmployeeModel employeeModel) {
		List<Employee> existingEmployeeList = employeeRepository.findByEmailOrLdapOrHccId(employeeModel.getEmail(),
				employeeModel.getLdap(), employeeModel.getHccId());
		if (existingEmployeeList.isEmpty()) {
			branchRepository.findById(employeeModel.getBranch().getId())
					.orElseThrow(() -> new InvalidDataException(ErrorConstant.CODE_BRANCH_NOT_NULL,
							ErrorConstant.MESSAGE_BRANCH_NOT_NULL));

			businessUnitRepository.findById(employeeModel.getBusinessUnit().getId())
					.orElseThrow(() -> new InvalidDataException(ErrorConstant.CODE_BUSINESSUNIT_NULL,
							ErrorConstant.MESSAGE_BUSINESSUNIT_NULL));

			coeCoreTeamRepository.findById(employeeModel.getCoeCoreTeam().getId())
					.orElseThrow(() -> new InvalidDataException(ErrorConstant.CODE_COE_TEAM_NOT_FOUND,
							ErrorConstant.MESSAGE_COE_TEAM_NOT_FOUND));
			Employee em = employeeModelTransformer.apply(employeeModel);
			if (null == em) {
				throw new InvalidDataException(ErrorConstant.CODE_EMPLOYEE_NULL, ErrorConstant.MESSAGE_EMPLOYEE_NULL);
			}
			EmployeeStatus employeeStatus = new EmployeeStatus();
			employeeStatus.setStatus(employeeModel.getEmployeeLatestStatus());
			em.setCreated(new Date());
			em.setEmployeeStatuses(Arrays.asList(employeeStatus));
			Employee optEmployee = employeeRepository.save(em);
			employeeModel.getEmployeeSkills().forEach(employeeSkill ->{
				employeeSkillService.addEmployeeSkill(optEmployee, employeeSkill);
			});
			employeeStatus.setEmployee(optEmployee);
			employeeStatus.setStatusDate(new Timestamp(System.currentTimeMillis()));
			employeeStatusRepository.save(employeeStatus);
			return optEmployee.getId();
		} else {
			// exists at least 1 employee
			throw new InvalidDataException(ErrorConstant.CODE_EMPLOYEE_LDAP_OR_EMAIL_OR_HCCID_EXIST,
					ErrorConstant.MESSAGE_EMPLOYEE_LDAP_OR_EMAIL_OR_HCCID_EXIST);
		}
	}

	@Override
	public Integer updateEmployee(EmployeeModel employeeModel) {

		// 1. Validate Employee.
		Optional<Employee> employeeDb = employeeRepository.findById(employeeModel.getId());
		if (employeeDb.isEmpty()) {
			throw new InvalidDataException(ErrorConstant.CODE_EMPLOYEE_NOT_FOUND,
					ErrorConstant.MESSAGE_EMPLOYEE_NOT_FOUND);
		}
		Employee existingEmployee = employeeDb.get();

		// 3. Validate Practice
		Optional<BusinessUnit> optExistedBusinessUnit = Optional.empty();
		if (!ObjectUtils.isEmpty(employeeModel.getBusinessUnit())
				&& !ObjectUtils.isEmpty(employeeModel.getBusinessUnit().getId())) {
			optExistedBusinessUnit = businessUnitRepository.findById(employeeModel.getBusinessUnit().getId());
		}
		if (optExistedBusinessUnit.isEmpty()) {
			throw new InvalidDataException(ErrorConstant.CODE_PRACTICE_PROBLEM, ErrorConstant.MESSAGE_PRACTICE_PROBLEM);
		}
		existingEmployee.setBusinessUnit(optExistedBusinessUnit.get());
		// 4. Validate CoeCoreTeam
		Optional<CoeCoreTeam> optExistedCoeTeam = Optional.empty();
		if (!ObjectUtils.isEmpty(employeeModel.getCoeCoreTeam())
				&& !ObjectUtils.isEmpty(employeeModel.getCoeCoreTeam().getId())) {
			optExistedCoeTeam = coeCoreTeamRepository.findById(employeeModel.getCoeCoreTeam().getId());
		}
		if (optExistedCoeTeam.isEmpty()) {
			throw new InvalidDataException(ErrorConstant.CODE_COECORETEAM_PROBLEM,
					ErrorConstant.MESSAGE_COECORETEAM_PROBLEM);
		}
		existingEmployee.setCoeCoreTeam(optExistedCoeTeam.get());

		// 5. Validate Branch
		Optional<Branch> optExistedBranch = Optional.empty();
		if (!ObjectUtils.isEmpty(employeeModel.getBranch())
				&& !ObjectUtils.isEmpty(employeeModel.getBranch().getId())) {
			optExistedBranch = branchRepository.findById(employeeModel.getBranch().getId());
		}
		if (optExistedBranch.isEmpty()) {
			throw new InvalidDataException(ErrorConstant.CODE_BRANCH_PROBLEM, ErrorConstant.MESSAGE_BRANCH_PROBLEM);
		}
		existingEmployee.setBranch(optExistedBranch.get());

		// 6. Validate Skill
		if (!ObjectUtils.isEmpty(employeeModel.getEmployeeSkills())) {
			List<EmployeeSkill> updatedEmployeeSkills = new ArrayList<>();
			List<EmployeeSkill> existingEmployeeSkills = existingEmployee.getEmployeeSkills();

			for (EmployeeSkillModel employeeSkillModel : employeeModel.getEmployeeSkills()) {
				if (employeeSkillModel.getId() != null) {
					Optional<SkillSet> optSkillSet = skillSetRepository.findById(employeeSkillModel.getId());
					if (optSkillSet.isEmpty()) {
						throw new InvalidDataException("404", "Skill Not Found");
					}

					// Check if the skill ID already exists for the employee
					boolean skillExists = existingEmployeeSkills.stream()
							.anyMatch(skill -> skill.getSkillSet().getId().equals(employeeSkillModel.getId()));

					if (skillExists) {
						continue; // Skip adding the skill if it already exists
					}

					EmployeeSkill employeeSkill = new EmployeeSkill();
					employeeSkill.setEmployee(existingEmployee);
					employeeSkill.setSkillSet(optSkillSet.get());
					employeeSkill.setSkillSetDate(new Timestamp(System.currentTimeMillis()));
					employeeSkill.setCreatedBy("admin");
					employeeSkill.setSkillLevel(1);
					employeeSkill.setCreated(new Date());
					updatedEmployeeSkills.add(employeeSkill);
				} else {
					throw new InvalidDataException("404", "Invalid id");
				}
			}

			// Remove skills that are not present in the updated skills list
			List<EmployeeSkill> removedSkills = new ArrayList<>(existingEmployeeSkills);

			// Remove skills that are present in the updated skills list
			removedSkills.removeIf(skill -> employeeModel.getEmployeeSkills().stream()
					.anyMatch(updatedSkill -> updatedSkill.getId().equals(skill.getSkillSet().getId())));
			employeeSkillRepository.deleteAll(removedSkills);
			employeeSkillRepository.saveAll(updatedEmployeeSkills);
			existingEmployee.setEmployeeSkills(updatedEmployeeSkills);
		}

		// 7. Update Employee Information.
		EmployeeStatus employeeStatus = new EmployeeStatus();
		employeeStatus.setEmployee(existingEmployee);
		employeeStatus.setStatusDate(new Date());
		employeeStatus.setStatus(employeeModel.getEmployeeLatestStatus());
		employeeStatusRepository.save(employeeStatus);

		existingEmployee.setLegalEntityHireDate(employeeModel.getLegalEntityHireDate());
		existingEmployee.setName(employeeModel.getName());

		return employeeRepository.save(existingEmployee).getId();
	}

	/**
	 *
	 * Get the percentage values for the skill bar chart for a specific branch,
	 * group, and team.
	 *
	 * @param branchId the ID of the branch to get the data
	 * @param groupId  the ID of the group within the branch to retrieve the data
	 * @param teamId   the ID of the team within the group to retrieve the data
	 * @param skillIds the list Id of the skill to filter the data (optional).
	 * @return a list of Object arrays representing the percentage values for the
	 *         skill bar chart
	 */
	@Override
	public BarChartModel getQuantityOfEachSkillForBarChart(Integer branchId, Integer groupId, Integer teamId,
			List<Integer> skillIds) {
		if (groupId == null && teamId != null) {
			throw new CoEException(ErrorConstant.CODE_CENTER_OF_EXCELLENCE_NULL,
					ErrorConstant.MESSAGE_CENTER_OF_EXCELLENCE_NULL);
		}

		String listId = CommonUtils.convertListIntegertoString(skillIds);
		List<IResultOfQueryBarChart> results = employeeRepository.getQuantityOfEachSkillForBarChart(branchId, groupId,
				teamId, listId);

		BarChartModel barChartModel = new BarChartModel();
		List<String> labelSkills = new ArrayList<>();
		List<DataSetBarChart> datasets = new ArrayList<>();
		List<String> labels = new ArrayList<>();

		for (IResultOfQueryBarChart result : results) {
			String skillName = result.getFieldName();
			String label = result.getLabel();

			if (!labelSkills.contains(skillName)) {
				labelSkills.add(skillName);
			}

			if (!labels.contains(label)) {
				labels.add(label);
			}
		}

		for (String label : labels) {
			DataSetBarChart dataSetBarChart = new DataSetBarChart();
			dataSetBarChart.setLabel(label);
			List<Long> data = new ArrayList<>();

			for (String skill : labelSkills) {
				boolean skillExists = false;
				for (IResultOfQueryBarChart result : results) {
					if (result.getLabel().equals(label) && result.getFieldName().equals(skill)) {
						data.add(result.getTotal());
						skillExists = true;
						break;
					}
				}

				if (!skillExists) {
					data.add(0L);
				}
			}

			dataSetBarChart.setData(data);
			datasets.add(dataSetBarChart);
		}

		barChartModel.setLabels(labelSkills);
		barChartModel.setDatasets(datasets);
		return barChartModel;
	}

	/**
	 * Retrieves the quantity of levels for employees based on the provided branch,
	 * group, and team IDs.
	 *
	 * @param branchId The ID of the branch. Pass null to retrieve data for all
	 *                 branches.
	 * @param groupId  The ID of the group. Pass null to retrieve data for all
	 *                 groups.
	 * @param teamId   The ID of the team. Pass null to retrieve data for all teams.
	 * @return The BarChartModel representing the quantity of levels for employees.
	 * @throws CoEException if the provided team ID is not null and the group ID is
	 *                      null, indicating an invalid combination.
	 */
	@Override
	public BarChartModel getQuantityOfLevelForBarChart(Integer branchId, Integer groupId, Integer teamId) {
		if (groupId == null && teamId != null) {
			throw new CoEException(ErrorConstant.CODE_CENTER_OF_EXCELLENCE_NULL,
					ErrorConstant.MESSAGE_CENTER_OF_EXCELLENCE_NULL);
		}
		List<IResultOfQueryBarChart> results = employeeRepository.getQuantityOfLevelForBarChart(branchId, groupId,
				teamId);
		BarChartModel barChartModel = new BarChartModel();
		List<String> labelLevels = new ArrayList<>();
		List<DataSetBarChart> datasets = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		for (IResultOfQueryBarChart result : results) {
			String levelName = result.getFieldName();
			String label = result.getLabel();
			if (!labelLevels.contains(levelName)) {
				labelLevels.add(levelName);
			}
			if (!labels.contains(label)) {
				labels.add(label);
			}
		}
		for (String label : labels) {
			DataSetBarChart dataSetBarChart = new DataSetBarChart();
			dataSetBarChart.setLabel(label);
			List<Long> data = new ArrayList<>();
			for (String level : labelLevels) {
				boolean levelExists = false;
				for (IResultOfQueryBarChart result : results) {
					if (result.getLabel().equals(label) && result.getFieldName().equals(level)) {
						data.add(result.getTotal());
						levelExists = true;
						break;
					}
				}
				if (!levelExists) {
					data.add(0L);
				}
			}
			dataSetBarChart.setData(data);
			datasets.add(dataSetBarChart);

		}
		barChartModel.setLabels(labelLevels);
		barChartModel.setDatasets(datasets);
		return barChartModel;
	}

	/**
	 * Retrieves the quantity of employees in each department at a certain level
	 * within an organization.
	 * 
	 * @author hchantran
	 * @param branchId (optional) The ID of the branch to filter by.
	 * @param groupId  (optional) The ID of the group to filter by.
	 * @param teamId   (optional) The ID of the team to filter by.
	 * @return A data contains the quantity of employees in each department at a
	 *         certain level.
	 */
	@Override
	public List<IBarChartDepartmentModel> getQuantityEmployeeOfLevel(Integer branchId, Integer groupId,
			Integer teamId) {
		return employeeRepository.getQuantityEmployeeOfLevel(branchId, groupId, teamId);
	}

	/**
	 * Gets data for a pie chart on the level of employees in a CoE team.
	 *
	 * @param branchId      The ID of the branch.
	 * @param coeCoreTeamId The ID of the CoE team.
	 * @param coe_id        The ID of the CoE.
	 * @return Data for a pie chart.
	 */
	@Override
	public List<IPieChartModel> getLevelPieChart(Integer branchId, Integer coeCoreTeamId, Integer coe_id) {
		List<IPieChartModel> pieChartModels = levelRepository.piechartlevel(branchId, coeCoreTeamId, coe_id);
		if(pieChartModels.isEmpty()) {
			throw new InvalidDataException(ErrorConstant.CODE_DATA_IS_EMPTY,
					ErrorConstant.MESSAGE_DATA_IS_EMPTY);
		}
		return pieChartModels;

	}

	@Override
	public ImportResponse importInsertEmployee(ExcelResponseModel listOfEmployeeInsert) {
		ImportResponse importResponse = new ImportResponse();
		int totalSuccess = 0;
		HashMap<Integer, Object> dataList = listOfEmployeeInsert.getData();
		List<ExcelErrorDetail> errorList = listOfEmployeeInsert.getErrorDetails();
		List<ErrorModel> errorModelList = ErrorModel.importErrorDetails(errorList);
		List<Branch> branchList = branchRepository.findAll();
		List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
		List<Level> levelList = levelRepository.findAll();
		List<CoeCoreTeam> teamList = coeCoreTeamRepository.findAll();
		List<String> branchesName = branchList.stream().map(Branch::getName).collect(Collectors.toList());
		List<String> businessUnitsName = businessUnitList.stream().map(BusinessUnit::getName).collect(Collectors.toList());
		List<String> levelsName = levelList.stream().map(Level::getName).collect(Collectors.toList());
		List<String> teamsName = teamList.stream().map(CoeCoreTeam::getName).collect(Collectors.toList());


		for (Map.Entry<Integer, Object> entry : dataList.entrySet()) {
			EmployeeInsertModel em = (EmployeeInsertModel) entry.getValue();
			Employee employee = employeeRepository.findByHccIdAndLdap(em.getHccId(), em.getLdap());
			EmployeeStatus employeeStatus = new EmployeeStatus();
			employeeStatus.setStatus(1);
			if (ObjectUtils.isEmpty(employee) && branchesName.contains(em.getLocation())
					&& businessUnitsName.contains(em.getBusinessUnit()) && levelsName.contains(em.getLevel()) && teamsName.contains(em.getTeam())) {
				Employee employeeInsert = new Employee();
				employeeInsert.setLdap(em.getLdap());
				employeeInsert.setHccId(em.getHccId());
				employeeInsert.setName(em.getEmployeeName());
				employeeInsert.setLegalEntityHireDate(em.getLegalEntityHireDate());
				employeeInsert.setEmail(em.getEmail());
				employeeInsert.setBusinessUnit(businessUnitList.get(businessUnitsName.indexOf(em.getBusinessUnit())));
				employeeInsert.setBranch(branchList.get(branchesName.indexOf(em.getLocation())));
				employeeInsert.setCoeCoreTeam(teamList.get(teamsName.indexOf(em.getTeam())));
				employeeInsert.setCreated(new Date());
				employeeInsert.setCreatedBy("admin");

				employeeInsert.setEmployeeStatuses(Arrays.asList(employeeStatus));
				employeeStatus.setEmployee(employeeInsert);
				employeeStatus.setStatusDate(new Timestamp(System.currentTimeMillis()));

				employeeRepository.save(employeeInsert);
				employeeStatusRepository.save(employeeStatus);
				employeeLevelService.saveEmployeeLevel(employeeInsert, levelList.get(levelsName.indexOf(em.getLevel())));

				totalSuccess++;

			} else {
				errorModelList.add(new ErrorModel(entry.getKey(), ErrorLineModel.importInsertEmployeeErrorDetails(employee,
						branchesName, businessUnitsName, levelsName, teamsName, em.getLocation(), em.getBusinessUnit(), em.getLevel(), em.getTeam())));
			}
		}
		ErrorModel.sortModelsByLine(errorModelList);
		importResponse.setTotalRows(listOfEmployeeInsert.getTotalRows());
		importResponse.setErrorRows(errorModelList.size());
		importResponse.setSuccessRows(totalSuccess);
		importResponse.setErrorList(errorModelList);
		return importResponse;
	}

	@Override
    public IEmployeeDetails getEmployeeDetailsByHccId(String hccId) {
		// Check if hccId is null or empty
		if (ObjectUtils.isEmpty(hccId)) {
			throw new CoEException(ErrorConstant.CODE_HCC_ID_REQUIRED, ErrorConstant.MESSAGE_HCC_ID_REQUIRED);
		}
		// Check if employee exist by hccId
		if (ObjectUtils.isEmpty(employeeRepository.findByHccId(hccId))) {
			throw new CoEException(ErrorConstant.CODE_EMPLOYEE_NOT_FOUND, ErrorConstant.MESSAGE_EMPLOYEE_NOT_FOUND);
		}
		return employeeRepository.getEmployeeDetailsByHccId(hccId);
    }

	/**
	 *
	 * @param employee employee entity
	 * @return latest status of this employee
	 */
	private static int searchLatestStatus(Employee employee) {
		List<EmployeeStatus> employeeStatuses = employee.getEmployeeStatuses();
		Date maxDateStatus = employeeStatuses.stream().map(EmployeeStatus::getStatusDate).max(Date::compareTo).orElseThrow(RuntimeException::new);
		EmployeeStatus employeeLatestStatus = employeeStatuses.stream()
				.filter(employeeStatus -> employeeStatus.getStatusDate().equals(maxDateStatus)).findFirst().orElse(new EmployeeStatus());
		return employeeLatestStatus.getStatus();
	}
}
