package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.*;
import com.hitachi.coe.fullstack.model.EmployeeProjectModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EmployeeProjectTransformer extends AbstractCopyPropertiesTransformer<EmployeeProject, EmployeeProjectModel>
        implements EntityToModelTransformer<EmployeeProject, EmployeeProjectModel, Integer> {

    @Autowired
    private ProjectTransformer projectTransformer;

    @Autowired
    private EmployeeTransformer employeeTransformer;

    @Override
    public EmployeeProjectModel apply(EmployeeProject employeeProject) {
        EmployeeProjectModel employeeProjectModel = new EmployeeProjectModel();
        employeeProjectModel.setId(employeeProject.getId());
        employeeProjectModel.setProject(projectTransformer.apply(employeeProject.getProject()));
        employeeProjectModel.setEmployee(employeeTransformer.apply(employeeProject.getEmployee()));
        employeeProjectModel.setEmployeeType(employeeProjectModel.getEmployeeType());
        employeeProjectModel.setStartDate(employeeProject.getStartDate());
        employeeProjectModel.setEndDate(employeeProject.getEndDate());

        return employeeProjectModel;
    }
}
