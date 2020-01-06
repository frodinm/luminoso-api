package com.luminoso.authorization.repository.cookies.impl

import com.luminoso.authorization.repository.cookies.ICookieSystem
import com.luminoso.authorization.repository.cookies.impl.CookieSystem.Companion.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME
import com.luminoso.authorization.repository.cookies.impl.CookieSystem.Companion.REDIRECT_URI_PARAM_COOKIE_NAME
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CookieOAuth2AuthorizationRequestRepository(private val cookieSystem: ICookieSystem) : AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private val cookieExpireSeconds = 180


    override fun loadAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        return cookieSystem.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME).map {
            if (it != null) {
                cookieSystem.deserialize(it, OAuth2AuthorizationRequest::class.java).block()
            } else {
                null
            }
        }.block()
    }

    override fun removeAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        return this.loadAuthorizationRequest(request)
    }

    override fun saveAuthorizationRequest(authorizationRequest: OAuth2AuthorizationRequest?, request: HttpServletRequest, response: HttpServletResponse) {

        if (authorizationRequest == null) {
            cookieSystem.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
            cookieSystem.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME)
            return
        }

        val value = cookieSystem.serialize(authorizationRequest).block()
        if (value != null) {
            cookieSystem.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, value, cookieExpireSeconds)
            val redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME)
            if (redirectUriAfterLogin.isNotBlank()) {
                cookieSystem.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, cookieExpireSeconds)
            }
        }
    }
}