package com.hitachi.coe.fullstack.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
	public static final String PEER_GROUP_REPORT_GENERATED_SUCCESS = "Peer Group Report Generated successfully.";
	public static final String NO_DATA_AVAILABLE = "Customer history data is not yet available.";
	public static final String ERROR_HAS_OCCURRED_WHILE_REPORT_GENERATION = "An error has occurred while report generation.";
	public static final String STEP4 = "STEP_4";
	public static final String DCP = "DCP";
	public static final String RUO = "RUO";
	public static final String WBC = "WBC";
	public static final String RBC = "RBC";
	public static final String MCH = "MCH";
	public static final String HCT = "HCT";
	public static final String MCV = "MCV";
	public static final String RDWSD = "RDWSD";
	public static final String RDWCV = "RDWCV";
	public static final String NEUTP = "NEUTP";
	public static final String NEUTN = "NEUTN";
	public static final String LYMPHP = "LYMPHP";
	public static final String LYMPHN = "LYMPHN";
	public static final String MPV = "MPV";
	public static final String PLT = "PLT";
	public static final String EON = "EON";
	public static final String EOP = "EOP";
	public static final String RBCO = "RBCO";
	public static final String PLTO = "PLTO";
	public static final String RETP = "RETP";
	public static final String RETN = "RETN";
	public static final String XN450 = "XN-450";
	public static final String RBCREPTRETN = "RBCREPTRETN";
	public static final String RBCOPLTO = "RBCOPLTO";
	public static final String IRF = "IRF";
	public static final String BASON = "BASON";
	public static final String BASOP = "BASOP";
	public static final String XT_2000_I = "XT-2000i";
	public static final String XT_2000 = "XT-2000I";
	public static final String XE_2100 = "XE-2100";
	public static final String EIGHTCHECK = "EIGHTCHECK";
	public static final String WBCMONONMONOP = "WBC and MONON and MONOP";
	public static final String MONON = "MONON";
	public static final String MONOP = "MONOP";
	public static final String WBCEONEOP = "WBC and EON and EOP";
	public static final String WBCBASONBASOP = "WBC and BASON and BASOP";
	public static final String RDWSDRDWCVMCV = "RDWSD and RDWCV and MCV";
	public static final String RBCOPLTORETPIRF = "RBCO and PLTO and RETP and IRF";
	public static final String WBCLYMPHPLYMPHN = "WBC and LYMPHP and LYMPHN";
	public static final String WBCNEUTPNEUTN = "WBC and NEUTP and NEUTN";
	public static final String MCHRBCHGB = "MCH and RBC and HGB";
	public static final String HCTRBCMCV = "HCT and RBC and MCV";
	public static final String MPVPLT = "MPV and PLT";
	public static final String HGB = "HGB";
	public static final String PTC = "PTC";
	public static final String CLIA_CODE = "*";
	public static final String NUM_REGEX = "[^0-9.]+";
	public static final String POCHI_CW = "POCHI-CW";
	public static final String POCH = "POCH";
	public static final String KX = "KX";
	public static final String XP = "XP";
	public static final String NRBCP = "NRBCP";
	public static final String E_CHECK_XE_XN_CHECK_XN_L_CHECK = "e-CHECK XE|XN CHECK|XN L CHECK";
	public static final String E_CHECK_XE = "e-CHECK(XE)";
	public static final String POCH_100I = "POCH-100I";
	private static final Integer[] LEVEL_LIST = { 1, 2, 3 };

	public static Integer[] getLevelList() {
		return LEVEL_LIST;
	}

	public static final String DEFAULT_NUMBER_TOP_SKILL = "6";
	public static final String REPORT_REVIEWED = "Report Reviewed";
	public static final String REPORT_REVIEWED_DESCRIPTION = "Insight Peer Group Report";
	public static final String MIME_TYPE_PDF = "application/pdf";
	public static final String USER_ACTIVITY_SAVE = "UserActivity data saved successfully.";
	public static final String DATE_FORMAT = "MM/dd/yyyy hh:mm:ss aa";
	public static final String DATE_TIME_FORMAT = "yyyy/MM/dd hh:mm:ss";
	public static final String HOUR_24H_FORMAT = "HH:mm:ss";
	public static final String HOUR_12H_FORMAT = "hh:mm:ss aa";
	public static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";


	public static final String UTC_TIME_ZONE = "UTC";
	public static final String USER_ACTIVITY_EXCEPTION = "Exception occurred while user activity save.";
	public static final String BIAS_MESSAGE = "This report has Accuracy Bias codes for ";
	public static final String GENERATE_STATUS_RUNNING = "Running";
	public static final String GENERATE_STATUS_COMPLETED = "Completed";
	public static final String GENERATE_STATUS_FAILED = "Failed";

	public static final String XE_MODEL = "XE";
	public static final String XT_MODEL = "XT";
	public static final String XS_MODEL = "XS";
	public static final String POCH_MODEL = "POCH";
	public static final String KX_MODEL = "KX";
	public static final String XN_MODEL = "XN";
	public static final String XN_L_MODEL = "XN-L";
	public static final String XS_1000IC_AL = "XS-1000IC-AL";
	public static final String XS_1000IC = "XS-1000IC";
	public static final String XS_1000IAL = "XS-1000IAL";
	public static final String XS_1000I = "XS-1000I";
	public static final String XS_800IAL = "XS-800IAL";
	public static final String XS_800I = "XS-800I";
	public static final String XR_MODEL = "XR";
	public static final String XP_MODEL = "XP";
	public static final String XQ_MODEL = "XQ";
	public static final String FALSE = "false";

	// Raw Data report
	public static final int RAW_DATA_REPORT_MAX_COLUMNS = 19;
	public static final int RAW_DATA_REPORT_MAX_COLUMNS_FOR_LEGACY = 17;
	public static final int RAW_DATA_REPORT_MAX_ROWS = 89;

	// QC comment
	public static final String QC_COMMENT_MANAGED = "MN (Managed) - Data has been included from QC calculations";
	public static final String QC_COMMENT_UNMANAGED = "NM (Not Managed) - Data has been excluded from QC calculations";
	// Data Position Of Chart
	public static final int POSITION_CALC_HALF_TABLE = 58;
	public static final int POSITION_CALC_LEFT = 56;
	public static final int SDI_RANGE_LIMIT = 3;

	public static final int MAX_LOTS_HISTORICAL_COLUMNS = 9;

	// Report types for generate PDF
	public static final String INSIGHT_PEER_GROUP_REPORT_TYPE_URL = "INSIGHT-PEER-GROUP-REPORT";

	// Total Pixel for Parameter Report
	public static final double TOTAL_PIXEL = 40;
	// Total Percent for Parameter Report
	public static final double TOTAL_PERCENT = 100;
	// negative position point for Parameter Report
	public static final BigDecimal NEGATIVE_POSITION_POINT = BigDecimal.valueOf(-57);
	// positive position point for Parameter Report
	public static final BigDecimal POSITIVE_POSITION_POINT = BigDecimal.valueOf(52);
	// center position point for Parameter Report
	public static final BigDecimal CENTER_POSITION_POINT = BigDecimal.valueOf(4);

	public static final String XN_10 = "XN-10";
	public static final String XN_20 = "XN-20";
	public static final String XN_11 = "XN-11";
	public static final String XN_21 = "XN-21";
	public static final String XR_10 = "XR-10";
	public static final String XR_20 = "XR-20";
	public static final String XN_330 = "XN-330";
	public static final String XN_430 = "XN-430";
	public static final String XN_530 = "XN-530";
	public static final String XN_350 = "XN-350";
	public static final String XN_450 = "XN-450";
	public static final String XN_550 = "XN-550";
	public static final String XQ_320 = "XQ-320";
	public static final String KN_21 = "KN-21";
	public static final String KN_21N = "KN-21N";
	public static final String XE_2100L = "XE-2100L";
	public static final String XE_2100D = "XE-2100D";
	public static final String XE_5000 = "XE-5000";
	public static final String XS_1000_I = "XS-1000i";
	public static final String XS_800_I = "XS-800i";
	public static final String XT_1800_I = "XT-1800i";
	public static final String XT_4000_I = "XT-4000i";

	public static final String GROUP1 = "Group1";
	public static final String GROUP2 = "Group2";
	public static final String GROUP3 = "Group3";
	public static final String GROUP4 = "Group4";
	public static final String GROUP5 = "Group5";
	public static final String XN_GROUP1 = "XN-10/XN-20";
	public static final String XN_GROUP2 = "XN-11/XN-21";
	public static final String XN_GROUP3 = "XN-330/XN-430/XN-530/XN-350/XN-450/XN-550";
	public static final String NONE = "None";
	public static final String PARAM_NOTE_1 = "*1 ";
	public static final String PARAM_NOTE_2 = "*2 ";
	public static final String PARAM_NOTE_2_BREAK = ",*2 ";

	// List uncertainty parameter in Traceability Report
	private static final List<String> UNCERTAINLY_LIST = Arrays.asList("WBC", "WBCC", "WBCD", "RBC", "HGB", "HCT",
			"MCV", "PLT", "PLTF", "RET%");

	public static List<String> getUncertainlyList() {
		return UNCERTAINLY_LIST;
	}

	private static final List<String> SPECIAL_UNCERTAINLY_LIST = Arrays.asList("WBCC", "WBCD");

	public static List<String> getSpecialUncertainlyList() {
		return SPECIAL_UNCERTAINLY_LIST;
	}

	public static final String STATUS_MANAGED = "MANAGED";

	private static final List<String> MODERN_SERIAL_CODE = Arrays.asList("XN", "XN-L", "XR", "XQ");

	public static List<String> getModernSerialCode() {
		return MODERN_SERIAL_CODE;
	}

	// temporary hard code while waiting confirmation
	public static final String PLOT_TEA_MULTIPLIER = "PLOT_TEA_MULTIPLIER";

	public static final String SECURITY_KEY = "SecurityKey";

	// For XE/ XT/ XS analyzer models
	public static final int LEGACY_ANALYZER = 0;
	// For POCHI, KX, XP analyzer models
	public static final int POCHI_KX_XP = 1;
	// For XN, XNL, XR, XQ analyzer models
	public static final int MODERN_ANALYZER = 2;
	// STANDARD UOM
	public static final int STANDARD_UOM = 65;

	public static final int SI_UOM = 66;

	public static final String XE_XT_XS_UOM = "standard";
	// REDIS CACHE CONFIG
	public static final int REDIS_CACHE_REFRESH_MINUTE = 5;

	public static final long DEFAULT_DISPLAY_ORDER = 9999L;

	public static final int DEFAULT_FORMAT = 2;

	public static final String USER_INFO = "userinfo";

	public static final String API_VERSION_FORMAT = "/api/v1/**";

	public static final String COMMA = ",";

	public static final String NOTE_HIGHER = "1 - CV Higher than group";

	public static final String NOTE_DELTA = "2 - Delta% greater than performance goal";

	public static final String BLANK = "";

	public static final String PARAM_LL = "paramLL";

	public static final String PARAM_UL = "paramUL";

	public static final String ID = "id";
	public static final String SIZE = "size";
	public static final String RESULT = "result";

	public static final String TIMESTAMP_FORMAT = "YYYY-MM-DD HH:MM:SS.sss";

	public static final String YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd";
}
