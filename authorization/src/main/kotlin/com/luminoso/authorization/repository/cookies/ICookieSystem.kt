package com.luminoso.authorization.repository.cookies

import reactor.core.publisher.Mono
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface ICookieSystem {
    fun getCookie(request: HttpServletRequest, name: String): Mono<Cookie>
    fun addCookie(response: HttpServletResponse, name: String, value: String, maxAge: Int, path: String = "/")
    fun deleteCookie(request: HttpServletRequest, response: HttpServletResponse, name: String)
    fun serialize(`object`: Any): Mono<String>
    fun <T> deserialize(cookie: Cookie, cls: Class<T>): Mono<T>
}