package com.hitachi.coe.fullstack.model;

import java.util.Date;

import com.hitachi.coe.fullstack.model.base.AuditModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectSearchModel extends AuditModel<Integer>{
    private static final long serialVersionUID = 6934142214562581385L;

    private String code;

    private String customerName;

    private String description;

    private Date endDate;

    private String name;

    private String projectManager;

    private Date startDate;

    private Integer status;

    private Integer businessDomainId;

    private Integer projectTypeId;

    private String projectTypeName;

    private String businessDomainName;
}
