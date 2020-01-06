package com.luminoso.authorization.repository.cookies.impl

import com.luminoso.authorization.repository.cookies.ICookieRepository
import com.luminoso.authorization.repository.cookies.ICookieSystem
import com.luminoso.authorization.repository.cookies.impl.CookieSystem.Companion.OAUTH2_USER_AUTHENTICATION_TOKEN
import com.luminoso.authorization.repository.cookies.impl.CookieSystem.Companion.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME
import com.luminoso.authorization.repository.cookies.impl.CookieSystem.Companion.REDIRECT_URI_PARAM_COOKIE_NAME
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CookieRepository(private val cookieSystem: ICookieSystem) : ICookieRepository {

    override fun getRedirectUriCookie(request: HttpServletRequest): Mono<Cookie> {
        return cookieSystem.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
    }

    override fun removeAuthorizationRequestCookies(request: HttpServletRequest, response: HttpServletResponse) {
        cookieSystem.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
        cookieSystem.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME)
    }

    // Every resource api route which will require the token
    // will match with this path. The browser will automatically
    // add the httpOnly cookie.
    override fun saveUserAuthenticationCookie(token: DefaultOAuth2AccessToken, response: HttpServletResponse) {
        cookieSystem.serialize(token).map {
            val expiration = getExpiresIn((token.refreshToken as DefaultExpiringOAuth2RefreshToken).expiration)
            cookieSystem.addCookie(response, OAUTH2_USER_AUTHENTICATION_TOKEN, it, expiration, "/")
        }.subscribe()
    }

    override fun getAuthenticationCookie(request: HttpServletRequest): Mono<DefaultOAuth2AccessToken?> {
        return cookieSystem.getCookie(request, OAUTH2_USER_AUTHENTICATION_TOKEN).flatMap {
            if (it != null) {
                cookieSystem.deserialize(it, DefaultOAuth2AccessToken::class.java)
            } else {
                null
            }

        }
    }

    override fun removeAuthenticationTokenCookie(request: HttpServletRequest, response: HttpServletResponse) {
        cookieSystem.deleteCookie(request, response, OAUTH2_USER_AUTHENTICATION_TOKEN)
    }

    private fun getExpiresIn(expiration: Date): Int {
        return java.lang.Long.valueOf((expiration.time - System.currentTimeMillis()) / 1000L)
            .toInt()
    }

}