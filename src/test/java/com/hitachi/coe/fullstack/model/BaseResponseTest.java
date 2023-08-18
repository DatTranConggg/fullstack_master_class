package com.hitachi.coe.fullstack.model;

import com.hitachi.coe.fullstack.model.common.BaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


public class BaseResponseTest {

    ImportResponse response;

    @BeforeEach
    void setUp(){
        response = new ImportResponse();
        response.setTotalRows(100);
        response.setSuccessRows(80);
        response.setErrorRows(20);
        response.setErrorList(Collections.emptyList());
    }

    @Test
    public void testBaseResponse_whenAllArgsConstructor_thenSuccess(){

        BaseResponse<ImportResponse> baseResponse = new BaseResponse<>(HttpStatus.OK.value(), null, response);

        assertNotNull(baseResponse);
        assertEquals(HttpStatus.OK.value(), baseResponse.getStatus());
        assertNull(baseResponse.getMessage());
        assertEquals(response, baseResponse.getData());

    }
    @Test
    public void testBaseResponse_whenGetterSetter_thenSuccess(){

        BaseResponse<ImportResponse> baseResponse = new BaseResponse<>();
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage(null);
        baseResponse.setData(null);

        assertNotNull(baseResponse);
        assertEquals(HttpStatus.OK.value(), baseResponse.getStatus());
        assertNull(baseResponse.getMessage());
        assertNull(baseResponse.getData());

    }
    @Test
    public void testBaseResponse_whenInvalidDataAndEmail_thenSuccess(){

        BaseResponse<ImportResponse> baseResponse = BaseResponse.success();

        assertNotNull(baseResponse);
        assertEquals(HttpStatus.OK.value(), baseResponse.getStatus());
        assertNull(baseResponse.getMessage());
        assertNull(baseResponse.getData());

    }
    @Test
    public void testBaseResponse_whenValidMessage_thenSuccess(){

        BaseResponse<ImportResponse> baseResponse = BaseResponse.success("Success");

        assertNotNull(baseResponse);
        assertEquals(HttpStatus.OK.value(), baseResponse.getStatus());
        assertEquals("Success", baseResponse.getMessage());
        assertNull(baseResponse.getData());
    }
    @Test
    public void testBaseResponse_whenValidData_thenSuccess(){

        BaseResponse<ImportResponse> baseResponse = BaseResponse.success(response);

        assertNotNull(baseResponse);
        assertEquals(HttpStatus.OK.value(), baseResponse.getStatus());
        assertNull(baseResponse.getMessage());
        assertEquals(response, baseResponse.getData());
    }

    @Test
    public void testBaseResponse_whenInvalidDataAndMessage_thenBadRequest(){

        BaseResponse<ImportResponse> baseResponse = BaseResponse.badRequest("Error",response);

        assertNotNull(baseResponse);
        assertEquals(HttpStatus.BAD_REQUEST.value(), baseResponse.getStatus());
        assertEquals("Error", baseResponse.getMessage());
        assertEquals(response, baseResponse.getData());
    }

    @Test
    public void testBaseResponse_whenInvalidMessage_thenBadRequest(){

        BaseResponse<ImportResponse> baseResponse = BaseResponse.badRequest("Error");

        assertNotNull(baseResponse);
        assertEquals(HttpStatus.BAD_REQUEST.value(), baseResponse.getStatus());
        assertEquals("Error", baseResponse.getMessage());
        assertNull(baseResponse.getData());
    }

    @Test
    public void testBaseResponse_whenInvalidData_thenBadRequest(){

        BaseResponse<ImportResponse> baseResponse = BaseResponse.badRequest(response);

        assertNotNull(baseResponse);
        assertEquals(HttpStatus.BAD_REQUEST.value(), baseResponse.getStatus());
        assertNull(baseResponse.getMessage());
        assertEquals(response, baseResponse.getData());
    }

    @Test
    public void testBaseResponse_whenInvalidData_thenFail(){

        BaseResponse<Object> baseResponse = BaseResponse.fail("Error");

        assertNotNull(baseResponse);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), baseResponse.getStatus());
        assertEquals("Error", baseResponse.getMessage());
    }

    @Test
    public void testBaseResponse_whenInvalidData_theUnauthorized(){

        BaseResponse<Object> baseResponse = BaseResponse.unAuthorized("Bad Credential");

        assertNotNull(baseResponse);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), baseResponse.getStatus());
        assertEquals("Bad Credential", baseResponse.getMessage());
        assertNull(baseResponse.getData());

    }
}
