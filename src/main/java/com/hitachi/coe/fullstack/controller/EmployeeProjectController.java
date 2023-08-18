package com.hitachi.coe.fullstack.controller;

import com.hitachi.coe.fullstack.model.EmployeeProjectAddModel;
import com.hitachi.coe.fullstack.model.EmployeeProjectModel;
import com.hitachi.coe.fullstack.model.IEmployeeProjectDetails;
import com.hitachi.coe.fullstack.model.IEmployeeProjectModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.EmployeeProjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employee-project")
public class EmployeeProjectController {

    @Autowired
    private EmployeeProjectService employeeProjectService;

    /**
     * This api will assign employee to projects
     *
     * @author tquangpham
     * @param employeeProjectAddModel The model containing the employee ID and projects to add.
     * @return BaseResponse containing the list of added employee projects.
     */
    @PostMapping("/assign")
    @ApiOperation("This api assign employee to projects")
    public BaseResponse<List<EmployeeProjectModel>> assignEmployeeProject(@Valid @RequestBody EmployeeProjectAddModel employeeProjectAddModel) {
        return new BaseResponse<>(HttpStatus.OK.value(), null, employeeProjectService.assignEmployeeProjects(employeeProjectAddModel));
    }

    @GetMapping("/details/hccid/{hccId}")
    @ApiOperation("This api return employee project detail information by employee's hccId")
    public BaseResponse<List<IEmployeeProjectDetails>> getEmployeeProjectDetails(@PathVariable(name = "hccId", required = true) String hccId){
        return BaseResponse.success(employeeProjectService.getEmployeeProjectDetailsByEmployeeHccId(hccId));
    }

    /**
     * This api will release employee from project
     *
     * @author tquangpham
     * @param employeeId The ID of the employee project to delete.
     * @return ResponseEntity containing the optional employee project (if found) with the corresponding HTTP status.
     */
    @PostMapping("/release")
    @ApiOperation("This api will release employee from project")
    public BaseResponse<EmployeeProjectModel> releaseProjectEmployee(@RequestParam Integer employeeId, @RequestParam Integer projectId) {
        return new BaseResponse<>(HttpStatus.OK.value(), null, employeeProjectService.releaseEmployeeProject(employeeId, projectId));
    }

    /**
     * This API search employee in project with status.
     *
     * @author tquangpham
     * @param keyword The keyword for searching.
     * @param projectId The project id of Project.
     * @param status The status of Project.
     * @param no     The page number to retrieve.
     * @param limit   The maximum employee of results to return per page.
     * @param sortBy  The field to sort the results by.
     * @param desc    The field to sort desc or asc the results.
     * @return BaseResponse containing the list of employees.
     */
    @GetMapping("/search")
    @ApiOperation("This api will return list of Employees in Project matches with one or many conditions ")
    public BaseResponse<Page<IEmployeeProjectModel>> searchEmployeesProjectWithStatus(
            @RequestParam(required = false) String keyword, @RequestParam Integer projectId, @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") Integer no, @RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(required = false) Boolean desc) {
        return new BaseResponse<>(HttpStatus.OK.value(), null, employeeProjectService.searchEmployeesProjectWithStatus(projectId, keyword, status, no, limit, sortBy,desc));

    }
}