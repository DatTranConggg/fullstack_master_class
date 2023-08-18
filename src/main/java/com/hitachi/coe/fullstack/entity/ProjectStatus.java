package com.hitachi.coe.fullstack.entity;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.exceptions.CoEException;

public enum ProjectStatus {
    ON_HOLD(0),
    POC(1),
    OPEN(2),
    CLOSE(3),
    REOPEN(4),
    ONGOING(5),
    CANCEL(6),
    ERROR(-1);

    private final int value;

    ProjectStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ProjectStatus valueOf(Integer integer) {
        for (ProjectStatus type : values()) {
            if (type.getValue() == integer) {
                return type;
            }
        }
        throw new CoEException(ErrorConstant.PROJECT_STATUS_NOT_EXIST, ErrorConstant.MESSAGE_STATUS_PROJECT_NOT_EXIST);
    }

    /**
     * Get Project Status based on the provided conditions.
     *
     * @param str stand for string name of project status
     * @author tquangpham
     * @return the project status that match the string name
     */
    public static ProjectStatus nameOf(String str) {
        for (ProjectStatus type : values()) {
            if (type.name().equalsIgnoreCase(str)) {
                return type;
            }
        }
        throw new CoEException(ErrorConstant.PROJECT_STATUS_NOT_EXIST, ErrorConstant.MESSAGE_STATUS_PROJECT_NOT_EXIST);
    }
}

