/*
 * Copyright 2012-2019 the original author or authors.
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
package com.luminoso.apiauthorization.core.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * OAuth 2.0 api key resource server properties.
 *
 * @author Fabrizio Rodin-Miron
 */
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver")
class OAuth2ResourceServerApiKeyProperties {
    val apiKey = ApiKey()

    class ApiKey {
        /**
         * Client id used to authenticate with the token introspection endpoint.
         */
        var clientId: String? = null

        /**
         * Client secret used to authenticate with the token introspection endpoint.
         */
        var clientSecret: String? = null

        /**
         * OAuth 2.0 endpoint through which token introspection is accomplished.
         */
        var introspectionUri: String? = null

    }
}
