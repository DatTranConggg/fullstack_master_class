package com.hitachi.coe.fullstack.model;

import java.util.List;

import com.hitachi.coe.fullstack.model.common.ErrorModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents an ImportResponse model
 */
@Getter
@Setter
@NoArgsConstructor
public class ImportResponse {
    private int totalRows;
    private int successRows;
    private int errorRows;
    private List<ErrorModel> errorList;
}
