package com.hitachi.coe.fullstack.service.impl;

import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.model.EmployeeExportModel;
import com.hitachi.coe.fullstack.model.ExcelConfigModel;
import com.hitachi.coe.fullstack.model.ExportRequest;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.service.ExportService;
import com.hitachi.coe.fullstack.transformation.EmployeeTransformer;
import com.hitachi.coe.fullstack.util.CsvUtils;
import com.hitachi.coe.fullstack.util.DateFormatUtils;
import com.hitachi.coe.fullstack.util.ExcelUtils;
import com.hitachi.coe.fullstack.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {


    private final EmployeeRepository employeeRepository;

    private final EmployeeTransformer employeeTransformer;



    @Override
    public ResponseEntity<Resource> exportExcel(ExportRequest exportRequest )  {
        Pageable pageable ;
        String headerValue;
        if ( exportRequest.isAscending() )
            pageable = PageRequest.of(exportRequest.getPageNo(), exportRequest.getItemPerPage(), Sort.by(exportRequest.getSortBy()).ascending());
        else
            pageable = PageRequest.of(exportRequest.getPageNo(), exportRequest.getItemPerPage(), Sort.by(exportRequest.getSortBy()).descending());

        Page<Employee> employees = employeeRepository.filterEmployees(exportRequest.getKeyWord(), exportRequest.getPracticeName(), exportRequest.getCoeCoreTeamName(), exportRequest.getBranchName(),1, DateFormatUtils.convertTimestampFromString(exportRequest.getFromDateStr()), DateFormatUtils.convertTimestampFromString(exportRequest.getToDateStr()), pageable);
        log.info("employeesSize: {} " , employees.getContent().size());
        employees.getContent().forEach(employee -> log.info("employee: " + employee.getLdap()));

        List<EmployeeExportModel> employeeExportModelList = employees.getContent().stream().map(employeeTransformer::convertEmployeeToExportModel).collect(Collectors.toList());

        ByteArrayResource byteArrayResource;
        try {
            String file = JsonUtils.readFileAsString("/jsonconfig/EmployeeExportConfig.json");
            ExcelConfigModel excelConfigModel = JsonUtils.convertJsonToPojo(file);
            if ( exportRequest.getTypeFile().equalsIgnoreCase( "xlsx") ) {
                headerValue = ".xlsx";
                byteArrayResource = new ByteArrayResource(ExcelUtils.writeToExcel(employeeExportModelList, excelConfigModel).readAllBytes());

            } else {
                headerValue = ".csv";
                byteArrayResource = new ByteArrayResource(CsvUtils.writeToCSV(employeeExportModelList, excelConfigModel).readAllBytes());
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + excelConfigModel.getFileName() + headerValue)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(byteArrayResource);

        } catch (IOException | CoEException e){
            log.error(e.getMessage());
        }
        return ResponseEntity.internalServerError().body(null);
    }
}