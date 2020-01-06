package com.luminoso.authorization.usecases.cookie.impl

import com.luminoso.authorization.repository.cookies.ICookieRepository
import com.luminoso.authorization.usecases.cookie.IRemoveCookieUseCase
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RemoveCookieUseCase(private val cookieRepository: ICookieRepository) : IRemoveCookieUseCase {
    override fun removeAuthorization(request: HttpServletRequest, response: HttpServletResponse) {
        cookieRepository.removeAuthorizationRequestCookies(request, response)
    }
}