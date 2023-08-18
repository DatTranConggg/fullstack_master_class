package com.hitachi.coe.fullstack.controller;

import com.hitachi.coe.fullstack.model.LoginRequest;
import com.hitachi.coe.fullstack.model.LoginResponse;
import com.hitachi.coe.fullstack.model.RefreshTokenRequest;
import com.hitachi.coe.fullstack.model.RefreshTokenResponse;
import com.hitachi.coe.fullstack.model.common.BaseResponse;
import com.hitachi.coe.fullstack.service.AuthenticationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    @Autowired
    private AuthenticationService authenticationService;

    @ApiOperation(value = "Logs in a user with name/password fields and returns access token and refresh token.")
    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return BaseResponse.success(authenticationService.login(loginRequest));
    }

    @ApiOperation(value = "Refreshes access token, refresh token using a valid refresh token in request body.")
    @PostMapping("/refresh")
    public BaseResponse<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return BaseResponse.success(authenticationService.refreshToken(refreshTokenRequest));
    }

}
