/*
 * Copyright 2002-2018 the original author or authors.
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

import com.luminoso.apiauthorization.core.security.ApiKeyError
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.server.resource.BearerTokenError
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.util.StringUtils
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * An [AuthenticationEntryPoint] implementation used to commence authentication of protected resource requests
 * using [ApiKeyAuthenticationFilter].
 *
 *
 * Uses information provided by [BearerTokenError] to set HTTP response status code and populate
 * `WWW-Authenticate` HTTP header.
 *
 * @author Vedran Pavic
 * @author Fabrizio Rodin-Miron
 * @see BearerTokenError
 *
 * @see [RFC 6750 Section 3: The WWW-Authenticate
 * Response Header Field](https://tools.ietf.org/html/rfc6750.section-3)
 *
 */
class ApiKeyAuthenticationEntryPoint : AuthenticationEntryPoint {
    private var realmName: String? = null
    /**
     * Collect error details from the provided parameters and format according to
     * RFC 6750, specifically `error`, `error_description`, `error_uri`, and `scope`.
     *
     * @param request       that resulted in an `AuthenticationException`
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     */
    override fun commence(
        request: HttpServletRequest, response: HttpServletResponse,
        authException: AuthenticationException) {
        var status = HttpStatus.UNAUTHORIZED
        val parameters: MutableMap<String, String?> = LinkedHashMap()
        if (realmName != null) {
            parameters["realm"] = realmName
        }
        if (authException is OAuth2AuthenticationException) {
            val error = authException.error
            parameters["error"] = error.errorCode
            if (StringUtils.hasText(error.description)) {
                parameters["error_description"] = error.description
            }
            if (StringUtils.hasText(error.uri)) {
                parameters["error_uri"] = error.uri
            }
            if (error is ApiKeyError) {
                if (StringUtils.hasText(error.scope)) {
                    parameters["scope"] = error.scope
                }
                status = error.httpStatus
            }
        }
        val wwwAuthenticate = computeWWWAuthenticateHeaderValue(parameters)
        response.addHeader(HttpHeaders.WWW_AUTHENTICATE, wwwAuthenticate)
        response.status = status.value()
    }

    /**
     * Set the default realm name to use in the bearer token error response
     *
     * @param realmName
     */
    fun setRealmName(realmName: String?) {
        this.realmName = realmName
    }

    private fun computeWWWAuthenticateHeaderValue(parameters: Map<String, String?>): String {
        val wwwAuthenticate = StringBuilder()
        wwwAuthenticate.append("X-API-KEY")
        if (parameters.isNotEmpty()) {
            wwwAuthenticate.append(" ")
            var i = 0
            for ((key, value) in parameters) {
                wwwAuthenticate.append(key).append("=\"").append(value).append("\"")
                if (i != parameters.size - 1) {
                    wwwAuthenticate.append(", ")
                }
                i++
            }
        }
        return wwwAuthenticate.toString()
    }
}
