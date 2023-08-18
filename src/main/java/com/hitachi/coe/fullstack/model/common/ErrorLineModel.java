package com.hitachi.coe.fullstack.model.common;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.Employee;

import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.entity.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The class ErrorLineModel is used to define errors in each field with a message.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorLineModel {
    private String field;
    private String message;
    /**
     * Imports error details from error list to list of ErrorModel model.
     * @author tquangpham
     * @param employee the employee object.
     * @param branches the list of branches name.
     * @param levels the list of levels name.
     * @param branch the String branch name.
     * @param businessUnit the String business Unit name.
     * @param level the String level name.
     * @return the list of ErrorLineModel.
     */
    public static List<ErrorLineModel> importEmployeeErrorDetails(Employee employee, List<String> branches, List<String> businessUnits, List<String> levels, String branch, String businessUnit, String level){

        List<ErrorLineModel> errorLineModelList = new ArrayList<>();
        if (ObjectUtils.isEmpty(employee)){
            errorLineModelList.add(new ErrorLineModel("Employee", ErrorConstant.MESSAGE_EMPLOYEE_DO_NOT_EXIST));
        }
        if (!branches.contains(branch)){
            errorLineModelList.add(new ErrorLineModel("Branch",ErrorConstant.MESSAGE_BRANCH_DO_NOT_EXIST));
        }
        if (!businessUnits.contains(businessUnit)){
            errorLineModelList.add(new ErrorLineModel("Business Unit",ErrorConstant.MESSAGE_BUSINESS_UNIT_DO_NOT_EXIST));
        }
        if (!levels.contains(level)){
            errorLineModelList.add(new ErrorLineModel("Level",  ErrorConstant.MESSAGE_LEVEL_DO_NOT_EXIST));
        }
        return errorLineModelList;
    }
    public static List<ErrorLineModel> importInsertEmployeeErrorDetails(Employee employee, List<String> branches, List<String> businessUnits, List<String> levels, List<String> teams, String branch, String businessUnit, String level, String team){

        List<ErrorLineModel> errorLineModelList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(employee)){
            errorLineModelList.add(new ErrorLineModel("Employee", ErrorConstant.MESSAGE_HCCID_LDAP_EMAIL_ALEARDY_EXIST));
        }
        if (!branches.contains(branch)){
            errorLineModelList.add(new ErrorLineModel("Branch",ErrorConstant.MESSAGE_BRANCH_DO_NOT_EXIST));
        }
        if (!businessUnits.contains(businessUnit)){
            errorLineModelList.add(new ErrorLineModel("Business Unit", ErrorConstant.MESSAGE_BUSINESS_UNIT_DO_NOT_EXIST));
        }
        if (!levels.contains(level)){
            errorLineModelList.add(new ErrorLineModel("Level",  ErrorConstant.MESSAGE_LEVEL_DO_NOT_EXIST));
        }
        if (!teams.contains(team)){
            errorLineModelList.add(new ErrorLineModel("Coe Core Team",  ErrorConstant.MESSAGE_TEAM_DO_NOT_EXIST));
        }
        return errorLineModelList;
    }
    public static List<ErrorLineModel> importProjectErrorDetails(Project project, String status , List<String> businessDomains, List<String> skillSets, String businessDomain, String skillSet){

        List<ErrorLineModel> errorLineModelList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(project)){
            errorLineModelList.add(new ErrorLineModel("Project", ErrorConstant.MESSAGE_CODE_PROJECT_EXIST));
        }
        if (!businessDomains.contains(businessDomain)){
            errorLineModelList.add(new ErrorLineModel("Business Domain",ErrorConstant.MESSAGE_BUSINESS_DOMAIN_DO_NOT_EXIST));
        }
        String[] tech = skillSet.split(",");

        for (String word : tech) {
            if (!skillSets.contains(word.trim())){
                errorLineModelList.add(new ErrorLineModel("Skill ",word + " " + ErrorConstant.MESSAGE_SKILL_SET_DO_NOT_EXIST.toLowerCase() + "in system"));
            }
        }
        if (status!=null){
            errorLineModelList.add(new ErrorLineModel("Project status",status+ ": " + ErrorConstant.MESSAGE_PROJECT_STATUS_EXIST));
        }
        return errorLineModelList;
    }
}
