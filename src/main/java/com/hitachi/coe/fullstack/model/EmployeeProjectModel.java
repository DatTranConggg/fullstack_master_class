package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.enums.EmployeeType;
import com.hitachi.coe.fullstack.model.base.BaseReadonlyModel;
import lombok.*;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProjectModel implements BaseReadonlyModel<Integer> {

    private Integer id;

    @NotNull(message = "Project cannot be null")
    private ProjectModel project;

    @NotNull(message = "Employee cannot be null")
    private EmployeeModel employee;

    @NotNull(message = "Utilization cannot be null")
    private Integer utilization;

    @NotNull(message = "Employee Type cannot be null")
    private EmployeeType employeeType;

    @NotNull(message = "Start Date cannot be null")
    private Date startDate;

    private Date endDate;

}
