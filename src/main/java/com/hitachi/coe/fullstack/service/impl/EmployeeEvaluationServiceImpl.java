package com.hitachi.coe.fullstack.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.model.IEmployeeEvaluationDetails;
import com.hitachi.coe.fullstack.repository.EmployeeEvaluationRepository;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.service.EmployeeEvaluationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeEvaluationServiceImpl implements EmployeeEvaluationService {

    private final EmployeeEvaluationRepository employeeEvaluationRepository;

    private final EmployeeRepository employeeRepository;

    @Override
    public List<IEmployeeEvaluationDetails> getEmployeeEvaluationDetailsByEmployeeHccId(String hccId) {
        // Check if hccId is null or empty
        if (ObjectUtils.isEmpty(hccId)) {
            throw new CoEException(ErrorConstant.CODE_HCC_ID_REQUIRED, ErrorConstant.MESSAGE_HCC_ID_REQUIRED);
        }
		// Check if employee exist by hccId
		if (ObjectUtils.isEmpty(employeeRepository.findByHccId(hccId))) {
			throw new CoEException(ErrorConstant.CODE_EMPLOYEE_NOT_FOUND, ErrorConstant.MESSAGE_EMPLOYEE_NOT_FOUND);
		}
        return employeeEvaluationRepository.getEmployeeEvaluationDetailsByEmployeeHccId(hccId);
    }
}
