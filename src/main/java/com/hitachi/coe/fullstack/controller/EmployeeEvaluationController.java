package com.hitachi.coe.fullstack.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.coe.fullstack.model.IEmployeeEvaluationDetails;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.impl.EmployeeEvaluationServiceImpl;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/employee-evaluation")
@RequiredArgsConstructor
public class EmployeeEvaluationController {

    private final EmployeeEvaluationServiceImpl employeeEvaluationService;

    @GetMapping("/details/hccid/{hccId}")
    @ApiOperation("This api will return details employee evaluation information")
    public BaseResponse<List<IEmployeeEvaluationDetails>> getEmployeeById(
            @PathVariable(name = "hccId", required = true) String hccId) {
        return BaseResponse.success(employeeEvaluationService.getEmployeeEvaluationDetailsByEmployeeHccId(hccId));
    }
}
