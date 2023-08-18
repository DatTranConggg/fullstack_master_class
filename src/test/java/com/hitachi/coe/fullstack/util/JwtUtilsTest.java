package com.hitachi.coe.fullstack.util;

import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class JwtUtilsTest {
    private static JwtUtils jwtUtils;

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationInMs;

    @Value("${app.jwtRefreshExpirationInMs}")
    private long jwtRefreshExpirationInMs;

    @BeforeAll
    public static void setUp() {
        jwtUtils = new JwtUtils();
    }

    @BeforeEach
    public void beforeEach() {
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", jwtSecret);
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationInMs", jwtExpirationInMs);
        ReflectionTestUtils.setField(jwtUtils, "jwtRefreshExpirationInMs", jwtRefreshExpirationInMs);
    }

    @Test
    public void testGenerateJwtAccessToken_whenUsernameNotNull_thenReturnNotNullToken() {
        String username = "testUsername";
        String token = jwtUtils.generateJwtAccessToken(username);
        assertNotNull(token);
    }

    @Test
    public void testGenerateJwtAccessToken_whenUsernameIsNull_thenReturnNotNullToken() {
        String token = jwtUtils.generateJwtAccessToken(null);
        assertNotNull(token);
    }

    @Test
    public void testGenerateJwtAccessToken_whenExtraClaimsAndUsernameAreNotNull_thenReturnNotNullToken() {
        Map<String, Object> extraClaims = new HashMap<>();
        String username = "testUsername";
        List<String> authorities = Arrays.asList("ROLE_ADMIN", "ROLE_USER");
        extraClaims.put("username", username);
        extraClaims.put("authorities", authorities);
        String token = jwtUtils.generateJwtAccessToken(extraClaims, username);
        assertNotNull(token);
    }

    @Test
    public void testGenerateJwtAccessToken_whenExtraClaimsAndUsernameAreNull_thenReturnNotNullToken() {
        String token = jwtUtils.generateJwtAccessToken(null, null);
        assertNotNull(token);
    }

    @Test
    public void testGenerateJwtRefreshToken_whenUsernameNotNull_thenReturnNotNullToken() {
        String username = "testUsername";
        String token = jwtUtils.generateJwtRefreshToken(username);
        assertNotNull(token);
    }

    @Test
    public void testGenerateJwtRefreshToken_whenUsernameAndExtraClaimsAreNotNull_thenReturnNotNullToken() {
        Map<String, Object> extraClaims = new HashMap<>();
        String username = "testUsername";
        List<String> authorities = Arrays.asList("ROLE_ADMIN", "ROLE_USER");
        extraClaims.put("username", username);
        extraClaims.put("authorities", authorities);
        String token = jwtUtils.generateJwtRefreshToken(extraClaims, username);
        assertNotNull(token);
    }

    @Test
    public void testGenerateJwtRefreshToken_whenExtraClaimsAndUsernameAreNull_thenReturnNotNullToken() {
        String token = jwtUtils.generateJwtRefreshToken(null, null);
        assertNotNull(token);
    }

    @Test
    public void testExtractClaims_whenTokenAndClaimsResolverAreValid_thenReturnNotNullString() {
        String username = "testUsername";
        String token = jwtUtils.generateJwtAccessToken(username);
        String subject = jwtUtils.extractClaim(token, Claims::getSubject);
        assertNotNull(subject);
    }

    @Test
    public void testExtractClaims_whenTokenIsValidAndClaimsResolverIsNull_thenThrowNullPointerException() {
        String username = "testUsername";
        String validToken = jwtUtils.generateJwtAccessToken(username);
        assertThrows(IllegalArgumentException.class, () -> jwtUtils.extractClaim(validToken, null));
    }

    @Test
    public void testExtractClaims_whenTokenMalformedAndClaimsResolverIsValid_thenReturnNullString() {
        assertThrows(IllegalArgumentException.class, () -> jwtUtils.extractClaim(null, Claims::getSubject));
    }

    @Test
    public void testExtractClaims_whenTokenIsNullAndClaimsResolverIsNull_thenReturnNullString() {
        assertThrows(IllegalArgumentException.class, () -> jwtUtils.extractClaim(null, null));
    }

    @Test
    public void testExtractClaims_whenTokenIsInvalidAndClaimsResolverIsValid_thenReturnNullString() {
        assertThrows(JwtException.class, () -> jwtUtils.extractClaim("testInvalid", Claims::getSubject));
    }

    @Test
    public void testExtractClaims_whenTokenIsInvalidAndClaimsResolverIsNull_thenReturnNullString() {
        assertThrows(IllegalArgumentException.class, () -> jwtUtils.extractClaim("testInvalid", null));
    }

    @Test
    public void testValidateJwtToken_whenTokenIsValid_thenReturnTrue() {
        String token = jwtUtils.generateJwtAccessToken("testUsername");
        boolean isValid = jwtUtils.validateJwtToken(token);
        assertTrue(isValid);
    }

    @Test
    public void testValidateJwtToken_whenTokenIsInvalid_thenReturnFalse() {
        boolean isValid = jwtUtils.validateJwtToken("testInvalidToken");
        assertFalse(isValid);
    }

    @Test
    public void testValidateJwtToken_whenTokenIsNull_thenReturnFalse() {
        boolean isValid = jwtUtils.validateJwtToken(null);
        assertFalse(isValid);
    }

    @Test
    public void testExtractAllClaims_whenTokenIsValid_thenReturnNotNullClaims() {
        String token = jwtUtils.generateJwtAccessToken("testUsername");
        Claims claims = jwtUtils.extractAllClaims(token);
        assertNotNull(claims);
    }

    @Test
    public void testExtractAllClaims_whenTokenIsInvalid_thenThrowJwtException() {
        assertThrows(JwtException.class, () -> jwtUtils.extractAllClaims("testInvalidToken"));
    }

    @Test
    public void testExtractAllClaims_whenTokenIsNull_thenThrowJwtException() {
        assertThrows(JwtException.class, () -> jwtUtils.extractAllClaims(null));
    }
}
