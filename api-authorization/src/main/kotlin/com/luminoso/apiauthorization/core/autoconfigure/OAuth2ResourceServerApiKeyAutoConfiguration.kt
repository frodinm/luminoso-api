/*
 * Copyright 2012-2020 the original author or authors.
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

import com.luminoso.apiauthorization.core.autoconfigure.servlet.OAuth2ResourceServerApiKeyConfiguration
import com.luminoso.apiauthorization.core.security.introspection.ApiKeyIntrospector
import com.luminoso.apiauthorization.core.security.web.ApiKeyAuthenticationToken
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * [Auto-configuration][EnableAutoConfiguration] for OAuth api key resource server support.
 *
 * @author Fabrizio Rodin-Miron
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(OAuth2ResourceServerAutoConfiguration::class)
@EnableConfigurationProperties(OAuth2ResourceServerApiKeyProperties::class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class OAuth2ResourceServerApiKeyAutoConfiguration {
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(ApiKeyAuthenticationToken::class, ApiKeyIntrospector::class)
    @Import(OAuth2ResourceServerApiKeyConfiguration::class)
    internal class ApiKeyConfiguration
}
