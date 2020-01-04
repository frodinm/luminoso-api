package com.luminoso.usersmanagement.usecases.messaging

import com.luminoso.usersmanagement.models.messaging.Client
import com.luminoso.usersmanagement.models.messaging.Tenant
import com.luminoso.usersmanagement.models.pojo.representations.idm.ClientRepresentation
import com.luminoso.usersmanagement.models.pojo.representations.idm.RealmRepresentation
import reactor.core.publisher.Mono

interface IMessagingUseCase {
    fun tenantRealmCreated(payload: Tenant): Mono<Tenant>
    fun tenantClientCreated(payload: Client): Mono<Client>
    fun toTenant(realmRepresentation: RealmRepresentation): Tenant
    fun toClient(client: ClientRepresentation, alias: String): Client
}