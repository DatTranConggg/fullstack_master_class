package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EvaluationLevel;
import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.model.base.AuditModel;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectFeedbackModel extends AuditModel<Integer> {

    private static final long serialVersionUID = -8587100380956718537L;

    @Length(max = 250)
    @NotNull(message = "Feedback cannot be null")
    @NotBlank(message = "Feedback cannot be blank")
    private String feedback;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Past(message = "Enter valid date.")
    @NotNull
    private Date feedbackDate;

    @Length(max = 50)
    @NotNull(message = "Project Manager cannot be null")
    @NotBlank(message = "Project Manager cannot be blank")
    private String projectManager;

    @NotNull(message = "Employee cannot be null")
    private EmployeeModel employee;

    @NotNull(message = "Evaluation Level cannot be null")
    private EvaluationLevelModel evaluationLevel;

    @NotNull(message = "Project cannot be null")
    private ProjectModel project;
}
