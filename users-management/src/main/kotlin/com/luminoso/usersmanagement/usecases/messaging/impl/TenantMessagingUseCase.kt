package com.luminoso.usersmanagement.usecases.messaging.impl

import com.luminoso.usersmanagement.models.messaging.Client
import com.luminoso.usersmanagement.models.messaging.Tenant
import com.luminoso.usersmanagement.models.messaging.TenantRealmChannel
import com.luminoso.usersmanagement.models.pojo.representations.idm.ClientRepresentation
import com.luminoso.usersmanagement.models.pojo.representations.idm.RealmRepresentation
import com.luminoso.usersmanagement.properties.EnvProperties
import com.luminoso.usersmanagement.usecases.messaging.IMessagingUseCase
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class TenantMessagingUseCase(
    private val env: EnvProperties,
    private val tenantRealmChannel: TenantRealmChannel): IMessagingUseCase {

    override fun tenantRealmCreated(payload: Tenant): Mono<Tenant> {
        return Mono.defer {
            val message = MessageBuilder.withPayload(payload).build()
            tenantRealmChannel.output().send(message)

            Mono.just(payload)
        }
    }

    override fun tenantClientCreated(payload: Client): Mono<Client> {
        return Mono.defer {
            val message = MessageBuilder.withPayload(payload).build()
            tenantRealmChannel.output().send(message)

            Mono.just(payload)
        }
    }

    override fun toClient(client: ClientRepresentation, alias: String): Client {
        val clientId = client.clientId!!
        return Client(alias, "${env.authorization.serverUrl}/realms/$alias",clientId)
    }

    override fun toTenant(realmRepresentation: RealmRepresentation): Tenant {
        val alias = realmRepresentation.realm!!
        return Tenant( alias, "${env.authorization.serverUrl}/realms/${alias}","${env.authorization.serverUrl}/realms/${alias}/protocol/openid-connect/certs" )
    }


}