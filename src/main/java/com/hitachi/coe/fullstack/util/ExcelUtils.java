package com.hitachi.coe.fullstack.util;


import com.hitachi.coe.fullstack.constant.Constants;
import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.enums.Status;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.model.ConfigKey;
import com.hitachi.coe.fullstack.model.ExcelConfigModel;
import com.hitachi.coe.fullstack.model.ExcelErrorDetail;
import com.hitachi.coe.fullstack.model.ExcelResponseModel;
import com.hitachi.coe.fullstack.model.StyleExcelModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math3.util.Precision;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hitachi.coe.fullstack.util.DateFormatUtils.isValidFormatDate;
import static com.hitachi.coe.fullstack.util.StringUtil.isLengthValid;

@Slf4j
@Component
public class ExcelUtils {

    private ExcelUtils() {
    }

    /**
     * @param data             that write to the InputStream
     * @param excelConfigModel config the pacific templates for write file
     * @param <T>              any object can pass to this params
     * @return ByteArrayInputStream that contains all the data of Excel workbook
     * @author Lam
     */
    public static <T> ByteArrayInputStream writeToExcel(List<T> data, ExcelConfigModel excelConfigModel) {
        ByteArrayOutputStream fos;
        XSSFWorkbook workbook;
        ByteArrayInputStream byteArrayInputStream;
        HashMap<String, String> mappingRule = excelConfigModel.getMappingRule();

        mappingRule.forEach((key, value) -> {
            String validValue = StringUtil.formatSpace(value);
            mappingRule.put(key, validValue);
        });

        HashMap<String, String> reversedMappingRule = new HashMap<>();
        mappingRule.forEach((key, value) -> reversedMappingRule.put(value, key));

        // check if data is empty
        if (ObjectUtils.isEmpty(data))
            throw new CoEException(ErrorConstant.CODE_DATA_IS_EMPTY, ErrorConstant.MESSAGE_DATA_IS_EMPTY);

        try {
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(excelConfigModel.getStyle().getSheetName());
            List<String> excelHeaders = excelConfigModel.getHeaders().stream().map(StringUtil::formatSpace).collect(Collectors.toList());

            int rowCount = excelConfigModel.getStartRow();
            int columnCount = excelConfigModel.getStartColumn();
            Row row = sheet.createRow(rowCount++);

            // Create style for Excel header and write Excel headers
            XSSFCellStyle headerCellStyle = createHeaderStyle(workbook, excelConfigModel);
            createExcelHeader(excelHeaders, columnCount, row, headerCellStyle);

            Class<?> model = data.get(0).getClass();

            for (T t : data) {
                row = sheet.createRow(rowCount++);
                columnCount = 0;
                for (String excelHeader : excelHeaders) {

                    Cell cell = row.createCell(columnCount);
                    ConfigKey configKey = searchConfigKey(excelHeader, excelConfigModel.getConfigKey());
                    if (configKey == null)
                        throw new CoEException(ErrorConstant.CODE_WRITE_EXCEL_ERROR, ErrorConstant.MESSAGE_MISSING_CONFIGKEY);
                    CellStyle bodyCellStyle = createCellStyle(workbook, excelConfigModel.getStyle().getBody(), configKey);

                    if (log.isDebugEnabled()) log.debug("excelHeader: " + excelHeader);
                    if (!reversedMappingRule.containsKey(excelHeader))
                        throw new CoEException(ErrorConstant.CODE_WRITE_EXCEL_ERROR, ErrorConstant.MESSAGE_MAPPING_FIELD_MISSING);
                    String modelAttribute = reversedMappingRule.get(excelHeader);
                    if (log.isDebugEnabled()) log.debug("modelAttribute : " + modelAttribute);

                    Method modelMethod = createModelMethod(model, modelAttribute);
                    Object value = modelMethod.invoke(t, (Object[]) null);
                    if (log.isDebugEnabled()) log.debug("value: " + value);
                    setCellValue(value, cell, workbook, bodyCellStyle);
                    columnCount++;
                }
            }
            int headerRow = excelConfigModel.getStartRow();
            sheet.setAutoFilter(new CellRangeAddress(headerRow, headerRow, headerRow, excelHeaders.size() - 1));
            sheet.createFreezePane(headerRow, headerRow + 1);
            fos = new ByteArrayOutputStream();
            workbook.write(fos);
            workbook.close();
            fos.flush();
            fos.close();
            byteArrayInputStream = new ByteArrayInputStream(fos.toByteArray());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IOException ex) {
            log.error(ex.getClass().getName());
            throw new CoEException(ErrorConstant.CODE_WRITE_EXCEL_ERROR, ex.getMessage());
        }
        return byteArrayInputStream;
    }

