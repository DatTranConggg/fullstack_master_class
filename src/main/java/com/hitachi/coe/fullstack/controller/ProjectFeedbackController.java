package com.hitachi.coe.fullstack.controller;

import com.hitachi.coe.fullstack.constant.Constants;
import com.hitachi.coe.fullstack.model.ProjectFeedbackModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.ProjectFeedbackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/project-feedback")
public class ProjectFeedbackController {
    @Autowired
    ProjectFeedbackService projectFeedbackService;

    @PostMapping("add")
    @ApiOperation("This api add project feedback will return Project Feedback Model")
    public BaseResponse<Object> createProjectFeedback(@Validated @RequestBody ProjectFeedbackModel projectFeedbackModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
        {
            return BaseResponse.badRequest( bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Map<String, String> response = new HashMap<>();
        response.put(Constants.ID, String.valueOf(projectFeedbackService.add(projectFeedbackModel)));

        return BaseResponse.success(response);
    }
}
