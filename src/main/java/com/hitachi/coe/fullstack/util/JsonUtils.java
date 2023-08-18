package com.hitachi.coe.fullstack.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.model.ExcelConfigModel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Slf4j
public class JsonUtils {

    private JsonUtils(){}

    /**
     * @param jsonString json string from resource
     * @return Json Config Model for the Excel Templates
     * @throws JsonProcessingException error when parsing json to POJO
     */
    public static ExcelConfigModel convertJsonToPojo(String jsonString) throws JsonProcessingException {
        ExcelConfigModel excelConfigModel;
        ObjectMapper objectMapper = new ObjectMapper();
        excelConfigModel = objectMapper.readValue(jsonString, ExcelConfigModel.class);
        return excelConfigModel;
    }

    /**
     * @param filePath file location
     * @return Json string from a file
     * @author Lam
     */
    public static String readFileAsString(String filePath) {
        InputStream inputStream;
        String result = "";
        try {
            inputStream = JsonUtils.class.getResourceAsStream(filePath);
            result = new String(Objects.requireNonNull(inputStream).readAllBytes());
        } catch ( IOException ex ) {
            log.error(ErrorConstant.MESSAGE_FILE_NOT_FOUND);
        }
        return result;
    }
}
