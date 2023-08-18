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
public class LevelModel extends AuditModel<Integer> {

    private static final long serialVersionUID = 4741724320109840927L;

    @Length(max = 50)
    @NotNull
    private String code;

    private String description;

    @Length(max = 100)
    private String name;

}