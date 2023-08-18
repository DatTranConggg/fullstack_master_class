package com.hitachi.coe.fullstack.util;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CommonUtils {


	/**
	 * Partition list sets of N item
	 *
	 * @param objects List<T> List input of data
	 * @param number  int number of item
	 * @return new list with N item in each list
	 * @param <T> generic type
	 */
	public static <T> List<List<T>> nItemPartition(List<T> objects, final int number) {
		if (CollectionUtils.isEmpty(objects)) {
			return Collections.emptyList();
		}
		return new ArrayList<>(IntStream.range(0, objects.size()).boxed()
				.collect(Collectors.groupingBy(e -> e / number, Collectors.mapping(objects::get, Collectors.toList())))
				.values());
	}

	/**
	 * Format value for bid decimal number. EX: 0.1 to .1
	 *
	 * @param sdi BigDecimal
	 * @return String
	 */
	public static String formatValue(BigDecimal sdi) {
		if (sdi == null) {
			return "--";
		}
		String formatSdi = sdi.toString();

		if (sdi.compareTo(BigDecimal.valueOf(0)) >= 0) {
			String subSDI = sdi.toString().substring(0, 1);
			String format = sdi.toString().substring(1);
			if (subSDI.equalsIgnoreCase("0")) {
				formatSdi = format;
			}
		} else {
			String subSDI = sdi.toString().substring(1, 2);
			String format = sdi.toString().substring(2);
			if (subSDI.equalsIgnoreCase("0")) {
				if (sdi.compareTo(BigDecimal.valueOf(0)) < 0) {
					formatSdi = "-" + format;
				} else {
					formatSdi = format;
				}
			}
		}
		return formatSdi;
	}

	/**
	 * Format number of rounding and remove zero first
	 *
	 * @param bigDecimal {@link BigDecimal}
	 * @param format     {@link String}
	 * @return String of converted Big decimal
	 */
	public static String formatBigDecimal(BigDecimal bigDecimal, String format) {
		if (bigDecimal == null) {
			return "--";
		}
		String formatTypeString = checkFormatType(format);
		DecimalFormat df = new DecimalFormat(formatTypeString);
		return df.format(bigDecimal);
	}

	/**
	 * Check type of big decimal format
	 *
	 * @param format input Format {@link String}
	 * @return new regex to convert
	 */
	public static String checkFormatType(String format) {
		if (format == null) {
			return "#.00";
		}
		if (format.startsWith(".")) {
			return "#" + format;
		}
		if (format.startsWith("0")) {
			return format;
		}
		return "#.00";
	}

	/**
	 * Change format a double to new pattern #.###
	 *
	 * @param data Double
	 * @return Double
	 */
	public static double doubleFormatter(Double data) {
		DecimalFormat df = new DecimalFormat("#.####");
		return Double.parseDouble(df.format(data));
	}

	/**
	 * Convert date pattern from database (replace D to d, replace Y to y)
	 *
	 * @param s String
	 * @return String
	 */
	public static String convertDateYearPattern(String s) {
		s = s.replace("D", "d");
		s = s.replace("Y", "y");
		return s;
	}

	/**
	 * Convert List Integer to String
	 *
	 * @param list List Integer
	 * @return String
	 */
	public static String convertListIntegertoString(List<Integer> list) {
		String listId = null;
		if (list == null) {
			throw new CoEException(ErrorConstant.CODE_LIST_ID_OF_SKILL_NULL,
					ErrorConstant.MESSAGE_LIST_ID_OF_SKILL_NULL);
		} else {
			listId = list.stream().map(Object::toString).reduce("", (a, b) -> a.isEmpty() ? b : a + "," + b);
		}
		return listId;
	}

	/**
	 * Convert String to Time Stamp
	 *
	 * @param dateStr String
	 * @return Time Stamp
	 */
	public static Timestamp convertStringToTimestamp(String dateStr) {
		try {
			if (dateStr == null || dateStr.isBlank()) {
				return null;
			}
			return Timestamp.valueOf(dateStr);
		} catch (IllegalArgumentException e) {
			throw new CoEException(ErrorConstant.CODE_INVALID_TIMESTAMP, ErrorConstant.MESSAGE_INVALID_TIMESTAMP,
					e.getCause());
		}
	}

	/**
	 *
	 * @param dateStr date value in string type
	 * @param format format type
	 * @return Date value with specific format type
	 */

	public static Date convertStringToDate(String dateStr,String format) {
	    try {
	        if (dateStr == null || dateStr.isBlank()) {
	            return null;
	        }
	        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	        return dateFormat.parse(dateStr);
	    } catch (ParseException e) {
	        throw new CoEException(ErrorConstant.CODE_INVALID_TIMESTAMP, ErrorConstant.MESSAGE_INVALID_TIMESTAMP, e.getCause());
	    }
	}
}
