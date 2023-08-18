package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.*;
import com.hitachi.coe.fullstack.model.EmployeeProjectModel;
import com.hitachi.coe.fullstack.enums.EmployeeType;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.ModelToEntityTransformer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeProjectModelTransformer extends AbstractCopyPropertiesTransformer<EmployeeProjectModel, EmployeeProject>
        implements ModelToEntityTransformer<EmployeeProjectModel, EmployeeProject, Integer> {

    public List<EmployeeProject> applyList(List<EmployeeProjectModel> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }

    /**
     * Apply the transformation from EmployeeProjectModel to EmployeeProject entity.
     *
     * This method transforms an EmployeeProjectModel object into an EmployeeProject entity object.
     *
     * @param employeeProjectModel The EmployeeProjectModel object to be transformed
     * @return                     The transformed EmployeeProject entity object
     */
    @Override
    public EmployeeProject apply(EmployeeProjectModel employeeProjectModel) {
        EmployeeProject employeeProject = super.apply(employeeProjectModel);

        if (employeeProjectModel.getProject() != null) {
            Project project = new Project();
            project.setId(employeeProjectModel.getProject().getId());
            employeeProject.setProject(project);
        }
        if (employeeProjectModel.getEmployee() != null){
            Employee employee = new Employee();
            employee.setId(employeeProjectModel.getEmployee().getId());
            employeeProject.setEmployee(employee);
        }
        return employeeProject;
    }
}
