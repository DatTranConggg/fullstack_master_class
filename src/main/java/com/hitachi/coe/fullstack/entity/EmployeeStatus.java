package com.hitachi.coe.fullstack.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hitachi.coe.fullstack.entity.base.BaseReadonlyEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the employee_status database table.
 *
 */
@Getter
@Setter
@Entity
@Table(name = "employee_status")
public class EmployeeStatus  implements BaseReadonlyEntity<Integer>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "status_date")
	private Date statusDate;

	@Column(name = "status")
	private Integer status;

	// bi-directional many-to-one association to Employee
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	private Employee employee;
}
