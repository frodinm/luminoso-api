package com.luminoso.communications.config

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.context.annotation.*
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class SecurityConfig(private val resourceServerProperties: OAuth2ResourceServerProperties): WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.csrf().disable().authorizeRequests()
            .antMatchers("/notifications/**").hasRole("CLIENT")
            .antMatchers("/actuator/health").permitAll()
            .antMatchers("/email/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt().jwkSetUri(resourceServerProperties.jwt.jwkSetUri)
    }
}
