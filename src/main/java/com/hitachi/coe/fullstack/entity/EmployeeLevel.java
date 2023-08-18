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

import com.hitachi.coe.fullstack.entity.base.BaseAudit;
import com.hitachi.coe.fullstack.entity.base.BaseReadonlyEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the employee_level database table.
 */
@Getter
@Setter
@Entity
@Table(name = "employee_level")
public class EmployeeLevel extends BaseAudit implements BaseReadonlyEntity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "level_date")
  private Date levelDate;

  // bi-directional many-to-one association to Employee
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id")
  private Employee employee;

  // bi-directional many-to-one association to Level
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "level_id")
  private Level level;

}
