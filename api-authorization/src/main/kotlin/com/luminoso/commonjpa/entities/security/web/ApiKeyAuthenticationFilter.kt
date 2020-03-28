/*
 * Copyright 2002-2020 the original author or authors.
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
package com.luminoso.commonjpa.entities.security.web

import org.springframework.security.authentication.AuthenticationDetailsSource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationManagerResolver
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Authenticates requests that contain an OAuth 2.0
 * [Bearer Token](https://tools.ietf.org/html/rfc6750#section-1.2).
 *
 * This filter should be wired with an [AuthenticationManager] that can authenticate a
 * [ApiKeyAuthenticationToken].
 *
 * @author Josh Cummings
 * @author Vedran Pavic
 * @author Joe Grandja
 * @author Fabrizio Rodin-Miron
 *
 * @see JwtAuthenticationProvider
 */
class ApiKeyAuthenticationFilter(
    private val authenticationManagerResolver: AuthenticationManagerResolver<HttpServletRequest>
): OncePerRequestFilter() {

    private val authenticationDetailsSource: AuthenticationDetailsSource<HttpServletRequest, *> = WebAuthenticationDetailsSource()
    private var apiKeyResolver: ApiKeyResolver = DefaultApiKeyResolver()
    private var authenticationEntryPoint: AuthenticationEntryPoint = ApiKeyAuthenticationEntryPoint()
    private var authenticationFailureHandler = AuthenticationFailureHandler { request: HttpServletRequest?, response: HttpServletResponse?, exception: AuthenticationException? -> authenticationEntryPoint.commence(request, response, exception) }


    /**
     * Extract any [Api key](https://tools.ietf.org/html/rfc6750#section-1.2) from
     * the request and attempt an authentication.
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val debug = logger.isDebugEnabled
        val token: String?
        token = try {
            apiKeyResolver.resolve(request)
        } catch (invalid: OAuth2AuthenticationException) {
            authenticationEntryPoint.commence(request, response, invalid)
            return
        }
        if (token == null) {
            filterChain.doFilter(request, response)
            return
        }
        val authenticationRequest = ApiKeyAuthenticationToken(token)
        authenticationRequest.details = authenticationDetailsSource.buildDetails(request)
        try {
            val authenticationManager = authenticationManagerResolver.resolve(request)
            val authenticationResult = authenticationManager.authenticate(authenticationRequest)
            val context = SecurityContextHolder.createEmptyContext()
            context.authentication = authenticationResult
            SecurityContextHolder.setContext(context)
            filterChain.doFilter(request, response)
        } catch (failed: AuthenticationException) {
            SecurityContextHolder.clearContext()
            if (debug) {
                logger.debug("Authentication request for failed!", failed)
            }
            authenticationFailureHandler.onAuthenticationFailure(request, response, failed)
            throw failed
        }
    }

    /**
     * Set the [ApiKeyResolver] to use. Defaults to [DefaultApiKeyResolver].
     * @param apiKeyResolver the `ApiKeyResolver` to use
     */
    fun setApiKeyResolver(apiKeyResolver: ApiKeyResolver) {
        this.apiKeyResolver = apiKeyResolver
    }

    /**
     * Set the [AuthenticationEntryPoint] to use. Defaults to [ApiKeyAuthenticationEntryPoint].
     * @param authenticationEntryPoint the `AuthenticationEntryPoint` to use
     */
    fun setAuthenticationEntryPoint(authenticationEntryPoint: AuthenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint
    }

    /**
     * Set the [AuthenticationFailureHandler] to use. Default implementation invokes [AuthenticationEntryPoint].
     * @param authenticationFailureHandler the `AuthenticationFailureHandler` to use
     */
    fun setAuthenticationFailureHandler(authenticationFailureHandler: AuthenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler
    }
}
