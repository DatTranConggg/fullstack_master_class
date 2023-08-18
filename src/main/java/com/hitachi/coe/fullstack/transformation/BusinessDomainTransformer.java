package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.BusinessDomain;
import com.hitachi.coe.fullstack.model.BusinessDomainModel;
import com.hitachi.coe.fullstack.model.ProjectModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class BusinessDomainTransformer is convert entity to DTO.
 * 
 * @author thovo
 *
 */
@Component
@RequiredArgsConstructor
public class BusinessDomainTransformer extends AbstractCopyPropertiesTransformer<BusinessDomain, BusinessDomainModel>
		implements EntityToModelTransformer<BusinessDomain, BusinessDomainModel, Integer> {
		/**
		 * Transformer array entities to array DTO.
		 * 
		 * @param entities {@link List} of {@link BusinessDomain}
		 * @return {@link List} of {@link BusinessDomainModel}
		 */

		public List<BusinessDomainModel> applyList(List<BusinessDomain> entities) {
			if (null == entities || entities.isEmpty()) {
				return Collections.emptyList();
			}

			return entities.stream().map(this::apply)
					.collect(Collectors.toList());
		}
		  @Override
			public BusinessDomainModel apply(BusinessDomain businessDomain) {
			    List<ProjectModel> listProject =  businessDomain.getProjects().stream().map(project -> {
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
				    return model;
			    }).collect(Collectors.toList());
				BusinessDomainModel model = new BusinessDomainModel();
				model.setId(businessDomain.getId());
				model.setCode(businessDomain.getCode());
				model.setCreated(businessDomain.getCreated());
				model.setCreatedBy(businessDomain.getCreatedBy());
				model.setDescription(businessDomain.getDescription());
				model.setName(businessDomain.getName());
				model.setPracticeId(businessDomain.getPractice().getId());
				model.setProjects(listProject);
				return model;
			}
}
