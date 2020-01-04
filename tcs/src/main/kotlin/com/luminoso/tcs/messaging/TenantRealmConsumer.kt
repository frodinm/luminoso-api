package com.luminoso.tcs.messaging

import com.luminoso.tcs.model.messaging.Tenant
import com.luminoso.tcs.model.messaging.TenantRealmChannel
import com.luminoso.tcs.repositories.ITenantIssuerRepository
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.stereotype.Component
import reactor.core.scheduler.Schedulers

@Component
class TenantRealmConsumer(private val tenantIssuerRepository: ITenantIssuerRepository) {

    @StreamListener(TenantRealmChannel.INPUT)
    fun handleTenantRealmCreatedEvent(payload: Tenant){
        println("received realm creation: $payload")
        tenantIssuerRepository.save(payload)
            .subscribeOn(Schedulers.elastic()).subscribe({}, {
                println(it)
            })
    }
}