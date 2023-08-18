package com.hitachi.coe.fullstack.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.hitachi.coe.fullstack.constant.Constants;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.authorizeRequests().antMatchers("/v2/api-docs").permitAll().antMatchers("/configuration/ui").permitAll()
                .antMatchers("/manage/*").permitAll().antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/configuration/security").permitAll().antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-ui/*").permitAll().antMatchers("/webjars/**").permitAll().antMatchers("/v1/**")
                .permitAll().antMatchers(HttpMethod.POST, Constants.API_VERSION_FORMAT).permitAll()
                .antMatchers(HttpMethod.GET, Constants.API_VERSION_FORMAT).permitAll()
                .antMatchers(HttpMethod.PUT, Constants.API_VERSION_FORMAT).permitAll()
                .antMatchers(HttpMethod.DELETE, Constants.API_VERSION_FORMAT).permitAll()
                .antMatchers(HttpMethod.OPTIONS, Constants.API_VERSION_FORMAT).permitAll().anyRequest().authenticated();
    }
}
