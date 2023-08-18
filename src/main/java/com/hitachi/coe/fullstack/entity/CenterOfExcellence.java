package com.hitachi.coe.fullstack.entity;

import java.util.List;

import javax.persistence.Entity;
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
 * The persistent class for the center_of_excellence database table.
 */
@Getter
@Setter
@Entity
@Table(name = "center_of_excellence")
public class CenterOfExcellence extends BaseAudit implements BaseReadonlyEntity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String code;

  private String name;

  // bi-directional many-to-one association to CoeCoreTeam
  @OneToMany(mappedBy = "centerOfExcellence")
  private List<CoeCoreTeam> coeCoreTeams;

}
