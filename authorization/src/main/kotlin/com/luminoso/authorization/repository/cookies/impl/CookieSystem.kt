package com.luminoso.authorization.repository.cookies.impl

import com.luminoso.authorization.repository.cookies.ICookieSystem
import org.springframework.stereotype.Component
import org.springframework.util.SerializationUtils
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import reactor.core.scheduler.Schedulers
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

// Schedulers.immediate() was used to guaranty the response is not committed before the cookie has been
// added/updated/removed from the response object

@Component
class CookieSystem : ICookieSystem {

    override fun getCookie(request: HttpServletRequest, name: String): Mono<Cookie> {
        return if (request.cookies != null) {
            Flux.fromArray(request.cookies).filter {
                it.name == name
            }.toMono().subscribeOn(Schedulers.immediate())
        } else {
            Mono.empty()
        }
    }

    override fun addCookie(response: HttpServletResponse, name: String, value: String, maxAge: Int, path: String) {
        Mono.fromCallable {
            val cookie = Cookie(name, value)
            cookie.path = path
            cookie.isHttpOnly = true
            cookie.secure = true
            cookie.maxAge = maxAge
            response.addCookie(cookie)
        }.subscribeOn(Schedulers.immediate()).subscribe()
    }

    override fun deleteCookie(request: HttpServletRequest, response: HttpServletResponse, name: String) {
        Flux.fromArray(request.cookies).map {
            if (it.name == name) {
                it.value = ""
                it.path = "/"
                it.maxAge = 0
                response.addCookie(it)
            }
        }.subscribeOn(Schedulers.immediate()).subscribe()
    }

    override fun serialize(`object`: Any): Mono<String> {
        return Mono.just(Base64.getUrlEncoder()
            .encodeToString(SerializationUtils.serialize(`object`))).subscribeOn(Schedulers.immediate())
    }

    override fun <T> deserialize(cookie: Cookie, cls: Class<T>): Mono<T> {
        return Mono.just(cls.cast(SerializationUtils.deserialize(
            Base64.getUrlDecoder().decode(cookie.value)))).subscribeOn(Schedulers.immediate())
    }

    companion object {
        const val OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request"
        const val REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri"
        const val OAUTH2_USER_AUTHENTICATION_TOKEN = "tk"
    }
}