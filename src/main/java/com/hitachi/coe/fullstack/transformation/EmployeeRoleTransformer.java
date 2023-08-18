package com.hitachi.coe.fullstack.transformation;

import org.springframework.stereotype.Component;

import com.hitachi.coe.fullstack.entity.EmployeeRole;
import com.hitachi.coe.fullstack.model.EmployeeRoleModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;

/**
 * This class use to transform EmployeeRole entity to EmployeeRoleModel
 *
 * @author tminhto
 * @see EmployeeRole
 * @see EmployeeRoleModel
 */
@Component
public class EmployeeRoleTransformer extends AbstractCopyPropertiesTransformer<EmployeeRole, EmployeeRoleModel>
        implements EntityToModelTransformer<EmployeeRole, EmployeeRoleModel, Integer> {
}
