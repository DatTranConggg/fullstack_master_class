package com.hitachi.coe.fullstack.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This POJO is use for mapping fields "configKey" in jsonConfig.json
 */
@Getter
@Setter
public class ConfigKey {

    String key;

    String type;

    @JsonProperty("isRequired")
    boolean isRequired;

    String validation;

    List<Integer> backGroundColor;

    List<Integer> fontColor;

    @JsonProperty("style")
    boolean style;

    String inputFormat;

    String outputFormat;

    Integer max;

    Integer min;
}
