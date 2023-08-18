package com.hitachi.coe.fullstack.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static com.hitachi.coe.fullstack.constant.ErrorConstant.MESSAGE_PERMISSION_ACCESS_DENIED;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler
{
    /**
     * Handles an <code>AccessDeniedException</code> by logging the error message, creating a JSON response with an error message, and sending the response to the client.
     *
     * @param request the HTTP servlet request that resulted in the access denied exception
     * @param response the HTTP servlet response so that the user agent can be advised of the failure
     * @param accessDeniedException the access denied exception that caused the invocation
     * @throws IOException if an input or output error occurs
     * @throws ServletException if the servlet encounters a problem
     * @author Dat Tran
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("Access denied error: {}", accessDeniedException.getMessage());
        final com.hitachi.coe.fullstack.model.common.BaseResponse<?> baseResponse = BaseResponse.accessDenied(MESSAGE_PERMISSION_ACCESS_DENIED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        final ObjectMapper mapper = new ObjectMapper();
        final OutputStream outputStream = response.getOutputStream();
        mapper.writeValue(outputStream, baseResponse);
        outputStream.flush();
    }
}
