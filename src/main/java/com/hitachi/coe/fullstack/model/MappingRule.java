package com.hitachi.coe.fullstack.model;


import lombok.Getter;
import lombok.Setter;

/**
 * This POJO is used for mapping "mappingRule" json field in jsonConfig.json
 */
@Getter
@Setter
public class MappingRule {

	private String email;

	private String ldap;

	private String legalEntityHireDate;

	private String name;

	private String hccId;

}
