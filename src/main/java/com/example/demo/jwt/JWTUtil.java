package com.example.demo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class JWTUtil {

    private static final String SECRET_KEY = "something1908290182903@$#$#@@sjdflkjsdkfl20398re";

    public String issueToken(String subject) {
        return issueToken(subject, Map.of());
    }

    public String issueToken(String subject, String ...scopes) {
        return issueToken(subject, Map.of("scopes", scopes));
    }

    public String issueToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setIssuer("xxx")
                .setExpiration(
                        Date.from(
                                Instant.now().plus(12, DAYS)
                        )
                )
                .signWith(getSigningKet(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims getClaims(String token) {
         return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKet())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSubject(String token) {
        return this.getClaims(token).getSubject();
    }

    private Key getSigningKet() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean issueTokenValid(String jwt, String username) {
        String subject = getSubject(jwt);
        return subject.equals(username) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        Date today = Date.from(Instant.now());
        return getClaims(jwt).getExpiration().before(today);
    }
}
