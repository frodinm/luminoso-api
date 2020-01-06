package com.luminoso.websitemanagement.messaging.consumer

import com.luminoso.websitemanagement.messaging.channels.TenantCreatedChannel
import com.luminoso.websitemanagement.models.messaging.Tenant
import com.luminoso.websitemanagement.services.ICreateTenantWebsiteService
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.stereotype.Component

@Component
class TenantCreatedConsumer(
    private val createTenantWebsiteService: ICreateTenantWebsiteService) {

    @StreamListener(TenantCreatedChannel.INPUT)
    fun handleTenantRealmCreatedEvent(payload: Tenant){
        println("received realm creation: $payload")

        createTenantWebsiteService.createWebsite(payload.alias)
    }
}