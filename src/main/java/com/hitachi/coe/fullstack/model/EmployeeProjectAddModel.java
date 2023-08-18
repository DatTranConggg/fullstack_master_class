package com.hitachi.coe.fullstack.model;


import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeProjectAddModel  {

    @NotNull(message = "Employee ID is required")
    @Min(value = 1, message = "Employee ID must be greater than or equal to 1")
    private Integer employeeId;

    @NotBlank(message = "Employee Type is required")
    private String employeeType;

    @NotBlank(message = "Start date is required")
    private String startDate;

    @NotBlank(message = "End date is required")
    private String endDate;

    @NotEmpty(message = "Project is required")
    private List<Integer> projects;

    public static void validateEmployeeProject(final Project project, final Page<IEmployeeProjectModel> isAssignedProject, final Timestamp startDate, final Timestamp endDate){

        if (startDate.after(endDate)){
            throw new CoEException(ErrorConstant.CODE_INVALID_START_DATE_END_DATE, ErrorConstant.MESSAGE_INVALID_START_DATE_END_DATE);
        }

        if(endDate.before(new Date(System.currentTimeMillis()))){
            throw new CoEException(ErrorConstant.CODE_INVALID_END_DATE_GREATER_THAN_CURRENT, ErrorConstant.MESSAGE_INVALID_END_DATE_GREATER_THAN_CURRENT);
        }

        if (startDate.before(project.getStartDate()) || startDate.after(project.getEndDate()) && (!startDate.equals(project.getStartDate()))){
            throw new CoEException(ErrorConstant.CODE_INVALID_START_DATE, ErrorConstant.MESSAGE_INVALID_START_DATE);
        }

        if (endDate.before(project.getStartDate()) || endDate.after(project.getEndDate()) && (!endDate.equals(project.getEndDate()))){
            throw new CoEException(ErrorConstant.CODE_INVALID_END_DATE, ErrorConstant.MESSAGE_INVALID_END_DATE);
        }

        if (!isAssignedProject.isEmpty()) {
            throw new CoEException(ErrorConstant.CODE_EMPLOYEE_PROJECT_ALREADY_ASSIGN, ErrorConstant.MESSAGE_EMPLOYEE_PROJECT_ALREADY_ASSIGN);
        }

    }
}
