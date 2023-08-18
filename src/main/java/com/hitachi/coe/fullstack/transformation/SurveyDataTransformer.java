package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.SurveyData;
import com.hitachi.coe.fullstack.model.SurveyDataModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.ModelToEntityTransformer;
import org.springframework.stereotype.Component;

/**
 * The class UserTransformer is convert entity to DTO.
 * 
 * @author tquangpham
 */

@Component
public class SurveyDataTransformer extends AbstractCopyPropertiesTransformer<SurveyDataModel, SurveyData>
		implements ModelToEntityTransformer<SurveyDataModel, SurveyData, Integer> {

	/**
	 * Transformer DTO to entity.
	 * 
	 * @param model SurveyDataModel needs to be transferred to an entity.
	 * @return SurveyData entity from SurveyDataModel dto.
	 */

	public SurveyData toEntity(SurveyDataModel model) {
		SurveyData surveyData = new SurveyData();

		surveyData.setId(model.getId());
		surveyData.setDotnetCore(model.getDotNetCore());
		surveyData.setDotnetWinform(model.getDotNetWinForm());
		surveyData.setDotnetWpf(model.getDotNetWpf());
		surveyData.setActiveMq(model.getActiveMq());
		surveyData.setAdobeAfterEffects(model.getAdobeAfterEffects());
		surveyData.setAdobeAnimate(model.getAdobeAnimate());
		surveyData.setAdobeAudition(model.getAdobeAudition());
		surveyData.setAdobeExpress(model.getAdobeExpress());
		surveyData.setAdobeFigma(model.getAdobeFigma());
		surveyData.setAdobeIllustrator(model.getAdobeIllustrator());
		surveyData.setAdobeIndesign(model.getAdobeInDesign());
		surveyData.setAdobePhotoshop(model.getAdobePhotoshop());
		surveyData.setAdobePremierePro(model.getDotNetCore());
		surveyData.setAdobeXd(model.getAdobeXd());
		surveyData.setAgile(model.getAgile());
		surveyData.setAngular(model.getAngular());
		surveyData.setAngularStyleTheme(model.getAngularStyleTheme());
		surveyData.setAngularjs(model.getAngularJs());
		surveyData.setAnsible(model.getDotNetCore());
		surveyData.setApacheIgnite(model.getApacheIgnite());
		surveyData.setApacheNifi(model.getApacheNifi());
		surveyData.setApacheSpark(model.getApacheSpark());
		surveyData.setApiTool(model.getApi());
		surveyData.setApigee(model.getApigee());
		surveyData.setAppleHumanInterfaceGuidelines(model.getAppleHumanInterfaceGuidelines());
		surveyData.setArangodb(model.getArangoDb());
		surveyData.setAspdotnet(model.getAspDotNet());
		surveyData.setAuth0(model.getAuthZero());
		surveyData.setAutomotive(model.getAutomotive());
		surveyData.setAws(model.getAws());
		surveyData.setAwsApiGateway(model.getAwsApiGateway());
		surveyData.setAwsCodecommit(model.getAwsCodeCommit());
		surveyData.setAwsIam(model.getAwsIam());
		surveyData.setAwsTimestream(model.getAwsTimeStream());
		surveyData.setAzure(model.getAzure());
		surveyData.setAzureAd(model.getAzureAd());
		surveyData.setAzureApiManagement(model.getAzureApiManagement());
		surveyData.setAzureRepos(model.getAzureRepos());
		surveyData.setAzureTimeSeriesInsight(model.getAzureTimeSeriesInsight());
		surveyData.setBitbucket(model.getBitbucket());
		surveyData.setBootstrapOrMaterial(model.getBootstrapMaterial());
		surveyData.setBuildOrCompose(model.getBuildCompose());
		surveyData.setC(model.getCPlus());
		surveyData.setCSharp(model.getCSharp());
		surveyData.setCamunda(model.getCamunda());
		surveyData.setCassandra(model.getCassandra());
		surveyData.setCertificate(model.getCertificate());
		surveyData.setCicdTools(model.getCicdTools());
		surveyData.setCircuitBreaker(model.getCircuitBreaker());
		surveyData.setCompletionTime(model.getCompletionTime());
		surveyData.setCouchbase(model.getCouchbase());
		surveyData.setCouldstack(model.getCouldStack());
		surveyData.setCqrs(model.getCqrs());
		surveyData.setCssHtml5(model.getCssAndHtml());
		surveyData.setCsunAccessibility(model.getCsunAccessibility());
		surveyData.setCurrentAddress(model.getCurrentAddress());
		surveyData.setDb2(model.getDbTwo());
		surveyData.setDocker(model.getDocker());
		surveyData.setDruid(model.getDruid());
		surveyData.setElasticsearch(model.getElasticSearch());
		surveyData.setEmail(model.getEmail());
		surveyData.setEmployeeId(model.getEmployeeId());
		surveyData.setEnergyUtilities(model.getEnergyUtilities());
		surveyData.setEnglish(model.getEnglish());
		surveyData.setFinanceBanking(model.getFinanceBanking());
		surveyData.setFlinto(model.getFlinto());
		surveyData.setFlutter(model.getFlutter());
		surveyData.setFramer(model.getFramer());
		surveyData.setFullName(model.getFullName());
		surveyData.setFundamentals(model.getFundamentals());
		surveyData.setGcp(model.getGcp());
		surveyData.setGithub(model.getGitHub());
		surveyData.setGitlab(model.getGitLab());
		surveyData.setGo(model.getGo());
		surveyData.setGolang(model.getGolang());
		surveyData.setGoogleDataStudio(model.getGoogleDataStudio());
		surveyData.setGradle(model.getGradle());
		surveyData.setHaproxy(model.getHaProxy());
		surveyData.setHealthcare(model.getHealthCare());
		surveyData.setIis(model.getIis());
		surveyData.setInfluxdb(model.getInfluxDb());
		surveyData.setInsurrance(model.getInsurance());
		surveyData.setInvision(model.getInVision());
		surveyData.setIotLumada(model.getIotLumada());
		surveyData.setIotMqtt(model.getIotMqtt());
		surveyData.setIotThinkworx(model.getIotThinkWorx());
		surveyData.setIotWebsocket(model.getIotWebSocket());
		surveyData.setJapanese(model.getJapanese());
		surveyData.setJava(model.getJava());
		surveyData.setJavaPlayFramework(model.getJavaPlayFramework());
		surveyData.setJavaSwing(model.getJavaSwing());
		surveyData.setJavajsp(model.getJavaJsp());
		surveyData.setJavaspringBoot(model.getJavaSpringBoot());
		surveyData.setJavaspringMvc(model.getJavaSpringMvc());
		surveyData.setJavafx(model.getJavaFx());
		surveyData.setJavascript(model.getJavascript());
		surveyData.setJbossOrWildfly(model.getJBossWildFly());
		surveyData.setJenkin(model.getJenkins());
		surveyData.setJira(model.getJira());
		surveyData.setJquery(model.getJQuery());
		surveyData.setKafka(model.getKafka());
		surveyData.setKeycloak(model.getKeycloak());
		surveyData.setKong(model.getKong());
		surveyData.setKotlin(model.getKotlin());
		surveyData.setKotlin2(model.getKotlinTwo());
		surveyData.setKubernetes(model.getKubernetes());
		surveyData.setLessCss(model.getLessCss());
		surveyData.setLocalstack(model.getLocalStack());
		surveyData.setManufacturing(model.getManufacturing());
		surveyData.setMariadb(model.getMariaDb());
		surveyData.setMaterialDesign(model.getMaterialDesign());
		surveyData.setMaven(model.getMaven());
		surveyData.setMemcached(model.getMemcached());
		surveyData.setMicronaut(model.getMicronaut());
		surveyData.setMicrosoftBiztalk(model.getMicrosoftBiztalk());
		surveyData.setMongodb(model.getMongoDb());
		surveyData.setMotionDesign(model.getMotionDesign());
		surveyData.setMuiTheme(model.getMuiTheme());
		surveyData.setMulesoft(model.getMuleSoft());
		surveyData.setMysql(model.getMySql());
		surveyData.setName(model.getName());
		surveyData.setNeo4j(model.getNeoFourJ());
		surveyData.setNestjs(model.getNestJs());
		surveyData.setNetty(model.getNetty());
		surveyData.setNetworking(model.getNetworking());
		surveyData.setNginx(model.getNginx());
		surveyData.setNodejs(model.getNodeJs());
		surveyData.setNodered(model.getNodeRed());
		surveyData.setOci(model.getOci());
		surveyData.setOkta(model.getOkta());
		surveyData.setOpenstack(model.getOpenStack());
		surveyData.setOracleDb(model.getOracleDb());
		surveyData.setOracleFormOrReport(model.getOracleFormReport());
		surveyData.setOraclePlOrSql(model.getOraclePlSql());
		surveyData.setOther(model.getOther());
		surveyData.setOtherSkills(model.getListOtherSkills());
		surveyData.setPcf(model.getPcf());
		surveyData.setPentaho(model.getPentaho());
		surveyData.setPerformance(model.getPerformance());
		surveyData.setPhp(model.getPhp());
		surveyData.setPostgresql(model.getPostgreSql());
		surveyData.setPowerBi(model.getPowerBi());
		surveyData.setPrinting(model.getPrinting());
		surveyData.setPrism(model.getPrism());
		surveyData.setPrometheus(model.getPrometheus());
		surveyData.setPython(model.getPython());
		surveyData.setPythonDjango(model.getPythonDjango());
		surveyData.setPythonFlask(model.getPythonFlask());
		surveyData.setQlikSense(model.getQlikSense());
		surveyData.setQlikview(model.getQlikView());
		surveyData.setQuarkus(model.getQuarkus());
		surveyData.setR(model.getR());
		surveyData.setRabbitmq(model.getRabbitMq());
		surveyData.setReact(model.getReact());
		surveyData.setReactNative(model.getReactNative());
		surveyData.setReactStyleTheme(model.getReactStyleTheme());
		surveyData.setRedis(model.getRedis());
		surveyData.setRetailLogistic(model.getRetailLogistic());
		surveyData.setSaga(model.getSaga());
		surveyData.setSaleforce(model.getSaleForce());
		surveyData.setSassCss(model.getSassCss());
		surveyData.setSecurity(model.getSecurityOne());
		surveyData.setSecurityLib(model.getSecurityTwo());
		surveyData.setSemiconductor(model.getSemiconductor());
		surveyData.setSencha(model.getSencha());
		surveyData.setSketch(model.getSketch());
		surveyData.setSmartSpace(model.getSmartSpace());
		surveyData.setSonarqube(model.getSonarQube());
		surveyData.setSqlServer(model.getSqlServer());
		surveyData.setStartTime(model.getStartTime());
		surveyData.setSvn(model.getSvn());
		surveyData.setTableau(model.getTableau());
		surveyData.setTerraform(model.getTerraform());
		surveyData.setTibcoEms(model.getTibcoEms());
		surveyData.setTomcat(model.getTomcat());
		surveyData.setTransportation(model.getTransportation());
		surveyData.setTyk(model.getTyk());
		surveyData.setTypescript(model.getTypeScript());
		surveyData.setUiAutomation(model.getUiAutomation());
		surveyData.setUxPin(model.getUxPin());
		surveyData.setUxResearcher(model.getUxResearcher());
		surveyData.setUxWriting(model.getUxWriting());
		surveyData.setVbdotnet(model.getVbDotNet());
		surveyData.setVirtualMachine(model.getVirtualMachine());
		surveyData.setVisualBasic6(model.getVisualBasic6());
		surveyData.setVisualBasicdotnet(model.getVisualBasicDotNET());
		surveyData.setVuejs(model.getVueJs());
		surveyData.setW3c(model.getW3c());
		surveyData.setWaterfall(model.getWaterfall());
		surveyData.setWebsphereApplicationServer(model.getWebSphereApplicationServer());
		surveyData.setWireframeBalsamiq(model.getWireFrameBalsamiq());
		surveyData.setWpfStyleTheme(model.getWpfStyleTheme());
		surveyData.setWpsSoa(model.getWpsSoa());
		surveyData.setWso2(model.getWsoTwo());
		surveyData.setHitachiNextTheme(model.getHitachiNextTheme());
		surveyData.setIbmCognos(model.getIbmCognos());
		return surveyData;
	}

}
