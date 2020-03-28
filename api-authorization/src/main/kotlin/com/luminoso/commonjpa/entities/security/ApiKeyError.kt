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
package com.luminoso.commonjpa.entities.security

import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.util.Assert

/**
 * A representation of a [Bearer Token Error](https://tools.ietf.org/html/rfc6750#section-3.1).
 *
 * @author Fabrizio Rodin-Miron
 * @see ApiTokenTokenErrorCodes
 *
 * @see [RFC 6750 Section 3: The WWW-Authenticate
 * Response Header Field](https://tools.ietf.org/html/rfc6750.section-3)
 */
class ApiKeyError @JvmOverloads constructor(errorCode: String, httpStatus: HttpStatus, description: String? = null, errorUri: String? = null, scope: String? = null) : OAuth2Error(errorCode, description, errorUri) {
    /**
     * Return the HTTP status.
     * @return the HTTP status
     */
    val httpStatus: HttpStatus
    /**
     * Return the scope.
     * @return the scope
     */
    val scope: String?

    companion object {
        private fun isDescriptionValid(description: String?): Boolean {
            return description == null ||
                description.chars().allMatch { c: Int ->
                    withinTheRangeOf(c, 0x20, 0x21) ||
                        withinTheRangeOf(c, 0x23, 0x5B) ||
                        withinTheRangeOf(c, 0x5D, 0x7E)
                }
        }

        private fun isErrorCodeValid(errorCode: String): Boolean {
            return errorCode.chars().allMatch { c: Int ->
                withinTheRangeOf(c, 0x20, 0x21) ||
                    withinTheRangeOf(c, 0x23, 0x5B) ||
                    withinTheRangeOf(c, 0x5D, 0x7E)
            }
        }

        private fun isErrorUriValid(errorUri: String?): Boolean {
            return errorUri == null ||
                errorUri.chars().allMatch { c: Int ->
                    c == 0x21 ||
                        withinTheRangeOf(c, 0x23, 0x5B) ||
                        withinTheRangeOf(c, 0x5D, 0x7E)
                }
        }

        private fun isScopeValid(scope: String?): Boolean {
            return scope == null ||
                scope.chars().allMatch { c: Int ->
                    withinTheRangeOf(c, 0x20, 0x21) ||
                        withinTheRangeOf(c, 0x23, 0x5B) ||
                        withinTheRangeOf(c, 0x5D, 0x7E)
                }
        }

        private fun withinTheRangeOf(c: Int, min: Int, max: Int): Boolean {
            return c >= min && c <= max
        }
    }
    /**
     * Create a `BearerTokenError` using the provided parameters
     *
     * @param errorCode the error code
     * @param httpStatus the HTTP status
     * @param description the description
     * @param errorUri the URI
     * @param scope the scope
     */
    /**
     * Create a `BearerTokenError` using the provided parameters
     *
     * @param errorCode the error code
     * @param httpStatus the HTTP status
     */
    init {
        Assert.notNull(httpStatus, "httpStatus cannot be null")
        Assert.isTrue(isDescriptionValid(description),
            "description contains invalid ASCII characters, it must conform to RFC 6750")
        Assert.isTrue(isErrorCodeValid(errorCode),
            "errorCode contains invalid ASCII characters, it must conform to RFC 6750")
        Assert.isTrue(isErrorUriValid(errorUri),
            "errorUri contains invalid ASCII characters, it must conform to RFC 6750")
        Assert.isTrue(isScopeValid(scope),
            "scope contains invalid ASCII characters, it must conform to RFC 6750")
        this.httpStatus = httpStatus
        this.scope = scope
    }
}
