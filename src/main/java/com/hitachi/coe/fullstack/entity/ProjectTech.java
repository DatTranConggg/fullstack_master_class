package com.hitachi.coe.fullstack.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hitachi.coe.fullstack.entity.base.BaseAudit;
import com.hitachi.coe.fullstack.entity.base.BaseReadonlyEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the project_tech database table.
 */
@Getter
@Setter
@Entity
@Table(name = "project_tech")
public class ProjectTech extends BaseAudit implements BaseReadonlyEntity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // bi-directional many-to-one association to Project
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id")
  private Project project;

  // bi-directional many-to-one association to SkillSet
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "skill_set_id")
  private SkillSet skillSet;

}
