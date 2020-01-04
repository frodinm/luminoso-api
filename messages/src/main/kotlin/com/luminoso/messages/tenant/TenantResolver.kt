package com.luminoso.messages.tenant

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken

object TenantResolver {
    fun resolve(): String? {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication is AbstractOAuth2TokenAuthenticationToken<*>) {
            return authentication.tokenAttributes["tenant_id"] as String?
        }
        return "all"
    }
}