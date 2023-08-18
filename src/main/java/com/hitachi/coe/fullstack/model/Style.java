package com.hitachi.coe.fullstack.model;


import lombok.Getter;
import lombok.Setter;

/**
 * this POJO is useds for mapping "style" field in jsonConfig.json
 */
@Getter
@Setter
public class Style {

    String sheetName;

    StyleExcelModel body;

    StyleExcelModel header;

}
