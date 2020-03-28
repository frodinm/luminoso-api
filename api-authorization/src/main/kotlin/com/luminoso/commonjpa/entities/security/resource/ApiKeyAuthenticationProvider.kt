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

import com.luminoso.apiauthorization.core.security.ApiKeyError
import com.luminoso.apiauthorization.core.security.introspection.ApiKeyIntrospector
import com.luminoso.apiauthorization.core.security.web.ApiKeyAuthenticationToken
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionClaimNames
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException
import java.time.Instant

/**
 * An [AuthenticationProvider] implementation for api key tokens
 * using an
 * [OAuth 2.0 Introspection Endpoint](https://tools.ietf.org/html/rfc7662)
 * to check the token's validity and reveal its attributes.
 *
 *
 * This [AuthenticationProvider] is responsible for introspecting and verifying an opaque access token,
 * returning its attributes set as part of the [Authentication] statement.
 *
 *
 * Scopes are translated into [GrantedAuthority]s according to the following algorithm:
 *
 *  1.
 * If there is a "scope" attribute, then convert to a [Collection] of [String]s.
 *  1.
 * Take the resulting [Collection] and prepend the "SCOPE_" keyword to each element, adding as [GrantedAuthority]s.
 *
 *
 * @author Josh Cummings
 * @author Fabrizio Rodin-Miron
 * @see AuthenticationProvider
 */
class ApiKeyAuthenticationProvider(private val introspector: ApiKeyIntrospector) : AuthenticationProvider {
    /**
     * Introspect and validate the api key
     *
     * @param authentication the authentication request object.
     *
     * @return A successful authentication
     * @throws AuthenticationException if authentication failed for some reason
     */
    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        if (authentication !is ApiKeyAuthenticationToken) {
            return null
        }
        val bearer = authentication
        val principal: OAuth2AuthenticatedPrincipal
        principal = try {
            introspector.introspect(bearer.token)
        } catch (failed: OAuth2IntrospectionException) {
            val invalidToken: OAuth2Error = invalidToken(failed.message)
            throw OAuth2AuthenticationException(invalidToken)
        }
        val result = convert(principal, bearer.token)
        result.details = bearer.details
        return result
    }

    /**
     * {@inheritDoc}
     */
    override fun supports(authentication: Class<*>?): Boolean {
        return ApiKeyAuthenticationToken::class.java.isAssignableFrom(authentication)
    }

    private fun convert(principal: OAuth2AuthenticatedPrincipal, token: String): AbstractAuthenticationToken {
        val iat = principal.getAttribute<Instant>(OAuth2IntrospectionClaimNames.ISSUED_AT)
        val exp = principal.getAttribute<Instant>(OAuth2IntrospectionClaimNames.EXPIRES_AT)
        val accessToken = OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
            token, iat, exp)
        return BearerTokenAuthentication(principal, accessToken, principal.authorities)
    }

    companion object {
        private val DEFAULT_INVALID_TOKEN = invalidToken("An error occurred while attempting to introspect the token: Invalid token")
        private fun invalidToken(message: String?): ApiKeyError {
            return try {
                ApiKeyError("invalid_token",
                    HttpStatus.UNAUTHORIZED, message,
                    "https://tools.ietf.org/html/rfc7662#section-2.2")
            } catch (malformed: IllegalArgumentException) {
                // some third-party library error messages are not suitable for RFC 6750's error message charset
                DEFAULT_INVALID_TOKEN
            }
        }
    }
}
