package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.EmployeeProject;
import com.hitachi.coe.fullstack.model.IEmployeeProjectDetails;
import com.hitachi.coe.fullstack.model.IEmployeeProjectModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Integer> {

    /**
     * Filters employees in project with status.
     *
     * @param keyword       The keyword to search for.
     *
     * @param projectId     The project id of Project.
     *
     * @param status        The status including assign or release
     * @author tquangpham
     * @return A list of employees that match the selected conditions.
     */
    @Query(value = "SELECT ep.id AS employeeProjectId, e.id AS employeeId, ep.project_id AS projectId, e.hcc_id AS hccId, e.name AS name, e.email AS email, ep.employee_type AS employeeType,  ep.start_date AS startDate, ep.end_date AS endDate, bu.manager AS pmName "
            + "FROM employee_project ep "
            + "JOIN employee e ON e.id = ep.employee_id "
            + "JOIN business_unit bu ON bu.id = e.business_unit_id "
            + "WHERE (:keyword is null OR e.hcc_id LIKE CONCAT('%', :keyword, '%') OR e.email LIKE CONCAT('%', :keyword, '%') OR e.name LIKE CONCAT('%', :keyword, '%')) "
            + "AND ep.project_id = :projectId "
            + "AND (:status is null OR (ep.end_date > now() AND :status = 'assign') OR (ep.end_date < now() AND :status = 'release'))",
            nativeQuery = true)
    Page<IEmployeeProjectModel> searchEmployeesProjectWithStatus(@Param("projectId") Integer projectId, @Param("keyword") String keyword, @Param("status") String status, Pageable page);

    /**
     * Filters projects that an employee is assigning except projects with projectId.
     *
     * @param employeeId    The employee id of Employee.
     * @param projectId     The project id of Project.
     * @author tquangpham
     * @return A list of projects that match the selected conditions.
     */
    @Query("select ep from EmployeeProject ep where ep.employee.id = :employeeId and ep.project.id != :projectId and ep.endDate > now() ")
    List<EmployeeProject> findEmployeeAssignedExceptProjectById(Integer employeeId, Integer projectId);

    /**
     * This method is used to find the project details by a given employee's hccId
     * 
     * @param hccId the employee's hccId to retrieve the project details from
     * @return the project details information 
     * @author tminhto
     * @see IEmployeeProjectDetails
     */
    @Query(value = "SELECT  " +
            "    ep.employee_type AS employeeType, " +
            "    ep.start_date AS startDate, " +
            "    ep.end_date AS endDate, " +
            "    pro.code AS projectCode, " +
            "    pro.name AS projectName, " +
            "    pro.project_manager AS projectManager, " +
            "    pro.status AS projectStatus, " +
            "    pt.name AS projectTypeName, " +
            "    CASE " +
            "       WHEN now() <= ep.end_date THEN 'assign' " +
            "       ELSE 'release' " +
            "    END AS assignStatus " +
            "FROM  " +
            "    public.employee_project ep " +
            "    INNER JOIN public.project pro ON pro.id = ep.project_id " +
            "    INNER JOIN public.project_type pt ON pro.project_type_id = pt.id " +
            "    INNER JOIN public.employee emp ON emp.id = ep.employee_id " +
            "WHERE  " +
            "    emp.hcc_id = :hccId", nativeQuery = true)
    List<IEmployeeProjectDetails> getEmployeeProjectDetailsByEmployeeHccId(@Param("hccId") String hccId);
}
