package com.hitachi.coe.fullstack.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import com.hitachi.coe.fullstack.exceptions.CoEException;

@TestPropertySource("classpath:application-data-test.properties")
public class DateFormatUtilsTests {
	   
    @Test
    void testConvertTimestampFromStringWithValidInput() {
        String dateStr = "2023-06-12";
        LocalDate localDate = LocalDate.parse(dateStr);
        Timestamp expectedTimestamp = Timestamp.valueOf(localDate.atStartOfDay());
        Timestamp actualTimestamp = DateFormatUtils.convertTimestampFromString(dateStr);
        
        assertEquals(expectedTimestamp, actualTimestamp);
    }
    
    @Test
    void testConvertTimestampFromStringWithNullInput() {
        String dateStr = null;
        Timestamp actualTimestamp = DateFormatUtils.convertTimestampFromString(dateStr);
        assertNull(actualTimestamp);
    }
    
    @Test
    void testConvertTimestampFromStringWithBlankInput() {
        String dateStr = "   ";
        Timestamp actualTimestamp = DateFormatUtils.convertTimestampFromString(dateStr);
        assertNull(actualTimestamp);
    }
    
    @Test
    void testConvertTimestampFromStringWithInvalidInput() {
        String dateStr = "invalid date";
        assertThrows(CoEException.class, () -> {
        	DateFormatUtils.convertTimestampFromString(dateStr);
        });
    }
}
