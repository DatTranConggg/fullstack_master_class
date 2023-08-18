package com.hitachi.coe.fullstack.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class ExcelConfigModel {

    HashMap<String,String> mappingRule;

    Style style;

    List<String> headers;

    String fileName;

    List<ConfigKey> configKey;

    Integer startRow;

    Integer startColumn;

    Integer cellRow;

    Integer cellColumn;

    String cellInputFormat;
}
