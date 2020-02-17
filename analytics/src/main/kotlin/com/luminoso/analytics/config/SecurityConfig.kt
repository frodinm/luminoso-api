package com.luminoso.analytics.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.jwt.JwtDecoder

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtDecoder: JwtDecoder
   ): WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/actuator/health").permitAll()
            .antMatchers("/event").permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer { configurer ->
                configurer.jwt().decoder(jwtDecoder)
            }

    }
}
