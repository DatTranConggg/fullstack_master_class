package com.hitachi.coe.fullstack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.coe.fullstack.model.CenterOfExcellenceModel;
import com.hitachi.coe.fullstack.service.CenterOfExcellenceService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/")
public class CenterOfExcellenceController {
	
	@Autowired
	CenterOfExcellenceService centerOfExcellenceService;
	
	/**
	 * @return list of center of excellence on database
	 * @author PhanNguyen
	 */
	@GetMapping("coe-core-team/list-coe")
	@ApiOperation("This api will return list of coe ")
	public ResponseEntity<List<CenterOfExcellenceModel>> getCenterOfExcellence() {
		return new ResponseEntity<>(centerOfExcellenceService.getAllCenterOfExcellence(), HttpStatus.OK);
	}

}
