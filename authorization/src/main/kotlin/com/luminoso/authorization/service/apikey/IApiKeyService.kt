package com.luminoso.authorization.service.apikey

import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.TokenRequest

/**
 * @author Fabrizio Rodin-Miron
 */

interface IApiKeyService {

    /**
     * Create an access token associated with the specified credentials.
     * @param authentication The credentials associated with the access token.
     * @return The access token.
     * @throws AuthenticationException If the credentials are inadequate.
     */
    @Throws(AuthenticationException::class)
    fun createApiKey(authentication: OAuth2Authentication): OAuth2AccessToken

    /**
     * Retrieve an access token stored against the provided authentication key, if it exists.
     *
     * @param authentication the authentication key for the access token
     *
     * @return the access token or null if there was none
     */
    fun getApiKeyAccessToken(authentication: OAuth2Authentication): OAuth2AccessToken
}
