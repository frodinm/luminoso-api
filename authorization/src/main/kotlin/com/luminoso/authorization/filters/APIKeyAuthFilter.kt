package com.luminoso.authorization.filters

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

class APIKeyAuthFilter(private val principalRequestHeader: String): AbstractPreAuthenticatedProcessingFilter() {
    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): Any {
        return "N/A"
    }

    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): Any {
        return request.getHeader(principalRequestHeader)
    }
}
