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
package com.luminoso.commonjpa.entities.security.access

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.oauth2.server.resource.BearerTokenErrorCodes
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken
import org.springframework.security.web.access.AccessDeniedHandler
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Translates any [AccessDeniedException] into an HTTP response in accordance with
 * [RFC 6750 Section 3: The WWW-Authenticate](https://tools.ietf.org/html/rfc6750#section-3).
 *
 *
 * So long as the class can prove that the request has a valid OAuth 2.0 [Authentication], then will return an
 * [insufficient scope error](https://tools.ietf.org/html/rfc6750#section-3.1); otherwise,
 * it will simply indicate the scheme (Bearer) and any configured realm.
 *
 * @author Josh Cummings
 * @author Fabrizio Rodin-Miron
 */
class ApiKeyAccessDeniedHandler : AccessDeniedHandler {
    private var realmName: String? = null
    /**
     * Collect error details from the provided parameters and format according to
     * RFC 6750, specifically `error`, `error_description`, `error_uri`, and `scope`.
     *
     * @param request               that resulted in an `AccessDeniedException`
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     */
    override fun handle(
        request: HttpServletRequest, response: HttpServletResponse,
        accessDeniedException: AccessDeniedException) {
        val parameters: MutableMap<String, String?> = LinkedHashMap()
        if (realmName != null) {
            parameters["realm"] = realmName
        }
        if (request.userPrincipal is AbstractOAuth2TokenAuthenticationToken<*>) {
            parameters["error"] = BearerTokenErrorCodes.INSUFFICIENT_SCOPE
            parameters["error_description"] = "The request requires higher privileges than provided by the access token."
            parameters["error_uri"] = "https://tools.ietf.org/html/rfc6750#section-3.1"
        }
        val wwwAuthenticate = computeWWWAuthenticateHeaderValue(parameters)
        response.addHeader(HttpHeaders.WWW_AUTHENTICATE, wwwAuthenticate)
        response.status = HttpStatus.FORBIDDEN.value()
    }

    /**
     * Set the default realm name to use in the bearer token error response
     *
     * @param realmName
     */
    fun setRealmName(realmName: String?) {
        this.realmName = realmName
    }

    companion object {
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
}
