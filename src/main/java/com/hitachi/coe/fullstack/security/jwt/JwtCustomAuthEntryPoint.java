package com.hitachi.coe.fullstack.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@Component
public class JwtCustomAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());
        final BaseResponse<?> baseResponse = BaseResponse.unAuthorized(ErrorConstant.MESSAGE_UN_AUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        final ObjectMapper mapper = new ObjectMapper();
        final OutputStream outputStream = response.getOutputStream();
        mapper.writeValue(outputStream, baseResponse);
        outputStream.flush();
    }
}
