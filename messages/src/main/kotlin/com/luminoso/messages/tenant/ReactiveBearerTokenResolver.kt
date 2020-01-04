package com.luminoso.messages.tenant

import org.springframework.http.server.reactive.ServerHttpRequest

interface ReactiveBearerTokenResolver {
    fun resolve(request: ServerHttpRequest): String?
}