package com.hitachi.coe.fullstack.util;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import static com.hitachi.coe.fullstack.constant.Constants.DATE_FORMAT;
import static com.hitachi.coe.fullstack.constant.Constants.UTC_TIME_ZONE;

@Slf4j
public class DateFormatUtils {
     private DateFormatUtils(){}

    /**
     * Convert Date at site time zone to Date at UTC 0
     * @param date input date - site time zone
     * @param siteTimeZone site time zone
     * @return output date - UTC
     */
    @SneakyThrows
    public static Date convertDateFromSiteTimeZoneToGMT(Date date, String siteTimeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String timeStringInSite =  sdf.format(date);

        SimpleDateFormat dateFormatSite = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat dateFormatUTC = new SimpleDateFormat(DATE_FORMAT);
        dateFormatSite.setTimeZone(TimeZone.getTimeZone(siteTimeZone));
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE));

        Date dateInSite = dateFormatSite.parse(timeStringInSite);

        return sdf.parse(dateFormatUTC.format(dateInSite));
    }

    
	public static Timestamp convertTimestampFromString(String dateStr) {
		try {
			if (Objects.isNull(dateStr) || dateStr.isBlank()) {
				return null;
			}
			LocalDate localDate = LocalDate.parse(dateStr);
			return Timestamp.valueOf(localDate.atStartOfDay());
		} catch (Exception e) {
			throw new CoEException(ErrorConstant.CODE_INVALID_TIMESTAMP, ErrorConstant.MESSAGE_INVALID_TIMESTAMP,
					e.getCause());
		}
	}

    /**
     *
     * @param format format type
     * @param value string value of date
     * @return if string value date match the format type or not
     */
    public static boolean isValidFormatDate(String format, String value) {
        LocalDate ldt = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            ldt = LocalDate.parse(value, formatter);
            String result = ldt.format(formatter);
            return result.equalsIgnoreCase(value);
        } catch (DateTimeParseException e1) {
            return false;
        }
    }

    /**
     * Parses a specific string with date format to date
     *
     * @param dateStr the date string to parse
     * @param format the format needed
     * @return the date with specific format
     * @author tquangpham
     */
    public static Date convertDateFromString(String dateStr, String format) {
        try {
            if (ObjectUtils.isEmpty(dateStr)) {
                return null;
            }
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
            LocalDate localDate = LocalDate.parse(dateStr, dateFormatter);
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException e) {
            throw new CoEException(ErrorConstant.CODE_INVALID_FORMAT_DATE, ErrorConstant.MESSAGE_INVALID_FORMAT_DATE + format);
        }
    }
}
