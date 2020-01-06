package com.luminoso.authorization.repository.cookies

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import reactor.core.publisher.Mono
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface ICookieRepository {
    fun getRedirectUriCookie(request: HttpServletRequest): Mono<Cookie>
    fun removeAuthorizationRequestCookies(request: HttpServletRequest, response: HttpServletResponse)
    fun saveUserAuthenticationCookie(token: DefaultOAuth2AccessToken, response: HttpServletResponse)
    fun getAuthenticationCookie(request: HttpServletRequest): Mono<DefaultOAuth2AccessToken?>
    fun removeAuthenticationTokenCookie(request: HttpServletRequest, response: HttpServletResponse)
}