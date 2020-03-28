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

import com.nimbusds.oauth2.sdk.TokenIntrospectionResponse
import com.nimbusds.oauth2.sdk.TokenIntrospectionSuccessResponse
import com.nimbusds.oauth2.sdk.http.HTTPResponse
import org.springframework.core.convert.converter.Converter
import org.springframework.http.*
import org.springframework.http.client.support.BasicAuthenticationInterceptor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionClaimNames
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException
import org.springframework.util.Assert
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.net.URL
import java.util.*
import kotlin.collections.HashMap

/**
 * A Nimbus implementation of [NimbusApiKeyIntrospector] that verifies and introspects
 * a token using the configured
 * [OAuth 2.0 Introspection Endpoint](https://tools.ietf.org/html/rfc7662).
 *
 * @author Josh Cummings
 * @author MD Sayem Ahmed
 * @author Fabrizio Rodin-Miron
 */
class NimbusApiKeyIntrospector(private val introspectionUri: String?, private val clientId: String, private val clientSecret: String, restOperations: RestOperations? = null) : ApiKeyIntrospector {

    private var requestEntityConverter: Converter<String, RequestEntity<*>>
    private val authorityPrefix = "SCOPE_"
    private val restOperations: RestOperations

    init {
        this.restOperations = restOperations ?: initRestOperations()
        this.requestEntityConverter = defaultRequestEntityConverter(URI.create(introspectionUri))
    }

    private fun initRestOperations(): RestOperations {
        val restTemplate = RestTemplate()
        restTemplate.interceptors.add(BasicAuthenticationInterceptor(clientId, clientSecret))
        return restTemplate
    }


    private fun defaultRequestEntityConverter(introspectionUri: URI): Converter<String, RequestEntity<*>> {
        return Converter { token: String ->
            val headers = requestHeaders()
            val body = requestBody(token)
            RequestEntity<Any?>(body, headers, HttpMethod.POST, introspectionUri)
        }
    }

    private fun requestHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        return headers
    }

    private fun requestBody(token: String): HashMap<String, String> {
        val body: HashMap<String, String> = HashMap()
        body["token"] = token
        return body
    }

    /**
     * {@inheritDoc}
     */
    override fun introspect(token: String): OAuth2AuthenticatedPrincipal {
        val requestEntity = requestEntityConverter.convert(token!!)
            ?: throw OAuth2IntrospectionException("requestEntityConverter returned a null entity")
        val responseEntity = makeRequest(requestEntity)
        val httpResponse = adaptToNimbusResponse(responseEntity)
        val introspectionResponse = parseNimbusResponse(httpResponse)
        val introspectionSuccessResponse = castToNimbusSuccess(introspectionResponse)
        // relying solely on the authorization server to validate this token (not checking 'exp', for example)
        if (!introspectionSuccessResponse.isActive) {
            throw OAuth2IntrospectionException("Provided token isn't active")
        }
        return convertClaimsSet(introspectionSuccessResponse)
    }

    /**
     * Sets the [Converter] used for converting the OAuth 2.0 access token to a [RequestEntity]
     * representation of the OAuth 2.0 token introspection request.
     *
     * @param requestEntityConverter the [Converter] used for converting to a [RequestEntity] representation
     * of the token introspection request
     */
    fun setRequestEntityConverter(requestEntityConverter: Converter<String, RequestEntity<*>>) {
        Assert.notNull(requestEntityConverter, "requestEntityConverter cannot be null")
        this.requestEntityConverter = requestEntityConverter
    }

    private fun makeRequest(requestEntity: RequestEntity<*>): ResponseEntity<String> {
        return try {
            restOperations.exchange(requestEntity, String::class.java)
        } catch (ex: Exception) {
            throw OAuth2IntrospectionException(ex.message, ex)
        }
    }

    private fun adaptToNimbusResponse(responseEntity: ResponseEntity<String>): HTTPResponse {
        val response = HTTPResponse(responseEntity.statusCodeValue)
        response.setHeader(HttpHeaders.CONTENT_TYPE, responseEntity.headers.contentType.toString())
        response.content = responseEntity.body
        if (response.statusCode != HTTPResponse.SC_OK) {
            throw OAuth2IntrospectionException(
                "Introspection endpoint responded with " + response.statusCode)
        }
        return response
    }

    private fun parseNimbusResponse(response: HTTPResponse): TokenIntrospectionResponse {
        return try {
            TokenIntrospectionResponse.parse(response)
        } catch (ex: Exception) {
            throw OAuth2IntrospectionException(ex.message, ex)
        }
    }

    private fun castToNimbusSuccess(introspectionResponse: TokenIntrospectionResponse): TokenIntrospectionSuccessResponse {
        if (!introspectionResponse.indicatesSuccess()) {
            throw OAuth2IntrospectionException("Token introspection failed")
        }
        return introspectionResponse as TokenIntrospectionSuccessResponse
    }

    private fun convertClaimsSet(response: TokenIntrospectionSuccessResponse): OAuth2AuthenticatedPrincipal {
        val authorities: MutableCollection<GrantedAuthority> = ArrayList()
        val claims: MutableMap<String, Any> = response.toJSONObject()
        if (response.audience != null) {
            val audiences: MutableList<String> = ArrayList()
            for (audience in response.audience) {
                audiences.add(audience.value)
            }
            claims[OAuth2IntrospectionClaimNames.AUDIENCE] = Collections.unmodifiableList(audiences)
        }
        if (response.clientID != null) {
            claims[OAuth2IntrospectionClaimNames.CLIENT_ID] = response.clientID.value
        }
        if (response.expirationTime != null) {
            val exp = response.expirationTime.toInstant()
            claims[OAuth2IntrospectionClaimNames.EXPIRES_AT] = exp
        }
        if (response.issueTime != null) {
            val iat = response.issueTime.toInstant()
            claims[OAuth2IntrospectionClaimNames.ISSUED_AT] = iat
        }
        if (response.issuer != null) {
            claims[OAuth2IntrospectionClaimNames.ISSUER] = issuer(response.issuer.value)
        }
        if (response.notBeforeTime != null) {
            claims[OAuth2IntrospectionClaimNames.NOT_BEFORE] = response.notBeforeTime.toInstant()
        }
        if (response.scope != null) {
            val scopes = Collections.unmodifiableList(response.scope.toStringList())
            claims[OAuth2IntrospectionClaimNames.SCOPE] = scopes
            for (scope in scopes) {
                authorities.add(SimpleGrantedAuthority(authorityPrefix + scope))
            }
        }
        return DefaultOAuth2AuthenticatedPrincipal(claims, authorities)
    }

    private fun issuer(uri: String): URL {
        return try {
            URL(uri)
        } catch (ex: Exception) {
            throw OAuth2IntrospectionException("Invalid " + OAuth2IntrospectionClaimNames.ISSUER + " value: " + uri)
        }
    }
}
