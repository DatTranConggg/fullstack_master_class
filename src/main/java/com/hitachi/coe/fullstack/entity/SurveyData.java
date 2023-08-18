package com.hitachi.coe.fullstack.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hitachi.coe.fullstack.entity.base.BaseReadonlyEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the survey_data database table.
 *
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "survey_data")

public class SurveyData implements BaseReadonlyEntity<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "\".net_core\"")
	private Integer dotnetCore;

	@Column(name = "\".net_winform\"")
	private Integer dotnetWinform;

	@Column(name = "\".net_wpf\"")
	private Integer dotnetWpf;

	@Column(name = "active_mq")
	private Integer activeMq;

	@Column(name = "adobe_after_effects")
	private Integer adobeAfterEffects;

	@Column(name = "adobe_animate")
	private Integer adobeAnimate;

	@Column(name = "adobe_audition")
	private Integer adobeAudition;

	@Column(name = "adobe_express")
	private Integer adobeExpress;

	@Column(name = "adobe_figma")
	private Integer adobeFigma;

	@Column(name = "adobe_illustrator")
	private Integer adobeIllustrator;

	@Column(name = "adobe_indesign")
	private Integer adobeIndesign;

	@Column(name = "adobe_photoshop")
	private Integer adobePhotoshop;

	@Column(name = "adobe_premiere_pro")
	private Integer adobePremierePro;

	@Column(name = "adobe_xd")
	private Integer adobeXd;

	private Integer agile;

	private Integer angular;

	@Column(name = "angular_style_theme")
	private Integer angularStyleTheme;

	private Integer angularjs;

	private Integer ansible;

	@Column(name = "apache_ignite")
	private Integer apacheIgnite;

	@Column(name = "apache_nifi")
	private Integer apacheNifi;

	@Column(name = "apache_spark")
	private Integer apacheSpark;

	@Column(name = "api_tool")
	private Integer apiTool;

	private Integer apigee;

	@Column(name = "apple_human_interface_guidelines")
	private Integer appleHumanInterfaceGuidelines;

	private Integer arangodb;

	@Column(name = "\"asp.net\"")
	private Integer aspdotnet;

	private Integer auth0;

	private String automotive;

	private Integer aws;

	@Column(name = "aws_api_gateway")
	private Integer awsApiGateway;

	@Column(name = "aws_codecommit")
	private Integer awsCodecommit;

	@Column(name = "aws_iam")
	private Integer awsIam;

	@Column(name = "aws_timestream")
	private Integer awsTimestream;

	private Integer azure;

	@Column(name = "azure_ad")
	private Integer azureAd;

	@Column(name = "azure_api_management")
	private Integer azureApiManagement;

	@Column(name = "azure_repos")
	private Integer azureRepos;

	@Column(name = "azure_time_series_insight")
	private Integer azureTimeSeriesInsight;

	private Integer bitbucket;

	@Column(name = "bootstrap_or_material")
	private Integer bootstrapOrMaterial;

	@Column(name = "build_or_compose")
	private Integer buildOrCompose;

	private Integer c;

	@Column(name = "\"c#\"")
	private Integer cSharp;

	private Integer camunda;

	private Integer cassandra;

	private String certificate;

	@Column(name = "\"ci/cd_tools\"")
	private Integer cicdTools;

	@Column(name = "circuit_breaker")
	private Integer circuitBreaker;

	@Temporal(TemporalType.DATE)
	@Column(name = "completion_time")
	private Date completionTime;

	private Integer couchbase;

	private Integer couldstack;

	private Integer cqrs;

	@Column(name = "css_html5")
	private Integer cssHtml5;

	@Column(name = "csun_accessibility")
	private Integer csunAccessibility;

	@Column(name = "current_address")
	private String currentAddress;

	private Integer db2;

	private Integer docker;

	private Integer druid;

	private Integer elasticsearch;

	private String email;

	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "energy_utilities")
	private String energyUtilities;

	private String english;

	@Column(name = "finance_banking")
	private String financeBanking;

	private Integer flinto;

	private Integer flutter;

	private Integer framer;

	@Column(name = "full_name")
	private String fullName;

	private Integer fundamentals;

	private Integer gcp;

	private Integer github;

	private Integer gitlab;

	private Integer go;

	private Integer golang;

	@Column(name = "google_data_studio")
	private Integer googleDataStudio;

	private Integer gradle;

	private Integer haproxy;

	private String healthcare;

	@Column(name = "hitachi_next_theme")
	private Integer hitachiNextTheme;

	@Column(name = "ibm_cognos")
	private Integer ibmCognos;

	private Integer iis;

	private Integer influxdb;

	private String insurrance;

	private Integer invision;

	@Column(name = "iot_lumada")
	private String iotLumada;

	@Column(name = "iot_mqtt")
	private Integer iotMqtt;

	@Column(name = "iot_thinkworx")
	private Integer iotThinkworx;

	@Column(name = "iot_websocket")
	private Integer iotWebsocket;

	private String japanese;

	private Integer java;

	@Column(name = "java_play_framework")
	private Integer javaPlayFramework;

	@Column(name = "java_swing")
	private Integer javaSwing;

	@Column(name = "\"java-jsp\"")
	private Integer javajsp;

	@Column(name = "\"java-spring_boot\"")
	private Integer javaspringBoot;

	@Column(name = "\"java-spring_mvc\"")
	private Integer javaspringMvc;

	private Integer javafx;

	private Integer javascript;

	@Column(name = "jboss_or_wildfly")
	private Integer jbossOrWildfly;

	private Integer jenkin;

	private Integer jira;

	private Integer jquery;

	private Integer kafka;

	private Integer keycloak;

	private Integer kong;

	private Integer kotlin;

	private Integer kotlin2;

	private Integer kubernetes;

	@Column(name = "less_css")
	private Integer lessCss;

	private Integer localstack;

	private String manufacturing;

	private Integer mariadb;

	@Column(name = "material_design")
	private Integer materialDesign;

	private Integer maven;

	private Integer memcached;

	private Integer micronaut;

	@Column(name = "microsoft_biztalk")
	private Integer microsoftBiztalk;

	private Integer mongodb;

	@Column(name = "motion_design")
	private Integer motionDesign;

	@Column(name = "mui_theme")
	private Integer muiTheme;

	private Integer mulesoft;

	private Integer mysql;

	private String name;

	private Integer neo4j;

	private Integer nestjs;

	private Integer netty;

	private Integer networking;

	private Integer nginx;

	private Integer nodejs;

	private Integer nodered;

	private Integer oci;

	private Integer okta;

	private Integer openstack;

	@Column(name = "oracle_db")
	private Integer oracleDb;

	@Column(name = "oracle_form_or_report")
	private Integer oracleFormOrReport;

	@Column(name = "oracle_pl_or_sql")
	private Integer oraclePlOrSql;

	private String other;

	@Column(name = "other_skills")
	private String otherSkills;

	private Integer pcf;

	private Integer pentaho;

	private Integer performance;

	private Integer php;

	private Integer postgresql;

	@Column(name = "power_bi")
	private Integer powerBi;

	private String printing;

	private Integer prism;

	private Integer prometheus;

	private Integer python;

	@Column(name = "python_django")
	private Integer pythonDjango;

	@Column(name = "python_flask")
	private Integer pythonFlask;

	@Column(name = "qlik_sense")
	private Integer qlikSense;

	private Integer qlikview;

	private Integer quarkus;

	private Integer r;

	private Integer rabbitmq;

	private Integer react;

	@Column(name = "react_native")
	private Integer reactNative;

	@Column(name = "react_style_theme")
	private Integer reactStyleTheme;

	private Integer redis;

	@Column(name = "retail_logistic")
	private String retailLogistic;

	private Integer saga;

	private Integer saleforce;

	@Column(name = "sass_css")
	private Integer sassCss;

	private Integer security;

	@Column(name = "security_lib")
	private Integer securityLib;

	private String semiconductor;

	private Integer sencha;

	private Integer sketch;

	@Column(name = "smart_space")
	private String smartSpace;

	private Integer sonarqube;

	@Column(name = "sql_server")
	private Integer sqlServer;

	@Temporal(TemporalType.DATE)
	@Column(name = "start_time")
	private Date startTime;

	private Integer svn;

	private Integer tableau;

	private Integer terraform;

	@Column(name = "tibco_ems")
	private Integer tibcoEms;

	private Integer tomcat;

	private String transportation;

	private Integer tyk;

	private Integer typescript;

	@Column(name = "ui_automation")
	private Integer uiAutomation;

	@Column(name = "ux_pin")
	private Integer uxPin;

	@Column(name = "ux_researcher")
	private Integer uxResearcher;

	@Column(name = "ux_writing")
	private Integer uxWriting;

	@Column(name = "\"vb.net\"")
	private Integer vbdotnet;

	@Column(name = "virtual_machine")
	private Integer virtualMachine;

	@Column(name = "visual_basic_6")
	private Integer visualBasic6;

	@Column(name = "\"visual_basic.net\"")
	private Integer visualBasicdotnet;

	private Integer vuejs;

	private Integer w3c;

	private Integer waterfall;

	@Column(name = "websphere_application_server")
	private Integer websphereApplicationServer;

	@Column(name = "wireframe_balsamiq")
	private Integer wireframeBalsamiq;

	@Column(name = "wpf_style_theme")
	private Integer wpfStyleTheme;

	@Column(name = "wps_soa")
	private Integer wpsSoa;

	private Integer wso2;

}