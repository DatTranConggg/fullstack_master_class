package com.hitachi.coe.fullstack.security.jwt;

<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
=======
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
>>>>>>> 7edcd259b0824675717a0f609a980b6eaf065bb6
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
<<<<<<< HEAD
import java.util.Collection;
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    /**
     * Filters incoming requests and checks if the user is authorized to perform the requested action.
     *
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @param filterChain the filter chain to be executed
     * @throws ServletException if the servlet encounters a problem
     * @throws IOException if an input or output error occurs
     * @author Dat Tran
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            if(request.getRequestURI().contains("/auth")) {
                filterChain.doFilter(request, response);
                return;
            }
            String module = request.getRequestURI().split("/")[request.getRequestURI().split("/").length - 1].toUpperCase();
            String fullModule = request.getMethod() + "_" + module;
            GrantedAuthority currentGrantedAuthority = new SimpleGrantedAuthority(fullModule);
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if(authorities.contains(currentGrantedAuthority)) {
                filterChain.doFilter(request, response);
            } else {
                throw new AccessDeniedException("You are not authorized to perform this action");
            }
=======
import java.nio.file.attribute.UserPrincipal;

public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String module = request.getRequestURI().split("/")[request.getRequestURI().split("/").length - 1];
            System.out.println("request: " + request.getMethod());
            System.out.println("request URI" + request.getRequestURI());
            System.out.println("Module: " + module);
            GrantedAuthority currentGrantedAuthority = new SimpleGrantedAuthority(module);
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(currentGrantedAuthority));
        } catch (Exception exception){
            System.out.println(exception);
        }
        filterChain.doFilter(request, response);
>>>>>>> 7edcd259b0824675717a0f609a980b6eaf065bb6
    }
}
