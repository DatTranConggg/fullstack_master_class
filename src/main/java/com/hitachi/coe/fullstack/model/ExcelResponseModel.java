package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExcelResponseModel {
    //Key: row index, value: row object

    HashMap<Integer,Object> data;

    Integer totalRows;

    List<ExcelErrorDetail> errorDetails;

    Status status;
}
