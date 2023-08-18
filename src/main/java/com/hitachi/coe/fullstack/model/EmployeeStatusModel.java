package com.hitachi.coe.fullstack.model;

import java.util.Date;

import com.hitachi.coe.fullstack.model.base.AuditModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents an EmployeeStatus model.
 * @author loita
 */
@Getter
@Setter
@NoArgsConstructor
public class EmployeeStatusModel extends AuditModel<Integer> {
	private static final long serialVersionUID = 4741724320109840927L;

    private Date statusDate;

	private Integer status;

	private Integer employeeId;
	
	private String employeeName;

}
