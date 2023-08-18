package com.hitachi.coe.fullstack.enums;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.exceptions.CoEException;

public enum EmployeeType {
    SHADOW(1),
    OFFICE(0);

    private final int value;

    EmployeeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static int getEmployeeTypeByName(String employeeTypeString) {
        for (EmployeeType employeeType : EmployeeType.values()) {
            if (employeeType.name().equalsIgnoreCase(employeeTypeString)) {
                return employeeType.getValue();
            }
        }
        throw new CoEException(ErrorConstant.CODE_EMPLOYEE_TYPE_NOT_EXIST, ErrorConstant.MESSAGE_EMPLOYEE_TYPE_NOT_EXIST);
    }
}

