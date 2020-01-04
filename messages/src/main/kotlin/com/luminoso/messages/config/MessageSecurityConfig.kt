package com.luminoso.messages.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class MessageSecurityConfig(private val authenticationManager: ReactiveAuthenticationManagerResolver<ServerHttpRequest>) {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .authorizeExchange { exchange ->
                exchange.pathMatchers("/messages/list").hasAuthority("SCOPE_admin")
                exchange.pathMatchers(HttpMethod.GET,"/messages/**").hasAuthority("SCOPE_message:read")
                exchange.pathMatchers(HttpMethod.POST,"/messages/**").hasAuthority("SCOPE_message:write")
                exchange.anyExchange().authenticated()
            }.oauth2ResourceServer { o ->
                o.authenticationManagerResolver(authenticationManager)
            }.build()
    }
}