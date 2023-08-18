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
 * The persistent class for the project_feedback database table.
 */
@Getter
@Setter
@Entity
@Table(name = "project_feedback")
public class ProjectFeedback extends BaseAudit implements BaseReadonlyEntity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String feedback;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "feedback_date")
  private Date feedbackDate;

  @Column(name = "project_manager")
  private String projectManager;

  // bi-directional many-to-one association to Employee
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id")
  private Employee employee;

  // bi-directional many-to-one association to EvaluationLevel
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "evaluation_level_id")
  private EvaluationLevel evaluationLevel;

  // bi-directional many-to-one association to Project
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id")
  private Project project;

}