    /**
     * @param fileInputStream  Excel file containing the data you want to
     *                         read
     * @param excelConfigModel json config model corresponds to that excel
     *                         file template
     * @param modelClass       class that you want to set the information
     *                         read from row excel to
     * @return private field name of the class we pass in /**
     * @author Lam
     */
    public static ExcelResponseModel readFromExcel(InputStream fileInputStream, ExcelConfigModel excelConfigModel, Class<?> modelClass) {
        HashMap<Integer, Object> data = new HashMap<>();
        List<ExcelErrorDetail> errorDetails = new ArrayList<>();
        Status status = Status.SUCCESS;
        ExcelResponseModel results = new ExcelResponseModel();
        int totalRows = 0;

        try (fileInputStream; XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {
            log.info("Begin Reading Excel File");
            List<ConfigKey> configKeyList = excelConfigModel.getConfigKey();
            List<String> excelHeaders = new ArrayList<>();
            HashMap<String, String> details;
            HashMap<String, String> reversedMappingRule = new HashMap<>();
            int rowIndex;
            String neededAttribute;


            XSSFSheet sheet = workbook.getSheetAt(0);

            int startColumn = excelConfigModel.getStartColumn();
            int startRow = excelConfigModel.getStartRow();
            int lastRowNum = sheet.getPhysicalNumberOfRows();
            createReadExcelHeaders(sheet, lastRowNum, excelConfigModel, excelHeaders);

            startRow++;

            excelConfigModel.getMappingRule().forEach((key, value) -> {
                String validValue = StringUtil.formatSpace(value);
                reversedMappingRule.put(validValue, key);
            });

            for (int i = startRow; i < lastRowNum; i++) {
                Row row = sheet.getRow(i);
                Constructor<?> modelConstructor = modelClass.getConstructor();
                Object modelObject = modelConstructor.newInstance();
                details = new HashMap<>();
                for (int j = startColumn; j < excelHeaders.size(); j++) {
                    neededAttribute = reversedMappingRule.get(excelHeaders.get(j));
                    ConfigKey configKey = searchConfigKey(excelHeaders.get(j), configKeyList);
                    if (!reversedMappingRule.containsKey(excelHeaders.get(j)) || Objects.isNull(neededAttribute))
                        throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_MAPPING_FIELD_MISSING);
                    if (configKey == null)
                        throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_MISSING_CONFIGKEY);
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    log.info("========> Cell Value at column " + j + " is: " + cell.getCellType().toString() + " with header is: " + excelHeaders.get(j) + " and neededAttribute: " + neededAttribute);
                    Field modelField = findModelAttribute(modelObject, neededAttribute);
                    handleFieldValidValue(cell, modelObject, modelField, neededAttribute, details, excelHeaders, j, configKey);
                }
                if (details.isEmpty())
                    data.put(i + 1, modelObject);
                else {
                    rowIndex = i + 1;
                    ExcelErrorDetail excelErrorDetail = ExcelErrorDetail.builder().rowIndex(rowIndex).details(details).build();
                    errorDetails.add(excelErrorDetail);
                    status = Status.FAILED;
                }
                totalRows++;
                results = ExcelResponseModel.builder().status(status).data(data).errorDetails(errorDetails).totalRows(totalRows).build();
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 IOException | NullPointerException ex) {
            log.error(ex.getMessage());
        }
        return results;
    }

