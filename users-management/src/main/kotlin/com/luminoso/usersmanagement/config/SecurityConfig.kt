package com.luminoso.usersmanagement.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig(private val authenticationManager: ReactiveAuthenticationManagerResolver<ServerHttpRequest>) {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf().disable()
            .authorizeExchange { exchange ->
                exchange.pathMatchers("/signup").permitAll()
                exchange.pathMatchers("/auth/**").hasRole("USER")
                exchange.pathMatchers("/actuator/**").permitAll()
                exchange.anyExchange().authenticated()
            }.oauth2Client().and().oauth2ResourceServer { o ->
                o.authenticationManagerResolver(authenticationManager)
            }.build()
    }
}