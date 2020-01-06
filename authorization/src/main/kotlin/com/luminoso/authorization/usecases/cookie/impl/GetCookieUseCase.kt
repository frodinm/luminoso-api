package com.luminoso.authorization.usecases.cookie.impl

import com.luminoso.authorization.repository.cookies.ICookieRepository
import com.luminoso.authorization.usecases.cookie.IGetCookieUseCase
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest

@Component
class GetCookieUseCase(private val cookieRepository: ICookieRepository) : IGetCookieUseCase {
    override fun getRedirectUri(request: HttpServletRequest): Mono<Cookie> {
        return cookieRepository.getRedirectUriCookie(request)
    }
}