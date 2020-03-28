/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.luminoso.commonjpa.entities.security.resource

import com.luminoso.apiauthorization.core.security.access.ApiKeyAccessDeniedHandler
import com.luminoso.apiauthorization.core.security.introspection.ApiKeyIntrospector
import com.luminoso.apiauthorization.core.security.introspection.NimbusApiKeyIntrospector
import com.luminoso.apiauthorization.core.security.web.ApiKeyAuthenticationEntryPoint
import com.luminoso.apiauthorization.core.security.web.ApiKeyAuthenticationFilter
import com.luminoso.apiauthorization.core.security.web.ApiKeyResolver
import com.luminoso.apiauthorization.core.security.web.DefaultApiKeyResolver
import org.springframework.context.ApplicationContext
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationManagerResolver
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.HttpSecurityBuilder
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.util.Assert
import java.util.function.Supplier
import javax.servlet.http.HttpServletRequest

/**
 *
 * An [AbstractHttpConfigurer] for OAuth 2.0 Resource Server Support.
 *
 * By default, this wires a [ApiKeyAuthenticationFilter], which can be used to parse the request
 * for api keys and make an authentication attempt.
 *
 *
 *
 * The following configuration options are available:
 *
 *
 *  * [.accessDeniedHandler] - customizes how access denied errors are handled
 *  * [.authenticationEntryPoint] - customizes how authentication failures are handled
 *  * [.apiKeyResolver] - customizes how to resolve a api key from the request
 *  * [.apiKey] - enables api key support
 *
 *
 *
 *
 * When using [.apiKey] )}, supply an introspection endpoint and its authentication configuration
 *
 *
 * <h2>Security Filters</h2>
 *
 * The following `Filter`s are populated when [.apiKey] is configured:
 *
 *
 *  * [ApiKeyAuthenticationFilter]
 *
 *
 * <h2>Shared Objects Created</h2>
 *
 * The following shared objects are populated:
 *
 *
 *  * [SessionCreationPolicy] (optional)
 *
 *
 * <h2>Shared Objects Used</h2>
 *
 * The following shared objects are used:
 *
 *
 *  * [AuthenticationManager]
 *
 *
 * @author Josh Cummings
 * @author Fabrizio Rodin-Miron
 * @see ApiKeyAuthenticationFilter
 *
 * @see JwtAuthenticationProvider
 *
 * @see NimbusJwtDecoder
 *
 * @see AbstractHttpConfigurer
 */
class OAuth2ApiKeyResourceServerConfigurer<H : HttpSecurityBuilder<H>>(private val context: ApplicationContext) : AbstractHttpConfigurer<OAuth2ApiKeyResourceServerConfigurer<H>, H>() {
    private var authenticationManagerResolver: AuthenticationManagerResolver<HttpServletRequest>? = null
    private var apiKeyResolver: ApiKeyResolver? = null
    private var apiKeyConfigurer: ApiKeyConfigurer? = null
    private var accessDeniedHandler: AccessDeniedHandler = ApiKeyAccessDeniedHandler()
    private var authenticationEntryPoint: AuthenticationEntryPoint = ApiKeyAuthenticationEntryPoint()
    private val requestMatcher = ApiKeyRequestMatcher()
    fun accessDeniedHandler(accessDeniedHandler: AccessDeniedHandler): OAuth2ApiKeyResourceServerConfigurer<H> {
        this.accessDeniedHandler = accessDeniedHandler
        return this
    }

    fun authenticationEntryPoint(entryPoint: AuthenticationEntryPoint): OAuth2ApiKeyResourceServerConfigurer<H> {
        authenticationEntryPoint = entryPoint
        return this
    }

    fun authenticationManagerResolver(authenticationManagerResolver: AuthenticationManagerResolver<HttpServletRequest>): OAuth2ApiKeyResourceServerConfigurer<H> {
        this.authenticationManagerResolver = authenticationManagerResolver
        return this
    }

    fun apiKeyResolver(apiKeyResolver: ApiKeyResolver): OAuth2ApiKeyResourceServerConfigurer<H> {
        this.apiKeyResolver = apiKeyResolver
        return this
    }

    /**
     * Enables Api key token support.
     *
     * @param apiKeyCustomizer the [Customizer] to provide more options for
     * the [OAuth2ResourceServerConfigurer.JwtConfigurer]
     * @return the [OAuth2ResourceServerConfigurer] for further customizations
     */
    fun apiKey(apiKeyCustomizer: Customizer<ApiKeyConfigurer?>): OAuth2ApiKeyResourceServerConfigurer<H> {
        if (apiKeyConfigurer == null) {
            apiKeyConfigurer = ApiKeyConfigurer(context!!)
        }
        apiKeyCustomizer.customize(apiKeyConfigurer)
        return this
    }

