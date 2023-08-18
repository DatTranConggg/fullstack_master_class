package com.hitachi.coe.fullstack.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This POJO is used for mapping jsonConfig.json file
 */
@Getter
@Setter
public class JsonConfigModel {

	MappingRule mappingRule;

	Style style;

	String fileName;

	List<String> excelHeaders;

	List<ConfigKey> configKey;

}
