package com.hitachi.coe.fullstack.controller;

import java.util.List;

import com.hitachi.coe.fullstack.model.ProjectTypeModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.coe.fullstack.model.BusinessDomainModel;
import com.hitachi.coe.fullstack.service.BusinessDomainService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/")
public class BusinessDomainController {

    @Autowired
    private BusinessDomainService businessDomainService;

    @GetMapping("business-domain")
    @ApiOperation("This api will return list of Business Domains")
    public ResponseEntity<List<BusinessDomainModel>> getBusinessDomains() {
       
        // Get lot by date range
        return ResponseEntity.ok(
        		businessDomainService.getBusinessDomains());
    }
    /**
     * Get a list of business domains by practice ID.
     *
     * @author DatCongNguyen
     * @param practiceId The ID of the practice.
     * @return BaseResponse containing a list of BusinessDomainModel objects.
     */
    @GetMapping("business-domain-by-practice/{practiceId}")
    @ApiOperation("This api will return list of business domain by practice id")
    public BaseResponse<List<BusinessDomainModel>> getBusinessDomains(@PathVariable("practiceId") Integer practiceId) {
        return BaseResponse.success(businessDomainService.getByPractice(practiceId));
    }

    @GetMapping("get-all-business-domain")
    @ApiOperation("This api will return list of business domain")
    public BaseResponse<List<BusinessDomainModel>> getAllBusinessDomain() {
        return BaseResponse.success(businessDomainService.getAllBusinessDomain());
    }
}
