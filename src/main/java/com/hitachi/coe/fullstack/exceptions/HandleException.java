package com.hitachi.coe.fullstack.exceptions;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * This controller is vital in order to handle exceptions thrown in Filters.
 */

@RestController
@RequestMapping("/error")
@ApiIgnore
public class HandleException implements org.springframework.boot.web.servlet.error.ErrorController {

    private static final Logger LOGGER = LogManager.getLogger(HandleException.class);

    private final ErrorAttributes errorAttributes;

    @Autowired
    public HandleException(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    public String getErrorPath() {
        return "/error";
    }

    public ErrorAttributes getErrorAttributes() {
        return errorAttributes;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidateException(MethodArgumentNotValidException exception) {
        Map<String, String> err = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((err2) -> {
            String fieldName = ((FieldError) err2).getField();
            String errMessage = err2.getDefaultMessage();
            err.put(fieldName, errMessage);
        });
        return err;

    }

    ;

    @RequestMapping
    public ResponseEntity<BaseResponse> error(HttpServletRequest aRequest, HttpServletResponse response) {
        WebRequest requestAttributes = new ServletWebRequest(aRequest);
        Throwable error = this.errorAttributes.getError(requestAttributes);
        ResponseEntity<BaseResponse> responseEntity = null;
        String errorCode = null;

        if (error instanceof InvalidDataException) {
            errorCode = ((InvalidDataException) error).getCode();
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.addError(new Error(errorCode, error.getMessage()));
            responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } else {
            errorCode = HttpStatus.INTERNAL_SERVER_ERROR.toString();
            BaseResponse dataResponse = new BaseResponse();
            if (error != null)
                dataResponse.addError(new Error(errorCode, error.getMessage()));
            else
                dataResponse.addError(new Error(errorCode, "Error could not be extracted"));
            responseEntity = new ResponseEntity<>(dataResponse, HttpStatus.BAD_REQUEST);
        }
        if (error != null) {
            LOGGER.error("Request failed with error code [{}] and error message [{}]", errorCode, error.getMessage());
        } else {
            LOGGER.error("Request failed with error code [{}] and error message [{}]", errorCode, "Error could not be extracted");
        }

        return responseEntity;
    }
}
