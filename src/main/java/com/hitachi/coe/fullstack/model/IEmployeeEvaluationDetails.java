package com.hitachi.coe.fullstack.model;

import java.util.Date;

public interface IEmployeeEvaluationDetails {
    Date getEvaluationDate();

    String getEvaluationInfo();

    String getEvaluatorName();

    String getEvaluatorEmail();

    String getEvaluatorHccId();

    String getEvaluationLevelCode();

    String getEvaluationLevelDescription();
}
