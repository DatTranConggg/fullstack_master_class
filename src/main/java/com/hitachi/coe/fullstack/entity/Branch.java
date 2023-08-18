package com.hitachi.coe.fullstack.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hitachi.coe.fullstack.entity.base.BaseAudit;
import com.hitachi.coe.fullstack.entity.base.BaseReadonlyEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the branch database table.
 */
@Getter
@Setter
@Entity
@Table(name = "branch")
public class Branch extends BaseAudit implements BaseReadonlyEntity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String code;

  private String name;

  // bi-directional many-to-one association to Location
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id")
  @JsonIgnore
  private Location location;

  // bi-directional many-to-one association to Employee
  @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
  private List<Employee> employees;

}
