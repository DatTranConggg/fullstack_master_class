package com.hitachi.coe.fullstack.util;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.enums.Status;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.model.ConfigKey;
import com.hitachi.coe.fullstack.model.ExcelConfigModel;
import com.hitachi.coe.fullstack.model.ExcelErrorDetail;
import com.hitachi.coe.fullstack.model.ExcelResponseModel;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * The CsvUtils class provides utility methods for reading and writing CSV
 * files.
 *
 * @author Phan Nguyen
 *
 */
@Component
public class CsvUtils {

	/**
	 * @param inputStream     where user give the excel file to the server read
	 * @param jsonConfigModel json config model corresponds to that excel file
	 *                        template
	 * @param jsonConfigModel grap every thing it have. ex: mapping rule, start
	 *                        column, config type, ...
	 * 
	 * @param tClass          model class user give to set value for it
	 * @param <T>             generics
	 * @return Object contain data, total row has read and error details if it has
	 * @author Phan Nguyen
	 */

	public static <T> ExcelResponseModel readCsv(InputStream inputStream, ExcelConfigModel jsonConfigModel,
			Class<T> tClass) {
		int startRow = jsonConfigModel.getStartRow();
		int startColumn = jsonConfigModel.getStartColumn();
		int totalRow = 0;
		ExcelResponseModel result = new ExcelResponseModel();
		HashMap<Integer, Object> data = new HashMap<>();
		List<ExcelErrorDetail> errorDetails = new ArrayList<>();
		try {
			CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			csvReader.skip(startRow);
			List<String> csvHeaders = new ArrayList<>();
			String[] headers = csvReader.readNext();
			checkAcceptedFile(startColumn, headers, jsonConfigModel.getConfigKey());
			for (String header : headers) {
				header = StringUtil.formatSpace(header);
				csvHeaders.add(header);
			}
			String[] row;
			while ((row = csvReader.readNext()) != null) {
				if (!String.join("", row).isEmpty()) {
					totalRow++;
					startRow++;
					ExcelErrorDetail error = new ExcelErrorDetail();
					error.setRowIndex(startRow + 1);
					T t = tClass.getDeclaredConstructor().newInstance();
					handleColumnImport(startColumn, row, csvHeaders, jsonConfigModel, error, t);
					if (error.getDetails() == null) {
						data.put(startRow + 1, t);
					} else {
						errorDetails.add(error);
					}
				}
			}
		} catch (CsvValidationException | IOException e) {
			throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_CSV_VALIDATION);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_CLASS_FIELD_EMPTY);
		} catch (CoEException e) {
			throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR, e.getMessage());
		}
		result.setStatus(Status.SUCCESS);
		if (!errorDetails.isEmpty()) {
			result.setStatus(Status.FAILED);
		}
		result.setData(data);
		result.setTotalRows(totalRow);
		result.setErrorDetails(errorDetails);
		return result;
	}

	/**
	 * @param startColumn     will start reading excel file by specific order from
	 *                        the json config
	 * @param row             are the values contain on the excel by row
	 * @param header          is the header list to compare each column from the
	 *                        excel
	 * @param jsonConfigModel grap every thing it have. ex: mapping rule, start
	 *                        column, config type, ...
	 * @param error           to contain error detail
	 * @param <T>             generics
	 * @author Phan Nguyen
	 */

	private static <T> void handleColumnImport(int startColumn, String[] row, List<String> header,
			ExcelConfigModel jsonConfigModel, ExcelErrorDetail error, T t) {
		HashMap<String, String> jsonConfigMappingRules = jsonConfigModel.getMappingRule();
		jsonConfigMappingRules.forEach((key, value) -> {
			String newInstanceHeader = StringUtil.formatSpace(value);
			jsonConfigMappingRules.put(key, newInstanceHeader);
		});
		@SuppressWarnings("unchecked")
		HashMap<String, String> reversedHashMap = (HashMap<String, String>) MapUtils.invertMap(jsonConfigMappingRules);
		HashMap<String, String> detail = new HashMap<>();
		List<ConfigKey> configKeyList = jsonConfigModel.getConfigKey();
		for (int i = startColumn; i < header.size(); i++) {
			try {
				String headerValue = header.get(i);
				ConfigKey configKey = searchConfigKey(headerValue, configKeyList);
				if (configKey == null) {
					continue;
				}
				String neededAttribute = reversedHashMap.get(String.valueOf(headerValue));
				String cell = row[i].trim();
				setAttributeValue(neededAttribute, configKey, cell, t);

			} catch (CoEException ex) {
				detail.put(header.get(i), ex.getMessage());
				error.setDetails(detail);
			}
		}
	}

	/**
	 * @param excelHeader is the header list to compare each column from the excel
	 * @param configKeys  contain cell type and requirement
	 * @return config key if match with header or else return null
	 * @author Phan Nguyen
	 */
	private static ConfigKey searchConfigKey(String excelHeader, List<ConfigKey> configKeys) {
		for (ConfigKey key : configKeys) {
			ConfigKey configKey = key;
			String keyType = StringUtil.formatSpace(configKey.getKey());
			if (excelHeader.equals(keyType)) {
				return configKey;
			}
		}
		return null;
	}

	/**
	 * @param neededAttribute is the value from json config
	 * @param configkey      contain cell type and requirement
	 * @return configkey is the value if match with header or else return null
	 * @param cell is the excel cell value
	 * @author Phan Nguyen
	 */
	private static <T> void setAttributeValue(String neededAttribute, ConfigKey configkey, String cell, T t) {
		try {
			if (!checkRequire(configkey, cell)) {
				throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_FIELD_REQUIRED);
			}

			for (Field field : t.getClass().getDeclaredFields()) {
				String fieldName = field.getName();
				if (fieldName.equals(neededAttribute)) {
					Method method = t.getClass().getMethod("set" + StringUtil.capitalize(neededAttribute),
							field.getType());

					switch (configkey.getType().toUpperCase()) {
					case "INTEGER":
						handleIntegerValue(cell, method, t);
						break;
					case "STRING":
						method.invoke(t, cell);
						break;
					case "DATE":
						handleDateValue(cell, method, t);
						break;
					case "DOUBLE":
						handleDoubleValue(cell, method, t);
						break;
					case "EMAIL":
						handleEmailValue(cell, configkey.getValidation(), method, t);
						break;
					default:
						break;
					}
				}
			}
		} catch (NumberFormatException exd) {
			throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_INVALID_NUMBER);
		} catch (DateTimeException | IllegalArgumentException ex) {
			throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_INVALID_DATE);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_CLASS_METHOD_INVALIABLE);
		}
	}

	/**
	 * @param cell   is the excel cell value
	 * @param method is the method that T class has declared
	 * @param t      stand for class has getDeclaredConstructor
	 * @author Phan Nguyen
	 */

	private static <T> void handleIntegerValue(String cell, Method method, T t)
			throws IllegalAccessException, InvocationTargetException {
		cell = StringUtil.removeUnknownSymbol(cell);
		if (!cell.isEmpty()) {
			Integer cellNumber = Integer.parseInt(cell);
			method.invoke(t, cellNumber);
		}
	}

	/**
	 * @param cell   is the excel cell value
	 * @param method is the method that T class has declared
	 * @param t      stand for class has getDeclaredConstructor
	 * @author Phan Nguyen
	 */

	private static <T> void handleDateValue(String cell, Method method, T t)
			throws IllegalAccessException, InvocationTargetException {
		Date dateCell = new Date(cell);
		method.invoke(t, dateCell);
	}

	/**
	 * @param cell   is the excel cell value
	 * @param method is the method that T class has declared
	 * @param t      stand for class has getDeclaredConstructor
	 * @author Phan Nguyen
	 */

	private static <T> void handleDoubleValue(String cell, Method method, T t)
			throws IllegalAccessException, InvocationTargetException {
		cell = StringUtil.removeUnknownSymbol(cell);
		if (!cell.isEmpty()) {
			Double doubleCell = Double.parseDouble(cell);
			method.invoke(t, doubleCell);
		}
	}

	/**
	 * @param cell   is the excel cell value
	 * @param method is the method that T class has declared
	 * @param t      stand for class has getDeclaredConstructor
	 * @author Phan Nguyen
	 */

	private static <T> void handleEmailValue(String cell, String validation, Method method, T t)
			throws IllegalAccessException, InvocationTargetException {
		if (validation != null && !cell.matches(validation)) {
			throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_INVALID_STRING);
		}
		method.invoke(t, cell);
	}

	/**
	 * @return config key if match with header or else return null
	 * @param cell is the excel cell value
	 * @return if configkey is require and cell is not null will return true or else
	 *         it will return false
	 * @author Phan Nguyen
	 */
	private static boolean checkRequire(ConfigKey configkey, String cell) {
		return (!(configkey.isRequired() && cell.isEmpty()));
	}

	/**
	 * @param headers    is the list of header
	 * @param configKeys is the list of config key
	 * @return if headers is not empty will or headers contain configKeys value will
	 *         acknowledge is true or else it will throw exception
	 * @author Phan Nguyen
	 */
	private static void checkAcceptedFile(int startColumn, String[] headers, List<ConfigKey> configKeys) {
		HashMap<String, String> keys = new HashMap<>();
		if (headers == null || headers.length == 0) {
			throw new CoEException("read-csv", "File is empty , please check again");
		}
		for (ConfigKey configKey : configKeys) {
			String key = StringUtil.formatSpace(configKey.getKey());
			keys.put(key, key);
		}
		for (int i = startColumn; i < headers.length; i++) {
			String key = StringUtil.formatSpace(headers[i]);
			if (key.isEmpty()) {
				break;
			}
			if (!keys.containsKey(key) || keys.get(key) == null) {
				throw new CoEException("read-csv", "Wrong file or config is missing");
			}
		}
	}

	/**
	 * 
	 * Writes data to a new CSV file based on the given List of objects and
	 * JsonConfigModel configuration. Returns a ByteArrayInputStream containing the
	 * new CSV file.
	 * 
	 * @author Phan Nguyen
	 * @param data            - the List of objects to write to the CSV file
	 * @param excelConfigModel - the configuration settings for the CSV file,
	 *                        including the column headers
	 * @return ByteArrayInputStream - a stream containing the new CSV file
	 */

	public static <T> ByteArrayInputStream writeToCSV(List<T> data, ExcelConfigModel excelConfigModel) {

		ByteArrayOutputStream fos;
		ByteArrayInputStream byteArrayInputStream;
		CSVPrinter csvPrinter;
		HashMap<String, String> reverseMappingRule = new HashMap<>();
		String[] headers = excelConfigModel.getHeaders().toArray(new String[0]);
		excelConfigModel.getMappingRule().forEach((key, value) -> {
			String newInstanceHeader = StringUtil.formatSpace(value);
			reverseMappingRule.put(newInstanceHeader, key);
		});
		if (ObjectUtils.isEmpty(data)) {
			throw new CoEException(ErrorConstant.CODE_DATA_IS_EMPTY, ErrorConstant.MESSAGE_DATA_IS_EMPTY);
		}
		try {
			fos = new ByteArrayOutputStream();
			csvPrinter = new CSVPrinter(new OutputStreamWriter(fos, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader(headers));
			for (T t : data) {
				List<String> entities = new ArrayList<>();
				for (String header : headers) {
					String key = reverseMappingRule.get(StringUtil.formatSpace(header));
					entities.add(getDataByMethod(t, key));
				}
				csvPrinter.printRecord(entities);
			}
			csvPrinter.flush();
			csvPrinter.close();
			byteArrayInputStream = new ByteArrayInputStream(fos.toByteArray());
		} catch (IOException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException ex) {
			throw new CoEException(ErrorConstant.CODE_WRITE_EXCEL_ERROR, ex.getMessage());
		}
		return byteArrayInputStream;
	}

	/**
	 * 
	 * @param t    type of the object class
	 * @param key  the method name of t class that we want to interface
	 * @return value of the get method from the t class
	 */
	private static <T> String getDataByMethod(T t, String key) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method getMethod = t.getClass().getMethod("get" + StringUtil.capitalize(key));
		return getMethod.invoke(t).toString();
	}

	/**
	 * 
	 * Reads a file as a string.
	 * 
	 * @param filePath - the path of the file to read
	 * @return String - the contents of the file as a string
	 * @throws IOException - if there is an error in reading the file
	 */
	public static String readFileAsString(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}

	/**
	 *
	 * @param fileInputStream CSV file needed
	 * @param row row index start at 0
	 * @param column column index start at 0
	 * @return string value at that cell
	 */
	public static String getSpecificCellStringValue(InputStream fileInputStream, int row, int column) throws IOException {

		try (CSVReader csvReader = new CSVReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8))) {
			int currentRow = 0;
			String[] csvRecord;
			while ((csvRecord = csvReader.readNext()) != null) {
				if (currentRow == row) {
					if (column < csvRecord.length) {
						return csvRecord[column];
					} else {
						throw new IndexOutOfBoundsException("Column index is out of range.");
					}
				}
				currentRow++;
			}

			throw new IndexOutOfBoundsException("Row index is out of range.");
		} catch (CsvValidationException e) {
			throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_CSV_VALIDATION);
		}
	}

}
