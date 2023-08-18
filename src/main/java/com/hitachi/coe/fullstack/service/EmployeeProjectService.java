package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.model.EmployeeProjectAddModel;
import com.hitachi.coe.fullstack.model.EmployeeProjectModel;
import com.hitachi.coe.fullstack.model.IEmployeeProjectDetails;
import com.hitachi.coe.fullstack.model.IEmployeeProjectModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeProjectService {

    /**
     * Assign employee projects based on the provided EmployeeProjectAddModel.
     *
     * @author tquangpham
     * @param employeeProjectAddModel The EmployeeProjectAddModel containing the employee details and projects to add.
     * @return List of EmployeeProjectModel represents the list of projects that an employee has already been assigned for.
     */
    List<EmployeeProjectModel> assignEmployeeProjects(final EmployeeProjectAddModel employeeProjectAddModel);

    /**
     * Release an employee from project.
     *
     * @author tquangpham
     * @param employeeId The ID of employee in employee project table.
     * @param projectId The ID of project in employee project table.
     * @return EmployeeProjectModel representing the release employee project if found, or null otherwise.
     */
    EmployeeProjectModel releaseEmployeeProject(final Integer employeeId, final Integer projectId);

    /**
     * Get list of employees in project with status.
     *
     * @param keyword       The keyword to search for.
     * @param projectId     The project id of Project.
     * @param status        The status including assign or release
     * @param no            The page number to retrieve.
     * @param limit         The maximum employee of results to return per page.
     * @param sortBy        The field to sort the results by.
     * @param desc          The field to sort desc or asc the results.
     * @author tquangpham
     * @return A list of IEmployeeProjectModel model that match the selected conditions.
     */
    Page<IEmployeeProjectModel> searchEmployeesProjectWithStatus(Integer projectId, String keyword, String status, Integer no, Integer limit, String sortBy, Boolean desc);

    /**
     * Retrieves the list of project history information by employee's hccId
     *
     * @param hccId the hccId to lookup for list of project history information
     * @author tminhto
     * @return list of employee project details
     * @see IEmployeeProjectDetails
     */
    List<IEmployeeProjectDetails> getEmployeeProjectDetailsByEmployeeHccId(String hccId);

}
