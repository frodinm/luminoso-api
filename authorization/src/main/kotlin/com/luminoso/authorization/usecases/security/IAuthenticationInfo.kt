package com.luminoso.authorization.usecases.security

import org.springframework.security.core.Authentication

interface IAuthenticationInfo {
    fun getAuthentication(): Authentication
}