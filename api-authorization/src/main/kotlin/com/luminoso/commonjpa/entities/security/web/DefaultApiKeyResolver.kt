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

import com.luminoso.apiauthorization.core.security.ApiKeyError
import com.luminoso.apiauthorization.core.security.ApiTokenTokenErrorCodes
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.util.StringUtils
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest

/**
 * The default [ApiKeyResolver] implementation based on RFC 6750.
 *
 * @author Vedran Pavic
 * @author Fabrizio Rodin-Miron
 * @see [RFC 6750 Section 2: Authenticated Requests](https://tools.ietf.org/html/rfc6750.section-2)
 */
class DefaultApiKeyResolver : ApiKeyResolver {
    private var allowFormEncodedBodyParameter = false
    private var allowUriQueryParameter = false
    /**
     * {@inheritDoc}
     */
    override fun resolve(request: HttpServletRequest): String? {
        val authorizationHeaderToken = resolveFromAuthorizationHeader(request)
        if (authorizationHeaderToken != null) {
            return authorizationHeaderToken
        }
        return null
    }

    /**
     * Set if transport of access token using form-encoded body parameter is supported. Defaults to `false`.
     * @param allowFormEncodedBodyParameter if the form-encoded body parameter is supported
     */
    fun setAllowFormEncodedBodyParameter(allowFormEncodedBodyParameter: Boolean) {
        this.allowFormEncodedBodyParameter = allowFormEncodedBodyParameter
    }

    /**
     * Set if transport of access token using URI query parameter is supported. Defaults to `false`.
     *
     * The spec recommends against using this mechanism for sending bearer tokens, and even goes as far as
     * stating that it was only included for completeness.
     *
     * @param allowUriQueryParameter if the URI query parameter is supported
     */
    fun setAllowUriQueryParameter(allowUriQueryParameter: Boolean) {
        this.allowUriQueryParameter = allowUriQueryParameter
    }

    companion object {
        private val authorizationPattern = Pattern.compile(
            "^X-API-KEY (?<token>[a-zA-Z0-9-._~+/]+)=*$",
            Pattern.CASE_INSENSITIVE)

        private fun resolveFromAuthorizationHeader(request: HttpServletRequest): String? {
            val authorization = request.getHeader(HttpHeaders.AUTHORIZATION)
            if (StringUtils.startsWithIgnoreCase(authorization, "x-api-key")) {
                val matcher = authorizationPattern.matcher(authorization)
                if (!matcher.matches()) {
                    val error = ApiKeyError(ApiTokenTokenErrorCodes.INVALID_TOKEN,
                        HttpStatus.UNAUTHORIZED,
                        "Api key is malformed",
                        "https://tools.ietf.org/html/rfc6750#section-3.1")
                    throw OAuth2AuthenticationException(error)
                }
                return matcher.group("token")
            }
            return null
        }
    }
}
