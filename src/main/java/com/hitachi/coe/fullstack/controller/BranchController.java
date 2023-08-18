package com.hitachi.coe.fullstack.controller;

import com.hitachi.coe.fullstack.constant.Constants;
import com.hitachi.coe.fullstack.model.BranchModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.BranchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class BranchController {
    @Autowired
    private BranchService branchService;

    @PutMapping("branch/update")
    @ApiOperation("This api update branch will return Branch Model")
    public ResponseEntity<Boolean> updateBranch(@Validated @RequestBody BranchModel branchUpdate) {

        return ResponseEntity.ok(branchService.updateBranch(branchUpdate));
    }

    @PostMapping("branch/create")
    @ApiOperation("This api will return create a branch")
    public ResponseEntity<Object> createBranch(@Validated @RequestBody BranchModel branchModel) {
        Map<String, String> response = new HashMap<>();
        response.put(Constants.ID, String.valueOf(branchService.add(branchModel)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
	/**
	 * @return list of branch from the database 
	 * @author PhanNguyen
	 */
    @GetMapping("branch/get-branches")
    @ApiOperation("This api will return list of branches")
    public ResponseEntity<List<BranchModel>> getBranches() {
    	List<BranchModel> branches = branchService.getAllBranches();
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }

    /**
     * @author lam
     * @param locationId
     * @return all branches that this location have
     */
    @GetMapping("branch/get-by-location/{locationId}")
    public BaseResponse<List<BranchModel>> getBranchesByLocationId(@PathVariable int locationId){
        return new BaseResponse<>(HttpStatus.OK.value(),"List of Branches by location Id",branchService.getAllBranchesByLocationId(locationId));
    }
}
