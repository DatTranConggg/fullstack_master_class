package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.model.base.AuditModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RightModel extends AuditModel<Integer> {

    private static final long serialVersionUID = 4741724320109840927L;

    @Length(max = 250)
    @NotNull
    private String code;

    @Length(max = 100)
    private String module;

    @NotNull(message = "Group right cannot be null")
    private List<GroupRightModel> groupRights;
}
