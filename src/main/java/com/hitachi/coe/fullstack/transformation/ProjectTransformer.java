package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.model.ProjectModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectTransformer extends AbstractCopyPropertiesTransformer<Project, ProjectModel> 
implements EntityToModelTransformer<Project, ProjectModel, Integer> {

	@Autowired
	ProjectTypeTransformer projectTypeTransformer;

	@Autowired
	BusinessDomainTransformer businessDomainTransformer;

	public List<ProjectModel> applyList(List<Project> entities) {
		if (null == entities || entities.isEmpty()) {
			return Collections.emptyList();
		}

		return entities.stream().map(this::apply)
				.collect(Collectors.toList());
	}

	
	 @Override
		public ProjectModel apply(Project project) {
		 ProjectModel model = new ProjectModel();
		 model.setId(project.getId());
		 model.setCode(project.getCode());
		 model.setCustomerName(project.getCustomerName());
		 model.setDescription(project.getDescription());
		 model.setEndDate(project.getEndDate());
		 model.setName(project.getName());
		 model.setProjectManager(project.getProjectManager());
		 model.setStartDate(project.getStartDate());
		 model.setStatus(project.getStatus().getValue());
		 model.setCreatedBy(project.getCreatedBy());

		 model.setProjectType(projectTypeTransformer.apply(project.getProjectType()));
		 if(!ObjectUtils.isEmpty(project.getBusinessDomain())){
			 model.setBusinessDomain(businessDomainTransformer.apply(project.getBusinessDomain()));
		 }
		 return model;
	 
	 }
	 
}
