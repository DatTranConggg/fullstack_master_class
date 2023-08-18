package com.hitachi.coe.fullstack.model;

import java.util.List;

import com.hitachi.coe.fullstack.entity.Practice;
import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.model.base.AuditModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusinessDomainModel extends AuditModel<Integer> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4741724320109840927L;

	private String code;

	private String description;

	private String name;
	
	private Integer practiceId;
	
	private List<ProjectModel> projects;
    
}
