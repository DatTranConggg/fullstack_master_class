package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.model.EmployeeUtilizationModelResponse;
import com.hitachi.coe.fullstack.model.ExcelResponseModel;
import com.hitachi.coe.fullstack.model.IEmployeeUTModel;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationDetail;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationDetailResponse;
import com.hitachi.coe.fullstack.model.IEmployeeUtilizationFree;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import com.hitachi.coe.fullstack.model.ImportResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
/**
 * This class is a service to import employee_utilization table.
 */

public interface EmployeeUtilizationService {
	/**
	 * Imports employee utilization data from an Excel file.
	 *
	 * @param listOfEmployeeUT the list of Excel Response Model that contains data to import into the EmployeeUtilization table.
	 * @param fileType is the type of file input.
	 * @return the ImportResponse object.
	 * @author tquangpham
	 */
	ImportResponse importEmployeeUtilization(ExcelResponseModel listOfEmployeeUT, String fileType, InputStream stream, String strDate) throws IOException;

	/**
	 * Retrieves a PieChartModel representing utilization statistics for a specific branch, COE core team, or COE within a specified date range.
	 *
	 * @author lta
	 * @param branchId The ID of the branch to retrieve utilization statistics for, or null to retrieve statistics for all branches.
	 * @param coeCoreTeamId The ID of the COE core team to retrieve utilization statistics for, or null to retrieve statistics for all COE core teams.
	 * @param coeId The ID of the COE to retrieve utilization statistics for, or null to retrieve statistics for all COEs.
	 * @param fromDateStr The start date of the date range to retrieve utilization statistics for, in the format "yyyy-MM-dd".
	 * @param toDateStr The end date of the date range to retrieve utilization statistics for, in the format "yyyy-MM-dd".
	 * @return A PieChartModel representing utilization statistics for the specified branch, COE core team, or COE within the specified date range.
	 * @throws CoeException If the fromDateStr or toDateStr parameters are not in the format "yyyy-MM-dd".
	 */
	List<IPieChartModel> getUtilizationPieChart(Integer branchId, Integer coeId, Integer coeCoreTeamId,String fromDateStr, String toDateStr);

	/**
	 * Get list of employees in utilization.
	 *
	 * @param keyword       The keyword to search for.
	 * @param billable      The billable to search for.
	 * @param branchId      The branch id of Branch.
	 * @param coeCoreTeamId The coeCoreTeam id of CoE Core Team.
	 * @param coeTeamId     The Center Of Excellence id of Center Of Excellence.
	 * @param no            The page number to retrieve.
	 * @param limit         The maximum employee of results to return per page.
	 * @param sortBy        The field to sort the results by.
	 * @param desc          The field to sort desc or asc the results.
	 * @return A Page of IEmployeeUTModel interface that match the selected conditions.
	 * @author tquangpham
	 */
	Page<IEmployeeUTModel> searchEmployeeUtilization(String keyword, String billable, Integer branchId, Integer coeCoreTeamId,
													 Integer coeTeamId, Integer no, Integer limit, String sortBy, Boolean desc);


	/**
	 * Get project information by employee utilization by employee HCC ID.
	 *
	 * @param hccId is employee hccId from employee table
	 * @return a list that contains average numbers for statistics and a list of employee utilization by employee hccId
	 * @author tquangpham
	 */

	EmployeeUtilizationModelResponse getEmployeeUtilizationDetailByHccId(String hccId);

	/**
	 * Get project information by employee utilization by employee utilization id.
	 *
	 * @param empUtId is employee utilization id from employee utilization table
	 * @return a list of employee utilization details by employee utilization id
	 * @author tquangpham
	 */

	IEmployeeUtilizationDetailResponse getProjectInformationByEmployeeUtilizationId(Integer empUtId);
	/**
	 * Get list of employees that have no utilization.
	 *
	 * @param billableThreshold the Billable Threshold to retrieve list of employees that have no utilization.
	 * @return the list of employees utilization.
	 * @author tquangpham
	 */
	List<IEmployeeUtilizationFree> getListEmployeeUtilizationWithNoUT(Double billableThreshold);

	/**
	 * Get an employee utilization detail by employee utilization id.
	 *
	 * @param id The ID of employee utilization table
	 * @return an employee utilization detail.
	 * @author tquangpham
	 */
	IEmployeeUtilizationDetail getEmployeeUtilizationDetailByEmployeeUtilizationId(Integer id);



}
