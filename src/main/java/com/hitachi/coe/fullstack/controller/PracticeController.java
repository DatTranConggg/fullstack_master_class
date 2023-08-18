package com.hitachi.coe.fullstack.controller;

import com.hitachi.coe.fullstack.constant.Constants;
import com.hitachi.coe.fullstack.model.PracticeModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.PracticeService;
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
@RequestMapping("/api/v1/")
public class PracticeController {
    @Autowired
    private PracticeService practiceService;

    @PutMapping("practice/update")
    @ApiOperation("This api update practice will return Practice Model")
    public ResponseEntity<Object> updatePractice(@Validated @RequestBody PracticeModel practiceModel) {

        Map<String, String> response = new HashMap<>();
        response.put(Constants.ID, String.valueOf(practiceService.updatePractice(practiceModel)));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("practice/create")
    @ApiOperation("This api update practice will return Practice Model")
    public ResponseEntity<Object> createPractice(@Validated @RequestBody PracticeModel practice) {

        Map<String, String> response = new HashMap<>();
        response.put(Constants.ID, String.valueOf(practiceService.createPractice(practice)));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
	@GetMapping("practice/get-all-practices")
	@ApiOperation("This api will return list practices ")
    public BaseResponse<List<PracticeModel>> getListPractices() {
        return BaseResponse.success(practiceService.getAllPractice());
    }
	
	@GetMapping("practice/get-practice/{id}")
	@ApiOperation("This api will return list practices ")
    public BaseResponse<PracticeModel> getPracticeById(@PathVariable Integer id) {
        return BaseResponse.success(practiceService.getPracticeById(id));
    }

    /**
     * @author lam
     * @param buId business unit id
     * @return list of practices that this business unit have
     */
    @GetMapping("practice/get-practices-by-buid/{buId}")
    public BaseResponse<List<PracticeModel>> getPracticesByBusinessUnitId(@PathVariable Integer buId){
        return new BaseResponse<>(HttpStatus.OK.value(), "List of Practices by BusinessId",practiceService.getPracticesByBusinessUnitId(buId));
    }

}
