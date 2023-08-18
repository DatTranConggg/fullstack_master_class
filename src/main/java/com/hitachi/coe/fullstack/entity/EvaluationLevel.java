package com.hitachi.coe.fullstack.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hitachi.coe.fullstack.entity.base.BaseAudit;
import com.hitachi.coe.fullstack.entity.base.BaseReadonlyEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the evaluation_level database table.
 */
@Getter
@Setter
@Entity
@Table(name = "evaluation_level")
public class EvaluationLevel extends BaseAudit implements BaseReadonlyEntity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "code")
  private String code;

  @Column(name = "description")
  private String description;

  // bi-directional many-to-one association to EmployeeEvaluation
  @OneToMany(mappedBy = "evaluationLevel", fetch = FetchType.LAZY)
  private List<EmployeeEvaluation> employeeEvaluations;

  // bi-directional many-to-one association to ProjectFeedback
  @OneToMany(mappedBy = "evaluationLevel", fetch = FetchType.LAZY)
  private List<ProjectFeedback> projectFeedbacks;

}
