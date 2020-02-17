//package com.luminoso.authorization.usecases.security.impl
//
//import com.luminoso.authorization.models.auth.Login
//import com.luminoso.authorization.usecases.security.ITokenProviderUseCase
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.Authentication
//import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
//import org.springframework.security.oauth2.common.OAuth2AccessToken
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration
//import org.springframework.security.oauth2.provider.OAuth2Authentication
//import org.springframework.security.oauth2.provider.OAuth2Request
//import org.springframework.security.oauth2.provider.TokenRequest
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices
//import org.springframework.stereotype.Component
//import reactor.core.publisher.Mono
//import reactor.core.scheduler.Schedulers
//
//@Component
//class TokenProviderUseCase(private val luminosoProperties: LuminosoProperties,
//                           private val authConfig: AuthorizationServerEndpointsConfiguration,
//                           private val authenticationManager: AuthenticationManager,
//                           private val tokenRepository: ITokenRepository) : ITokenProviderUseCase {
//
//    override fun createZucciniWebToken(authentication: Authentication): Mono<OAuth2AccessToken> {
//        return getZucciniWebOauth2Authentication(authentication).map {
//            getTokenService().createAccessToken(it)
//        }
//    }
//
//    override fun createZucciniAdminWebToken(authentication: Authentication): Mono<OAuth2AccessToken> {
//        return getZucciniAdminWebOauth2Authentication(authentication).map {
//            getTokenService().createAccessToken(it)
//        }
//    }
//
//    override fun createDefaultOauth2TokenWithCred(login: Login): Mono<DefaultOAuth2AccessToken> {
//        return Mono.fromCallable {
//            val authReq = UsernamePasswordAuthenticationToken(login.username, login.password)
//
//            val authentication = authenticationManager.authenticate(authReq)
//            getZucciniWebOauth2Authentication(authentication).map {
//                val token = getTokenService().createAccessToken(it)
//                DefaultOAuth2AccessToken(token)
//            }
//        }.flatMap { it }.subscribeOn(Schedulers.elastic())
//    }
//
//    override fun createAdminDashboardDefaultOauth2TokenWithCred(login: Login): Mono<DefaultOAuth2AccessToken> {
//        return Mono.fromCallable {
//            val authReq = UsernamePasswordAuthenticationToken(login.username, login.password)
//
//            val authentication = authenticationManager.authenticate(authReq)
//            getZucciniAdminWebOauth2Authentication(authentication).map {
//                val token = getTokenService().createAccessToken(it)
//                DefaultOAuth2AccessToken(token)
//            }
//        }.flatMap { it }.subscribeOn(Schedulers.elastic())
//    }
//
//    private fun getZucciniWebOauth2Authentication(authentication: Authentication): Mono<OAuth2Authentication> {
//        val config = luminosoProperties.oauth2.web
//
//        return tokenRepository.getClientDetails(config.clientId).map {
//            val requestParameters = HashMap<String, String>()
//            val resourceIds = setOf(it.resourceIds)
//            val responseType = setOf("code")
//
//
//            val oauth2Request = OAuth2Request(requestParameters, config.clientId, authentication.authorities, true, it.getScopes(), resourceIds, it.webServerRedirectUri, responseType, null)
//
//            OAuth2Authentication(oauth2Request, authentication)
//        }
//    }
//
//    private fun getZucciniAdminWebOauth2Authentication(authentication: Authentication): Mono<OAuth2Authentication> {
//        val config = luminosoProperties.oauth2.adminWeb
//        return tokenRepository.getClientDetails(config.clientId).map {
//            val requestParameters = HashMap<String, String>()
//            val resourceIds = setOf(it.resourceIds)
//            val responseType = setOf("code")
//
//            tokenRepository.getClientDetails(config.clientId)
//
//            val oauth2Request = OAuth2Request(requestParameters, config.clientId, authentication.authorities, true, it.getScopes(), resourceIds, it.webServerRedirectUri, responseType, null)
//
//            OAuth2Authentication(oauth2Request, authentication)
//        }
//    }
//
//    override fun refreshToken(token: DefaultOAuth2AccessToken): Mono<DefaultOAuth2AccessToken> {
//        return Mono.fromCallable {
//            val clientId = token.additionalInformation["client_id"] as String
//            val tokenRequest = TokenRequest(emptyMap(), clientId, token.scope, "refresh_token")
//            val newToken = getTokenService().refreshAccessToken(token.refreshToken.value, tokenRequest)
//            DefaultOAuth2AccessToken(newToken)
//        }.subscribeOn(Schedulers.elastic())
//    }
//
//    private fun getTokenService(): AuthorizationServerTokenServices {
//        return authConfig.endpointsConfigurer.tokenServices
//    }
//}
