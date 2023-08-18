package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.entity.BusinessDomain;
import com.hitachi.coe.fullstack.entity.SkillSet;
import com.hitachi.coe.fullstack.model.base.AuditModel;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectImportModel extends AuditModel<Integer> {

    private static final long serialVersionUID = -392837135186039875L;

    private String projectCode;

    private String projectName;

    private String customerName;

    private String projectStatus;

    private Date startDate;

    private Date endDate;

    private String practices;

    private String projectManager;

    private String businessDomainList;

    private String shortDescription;

    private String description;

    private String skillSetList;
}