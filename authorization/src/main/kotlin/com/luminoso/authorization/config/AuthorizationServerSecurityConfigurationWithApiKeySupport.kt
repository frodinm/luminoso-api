package com.luminoso.authorization.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.configuration.ClientDetailsServiceConfiguration
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping

@Configuration
@Order(0)
@Import(ClientDetailsServiceConfiguration::class, AuthorizationServerEndpointsConfiguration::class)
class AuthorizationServerSecurityConfigurationWithApiKeySupport: AuthorizationServerSecurityConfiguration() {
    override fun configure(http: HttpSecurity) {
        super.configure(http)
        val handlerMapping = http.getSharedObject(FrameworkEndpointHandlerMapping::class.java)
        val apiKeyEndpointPath = handlerMapping.getServletPath("/oauth/apikey/introspect")

        http.authorizeRequests { authorizeRequestsConfigurer ->
            authorizeRequestsConfigurer.antMatchers(apiKeyEndpointPath).fullyAuthenticated()
        }.requestMatchers { requestMatcherConfigurer ->
            requestMatcherConfigurer.antMatchers(apiKeyEndpointPath)
        }
    }
}