    /**
     * @param excelHeader excel header at current column position
     * @param configKeys  config key corresponding to the current excel header
     * @return config key corresponding to the current column
     * @author lam
     */
    public static ConfigKey searchConfigKey(String excelHeader, List<ConfigKey> configKeys) {
        ConfigKey configKey = null;
        for (ConfigKey key : configKeys) {
            configKey = key;
            String keyType = StringUtil.formatSpace(configKey.getKey());
            if (StringUtil.formatSpace(excelHeader).equalsIgnoreCase(keyType))
                return configKey;
        }
        return configKey;
    }


    /**
     * @param modelObject     model contains the data in 1 rox of Excel file
     * @param neededAttribute The model's properties need to be set value into
     * @param cellValue       value to set to model
     * @author lam
     */
    private static void setAttributeValue(Object modelObject, String neededAttribute, Object cellValue) {
        try {
            if (cellValue == null) return;
            log.info("cellValue: " + cellValue);
            Field field = findModelAttribute(modelObject, neededAttribute);
            Class<?> propertyType = field.getType();
            Method method = modelObject.getClass().getMethod("set" + StringUtils.capitalize(neededAttribute), propertyType);
            if (propertyType == String.class) {
                if (cellValue instanceof Double) {
                    method.invoke(modelObject, BigDecimal.valueOf((Double) cellValue).toPlainString());
                }
                method.invoke(modelObject, cellValue);
            } else if (propertyType == Integer.class) {
                String cellStringValue = cellValue.toString();
                log.info("cellStringValue: " + cellStringValue);
                if (cellStringValue.contains(".")) {
                    cellStringValue = cellStringValue.split("[.]")[0];
                } else {
                    method.invoke(modelObject, Integer.valueOf(cellValue.toString()));
                    return;
                }
                method.invoke(modelObject, Integer.valueOf(cellStringValue));
            } else if (propertyType == Boolean.class)
                method.invoke(modelObject, cellValue);
            else if (propertyType == Double.class)
                method.invoke(modelObject, Double.valueOf(cellValue.toString()));
            else if (propertyType == Date.class)
                method.invoke(modelObject, cellValue);
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException ex) {
            log.error("setAttributeValue got exception: {}", ex.getMessage());
        }

    }

    /**
     * @param wb Workbook
     * @return CellStyle for styling Excel cell
     * @author Lam
     */

    private static XSSFCellStyle createCellStyle(XSSFWorkbook wb, StyleExcelModel style, ConfigKey configKey) {

        XSSFFont font = wb.createFont();
        font.setFontHeightInPoints(style.getFontWeight().shortValue());
        font.setFontName(style.getFont());
        font.setColor(style.getFontColor().shortValue());
        font.setBold(style.isBold());

        XSSFCellStyle cellStyle = wb.createCellStyle();
        int[] rgbBackGround = style.getBackGroundColor().stream().mapToInt(i -> i).toArray();

        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.WHITE.index);
        cellStyle.setFillForegroundColor((new XSSFColor(new Color(rgbBackGround[0], rgbBackGround[1], rgbBackGround[2]), new DefaultIndexedColorMap())));
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        setBorderStyle(cellStyle, style);
        cellStyle.setFont(font);

