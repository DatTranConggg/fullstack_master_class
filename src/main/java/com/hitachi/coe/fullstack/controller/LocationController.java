package com.hitachi.coe.fullstack.controller;

import com.hitachi.coe.fullstack.constant.Constants;
import com.hitachi.coe.fullstack.model.LocationModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.LocationService;
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

@RestController
@RequestMapping("/api/v1/")
public class LocationController {
    @Autowired
    LocationService locationService;

    @PostMapping("location/create")
    @ApiOperation("This api add location will return status and location id")
    public ResponseEntity<Object> createLocation(@Validated @RequestBody LocationModel model){
        Map<String, String> response = new HashMap<>();
        response.put(Constants.ID, String.valueOf(locationService.add(model)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("location/update")
    @ApiOperation("This api add employee will return status and employee id")
    public ResponseEntity<Object> updateLocation(@RequestBody LocationModel locationModel) {

        Map<String, String> response = new HashMap<>();
        response.put(Constants.ID, String.valueOf(locationService.updateLocation(locationModel)));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @author lam
     * @return a List of all location
     */
    @GetMapping("location")
    public BaseResponse<List<LocationModel>> getAllLocations(){
        return new BaseResponse<>(HttpStatus.OK.value(),"List of Locations",locationService.getAllLocations());
    }
}
