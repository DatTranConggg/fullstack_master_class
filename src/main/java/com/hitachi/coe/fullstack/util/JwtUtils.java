package com.hitachi.coe.fullstack.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/**
 * Utility class for generating and validating JSON Web Tokens (JWTs)
 *
 * @author tminhto
 */
@Slf4j
@Component
public class JwtUtils {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationInMs;

    @Value("${app.jwtRefreshExpirationInMs}")
    private long jwtRefreshExpirationInMs;

    /**
     * Generate a new JWT access token for the specified username
     *
     * @param username the username to generate the access token for
     * @return new JWT access token
     * @author tminhto
     */
    public String generateJwtAccessToken(String username) {
        final Map<String, Object> defaultClaims = new HashMap<>();
        return generateJwtAccessToken(defaultClaims, username);
    }

    /**
     * Generate a new JWT access token for the specified username and extra claims
     *
     * @param extraClaims extra claims (payload) to include in the access token
     * @param username    the username to generate the access token for
     * @return a new JWT access token
     * @author tminhto
     * @see #buildToken(Map, String, long)
     */
    public String generateJwtAccessToken(Map<String, Object> extraClaims, String username) {
        return buildToken(extraClaims, username, jwtExpirationInMs);
    }

    /**
     * Generate a new JWT refresh token and set username to token's subject
     *
     * @param username the username to generate the refresh token for
     * @return a new JWT refresh token
     * @author tminhto
     * @see #buildToken(Map, String, long)
     */
    public String generateJwtRefreshToken(String username) {
        final Map<String, Object> defaultClaims = new HashMap<>();
        return buildToken(defaultClaims, username, jwtRefreshExpirationInMs);
    }

    /**
     * Generate a new JWT refresh token for the specified username and extra claims
     *
     * @param extraClaims extra claims (payload) to include in the refresh token
     * @param username    the username to generate the refresh token for
     * @return a new JWT refresh token
     * @see #buildToken(Map, String, long)
     */
    public String generateJwtRefreshToken(Map<String, Object> extraClaims, String username) {
        return buildToken(extraClaims, username, jwtRefreshExpirationInMs);
    }

    /**
     * Builds a new JWT token with the specified extra claims, username, and expiration time.
     *
     * @param extraClaims the extra claims to include in the token, if null, an empty map will be used
     * @param username    the username to include in the token
     * @param expiration  the expiration time in milliseconds
     * @return the new JWT token
     * @author tminhto
     */
    private String buildToken(Map<String, Object> extraClaims, String username, long expiration) {
        log.debug("-----Building the jwt access token");
        if (extraClaims == null) {
            extraClaims = new HashMap<>();
        }
        final Instant issueTime = Instant.now();
        final Instant expirationTime = issueTime.plusMillis(expiration);
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(Date.from(issueTime))
                .setExpiration(Date.from(expirationTime))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts a claim from the specified token using the specified resolver function.
     *
     * @param token          the JWT token to extract the claim from
     * @param claimsResolver the resolver function to use to extract the claim
     * @param <T>            the type of the claim to extract
     * @return the extracted claim, if the token is invalid or the claim cannot be extracted, null will be returned
     * @throws {@link JwtException} if the token is invalid, {@link IllegalArgumentException} if the token string is null or if the claimsResolver is null
     * @author tminhto
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws JwtException, IllegalArgumentException {
        if (token == null || claimsResolver == null) {
            log.error("-----Token or claimsResolver is null");
            throw new IllegalArgumentException("Token or claimsResolver is null");
        }
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    /**
     * Check the jwt token if it valid or not
     *
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     * @author tminhto
     */
    public boolean validateJwtToken(String token) {
        try {
            log.debug("-----Validating access token");
            Jwts.parserBuilder().setSigningKey(getKey()).build().parse(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("JWT token is malformed: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("JWT token signature is invalid: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Extracts all claims from the specified token.
     *
     * @param token the JWT token to extract the claims from
     * @return the extracted claims
     * @throws {@link JwtException} if the token is invalid (null, expired, malformed, unsupported, or signature is invalid)
     * @author tminhto
     */
    public Claims extractAllClaims(String token) {
        log.debug("-----Extracting All Claims from access token");
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException("JWT token has expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Unsupported JWT token: " + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new JwtException("Malformed JWT token: " + e.getMessage());
        } catch (SignatureException e) {
            throw new JwtException("Invalid JWT signature: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new JwtException("Invalid JWT token: " + e.getMessage());
        }
    }

    /**
     * Get the JWT secret key from configuration
     *
     * @return the JWT secret key
     * @author tminhto
     */
    private Key getKey() {
        log.debug("-----Retrieving JWT secret key");
        byte[] decodedSecret = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(decodedSecret);
    }
}
