package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.model.base.AuditModel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class GroupRightModel extends AuditModel<Integer> {

    private static final long serialVersionUID = -7474448893325556817L;

    @NotNull
    private Integer groupId;

    @NotNull
    private Integer rightId;
}
