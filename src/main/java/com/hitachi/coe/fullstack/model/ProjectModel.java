package com.hitachi.coe.fullstack.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hitachi.coe.fullstack.model.base.AuditModel;
import com.hitachi.coe.fullstack.util.DateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjectModel extends AuditModel<Integer>{
    private static final long serialVersionUID = 6934142214562581385L;

    private String code;

    @Length(max = 50)
    @NotNull(message = "Customer name cannot be null")
    @NotBlank(message = "Customer name cannot be blank")
    private String customerName;

    @Length(max = 250)
    private String description;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = DateTimeSerializer.class)
    private Date endDate;

    @Length(max = 50)
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Length(max = 50)
    @NotNull(message = "Project manager cannot be null")
    @NotBlank(message = "Project manager cannot be blank")
    private String projectManager;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = DateTimeSerializer.class)
    private Date startDate;

    @NotNull(message = "Status cannot be null")
    private Integer status;

    @NotNull(message = "Business domain cannot be null")
    private BusinessDomainModel businessDomain;

    @NotNull(message = "Project Type cannot be null")
    private ProjectTypeModel projectType;

    private List<SkillSetModel> skillSets;
}
