package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.model.base.AuditModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CoeUtilizationModel extends AuditModel<Integer>{

	private static final long serialVersionUID = -8384013958808105025L;

    private String duration;
    
    private Date startDate;
    
    private Date endDate;
    
    private Double totalUtilization;
    
    private List<EmployeeUtilizationModel> employeeUtilizations;
}
