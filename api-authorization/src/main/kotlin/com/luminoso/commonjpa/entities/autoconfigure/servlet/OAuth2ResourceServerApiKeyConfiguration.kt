package com.luminoso.commonjpa.entities.autoconfigure.servlet

import com.luminoso.commonjpa.entities.autoconfigure.OAuth2ResourceServerApiKeyProperties
import com.luminoso.apiauthorization.core.security.introspection.ApiKeyIntrospector
import com.luminoso.apiauthorization.core.security.introspection.NimbusApiKeyIntrospector
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configures a {@link ApiKeyInstrospector} when a token introspection endpoint is
 * available. Also configures a {@link WebSecurityConfigurerAdapter} if a
 * {@link ApiKeyInstrospector} bean is found.
 *
 * @author Fabrizio Rodin-Miron
 */

@Configuration(proxyBeanMethods = false)
class OAuth2ResourceServerApiKeyConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnMissingBean(ApiKeyIntrospector::class)
    internal class ApiKeyIntrospectionClientConfiguration {
        @Bean
        @ConditionalOnProperty(name = ["spring.security.oauth2.resourceserver.apikey.introspection-uri"])
        fun apiKeyIntrospector(properties: OAuth2ResourceServerApiKeyProperties): ApiKeyIntrospector {
            val apiKey = properties.apiKey
            if(apiKey.clientId != null && apiKey.clientSecret != null) {
                return NimbusApiKeyIntrospector(apiKey.introspectionUri, apiKey.clientId!!,
                    apiKey.clientSecret!!)
            } else {
                throw Exception("Api key clientId/clientSecret cannot be null")
            }
        }
    }

}
