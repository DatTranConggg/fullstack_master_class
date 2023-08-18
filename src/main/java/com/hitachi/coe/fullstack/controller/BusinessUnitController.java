package com.hitachi.coe.fullstack.controller;

import com.hitachi.coe.fullstack.constant.Constants;
import com.hitachi.coe.fullstack.model.BusinessUnitModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.BusinessUnitService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create BusinessUnit
 *
 * @author dcongnguyen
 */
@RestController
@RequestMapping("/api/v1/")
public class BusinessUnitController {
    @Autowired
    BusinessUnitService businessUnitService;

    @PostMapping("business-unit/create")
    @ApiOperation("This api add business unit will business unit id")
    public ResponseEntity<Object> createBusinessUnit(@Validated @RequestBody BusinessUnitModel businessUnitModel) {

        Map<String, String> respone = new HashMap<>();
        respone.put(Constants.ID, String.valueOf(businessUnitService.add(businessUnitModel)));

        return new ResponseEntity<>(respone, HttpStatus.OK);
    }

    @PutMapping("business-unit/update")
    @ApiOperation("This api update business-unit will return status and businessUnit id")
    public ResponseEntity<Object> updateBusinessUnit(@Validated @RequestBody BusinessUnitModel businessUnitModel) {

        Map<String, String> response = new HashMap<>();
        response.put(Constants.ID, String.valueOf(businessUnitService.updateBusinessUnit(businessUnitModel)));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @author lam
     * @return All business unit in the database
     */
    @GetMapping("business-unit")
    public  BaseResponse<List<BusinessUnitModel>> getAllBusinessUnit(){
        return new BaseResponse<>(HttpStatus.OK.value(), "List of Business unit",businessUnitService.findAll());
    }
}
