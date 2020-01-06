package com.luminoso.authorization.usecases.cookie.impl

import com.luminoso.authorization.repository.cookies.ICookieRepository
import com.luminoso.authorization.usecases.cookie.IAuthenticationCookieUseCase
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationCookieUseCase(private val cookieRepository: ICookieRepository) : IAuthenticationCookieUseCase {

    override fun add(token: DefaultOAuth2AccessToken, response: HttpServletResponse) {
        cookieRepository.saveUserAuthenticationCookie(token, response)
    }

    override fun get(request: HttpServletRequest): Mono<DefaultOAuth2AccessToken?> {
        return cookieRepository.getAuthenticationCookie(request)
    }

    override fun remove(request: HttpServletRequest, response: HttpServletResponse) {
        cookieRepository.removeAuthenticationTokenCookie(request, response)
    }

//    override fun refresh(token: DefaultOAuth2AccessToken, response: HttpServletResponse): Mono<DefaultOAuth2AccessToken> {
//        return tokenProviderUseCase.refreshToken(token).map {
//            cookieRepository.saveUserAuthenticationCookie(it, response)
//            it
//        }
//    }
}