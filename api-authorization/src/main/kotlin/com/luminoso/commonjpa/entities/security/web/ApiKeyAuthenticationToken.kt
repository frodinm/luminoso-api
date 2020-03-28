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

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.SpringSecurityCoreVersion
import org.springframework.util.Assert

/**
 * An [Authentication] that contains a
 * [Bearer Token](https://tools.ietf.org/html/rfc6750#section-1.2).
 *
 * Used by [ApiKeyAuthenticationFilter] to prepare an authentication attempt and supported
 * by [JwtAuthenticationProvider].
 *
 * @author Josh Cummings
 * @author Fabrizio Rodin-Miron
 */
class ApiKeyAuthenticationToken(token: String) : AbstractAuthenticationToken(emptyList()) {
    /**
     * Get the [Bearer Token](https://tools.ietf.org/html/rfc6750#section-1.2)
     * @return the token that proves the caller's authority to perform the [javax.servlet.http.HttpServletRequest]
     */
    val token: String

    /**
     * {@inheritDoc}
     */
    override fun getCredentials(): Any {
        return token
    }

    /**
     * {@inheritDoc}
     */
    override fun getPrincipal(): Any {
        return token
    }

    companion object {
        private const val serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID
    }

    /**
     * Create a `BearerTokenAuthenticationToken` using the provided parameter(s)
     *
     * @param token - the bearer token
     */
    init {
        Assert.hasText(token, "token cannot be empty")
        this.token = token
    }
}