    override fun init(http: H) {
        registerDefaultAccessDeniedHandler(http)
        registerDefaultEntryPoint(http)
    }

    override fun configure(http: H) {
        val apiKeyResolver = getApiKeyResolver()
        requestMatcher.setApiKeyResolver(apiKeyResolver)

        val apiKeyConfigurer = ApiKeyConfigurer(context!!)
        this.apiKeyConfigurer = apiKeyConfigurer

        validateConfiguration()

        var resolver: AuthenticationManagerResolver<*>? = authenticationManagerResolver
        if (resolver == null) {
            val authenticationManager = getAuthenticationManager(http)
            resolver = AuthenticationManagerResolver { request: Any? -> authenticationManager }
        }
        var filter = ApiKeyAuthenticationFilter(resolver as AuthenticationManagerResolver<HttpServletRequest>)
        filter.setApiKeyResolver(apiKeyResolver)
        filter.setAuthenticationEntryPoint(authenticationEntryPoint)
        filter = postProcess(filter)
        http.addFilterAfter(filter, BearerTokenAuthenticationFilter::class.java)
    }

    private fun validateConfiguration() {
        if (authenticationManagerResolver == null) {
            checkNotNull(apiKeyResolver) { "Enable/Configure api key with http.oauth2ApiKeyResourceServer().apiKey()." }
        }
    }

    inner class ApiKeyConfigurer internal constructor(private val context: ApplicationContext) {
        private var authenticationManager: AuthenticationManager? = null
        private var introspectionUri: String? = null
        private var clientId: String? = null
        private var clientSecret: String? = null
        private var introspector: Supplier<ApiKeyIntrospector>? = null

        fun authenticationManager(authenticationManager: AuthenticationManager): ApiKeyConfigurer {
            this.authenticationManager = authenticationManager
            return this
        }

        fun introspectionUri(introspectionUri: String): ApiKeyConfigurer {
            this.introspectionUri = introspectionUri
            introspector = Supplier { NimbusApiKeyIntrospector(this.introspectionUri, clientId!!, clientSecret!!) }
            return this
        }

        fun introspectionClientCredentials(clientId: String, clientSecret: String): ApiKeyConfigurer {
            this.clientId = clientId
            this.clientSecret = clientSecret
            introspector = Supplier { NimbusApiKeyIntrospector(introspectionUri, this.clientId!!, this.clientSecret!!) }
            return this
        }

        fun introspector(introspector: ApiKeyIntrospector): ApiKeyConfigurer {
            this.introspector = Supplier { introspector }
            return this
        }

         fun getIntrospector(): ApiKeyIntrospector {
            return if (introspector != null) {
                introspector!!.get()
            } else this.context.getBean(ApiKeyIntrospector::class.java)
        }

        fun getAuthenticationManager(http: H): AuthenticationManager? {
            if (authenticationManager != null) {
                return authenticationManager
            }
            val introspector = getIntrospector()
            val provider = ApiKeyAuthenticationProvider(introspector)
            http.authenticationProvider(provider)
            return http.getSharedObject(AuthenticationManager::class.java)
        }

    }

    private fun registerDefaultAccessDeniedHandler(http: H) {
        val exceptionHandling: ExceptionHandlingConfigurer<H> = http
            .getConfigurer(ExceptionHandlingConfigurer::class.java as Class<ExceptionHandlingConfigurer<H>>)
            ?: return
        exceptionHandling.defaultAccessDeniedHandlerFor(
            accessDeniedHandler,
            requestMatcher)
    }

    private fun registerDefaultEntryPoint(http: H) {
        val exceptionHandling: ExceptionHandlingConfigurer<*> = http
            .getConfigurer(ExceptionHandlingConfigurer::class.java as Class<ExceptionHandlingConfigurer<H>>)
            ?: return
        exceptionHandling.defaultAuthenticationEntryPointFor(
            authenticationEntryPoint,
            requestMatcher)
    }

    private fun getAuthenticationManager(http: H): AuthenticationManager {
         if (apiKeyConfigurer != null) {
             return  apiKeyConfigurer!!.getAuthenticationManager(http)!!
        }
        return http.getSharedObject(AuthenticationManager::class.java)
    }

    private fun getApiKeyResolver(): ApiKeyResolver {
        if (apiKeyResolver == null) {
            apiKeyResolver = DefaultApiKeyResolver()
        }
        return apiKeyResolver!!
    }

    private class ApiKeyRequestMatcher : RequestMatcher {
        private var apiKeyResolver: ApiKeyResolver? = null
        override fun matches(request: HttpServletRequest): Boolean {
            return try {
                apiKeyResolver!!.resolve(request) != null
            } catch (e: OAuth2AuthenticationException) {
                false
            }
        }

        fun setApiKeyResolver(tokenResolver: ApiKeyResolver) {
            apiKeyResolver = tokenResolver
        }
    }
}
