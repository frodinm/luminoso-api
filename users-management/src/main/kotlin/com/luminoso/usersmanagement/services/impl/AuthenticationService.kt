package com.luminoso.usersmanagement.services.impl

import com.luminoso.usersmanagement.client.IAuthorizationClient
import com.luminoso.usersmanagement.models.messaging.Tenant
import com.luminoso.usersmanagement.models.pojo.authentication.SignUp
import com.luminoso.usersmanagement.services.IAuthenticationService
import com.luminoso.usersmanagement.usecases.keycloak.IKeycloakRealmUseCase
import com.luminoso.usersmanagement.usecases.messaging.impl.TenantMessagingUseCase
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientResponse
import reactor.core.publisher.Mono
import java.util.*

@Service
class AuthenticationService(
    private val authorizationClient: IAuthorizationClient,
    private val keycloakRealmUseCase: IKeycloakRealmUseCase,
    private val tenantMessagingUseCase: TenantMessagingUseCase): IAuthenticationService {

    override fun registerTenant(tenant: Tenant, signUp: SignUp): Mono<Tenant> {

        val realmRepresentation = keycloakRealmUseCase.hrTenantRealm(tenant.alias,signUp)

        return authorizationClient.create("/", realmRepresentation)
            .checkpoint()
            .flatMap { response: ClientResponse -> this.ifCreated(response) }
            .flatMap { tenantMessagingUseCase.tenantRealmCreated(tenantMessagingUseCase.toTenant(realmRepresentation)) }
    }

    override fun login() {

    }

    private fun ifCreated(response: ClientResponse): Mono<ClientResponse> {
        return response.bodyToMono<Map<*, *>>(MutableMap::class.java)
            .switchIfEmpty(Mono.just(emptyMap<Any, Any>()))
            .flatMap { map: Map<*, *> ->
                Mono.just(Collections.singletonMap(response.statusCode(), map))
                    .filter { response.statusCode() == HttpStatus.CREATED }
                    .switchIfEmpty(Mono.error { IllegalArgumentException("failed to create: $map") })
                    .map { response }
            }
    }
}