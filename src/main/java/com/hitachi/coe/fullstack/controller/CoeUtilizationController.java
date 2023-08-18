package com.hitachi.coe.fullstack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.coe.fullstack.model.CoeUtilizationModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.CoeUtilizationService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/coe-utilization")
public class CoeUtilizationController {
	@Autowired
	private CoeUtilizationService coeUtilizationService;
	
	/**
	 * @return list of CoE Utilization
	 * @author PhanNguyen
	 */
	@GetMapping("/get-all")
	@ApiOperation("This api will return list of coe utilization ")
	public BaseResponse<List<CoeUtilizationModel>> getListCoeUtilization() {
		return BaseResponse.success(coeUtilizationService.getAllCoeUtilization());
	}
}
