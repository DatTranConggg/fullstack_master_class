package com.hitachi.coe.fullstack.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeUtilizationModelResponse {

    Double avgAvailableHours;

    Double avgBillableHours;

    Double avgPtoOracle;

    Double avgLoggedHours;

    Double avgBillable;

    List<IEmployeeUtilizationModel> employeeUtilizationModels;
}
