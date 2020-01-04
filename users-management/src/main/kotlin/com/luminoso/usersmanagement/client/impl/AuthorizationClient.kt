package com.luminoso.usersmanagement.client.impl

import com.luminoso.usersmanagement.client.IAuthorizationClient
import com.luminoso.usersmanagement.properties.EnvProperties
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class AuthorizationClient(val rest: WebClient, val env: EnvProperties): IAuthorizationClient {
    override fun create(uri: String, body: Any): Mono<ClientResponse> {
        return rest.post()
            .uri("${env.authorization.serverUrl}/admin/realms" + uri)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .exchange()
    }
}