        if (configKey != null && configKey.getFontColor() != null) {
            int[] rgbFontConfig = configKey.getFontColor().stream().mapToInt(i -> i).toArray();
            font.setColor(new XSSFColor(new Color(rgbFontConfig[0], rgbFontConfig[1], rgbFontConfig[2]), new DefaultIndexedColorMap()));
            cellStyle.setFont(font);
        } else return cellStyle;
        return cellStyle;
    }

    /**
     * @param excelHeaders list of Excel headers
     * @param columnCount  column in Excel file
     * @param excelRow     Row in Excel file
     * @param cellStyle    Styling for Excel cell create Excel Header for Excel File
     * @author Lam
     */
    public static void createExcelHeader(List<String> excelHeaders, int columnCount, Row excelRow, CellStyle cellStyle) {
        for (String excelHeader : excelHeaders) {
            Cell cell = excelRow.createCell(columnCount++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(excelHeader);
        }
    }

    /**
     * @param value value that write to cell
     * @param cell  cell in Excel file
     *              set Cell value in Excel file
     * @author Lam
     */
    public static void setCellValue(Object value, Cell cell, XSSFWorkbook wb, CellStyle cellStyle) {
        cell.setCellStyle(cellStyle);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Date) {
            CellStyle dateFormat = dateFormatter(wb);
            cell.setCellValue((Date) value);
            cell.setCellStyle(dateFormat);
        }
    }

    /**
     * @param wb Workbook
     * @return date format in Excel cell
     * @author Lam
     */
    public static CellStyle dateFormatter(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(Constants.DEFAULT_DATE_FORMAT));
        return cellStyle;
    }

    /**
     * @param cell that have FORMULA type
     * @return formatted value from a formula
     */
    public static Object getFormulaTypeValue(Cell cell, ConfigKey configKey) {
        switch (cell.getCachedFormulaResultType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                double doubleValue = cell.getNumericCellValue();
                if (DateUtil.isCellDateFormatted(cell))
                    return cell.getDateCellValue();
                if (configKey.getKey().equalsIgnoreCase("%Billable"))
                    return Precision.round(doubleValue * 100, 1);
                return doubleValue;
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case ERROR:
                return null;
            default:
                return "";
        }
    }

    /**
     *
     * @param cellValue value of Excel Cell
     * @param configKey json config for this templates
     * @return true if a field can be blank
     */
    public static boolean isFieldCanBlank(Object cellValue, ConfigKey configKey) {
        return (!configKey.isRequired() && cellValue != null);
    }

    /**
     *
     * @param cell Excel cell
     * @param configKey json config for this templates
     * @param modelField Field need to validation
     * @return true if there is no violation
     */
    private static boolean isFieldValid(Cell cell, ConfigKey configKey, Field modelField) {
        switch (cell.getCellType()) {
            case STRING:
                String cellStringValue = cell.getStringCellValue();
                log.info("cellStringValue: {}",cellStringValue);
                if ((Integer.class == modelField.getType() && !NumberUtils.isParsable(cellStringValue))
                        || !StringUtil.isMatchedValidation(configKey, cellStringValue)
                )
                    return false;
                if ( configKey.getInputFormat() != null && configKey.getType().equals("Date"))
                    return isValidFormatDate(configKey.getInputFormat(),cellStringValue);
                if (configKey.getMin() != null || configKey.getMax() != null)
                    return isLengthValid(configKey, cellStringValue);
                break;
            case BOOLEAN:
                if (Boolean.class != modelField.getType())
                    return false;
                break;
            case NUMERIC:
                if (String.class == modelField.getType())
                    return false;
                break;
            case BLANK:
                return isFieldCanBlank(cell.getStringCellValue(), configKey);
            default:
                break;
        }
        return true;
    }

    /**
     * @param modelObject     Object that we need to find its attribute
     * @param neededAttribute attribute needed
     * @return the attribute needed from this object
     * @throws NoSuchMethodException when we not found this field in modelObject
     * @author lam
     */
    private static Field findModelAttribute(Object modelObject, String neededAttribute) throws NoSuchMethodException {
        Field[] modelObjectFieldName = modelObject.getClass().getDeclaredFields();
        Field field = null;
        for (Field value : modelObjectFieldName)
            if (value.getName().equals(neededAttribute)) {
                field = value;
                break;
            }
        if (field == null) throw new NoSuchMethodException();
        return field;
    }

    /**
     * @param workbook         Excel Workbook
     * @param excelConfigModel json config for this template
     * @return Style object for styling header
     * @author lam
     */
    private static XSSFCellStyle createHeaderStyle(XSSFWorkbook workbook, ExcelConfigModel excelConfigModel) {
        XSSFCellStyle headerCellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(excelConfigModel.getStyle().getHeader().getFontWeight().shortValue());
        font.setFontName(excelConfigModel.getStyle().getHeader().getFont());
        font.setColor(excelConfigModel.getStyle().getHeader().getFontColor().shortValue());
        font.setBold(excelConfigModel.getStyle().getHeader().isBold());

        int[] rgb = excelConfigModel.getStyle().getHeader().getBackGroundColor().stream().mapToInt(i -> i).toArray();

        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setFillForegroundColor((new XSSFColor(new Color(rgb[0], rgb[1], rgb[2]), new DefaultIndexedColorMap())));
        headerCellStyle.setAlignment(HorizontalAlignment.LEFT);
        headerCellStyle.setFont(font);

        return headerCellStyle;
    }

    /**
     * @param modelClass     class that we want to initialize its method
     * @param modelAttribute attribute we need to create method
     * @return Method
     * @throws NoSuchMethodException when we can not find method base on modelAttribute
     * @author lam
     */
    private static Method createModelMethod(Class<?> modelClass, String modelAttribute) throws NoSuchMethodException {
        Method modelMethod;
        try {
            modelMethod = modelClass.getMethod("get" + StringUtils.capitalize(modelAttribute));
            log.info("modelMethod : " + "get" + StringUtils.capitalize(modelAttribute));
        } catch (NoSuchMethodException ex) {
            log.error("Method not found in model" + ex.getMessage());
            modelMethod = modelClass.getMethod("get" + modelAttribute);
        }
        return modelMethod;
    }

    /**
     * @param xssfCellStyle cell Style
     * @param style         styling each cell
     * @author lam
     */
    private static void setBorderStyle(XSSFCellStyle xssfCellStyle, StyleExcelModel style) {
        if (style.isBorder()) {
            if (style.getBorderStyle() == 1) {
                xssfCellStyle.setBorderBottom(BorderStyle.THIN);
                xssfCellStyle.setBorderLeft(BorderStyle.THIN);
                xssfCellStyle.setBorderTop(BorderStyle.THIN);
                xssfCellStyle.setBorderRight(BorderStyle.THIN);
            }
            if (style.getBorderStyle() == 9) {
                xssfCellStyle.setBorderBottom(BorderStyle.DOTTED);
                xssfCellStyle.setBorderLeft(BorderStyle.THIN);
                xssfCellStyle.setBorderTop(BorderStyle.DOTTED);
                xssfCellStyle.setBorderRight(BorderStyle.THIN);
            }
        }
    }

    /**
     * @param sheet            excel sheet
     * @param lastRowNum       last row index
     * @param excelConfigModel json config for this Excel Templates
     * @param excelHeaders     header of this Excel file
     * @author lam
     */
    private static void createReadExcelHeaders(XSSFSheet sheet, int lastRowNum, ExcelConfigModel excelConfigModel, List<String> excelHeaders) {
        if (lastRowNum == 0 && sheet.getRow(0) == null)
            throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_SHEET_IS_EMPTY);
        if (sheet.getRow(excelConfigModel.getStartRow()) == null)
            throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_FILE_TYPE_ERROR);
        for (Cell cell : sheet.getRow(excelConfigModel.getStartRow()))
            if (Objects.requireNonNull(cell.getCellType()) == CellType.STRING) {
                String validHeader = StringUtil.formatSpace(cell.getStringCellValue());
                excelHeaders.add(validHeader);
            } else break;
        if (excelHeaders.size() != excelConfigModel.getHeaders().size())
            throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR, ErrorConstant.MESSAGE_MAPPING_FIELD_MISSING);
    }

    /**
     * @param cell            Excel cell
     * @param modelObject     Object for contain data
     * @param modelField      Field of Object Model
     * @param neededAttribute to call set method of it
     * @param details         contains error field of each cell
     * @param excelHeaders    header in Excel file
     * @param j               column index
     * @param configKey       json config for Excel file
     * @throws InvocationTargetException when invoke method fail
     * @throws NoSuchMethodException     when not found method
     * @throws IllegalAccessException    when can not create Object instance
     */
    public static void handleFieldValidValue(Cell cell, Object modelObject, Field modelField, String neededAttribute,
                                             Map<String, String> details, List<String> excelHeaders, int j,
                                             ConfigKey configKey) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Method setMethodModel;
        boolean isValid = isFieldValid(cell, configKey, modelField);
        switch (cell.getCellType()) {
            case STRING:
                String cellStringValue = cell.getStringCellValue();
                if (NumberUtils.isParsable(cellStringValue) && modelField.getType() == Integer.class)
                    setAttributeValue(modelObject, neededAttribute, Integer.parseInt(cellStringValue));
                if (isValid)
                    setAttributeValue(modelObject, neededAttribute, cellStringValue);
                else
                    details.put(excelHeaders.get(j), ErrorConstant.MESSAGE_INVALID_CELL_VALUE);
                break;
            case BOOLEAN:
                Boolean cellBooleanValue = cell.getBooleanCellValue();
                if (isValid) setAttributeValue(modelObject, neededAttribute, cellBooleanValue);
                else
                    details.put(excelHeaders.get(j), ErrorConstant.MESSAGE_INVALID_CELL_VALUE);
                break;
            case NUMERIC:
                Double cellDoubleValue = cell.getNumericCellValue();
                if (isValid) {
                    if (DateUtil.isCellDateFormatted(cell))
                        setAttributeValue(modelObject, neededAttribute, cell.getDateCellValue());
                    else
                        setAttributeValue(modelObject, neededAttribute, cellDoubleValue);
                } else
                    details.put(excelHeaders.get(j), ErrorConstant.MESSAGE_INVALID_CELL_VALUE);
                break;
            case BLANK:
                String dataType = configKey.getType();
                if (isValid) {
                    if (dataType.equals("Integer")) {
                        setMethodModel = modelObject.getClass().getMethod("set" + StringUtils.capitalize(neededAttribute), Integer.class);
                        log.info("set {}", StringUtils.capitalize(neededAttribute));
                        setMethodModel.invoke(modelObject, (Integer) null);
                    } else {
                        setMethodModel = modelObject.getClass().getMethod("set" + StringUtils.capitalize(neededAttribute), String.class);
                        setMethodModel.invoke(modelObject, " ");
                    }
                } else {
                    details.put(excelHeaders.get(j), ErrorConstant.MESSAGE_INVALID_CELL_VALUE);
                }
                break;
            case FORMULA:
                if (isValid) setAttributeValue(modelObject, neededAttribute, getFormulaTypeValue(cell, configKey));
                else
                    details.put(excelHeaders.get(j), ErrorConstant.MESSAGE_INVALID_CELL_VALUE);
                break;
            default:
                setMethodModel = modelObject.getClass().getMethod("set" + StringUtils.capitalize(neededAttribute), Object.class);
                setMethodModel.invoke(modelObject, "Unhandled Case");
                break;
        }
    }

    /**
     *
     * @param fileInputStream Excel file needed
     * @param rowIndex row index start at 0
     * @param colIndex column index start at 0
     * @param sheetName specific sheet name in Excel File
     * @return string value at that cell
     */
    public static String getSpecificCellStringValue(InputStream fileInputStream, int rowIndex, int colIndex, String sheetName) {
        String result = "" ;
        try (fileInputStream; Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowIndex);
            Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if ( cell.getCellType() == CellType.STRING )
                result = cell.getStringCellValue();
            else throw new CoEException(ErrorConstant.CODE_READ_EXCEL_ERROR,ErrorConstant.MESSAGE_INVALID_CELL_VALUE);
        } catch (IOException e) {
            throw new RuntimeException("File Not Found");
        }
        return result;
    }
}
