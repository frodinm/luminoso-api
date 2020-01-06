package com.luminoso.authorization.usecases.security.impl

import com.luminoso.authorization.usecases.security.IAuthenticationInfo
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationInfo: IAuthenticationInfo {
    override fun getAuthentication(): Authentication {
       return SecurityContextHolder.getContext().authentication
    }
}