package com.hitachi.coe.fullstack.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.hitachi.coe.fullstack.entity.base.BaseAudit;
import com.hitachi.coe.fullstack.entity.base.BaseReadonlyEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the employee_utilization database table.
 */
@Getter
@Setter
@Entity
@Table(name = "employee_utilization")
public class EmployeeUtilization extends BaseAudit implements BaseReadonlyEntity<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private UUID code;

	@Column(name = "available_hours")
	private Integer availableHours;

	@Column(name = "billable_hours")
	private Integer billableHours;

	@Column(name = "logged_hours")
	private Integer loggedHours;

	private Double billable;

	@Column(name = "pto_oracle")
	private Integer ptoOracle;

	// bi-directional many-to-one association to Employee
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@Column(name = "project_code", nullable = false)
	private String projectCode;

	// bi-directional many-to-one association to CoeUtilization
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coe_utilization_id")
	private CoeUtilization coeUtilization;

}
