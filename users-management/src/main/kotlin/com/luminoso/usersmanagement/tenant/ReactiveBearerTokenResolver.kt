package com.luminoso.usersmanagement.tenant

import org.springframework.http.server.reactive.ServerHttpRequest

interface ReactiveBearerTokenResolver {
    fun resolve(request: ServerHttpRequest): String?
}