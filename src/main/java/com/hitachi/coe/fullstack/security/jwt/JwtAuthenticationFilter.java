package com.hitachi.coe.fullstack.security.jwt;

import com.hitachi.coe.fullstack.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A filter class that handles JWT authentication for requests
 * It check the Authorization header for a valid JWT token and sets
 * the user authentication in the SecurityContext
 *
 * @author tminhto
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // if header "Authorization" is not present or does not start with "Bearer "
        // or is empty, clear the context and throw error
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")
                || authorizationHeader.trim().isEmpty()) {
            SecurityContextHolder.clearContext();
            log.debug("Missing or invalid Authorization header");
            filterChain.doFilter(request, response);
            return;
        }
        try {
            final String jwtAccessToken = authorizationHeader.substring(7);
            final String username = jwtUtils.extractClaim(jwtAccessToken, Claims::getSubject);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (username != null && authentication == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtils.validateJwtToken(jwtAccessToken)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    request.setAttribute("accessToken", jwtAccessToken);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (JwtException e) {
            SecurityContextHolder.clearContext();
            log.debug("Invalid JWT token (expired, malformed, etc...), can not get username from token");
            throw new AuthenticationException("Invalid JWT token, can not get username from token");
        }
        filterChain.doFilter(request, response);
    }
}
