package com.hitachi.coe.fullstack.entity;

import javax.persistence.Column;
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
 * The persistent class for the group_right database table.
 */
@Getter
@Setter
@Entity
@Table(name = "group_right")
public class GroupRight extends BaseAudit implements BaseReadonlyEntity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "group_id")
  private Integer groupId;

  // bi-directional many-to-one association to Right
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "right_id")
  private Right right;

}
