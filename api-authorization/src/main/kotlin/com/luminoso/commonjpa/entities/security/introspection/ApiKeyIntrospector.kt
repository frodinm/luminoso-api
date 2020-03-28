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
package com.luminoso.commonjpa.entities.security.introspection

import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal

/**
 * A contract for introspecting and verifying an OAuth 2.0 token.
 *
 * A typical implementation of this interface will make a request to an
 * [OAuth 2.0 Introspection Endpoint](https://tools.ietf.org/html/rfc7662)
 * to verify the token and return its attributes, indicating a successful verification.
 *
 * Another sensible implementation of this interface would be to query a backing store
 * of tokens, for example a distributed cache.
 *
 * @author Josh Cummings
 * @author Fabrizio Rodin-Miron
 */
interface ApiKeyIntrospector {
    /**
     * Introspect and verify the given token, returning its attributes.
     *
     * Returning a [Map] is indicative that the token is valid.
     *
     * @param token the token to introspect
     * @return the token's attributes
     */
    fun introspect(token: String): OAuth2AuthenticatedPrincipal
}
