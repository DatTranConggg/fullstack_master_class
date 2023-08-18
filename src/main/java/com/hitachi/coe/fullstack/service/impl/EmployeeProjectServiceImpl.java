package com.hitachi.coe.fullstack.service.impl;

import com.hitachi.coe.fullstack.constant.CommonConstant;
import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeProject;
import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.enums.EmployeeType;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.model.EmployeeProjectAddModel;
import com.hitachi.coe.fullstack.model.EmployeeProjectModel;
import com.hitachi.coe.fullstack.model.IEmployeeProjectDetails;
import com.hitachi.coe.fullstack.model.IEmployeeProjectModel;
import com.hitachi.coe.fullstack.repository.EmployeeProjectRepository;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.repository.ProjectRepository;
import com.hitachi.coe.fullstack.service.EmployeeProjectService;
import com.hitachi.coe.fullstack.transformation.EmployeeModelTransformer;
import com.hitachi.coe.fullstack.transformation.EmployeeProjectTransformer;
import com.hitachi.coe.fullstack.transformation.ProjectModelTransformer;
import com.hitachi.coe.fullstack.util.CommonUtils;
import com.hitachi.coe.fullstack.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeProjectServiceImpl implements EmployeeProjectService {

    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeProjectTransformer employeeProjectTransformer;

    @Autowired
    private ProjectModelTransformer projectModelTransformer;

    @Autowired
    private EmployeeModelTransformer employeeModelTransformer;

    @Override
    public List<EmployeeProjectModel> assignEmployeeProjects(final EmployeeProjectAddModel employeeProjectAddModel) {

        Integer type = EmployeeType.getEmployeeTypeByName(employeeProjectAddModel.getEmployeeType());
        List<EmployeeProject> anotherProjects;
        final List<EmployeeProjectModel> employeeProjectModelList = new ArrayList<>();
        final List<EmployeeProject> employeeProjectList = new ArrayList<>();
        final Timestamp startDate = CommonUtils.convertStringToTimestamp(employeeProjectAddModel.getStartDate());
        final Timestamp endDate = CommonUtils.convertStringToTimestamp(employeeProjectAddModel.getEndDate());
        final Optional<Employee> employee = employeeRepository.findById(employeeProjectAddModel.getEmployeeId());

        if (employee.isEmpty()) {
            throw new CoEException(ErrorConstant.CODE_EMPLOYEE_DO_NOT_EXIST, ErrorConstant.MESSAGE_EMPLOYEE_DO_NOT_EXIST);
        }

        for (Integer projectId : employeeProjectAddModel.getProjects()) {
            final Optional<Project> validProject = projectRepository.findById(projectId);

            if (validProject.isEmpty()) {
                throw new CoEException(ErrorConstant.CODE_PROJECT_NOT_FOUND, ErrorConstant.MESSAGE_PROJECT_NOT_FOUND);
            }

            final Page<IEmployeeProjectModel> isAssigned = employeeProjectRepository.searchEmployeesProjectWithStatus(projectId, employee.get().getHccId(), "assign", PageRequest.of(0, 10, Sort.by("name")));
            EmployeeProjectAddModel.validateEmployeeProject(validProject.get(), isAssigned, Objects.requireNonNull(startDate), endDate);
            //Find all project that employee is assigned to except projectId
            anotherProjects = employeeProjectRepository.findEmployeeAssignedExceptProjectById(employeeProjectAddModel.getEmployeeId(), projectId);

            if (!ObjectUtils.isEmpty(anotherProjects)) {
                anotherProjects.forEach( anotherProject -> employeeProjectModelList.add(employeeProjectTransformer.apply(anotherProject)));
            }

            EmployeeProject employeeProject = new EmployeeProject();
            employeeProject.setProject(validProject.get());
            employeeProject.setEmployee(employee.get());
            employeeProject.setStartDate(startDate);
            employeeProject.setEndDate(endDate);
            employeeProject.setEmployeeType(type);
            employeeProject.setCreatedBy(CommonConstant.CREATED_BY_ADMINISTRATOR);
            employeeProject.setCreated(new Date(System.currentTimeMillis()));
            employeeProjectList.add(employeeProject);
        }

        employeeProjectRepository.saveAll(employeeProjectList);
        return employeeProjectModelList;

    }

    @Override
    public EmployeeProjectModel releaseEmployeeProject(final Integer employeeId, final Integer projectId) {
        final Optional<Employee> employee = employeeRepository.findById(employeeId);
        final Optional<Project> project = projectRepository.findById(projectId);
        EmployeeProject releaseEmployeeProject = new EmployeeProject();
        final Date currentDate = new Date(System.currentTimeMillis());

        if (employee.isEmpty()) {
            throw new CoEException(ErrorConstant.CODE_EMPLOYEE_DO_NOT_EXIST, ErrorConstant.MESSAGE_EMPLOYEE_DO_NOT_EXIST);
        }

        if (project.isEmpty()) {
            throw new CoEException(ErrorConstant.CODE_PROJECT_NOT_FOUND, ErrorConstant.MESSAGE_PROJECT_NOT_FOUND);
        }

        final Page<IEmployeeProjectModel> isAssignedProject = employeeProjectRepository.searchEmployeesProjectWithStatus(projectId, employee.get().getHccId(), "assign", PageRequest.of(0, 10, Sort.by("name")));

        //Assign
        if (!isAssignedProject.isEmpty()) {
            final Optional<EmployeeProject> employeeProject = employeeProjectRepository.findById(isAssignedProject.getContent().get(0).getEmployeeProjectId());
            if (employeeProject.isPresent()){
                releaseEmployeeProject = employeeProject.get();
                if(currentDate.before(releaseEmployeeProject.getStartDate())){
                    throw new CoEException(ErrorConstant.CODE_EMPLOYEE_PROJECT_NOT_START, ErrorConstant.MESSAGE_EMPLOYEE_PROJECT_NOT_START);
                }
                releaseEmployeeProject.setEndDate(currentDate);
                employeeProjectRepository.save(releaseEmployeeProject);
            }
        } else {
            throw new CoEException(ErrorConstant.CODE_EMPLOYEE_PROJECT_ALREADY_RELEASE, ErrorConstant.MESSAGE_EMPLOYEE_PROJECT_ALREADY_RELEASE);
        }

        return employeeProjectTransformer.apply(releaseEmployeeProject);
    }

    @Override
    public Page<IEmployeeProjectModel> searchEmployeesProjectWithStatus(Integer projectId, String keyword, String status, Integer no, Integer limit, String sortBy, Boolean desc){
        String searchKeyword = StringUtil.removeUnknownSymbol(keyword);
        Sort sort = Sort.by(sortBy);

        if (desc != null) {
            sort = sort.descending();
        }

        return employeeProjectRepository.searchEmployeesProjectWithStatus(projectId,searchKeyword, status, PageRequest.of(no, limit, sort));
    }

    @Override
    public List<IEmployeeProjectDetails> getEmployeeProjectDetailsByEmployeeHccId(String hccId) {
        // Check if hccId is null or empty
        if (ObjectUtils.isEmpty(hccId)) {
            throw new CoEException(ErrorConstant.CODE_HCC_ID_REQUIRED, ErrorConstant.MESSAGE_HCC_ID_REQUIRED);
        }
		// Check if employee exist by hccId
		if (ObjectUtils.isEmpty(employeeRepository.findByHccId(hccId))) {
			throw new CoEException(ErrorConstant.CODE_EMPLOYEE_NOT_FOUND, ErrorConstant.MESSAGE_EMPLOYEE_NOT_FOUND);
		}
        return employeeProjectRepository.getEmployeeProjectDetailsByEmployeeHccId(hccId);
    }
}
