package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.model.LoginRequest;
import com.hitachi.coe.fullstack.model.LoginResponse;
import com.hitachi.coe.fullstack.service.impl.AuthenticationServiceImpl;
import com.hitachi.coe.fullstack.util.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-test.properties")
public class AuthenticationServiceTest {
    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationServiceImpl authenticationService;

    @Test
    public void testLogin_whenCredentialsValid_thenReturnLoginResponse() {
        final String username = "testUsername";
        final String password = "testPassword";
        final String accessToken = "testAccessToken";
        final String refreshToken = "testRefreshToken";
        final LoginRequest loginRequest = new LoginRequest(username, password);
        final List<String> authorities = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
        final UserDetails userDetails = User.withUsername(username)
                .password(password)
                .authorities(authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
                .build();
        final Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        Mockito.when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
        Mockito.when(jwtUtils.generateJwtAccessToken(username)).thenReturn(accessToken);
        Mockito.when(jwtUtils.generateJwtRefreshToken(username)).thenReturn(refreshToken);
        Mockito.when(authenticationService.login(loginRequest)).thenReturn(LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(username)
                .authorities(authorities)
                .build());
        LoginResponse loginResponse = authenticationService.login(loginRequest);
        Assertions.assertEquals(loginResponse.getAccessToken(), accessToken);
        Assertions.assertEquals(loginResponse.getRefreshToken(), refreshToken);
        Assertions.assertEquals(loginResponse.getUsername(), username);
        Assertions.assertEquals(loginResponse.getAuthorities(), authorities);
    }
}
