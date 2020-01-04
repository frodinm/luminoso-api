package com.luminoso.usersmanagement.client

import org.springframework.web.reactive.function.client.ClientResponse
import reactor.core.publisher.Mono

interface IAuthorizationClient {
    fun create(uri: String, body: Any): Mono<ClientResponse>
}