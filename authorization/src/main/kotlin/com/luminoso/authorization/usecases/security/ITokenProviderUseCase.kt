package com.luminoso.authorization.usecases.security

import com.luminoso.authorization.models.auth.Login
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import reactor.core.publisher.Mono

interface ITokenProviderUseCase {
    fun createZucciniWebToken(authentication: Authentication): Mono<OAuth2AccessToken>
    fun createZucciniAdminWebToken(authentication: Authentication): Mono<OAuth2AccessToken>
    fun createDefaultOauth2TokenWithCred(login: Login): Mono<DefaultOAuth2AccessToken>
    fun createAdminDashboardDefaultOauth2TokenWithCred(login: Login): Mono<DefaultOAuth2AccessToken>
    fun refreshToken(token: DefaultOAuth2AccessToken): Mono<DefaultOAuth2AccessToken>
}
