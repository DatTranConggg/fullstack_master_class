package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EvaluationLevel;
import com.hitachi.coe.fullstack.model.base.AuditModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeEvaluationModel extends AuditModel<Integer> {

    private static final long serialVersionUID = 5552240271042177259L;

    private Date evaluationDate;

    private String evaluationInfo;

    private Integer evaluationId;

    private Employee employee;

    private EvaluationLevel evaluationLevel;

}
