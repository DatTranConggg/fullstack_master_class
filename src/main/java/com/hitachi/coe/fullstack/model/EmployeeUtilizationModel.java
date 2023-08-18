package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.model.base.AuditModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Represents an EmployeeUtilization model
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class EmployeeUtilizationModel extends AuditModel<Integer> {

	private static final long serialVersionUID = -392837135186039875L;

	private Integer no;

	private String name;

	private String level;

	private String location;

	private String bu;

	private String coe;

	private Double billable;

	private Integer loggedHours;

	private String oracleStaffedProject;

	private String timeSheet;

	private String email;

	private Integer availableHours;

	private Integer billableHours;

	private Integer ptoOracle;

	private Integer hccId;

	private Date startDate;
}
