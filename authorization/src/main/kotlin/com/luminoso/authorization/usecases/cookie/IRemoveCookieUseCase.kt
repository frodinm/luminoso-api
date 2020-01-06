package com.luminoso.authorization.usecases.cookie

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface IRemoveCookieUseCase {
    fun removeAuthorization(request: HttpServletRequest, response: HttpServletResponse)
}