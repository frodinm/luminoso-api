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
package com.luminoso.apiauthorization.core.security

/**
 * Standard error codes defined by the OAuth 2.0 Authorization Framework: Bearer Token Usage.
 *
 * @author Fabrizio Rodin-Miron
 * @see [RFC 6750 Section 3.1: Error Codes](https://tools.ietf.org/html/rfc6750.section-3.1)
 */
interface ApiTokenTokenErrorCodes {
    companion object {
        /**
         * `invalid_request` - The request is missing a required parameter, includes an unsupported parameter or
         * parameter value, repeats the same parameter, uses more than one method for including an access token, or is
         * otherwise malformed.
         */
        const val INVALID_REQUEST = "invalid_request"
        /**
         * `invalid_token` - The access token provided is expired, revoked, malformed, or invalid for other
         * reasons.
         */
        const val INVALID_TOKEN = "invalid_token"
        /**
         * `insufficient_scope` - The request requires higher privileges than provided by the access token.
         */
        const val INSUFFICIENT_SCOPE = "insufficient_scope"
    }
}
