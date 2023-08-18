package com.hitachi.coe.fullstack.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hitachi.coe.fullstack.constant.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class DateTimeSerializer format yyyy/MM/dd HH:mm:ss.
 */
public class DateTimeSerializer extends JsonSerializer<Date> {

	/**
	 * Serialize.
	 *
	 * @param value       the value
	 * @param gen         the gen
	 * @param serializers the serializers
	 * @throws IOException Signals that an I/O exception has occurred.
	 */

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

		LocalDateTime localDateTime = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		gen.writeString(localDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT)));
	}

}
