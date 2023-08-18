package com.hitachi.coe.fullstack.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("Bad credentials error: {}", exception.getMessage());
        BaseResponse<?> baseResponse = BaseResponse.unAuthorized(ErrorConstant.MESSAGE_BAD_CREDENTIALS);
        if (response != null) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), baseResponse);
        } else {
            log.error("HttpServletResponse is null");
        }
    }
}
