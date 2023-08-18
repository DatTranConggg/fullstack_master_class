package com.hitachi.coe.fullstack.service.impl;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.model.LoginRequest;
import com.hitachi.coe.fullstack.model.LoginResponse;
import com.hitachi.coe.fullstack.model.RefreshTokenRequest;
import com.hitachi.coe.fullstack.model.RefreshTokenResponse;
import com.hitachi.coe.fullstack.util.JwtUtils;
import com.hitachi.coe.fullstack.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    JwtUtils jwtUtils;

    @Override
    public LoginResponse login(LoginRequest request) {
        // AuthenticationManager's provider  will handle username/password authentication for us
        // See the config in AuthConfig.java for more detail
        final Authentication authentication;
        try {
            // authenticate method will return a fully populated Authentication object
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            log.error("Bad credentials for username: {}", request.getUsername() + " " + e.getMessage());
            // We catch the exception here to throw back the exception for global exception handler to handle
            throw new BadCredentialsException(ErrorConstant.MESSAGE_BAD_CREDENTIALS);
        }
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        final Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("username", userDetails.getUsername());
        extraClaims.put("authorities", authorities);
        final String accessToken = jwtUtils.generateJwtAccessToken(userDetails.getUsername());
        final String refreshToken = jwtUtils.generateJwtRefreshToken(extraClaims, userDetails.getUsername());
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(userDetails.getUsername())
                .build();
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) throws JwtException {
        final String requestRefreshToken = request.getRefreshToken();
        // Check for malformed, expired, unsupported, illegal argument jwt exception
        final boolean isValidRefreshToken = jwtUtils.validateJwtToken(requestRefreshToken);
        final String username = jwtUtils.extractClaim(requestRefreshToken, Claims::getSubject);
        // throw exception for the exception handler to handle
        if (!isValidRefreshToken || username == null) {
            log.error("Invalid refresh token");
            // throw JwtException for global exception handler to handle
            throw new JwtException(ErrorConstant.MESSAGE_INVALID_TOKEN);
        }
        final String accessToken = jwtUtils.generateJwtAccessToken(username);
        final String refreshToken = jwtUtils.generateJwtRefreshToken(username);
        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
