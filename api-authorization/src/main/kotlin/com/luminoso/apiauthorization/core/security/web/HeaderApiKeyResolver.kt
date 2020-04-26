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
package com.luminoso.apiauthorization.core.security.web

import org.springframework.util.Assert
import javax.servlet.http.HttpServletRequest

/**
 * Generic resolver extracting pre-authenticated JWT identity from a custom header.
 *
 * @author Elena Felder
 * @author Fabrizio Rodin-Miron
 */
class HeaderApiKeyResolver(header: String?) : ApiKeyResolver {
    private val header: String?
    override fun resolve(request: HttpServletRequest): String {
        return request.getHeader(header)
    }

    init {
        Assert.hasText(header, "header cannot be empty")
        this.header = header
    }
}
