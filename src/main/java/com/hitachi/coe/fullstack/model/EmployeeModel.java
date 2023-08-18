package com.hitachi.coe.fullstack.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hitachi.coe.fullstack.model.base.AuditModel;
import com.hitachi.coe.fullstack.util.DateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Represents an Employee model.
 */


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeModel extends AuditModel<Integer> {

    private static final long serialVersionUID = 4741724320109840927L;

    @Length(max = 50)
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    @NotBlank
    private String email;

    @Length(max = 50)
    @NotNull(message = "hccId cannot be null")
    @NotBlank
    private String hccId;

    @Length(max = 50)
    @NotNull(message = "ldap cannot be null")
    @NotBlank
    private String ldap;

    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @JsonSerialize(using = DateTimeSerializer.class)
    private Date legalEntityHireDate;

    @Length(max = 100)
    @NotNull(message = "name cannot be null")
    @NotBlank
    private String name;

    @NotNull(message = "businessUnit cannot be null")
    private BusinessUnitModel businessUnit;

    @NotNull(message = "coeCoreTeam cannot be null")
    private CoeCoreTeamModel coeCoreTeam;

    @NotNull(message = "branch cannot be null")
    private BranchModel branch;
    
    @NotNull(message = "Skill Set cannot be null")
    private List<EmployeeSkillModel> employeeSkills;

    private Integer employeeLatestStatus;

}
