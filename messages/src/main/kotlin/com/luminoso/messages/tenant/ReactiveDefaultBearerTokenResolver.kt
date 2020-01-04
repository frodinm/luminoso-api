package com.luminoso.messages.tenant

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.server.resource.BearerTokenError
import org.springframework.security.oauth2.server.resource.BearerTokenErrorCodes
import org.springframework.util.StringUtils
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * The default [ReactiveBearerTokenResolver] implementation based on RFC 6750.
 *
 * @see [RFC 6750 Section 2: Authenticated Requests](https://tools.ietf.org/html/rfc6750.section-2)
 */
open class ReactiveDefaultBearerTokenResolver : ReactiveBearerTokenResolver {
    private var allowFormEncodedBodyParameter = false
    private var allowUriQueryParameter = false
    /**
     * {@inheritDoc}
     */
    override fun resolve(request: ServerHttpRequest): String? {
        val authorizationHeaderToken = resolveFromAuthorizationHeader(request)
        val parameterToken = resolveFromRequestParameters(request)
        if (authorizationHeaderToken != null) {
            if (parameterToken != null) {
                val error = BearerTokenError(BearerTokenErrorCodes.INVALID_REQUEST,
                    HttpStatus.BAD_REQUEST,
                    "Found multiple bearer tokens in the request",
                    "https://tools.ietf.org/html/rfc6750#section-3.1")
                throw OAuth2AuthenticationException(error)
            }
            return authorizationHeaderToken
        } else if (parameterToken != null && isParameterTokenSupportedForRequest(request)) {
            return parameterToken
        }
        return null
    }

    /**
     * Set if transport of access token using form-encoded body parameter is supported. Defaults to `false`.
     * @param allowFormEncodedBodyParameter if the form-encoded body parameter is supported
     */
    fun setAllowFormEncodedBodyParameter(allowFormEncodedBodyParameter: Boolean) {
        this.allowFormEncodedBodyParameter = allowFormEncodedBodyParameter
    }

    /**
     * Set if transport of access token using URI query parameter is supported. Defaults to `false`.
     *
     * The spec recommends against using this mechanism for sending bearer tokens, and even goes as far as
     * stating that it was only included for completeness.
     *
     * @param allowUriQueryParameter if the URI query parameter is supported
     */
    fun setAllowUriQueryParameter(allowUriQueryParameter: Boolean) {
        this.allowUriQueryParameter = allowUriQueryParameter
    }

    private fun isParameterTokenSupportedForRequest(request: ServerHttpRequest): Boolean {
        return (allowFormEncodedBodyParameter && "POST" == request.methodValue
            || allowUriQueryParameter && "GET" == request.methodValue)
    }

    companion object {
        private val authorizationPattern = Pattern.compile(
            "^Bearer (?<token>[a-zA-Z0-9-._~+/]+)=*$",
            Pattern.CASE_INSENSITIVE)

        private fun resolveFromAuthorizationHeader(request: ServerHttpRequest): String? {
            val authorization = request.headers[HttpHeaders.AUTHORIZATION]?.get(0)
            if(authorization != null){
                if (StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
                    val matcher: Matcher = authorizationPattern.matcher(authorization)
                    if (!matcher.matches()) {
                        val error = BearerTokenError(BearerTokenErrorCodes.INVALID_TOKEN,
                            HttpStatus.UNAUTHORIZED,
                            "Bearer token is malformed",
                            "https://tools.ietf.org/html/rfc6750#section-3.1")
                        throw OAuth2AuthenticationException(error)
                    }
                    return matcher.group("token")
                }
            }

            return null
        }

        private fun resolveFromRequestParameters(request: ServerHttpRequest): String? {
            val values = request.queryParams["access_token"]
            if (values == null || values.size == 0) {
                return null
            }
            if (values.size == 1) {
                return values[0]
            }
            val error = BearerTokenError(BearerTokenErrorCodes.INVALID_REQUEST,
                HttpStatus.BAD_REQUEST,
                "Found multiple bearer tokens in the request",
                "https://tools.ietf.org/html/rfc6750#section-3.1")
            throw OAuth2AuthenticationException(error)
        }
    }
}