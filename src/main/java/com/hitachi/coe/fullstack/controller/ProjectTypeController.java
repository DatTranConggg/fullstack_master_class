package com.hitachi.coe.fullstack.controller;

import com.hitachi.coe.fullstack.model.PracticeModel;
import com.hitachi.coe.fullstack.model.ProjectTypeModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.ProjectTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project-type")
public class ProjectTypeController {

    @Autowired
    private ProjectTypeService projectTypeService;

    @GetMapping("get-all-project-type")
    @ApiOperation("This api will return list project type ")
    public BaseResponse<List<ProjectTypeModel>> getListPractices() {
        return BaseResponse.success(projectTypeService.getAllProjectType());
    }

}
