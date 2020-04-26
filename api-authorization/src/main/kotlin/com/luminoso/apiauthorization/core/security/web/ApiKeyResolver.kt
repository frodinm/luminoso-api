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
package com.luminoso.apiauthorization.core.security.web

import javax.servlet.http.HttpServletRequest

/**
 * A strategy for resolving [Bearer Token](https://tools.ietf.org/html/rfc6750#section-1.2)s
 * from the [HttpServletRequest].
 *
 * @author Vedran Pavic
 * @author Fabrizio Rodin-Miron
 * @see [RFC 6750 Section 2: Authenticated Requests](https://tools.ietf.org/html/rfc6750.section-2)
 */
interface ApiKeyResolver {
    /**
     * Resolve any [Bearer Token](https://tools.ietf.org/html/rfc6750#section-1.2)
     * value from the request.
     *
     * @param request the request
     * @return the Bearer Token value or `null` if none found
     * @throws OAuth2AuthenticationException if the found token is invalid
     */
    fun resolve(request: HttpServletRequest): String?
}
