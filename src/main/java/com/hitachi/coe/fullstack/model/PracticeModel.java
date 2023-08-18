package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.model.base.AuditModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class PracticeModel extends AuditModel<Integer> {

    private static final long serialVersionUID = -2712400587890081950L;

    @Length(max = 50)
    @NotEmpty(message = "Code cannot be empty")
    private String code;

    @Length(max = 250)
    private String description;

    @Length(max = 50)
    @NotEmpty(message = "Manager cannot be empty")
    private String manager;

    @Length(max = 250)
    private String name;

    private int businessUnitId;
    
    private String businessUnitName;
}
