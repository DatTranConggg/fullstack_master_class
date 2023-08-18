package com.hitachi.coe.fullstack.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This POJO is used for mapping the object inside "style" field in jsonConfig.json
 */
@Getter
@Setter
public class StyleExcelModel {

    String font;

    List<Integer> backGroundColor;

    @JsonProperty("isBold")
    boolean isBold;

    @JsonProperty("isItalic")
    boolean isItalic;

    Integer fontWeight;

    Integer fontColor;

    @JsonProperty("border")
    boolean border;

    Integer borderStyle;
}
