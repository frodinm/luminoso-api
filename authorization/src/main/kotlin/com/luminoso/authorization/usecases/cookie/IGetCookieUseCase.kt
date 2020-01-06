package com.luminoso.authorization.usecases.cookie

import reactor.core.publisher.Mono
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest

interface IGetCookieUseCase {
    fun getRedirectUri(request: HttpServletRequest): Mono<Cookie>
}