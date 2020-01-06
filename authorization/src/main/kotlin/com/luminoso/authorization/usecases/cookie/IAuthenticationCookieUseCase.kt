package com.luminoso.authorization.usecases.cookie

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import reactor.core.publisher.Mono
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface IAuthenticationCookieUseCase {
    fun add(token: DefaultOAuth2AccessToken, response: HttpServletResponse)
    fun get(request: HttpServletRequest): Mono<DefaultOAuth2AccessToken?>
    fun remove(request: HttpServletRequest, response: HttpServletResponse)
}