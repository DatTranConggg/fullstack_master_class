package com.hitachi.coe.fullstack.controller;

import com.hitachi.coe.fullstack.model.EmployeeImportModel;
import com.hitachi.coe.fullstack.model.EmployeeInsertModel;
import com.hitachi.coe.fullstack.model.EmployeeUtilizationModel;
import com.hitachi.coe.fullstack.model.ImportResponse;
import com.hitachi.coe.fullstack.model.ProjectImportModel;
import com.hitachi.coe.fullstack.model.SurveyDataModel;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.EmployeeService;
import com.hitachi.coe.fullstack.service.EmployeeUtilizationService;
import com.hitachi.coe.fullstack.service.ProjectService;
import com.hitachi.coe.fullstack.service.SurveyDataService;
import com.hitachi.coe.fullstack.util.CsvUtils;
import com.hitachi.coe.fullstack.util.ExcelUtils;
import com.hitachi.coe.fullstack.util.JsonUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * The class ImportDataController is used to create an API to import data.
 */
@RestController
@RequestMapping("/api/v1/")
public class ImportDataController {
    @Autowired
    private SurveyDataService surveyDataService;
    @Autowired
    private EmployeeUtilizationService employeeUtilizationService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectService projectService;

    /**
     * This method is used to create an API to import data from an Excel or CSV file.
     *
     * @param file     The Excel or CSV file.
     * @param fileType The type of file used to import into the database.
     * @return A BaseResponse object with a status code, a message and data.
     * @author tquangpham
     */
    @PostMapping("import-data")
    @ApiOperation("This api will import data from excel file or csv file to database")
    public BaseResponse<ImportResponse> importData(@RequestParam("file") MultipartFile file, @RequestParam("fileType") String fileType, @RequestParam(required = false) String dateStr) throws IOException {

        String excelType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String csvType = "text/csv";
        if (excelType.equals(file.getContentType())) {
            switch (fileType) {
                case "survey_data":
                    return returnBaseResponse(surveyDataService.importSurveyDataExcel(
                            ExcelUtils.readFromExcel(
                                    file.getInputStream(),
                                    JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/SurveyDataReadConfig.json")),
                                    SurveyDataModel.class)));
                case "utilization":
                    return returnBaseResponse(employeeUtilizationService.importEmployeeUtilization(
                            ExcelUtils.readFromExcel(
                                    file.getInputStream(),
                                    JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/EmployeeUtilizationReadConfig.json")),
                                    EmployeeUtilizationModel.class), "excel", file.getInputStream(), dateStr));
                case "employee":
                    return returnBaseResponse(employeeService.importUpdateEmployee(
                            ExcelUtils.readFromExcel(
                                    file.getInputStream(),
                                    JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/EmployeeReadConfig.json")),
                                    EmployeeImportModel.class)));
                case "import-employee":
                    return returnBaseResponse(employeeService.importInsertEmployee(
                            ExcelUtils.readFromExcel(
                                    file.getInputStream(),
                                    JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/EmployeeImportReadConfig.json")),
                                    EmployeeInsertModel.class)));
                default:
                    return BaseResponse.badRequest("Not valid excel file.");
            }
        } else if (csvType.equals(file.getContentType())) {
            switch (fileType) {
                case "survey_data":
                    return returnBaseResponse(surveyDataService.importSurveyDataExcel(
                            CsvUtils.readCsv(
                                    file.getInputStream(),
                                    JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/SurveyDataReadConfig.json")),
                                    SurveyDataModel.class)));
                case "utilization":
                    return returnBaseResponse(employeeUtilizationService.importEmployeeUtilization(
                            CsvUtils.readCsv(
                                    file.getInputStream(),
                                    JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/EmployeeUtilizationReadConfig.json")),
                                    EmployeeUtilizationModel.class), "csv", file.getInputStream(), dateStr));
                case "employee":
                    return returnBaseResponse(employeeService.importUpdateEmployee(
                            CsvUtils.readCsv(
                                    file.getInputStream(),
                                    JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/EmployeeReadConfig.json")),
                                    EmployeeImportModel.class)));
                case "import-employee":
                    return returnBaseResponse(employeeService.importInsertEmployee(
                            CsvUtils.readCsv(
                                    file.getInputStream(),
                                    JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/EmployeeImportReadConfig.json")),
                                    EmployeeInsertModel.class)));
                default:
                    return BaseResponse.badRequest("Not valid csv file.");
            }
        } else {
            return BaseResponse.badRequest("Please select excel or csv file.");
        }
    }

    @PostMapping("import-project")
    @ApiOperation("This api will import project from excel file")
    public BaseResponse<ImportResponse> importProject(@RequestParam("file") MultipartFile file, @RequestParam("fileType") String fileType) throws IOException {

        String excelType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String csvType = "text/csv";
        if (excelType.equals(file.getContentType())) {
            switch (fileType) {
                case "project":
                    return returnBaseResponse(projectService.importProjectExcel(
                            ExcelUtils.readFromExcel(
                                    file.getInputStream(),
                                    JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/ProjectReadConfig.json")),
                                    ProjectImportModel.class)));

                case "employee-project":
                    return null;
                default:
                    return BaseResponse.badRequest("Not valid excel file.");
            }
        } else if (csvType.equals(file.getContentType())){
            switch (fileType) {
                case "project":
                    return returnBaseResponse(projectService.importProjectExcel(
                            CsvUtils.readCsv(
                                    file.getInputStream(),
                                    JsonUtils.convertJsonToPojo(JsonUtils.readFileAsString("/jsonconfig/ProjectReadConfig.json")),
                                    ProjectImportModel.class)));

                case "employee-project":
                    return null;
                default:
                    return BaseResponse.badRequest("Not valid csv file.");
            }
        }
        else {
            return BaseResponse.badRequest("Please select excel or csv file.");
        }
    }

    public BaseResponse<ImportResponse> returnBaseResponse(ImportResponse response) {

        if (response.getSuccessRows() == response.getTotalRows() && response.getErrorRows() == 0) {
            return BaseResponse.success(response);
        }
        return BaseResponse.badRequest(response);
    }
}


