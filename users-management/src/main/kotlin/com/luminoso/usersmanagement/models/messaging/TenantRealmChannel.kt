package com.luminoso.usersmanagement.models.messaging

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface TenantRealmChannel {

    companion object {
        const val OUTPUT = "tenant.realm"
    }

    /**
     * @return output channel
     */
    @Output(OUTPUT)
    fun output(): MessageChannel
}