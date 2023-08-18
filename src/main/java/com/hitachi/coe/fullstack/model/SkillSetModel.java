package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.model.base.AuditModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SkillSetModel extends AuditModel<Integer> {

	private static final long serialVersionUID = 2312816443004108959L;

	private String code;

	private String description;

	private String name;
}
