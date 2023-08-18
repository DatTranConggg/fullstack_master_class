package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.entity.EmployeeEvaluation;
import com.hitachi.coe.fullstack.entity.ProjectFeedback;
import com.hitachi.coe.fullstack.model.base.AuditModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeLevelModel extends AuditModel<Integer> {

    private String code;

    private String description;

    private List<EmployeeEvaluation> employeeEvaluation;

    private List<ProjectFeedback> projectFeedback;
}
