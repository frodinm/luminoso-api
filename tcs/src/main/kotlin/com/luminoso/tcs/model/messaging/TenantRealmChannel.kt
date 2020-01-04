package com.luminoso.tcs.model.messaging

import org.springframework.cloud.stream.annotation.Input
import org.springframework.messaging.SubscribableChannel

interface TenantRealmChannel {

    companion object {
        const val INPUT = "tenant.realm"
    }

    /**
     * @return input channel.
     */
    @Input(INPUT)
    fun input(): SubscribableChannel
}