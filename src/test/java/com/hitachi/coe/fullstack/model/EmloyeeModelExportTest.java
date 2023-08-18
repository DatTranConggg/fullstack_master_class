//package com.hitachi.coe.fullstack.model;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.Date;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestPropertySource;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@TestPropertySource("classpath:application-data-test.properties")
//public class EmloyeeModelExportTest {
//    @Test
//    void test_getsetEmployee() {
//        String email = "test@example.com";
//        String name = "nguyentantai";
//        String hccId = "123456";
//        String ldap = "john.doe";
//        Date legalEntityHireDate = new Date();
//        String branchName = "Branch 1";
//        String coeCoreTeamName = "Core Team 1";
//        String practiceName = "Practice 1";
//
//        EmployeeModel employeeModel = EmployeeModel.builder().name("nguyentantai").hccId("HV7000").ldap("HCM")
//                .email("abc@hitachivantara.com").legalEntityHireDate(new Date()).branchName("Branch 1").coeCoreTeamName("Core Team 1").practiceName("Practice 1").build();;
//        employeeModel.setEmail(email);
//        employeeModel.setName(name);
//        employeeModel.setHccId(hccId);
//        employeeModel.setLdap(ldap);
//        employeeModel.setLegalEntityHireDate(legalEntityHireDate);
//        employeeModel.setBranchName(branchName);
//        employeeModel.setCoeCoreTeamName(coeCoreTeamName);
//        employeeModel.setPracticeName(practiceName);
//    assertThat(employeeModel.getEmail()).isEqualTo(email);
//    assertThat(employeeModel.getName()).isEqualTo(name);
//    assertThat(employeeModel.getHccId()).isEqualTo(hccId);
//    assertThat(employeeModel.getLdap()).isEqualTo(ldap);
//    assertThat(employeeModel.getLegalEntityHireDate()).isEqualTo(legalEntityHireDate);
//    assertThat(employeeModel.getBranchName()).isEqualTo(branchName);
//    assertThat(employeeModel.getCoeCoreTeamName()).isEqualTo(coeCoreTeamName);
//    assertThat(employeeModel.getPracticeName()).isEqualTo(practiceName);
//
//    }
//}
//
