package com.hitachi.coe.fullstack.model;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hitachi.coe.fullstack.model.base.AuditModel;
import com.hitachi.coe.fullstack.util.DateTimeSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class EmployeeSkillModel extends AuditModel<Integer> {

    private static final long serialVersionUID = -7474448893325556817L;

    private Integer skillLevel;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date skillSetDate;

    private SkillSetModel skillSet;
}
