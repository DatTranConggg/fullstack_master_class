package com.hitachi.coe.fullstack.model.common;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.model.ExcelErrorDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Comparator;

/**
 * The class ErrorModel is used to define errors in each field with a message on each line.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorModel {
    private int line;
    private List<ErrorLineModel> errorLineList;

    /**
     * Imports error details from error list to list of ErrorModel model.
     * @author tquangpham
     * @param errorList the error list.
     * @return the object containing the list of ErrorModel object.
     */
    public static List<ErrorModel> importErrorDetails(List<ExcelErrorDetail> errorList) {
        List<ErrorModel> errorModelList = new ArrayList<>();
        for (ExcelErrorDetail excelErrorDetail : errorList){
            List<ErrorLineModel> errorLineModels= new ArrayList<>();

            HashMap<String,String> errorMap = excelErrorDetail.getDetails();

            for (Map.Entry<String, String> entry : errorMap.entrySet()) {
                errorLineModels.add(new ErrorLineModel(entry.getKey(), entry.getValue()));
            }
            errorModelList.add(new ErrorModel(excelErrorDetail.getRowIndex(), errorLineModels));
        }
        return errorModelList;
    }
    /**
     * Create an error detail model when missing email and the employee ID.
     * @author tquangpham
     * @param line The line number in Excel file.
     * @return return Error Model object.
     */
    public static ErrorModel errorSurveyDataDetails (Integer line){
        ErrorLineModel errorEmail = new ErrorLineModel("Email", ErrorConstant.MESSAGE_EMAIL_DO_NOT_EXIST);
        ErrorLineModel errorEmployeeId = new ErrorLineModel("Your Employee ID:", ErrorConstant.MESSAGE_EMPLOYEE_DO_NOT_EXIST);
        return new ErrorModel(line, Arrays.asList(errorEmail, errorEmployeeId));
    }
    /**
     * Sort list of ErrorModel by line
     * @author tquangpham
     * @param models The list of ErrorMode.
     */
    public static void sortModelsByLine(List<ErrorModel> models) {
        models.sort(Comparator.comparingInt(ErrorModel::getLine));
    }

}
