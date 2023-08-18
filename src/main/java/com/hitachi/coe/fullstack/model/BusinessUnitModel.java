package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.model.base.AuditModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BusinessUnitModel extends AuditModel<Integer> {

    /**
     *
     */
    private static final long serialVersionUID = -3974364501386994660L;

    @Length(max = 10)
    @NotNull
    private String code;

    @Length(max = 250)
    private String description;

    @Length(max = 50)
    @NotNull
    private String manager;

    @Length(max = 50)
    private String name;
}
