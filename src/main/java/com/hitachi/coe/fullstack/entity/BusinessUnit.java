package com.hitachi.coe.fullstack.entity;

import java.util.List;

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
 * The persistent class for the business_unit database table.
 */
@Getter
@Setter
@Entity
@Table(name = "business_unit")
public class BusinessUnit extends BaseAudit implements BaseReadonlyEntity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String code;

  private String description;

  private String manager;

  private String name;

  // bi-directional many-to-one association to Practice
  @OneToMany(mappedBy = "businessUnit", fetch = FetchType.LAZY)
  private List<Practice> practices;

  // bi-directional many-to-one association to ProjectTech
  @OneToMany(mappedBy = "businessUnit", fetch = FetchType.LAZY)
  private List<Project> projects;

}
