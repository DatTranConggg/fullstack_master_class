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
public class BranchModel extends AuditModel<Integer> {

    private static final long serialVersionUID = 4741724320109840927L;

    @Length(max = 50)
    @NotNull
    private String code;

    @Length(max = 250)
    private String name;

    String label;



}
