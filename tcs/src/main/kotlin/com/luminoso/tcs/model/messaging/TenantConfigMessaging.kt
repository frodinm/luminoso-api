package com.luminoso.tcs.model.messaging

import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel

interface TenantConfigMessaging {

    companion object {
        const val INPUT = "tenant.config"
        const val OUTPUT = "tenant.config"
    }

    /**
     * @return output channel
     */
    @Output(OUTPUT)
    fun output(): MessageChannel


    /**
     * @return input channel.
     */
    @Input(INPUT)
    fun input(): SubscribableChannel
}