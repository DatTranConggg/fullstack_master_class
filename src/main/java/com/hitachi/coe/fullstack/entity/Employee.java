package com.hitachi.coe.fullstack.entity;

import java.util.Date;
import java.util.List;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hitachi.coe.fullstack.entity.base.BaseAudit;
import com.hitachi.coe.fullstack.entity.base.BaseReadonlyEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the employee database table.
 */
@Getter
@Setter
@Entity
@Table(name = "employee", schema = "public")
public class Employee extends BaseAudit implements BaseReadonlyEntity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "email")
  private String email;

  @Column(name = "hcc_id")
  private String hccId;

  @Column(name = "ldap")
  private String ldap;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "legal_entity_hire_date")
  private Date legalEntityHireDate;
  
  @Column(name = "name")
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "branch_id")
  private Branch branch;

  // bi-directional many-to-one association to CoeCoreTeam
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coe_core_team_id")
  private CoeCoreTeam coeCoreTeam;

  // bi-directional many-to-one association to Practice
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "business_unit_id")
  private BusinessUnit businessUnit;

  // bi-directional many-to-one association to EmployeeEvaluation
  @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
  private List<EmployeeEvaluation> employeeEvaluations;

  // bi-directional many-to-one association to EmployeeLevel
  @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
  private List<EmployeeLevel> employeeLevels;

  // bi-directional many-to-one association to EmployeeSkill
  @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
  private List<EmployeeSkill> employeeSkills;

  // bi-directional many-to-one association to EmployeeStatus
  @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
  private List<EmployeeStatus> employeeStatuses;

  // bi-directional many-to-one association to EmployeeUtilization
  @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
  private List<EmployeeUtilization> employeeUtilizations;

  // bi-directional many-to-one association to ProjectFeedback
  @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
  private List<ProjectFeedback> projectFeedbacks;
  
//One-to-Many relationship with EmployeeProject
 @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
 private List<EmployeeProject> employeeProjects;


}
