package com.hitachi.coe.fullstack.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeExcelModelTest {

    String ldap;

    String hccId;

    String employeeName;

    String level;

    String email;

    String bu;

    String location;

    Date legalEntityHireDate;


}
