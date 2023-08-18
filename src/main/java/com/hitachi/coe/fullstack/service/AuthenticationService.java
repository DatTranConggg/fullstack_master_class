package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.model.LoginRequest;
import com.hitachi.coe.fullstack.model.LoginResponse;
import com.hitachi.coe.fullstack.model.RefreshTokenRequest;
import com.hitachi.coe.fullstack.model.RefreshTokenResponse;

/**
 * This interface is used to define methods for authentication.
 *
 * @author tminhto
 */
public interface AuthenticationService {
    /**
     * This method is used to authenticate a user.
     * @author tminhto
     * @param request the login request object, which contains the username and password
     * @return return an LoginResponse object, which contains the JWT tokens
     * @see LoginRequest
     * @see LoginResponse
     */
    LoginResponse login(LoginRequest request);

    /**
     * This method is used to refresh the JWT tokens.
     *
     * @author tminhto
     * @param request the refresh token request object, which contains the refresh token
     * @return return an RefreshTokenResponse object, which contains the new JWT tokens
     * @see RefreshTokenRequest
     * @see RefreshTokenResponse
     */
    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
